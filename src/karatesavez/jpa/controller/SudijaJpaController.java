/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import karatesavez.jpa.entity.Clan;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Sudija;

/**
 *
 * @author Marko
 */
public class SudijaJpaController implements Serializable {

    public SudijaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sudija sudija) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Clan clanOrphanCheck = sudija.getClan();
        if (clanOrphanCheck != null) {
            Sudija oldSudijaOfClan = clanOrphanCheck.getSudija();
            if (oldSudijaOfClan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Clan " + clanOrphanCheck + " already has an item of type Sudija whose clan column cannot be null. Please make another selection for the clan field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clan clan = sudija.getClan();
            if (clan != null) {
                clan = em.getReference(clan.getClass(), clan.getJmb());
                sudija.setClan(clan);
            }
            em.persist(sudija);
            if (clan != null) {
                clan.setSudija(sudija);
                clan = em.merge(clan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSudija(sudija.getJmb()) != null) {
                throw new PreexistingEntityException("Sudija " + sudija + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sudija sudija) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sudija persistentSudija = em.find(Sudija.class, sudija.getJmb());
            Clan clanOld = persistentSudija.getClan();
            Clan clanNew = sudija.getClan();
            List<String> illegalOrphanMessages = null;
            if (clanNew != null && !clanNew.equals(clanOld)) {
                Sudija oldSudijaOfClan = clanNew.getSudija();
                if (oldSudijaOfClan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Clan " + clanNew + " already has an item of type Sudija whose clan column cannot be null. Please make another selection for the clan field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clanNew != null) {
                clanNew = em.getReference(clanNew.getClass(), clanNew.getJmb());
                sudija.setClan(clanNew);
            }
            sudija = em.merge(sudija);
            if (clanOld != null && !clanOld.equals(clanNew)) {
                clanOld.setSudija(null);
                clanOld = em.merge(clanOld);
            }
            if (clanNew != null && !clanNew.equals(clanOld)) {
                clanNew.setSudija(sudija);
                clanNew = em.merge(clanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = sudija.getJmb();
                if (findSudija(id) == null) {
                    throw new NonexistentEntityException("The sudija with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sudija sudija;
            try {
                sudija = em.getReference(Sudija.class, id);
                sudija.getJmb();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sudija with id " + id + " no longer exists.", enfe);
            }
            Clan clan = sudija.getClan();
            if (clan != null) {
                clan.setSudija(null);
                clan = em.merge(clan);
            }
            em.remove(sudija);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Sudija> findSudijaEntities() {
        return findSudijaEntities(true, -1, -1);
    }

    public List<Sudija> findSudijaEntities(int maxResults, int firstResult) {
        return findSudijaEntities(false, maxResults, firstResult);
    }

    private List<Sudija> findSudijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sudija.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Sudija findSudija(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sudija.class, id);
        } finally {
            em.close();
        }
    }

    public int getSudijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sudija> rt = cq.from(Sudija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
