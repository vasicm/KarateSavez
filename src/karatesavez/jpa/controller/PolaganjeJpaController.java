/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Ispit;
import karatesavez.jpa.entity.Clan;
import karatesavez.jpa.entity.Polaganje;
import karatesavez.jpa.entity.PolaganjePK;

/**
 *
 * @author Marko
 */
public class PolaganjeJpaController implements Serializable {

    public PolaganjeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Polaganje polaganje) throws PreexistingEntityException, Exception {
        if (polaganje.getPolaganjePK() == null) {
            polaganje.setPolaganjePK(new PolaganjePK());
        }
        polaganje.getPolaganjePK().setIDKluba(polaganje.getIspit().getIspitPK().getIDKluba());
        polaganje.getPolaganjePK().setDatumIVrijeme(polaganje.getIspit().getIspitPK().getDatumIVrijeme());
        polaganje.getPolaganjePK().setJmb(polaganje.getClan().getJmb());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ispit ispit = polaganje.getIspit();
            if (ispit != null) {
                ispit = em.getReference(ispit.getClass(), ispit.getIspitPK());
                polaganje.setIspit(ispit);
            }
            Clan clan = polaganje.getClan();
            if (clan != null) {
                clan = em.getReference(clan.getClass(), clan.getJmb());
                polaganje.setClan(clan);
            }
            em.persist(polaganje);
            if (ispit != null) {
                ispit.getPolaganjeCollection().add(polaganje);
                ispit = em.merge(ispit);
            }
            if (clan != null) {
                clan.getPolaganjeCollection().add(polaganje);
                clan = em.merge(clan);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPolaganje(polaganje.getPolaganjePK()) != null) {
                throw new PreexistingEntityException("Polaganje " + polaganje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Polaganje polaganje) throws NonexistentEntityException, Exception {
        polaganje.getPolaganjePK().setIDKluba(polaganje.getIspit().getIspitPK().getIDKluba());
        polaganje.getPolaganjePK().setDatumIVrijeme(polaganje.getIspit().getIspitPK().getDatumIVrijeme());
        polaganje.getPolaganjePK().setJmb(polaganje.getClan().getJmb());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Polaganje persistentPolaganje = em.find(Polaganje.class, polaganje.getPolaganjePK());
            Ispit ispitOld = persistentPolaganje.getIspit();
            Ispit ispitNew = polaganje.getIspit();
            Clan clanOld = persistentPolaganje.getClan();
            Clan clanNew = polaganje.getClan();
            if (ispitNew != null) {
                ispitNew = em.getReference(ispitNew.getClass(), ispitNew.getIspitPK());
                polaganje.setIspit(ispitNew);
            }
            if (clanNew != null) {
                clanNew = em.getReference(clanNew.getClass(), clanNew.getJmb());
                polaganje.setClan(clanNew);
            }
            polaganje = em.merge(polaganje);
            if (ispitOld != null && !ispitOld.equals(ispitNew)) {
                ispitOld.getPolaganjeCollection().remove(polaganje);
                ispitOld = em.merge(ispitOld);
            }
            if (ispitNew != null && !ispitNew.equals(ispitOld)) {
                ispitNew.getPolaganjeCollection().add(polaganje);
                ispitNew = em.merge(ispitNew);
            }
            if (clanOld != null && !clanOld.equals(clanNew)) {
                clanOld.getPolaganjeCollection().remove(polaganje);
                clanOld = em.merge(clanOld);
            }
            if (clanNew != null && !clanNew.equals(clanOld)) {
                clanNew.getPolaganjeCollection().add(polaganje);
                clanNew = em.merge(clanNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PolaganjePK id = polaganje.getPolaganjePK();
                if (findPolaganje(id) == null) {
                    throw new NonexistentEntityException("The polaganje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PolaganjePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Polaganje polaganje;
            try {
                polaganje = em.getReference(Polaganje.class, id);
                polaganje.getPolaganjePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The polaganje with id " + id + " no longer exists.", enfe);
            }
            Ispit ispit = polaganje.getIspit();
            if (ispit != null) {
                ispit.getPolaganjeCollection().remove(polaganje);
                ispit = em.merge(ispit);
            }
            Clan clan = polaganje.getClan();
            if (clan != null) {
                clan.getPolaganjeCollection().remove(polaganje);
                clan = em.merge(clan);
            }
            em.remove(polaganje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Polaganje> findPolaganjeEntities() {
        return findPolaganjeEntities(true, -1, -1);
    }

    public List<Polaganje> findPolaganjeEntities(int maxResults, int firstResult) {
        return findPolaganjeEntities(false, maxResults, firstResult);
    }

    private List<Polaganje> findPolaganjeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Polaganje.class));
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

    public Polaganje findPolaganje(PolaganjePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Polaganje.class, id);
        } finally {
            em.close();
        }
    }

    public int getPolaganjeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Polaganje> rt = cq.from(Polaganje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
