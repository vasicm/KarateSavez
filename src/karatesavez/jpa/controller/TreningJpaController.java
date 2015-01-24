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
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.Trening;
import karatesavez.jpa.entity.TreningPK;

/**
 *
 * @author Marko
 */
public class TreningJpaController implements Serializable {

    public TreningJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Trening trening) throws PreexistingEntityException, Exception {
        if (trening.getTreningPK() == null) {
            trening.setTreningPK(new TreningPK());
        }
        trening.getTreningPK().setIDKluba(trening.getKarateKlub().getIDKluba());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKlub karateKlub = trening.getKarateKlub();
            if (karateKlub != null) {
                karateKlub = em.getReference(karateKlub.getClass(), karateKlub.getIDKluba());
                trening.setKarateKlub(karateKlub);
            }
            em.persist(trening);
            if (karateKlub != null) {
                karateKlub.getTreningCollection().add(trening);
                karateKlub = em.merge(karateKlub);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTrening(trening.getTreningPK()) != null) {
                throw new PreexistingEntityException("Trening " + trening + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Trening trening) throws NonexistentEntityException, Exception {
        trening.getTreningPK().setIDKluba(trening.getKarateKlub().getIDKluba());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trening persistentTrening = em.find(Trening.class, trening.getTreningPK());
            KarateKlub karateKlubOld = persistentTrening.getKarateKlub();
            KarateKlub karateKlubNew = trening.getKarateKlub();
            if (karateKlubNew != null) {
                karateKlubNew = em.getReference(karateKlubNew.getClass(), karateKlubNew.getIDKluba());
                trening.setKarateKlub(karateKlubNew);
            }
            trening = em.merge(trening);
            if (karateKlubOld != null && !karateKlubOld.equals(karateKlubNew)) {
                karateKlubOld.getTreningCollection().remove(trening);
                karateKlubOld = em.merge(karateKlubOld);
            }
            if (karateKlubNew != null && !karateKlubNew.equals(karateKlubOld)) {
                karateKlubNew.getTreningCollection().add(trening);
                karateKlubNew = em.merge(karateKlubNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TreningPK id = trening.getTreningPK();
                if (findTrening(id) == null) {
                    throw new NonexistentEntityException("The trening with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TreningPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Trening trening;
            try {
                trening = em.getReference(Trening.class, id);
                trening.getTreningPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The trening with id " + id + " no longer exists.", enfe);
            }
            KarateKlub karateKlub = trening.getKarateKlub();
            if (karateKlub != null) {
                karateKlub.getTreningCollection().remove(trening);
                karateKlub = em.merge(karateKlub);
            }
            em.remove(trening);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Trening> findTreningEntities() {
        return findTreningEntities(true, -1, -1);
    }

    public List<Trening> findTreningEntities(int maxResults, int firstResult) {
        return findTreningEntities(false, maxResults, firstResult);
    }

    private List<Trening> findTreningEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Trening.class));
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

    public Trening findTrening(TreningPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Trening.class, id);
        } finally {
            em.close();
        }
    }

    public int getTreningCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Trening> rt = cq.from(Trening.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
