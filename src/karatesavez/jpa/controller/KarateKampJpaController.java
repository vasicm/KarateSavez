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
import karatesavez.jpa.entity.KarateKamp;
import karatesavez.jpa.entity.KarateSavez;

/**
 *
 * @author Marko
 */
public class KarateKampJpaController implements Serializable {

    public KarateKampJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KarateKamp karateKamp) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateSavez IDSaveza = karateKamp.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza = em.getReference(IDSaveza.getClass(), IDSaveza.getIDSaveza());
                karateKamp.setIDSaveza(IDSaveza);
            }
            em.persist(karateKamp);
            if (IDSaveza != null) {
                IDSaveza.getKarateKampCollection().add(karateKamp);
                IDSaveza = em.merge(IDSaveza);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKarateKamp(karateKamp.getIDKampa()) != null) {
                throw new PreexistingEntityException("KarateKamp " + karateKamp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KarateKamp karateKamp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKamp persistentKarateKamp = em.find(KarateKamp.class, karateKamp.getIDKampa());
            KarateSavez IDSavezaOld = persistentKarateKamp.getIDSaveza();
            KarateSavez IDSavezaNew = karateKamp.getIDSaveza();
            if (IDSavezaNew != null) {
                IDSavezaNew = em.getReference(IDSavezaNew.getClass(), IDSavezaNew.getIDSaveza());
                karateKamp.setIDSaveza(IDSavezaNew);
            }
            karateKamp = em.merge(karateKamp);
            if (IDSavezaOld != null && !IDSavezaOld.equals(IDSavezaNew)) {
                IDSavezaOld.getKarateKampCollection().remove(karateKamp);
                IDSavezaOld = em.merge(IDSavezaOld);
            }
            if (IDSavezaNew != null && !IDSavezaNew.equals(IDSavezaOld)) {
                IDSavezaNew.getKarateKampCollection().add(karateKamp);
                IDSavezaNew = em.merge(IDSavezaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = karateKamp.getIDKampa();
                if (findKarateKamp(id) == null) {
                    throw new NonexistentEntityException("The karateKamp with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKamp karateKamp;
            try {
                karateKamp = em.getReference(KarateKamp.class, id);
                karateKamp.getIDKampa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The karateKamp with id " + id + " no longer exists.", enfe);
            }
            KarateSavez IDSaveza = karateKamp.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza.getKarateKampCollection().remove(karateKamp);
                IDSaveza = em.merge(IDSaveza);
            }
            em.remove(karateKamp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KarateKamp> findKarateKampEntities() {
        return findKarateKampEntities(true, -1, -1);
    }

    public List<KarateKamp> findKarateKampEntities(int maxResults, int firstResult) {
        return findKarateKampEntities(false, maxResults, firstResult);
    }

    private List<KarateKamp> findKarateKampEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KarateKamp.class));
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

    public KarateKamp findKarateKamp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KarateKamp.class, id);
        } finally {
            em.close();
        }
    }

    public int getKarateKampCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KarateKamp> rt = cq.from(KarateKamp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
