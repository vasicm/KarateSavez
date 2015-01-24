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
import karatesavez.jpa.entity.Trener;

/**
 *
 * @author Marko
 */
public class TrenerJpaController implements Serializable {

    public TrenerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trener trener) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Clan clanOrphanCheck = trener.getClan();
        if (clanOrphanCheck != null) {
            Trener oldTrenerOfClan = clanOrphanCheck.getTrener();
            if (oldTrenerOfClan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Clan " + clanOrphanCheck + " already has an item of type Trener whose clan column cannot be null. Please make another selection for the clan field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clan clan = trener.getClan();
            if (clan != null) {
                clan = em.getReference(clan.getClass(), clan.getJmb());
                trener.setClan(clan);
            }
            em.persist(trener);
            if (clan != null) {
                clan.setTrener(trener);
                clan = em.merge(clan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrener(trener.getJmb()) != null) {
                throw new PreexistingEntityException("Trener " + trener + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trener trener) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trener persistentTrener = em.find(Trener.class, trener.getJmb());
            Clan clanOld = persistentTrener.getClan();
            Clan clanNew = trener.getClan();
            List<String> illegalOrphanMessages = null;
            if (clanNew != null && !clanNew.equals(clanOld)) {
                Trener oldTrenerOfClan = clanNew.getTrener();
                if (oldTrenerOfClan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Clan " + clanNew + " already has an item of type Trener whose clan column cannot be null. Please make another selection for the clan field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clanNew != null) {
                clanNew = em.getReference(clanNew.getClass(), clanNew.getJmb());
                trener.setClan(clanNew);
            }
            trener = em.merge(trener);
            if (clanOld != null && !clanOld.equals(clanNew)) {
                clanOld.setTrener(null);
                clanOld = em.merge(clanOld);
            }
            if (clanNew != null && !clanNew.equals(clanOld)) {
                clanNew.setTrener(trener);
                clanNew = em.merge(clanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = trener.getJmb();
                if (findTrener(id) == null) {
                    throw new NonexistentEntityException("The trener with id " + id + " no longer exists.");
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
            Trener trener;
            try {
                trener = em.getReference(Trener.class, id);
                trener.getJmb();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trener with id " + id + " no longer exists.", enfe);
            }
            Clan clan = trener.getClan();
            if (clan != null) {
                clan.setTrener(null);
                clan = em.merge(clan);
            }
            em.remove(trener);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trener> findTrenerEntities() {
        return findTrenerEntities(true, -1, -1);
    }

    public List<Trener> findTrenerEntities(int maxResults, int firstResult) {
        return findTrenerEntities(false, maxResults, firstResult);
    }

    private List<Trener> findTrenerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trener.class));
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

    public Trener findTrener(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trener.class, id);
        } finally {
            em.close();
        }
    }

    public int getTrenerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trener> rt = cq.from(Trener.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
