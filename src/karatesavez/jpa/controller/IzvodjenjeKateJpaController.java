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
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUKatama;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.IzvodjenjeKate;
import karatesavez.jpa.entity.IzvodjenjeKatePK;

/**
 *
 * @author Marko
 */
public class IzvodjenjeKateJpaController implements Serializable {

    public IzvodjenjeKateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(IzvodjenjeKate izvodjenjeKate) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (izvodjenjeKate.getIzvodjenjeKatePK() == null) {
            izvodjenjeKate.setIzvodjenjeKatePK(new IzvodjenjeKatePK());
        }
        izvodjenjeKate.getIzvodjenjeKatePK().setIDKategorije(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getIDKategorije());
        izvodjenjeKate.getIzvodjenjeKatePK().setIDTakmicenja(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getIDTakmicenja());
        izvodjenjeKate.getIzvodjenjeKatePK().setJmb(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getJmb());
        List<String> illegalOrphanMessages = null;
        PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaOrphanCheck = izvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
        if (prijavljujeTakmicenjeUKatamaOrphanCheck != null) {
            IzvodjenjeKate oldIzvodjenjeKateOfPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaOrphanCheck.getIzvodjenjeKate();
            if (oldIzvodjenjeKateOfPrijavljujeTakmicenjeUKatama != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaOrphanCheck + " already has an item of type IzvodjenjeKate whose prijavljujeTakmicenjeUKatama column cannot be null. Please make another selection for the prijavljujeTakmicenjeUKatama field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama = izvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
            if (prijavljujeTakmicenjeUKatama != null) {
                prijavljujeTakmicenjeUKatama = em.getReference(prijavljujeTakmicenjeUKatama.getClass(), prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK());
                izvodjenjeKate.setPrijavljujeTakmicenjeUKatama(prijavljujeTakmicenjeUKatama);
            }
            em.persist(izvodjenjeKate);
            if (prijavljujeTakmicenjeUKatama != null) {
                prijavljujeTakmicenjeUKatama.setIzvodjenjeKate(izvodjenjeKate);
                prijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatama);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIzvodjenjeKate(izvodjenjeKate.getIzvodjenjeKatePK()) != null) {
                throw new PreexistingEntityException("IzvodjenjeKate " + izvodjenjeKate + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(IzvodjenjeKate izvodjenjeKate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        izvodjenjeKate.getIzvodjenjeKatePK().setIDKategorije(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getIDKategorije());
        izvodjenjeKate.getIzvodjenjeKatePK().setIDTakmicenja(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getIDTakmicenja());
        izvodjenjeKate.getIzvodjenjeKatePK().setJmb(izvodjenjeKate.getPrijavljujeTakmicenjeUKatama().getPrijavljujeTakmicenjeUKatamaPK().getJmb());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IzvodjenjeKate persistentIzvodjenjeKate = em.find(IzvodjenjeKate.class, izvodjenjeKate.getIzvodjenjeKatePK());
            PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaOld = persistentIzvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
            PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaNew = izvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
            List<String> illegalOrphanMessages = null;
            if (prijavljujeTakmicenjeUKatamaNew != null && !prijavljujeTakmicenjeUKatamaNew.equals(prijavljujeTakmicenjeUKatamaOld)) {
                IzvodjenjeKate oldIzvodjenjeKateOfPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaNew.getIzvodjenjeKate();
                if (oldIzvodjenjeKateOfPrijavljujeTakmicenjeUKatama != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaNew + " already has an item of type IzvodjenjeKate whose prijavljujeTakmicenjeUKatama column cannot be null. Please make another selection for the prijavljujeTakmicenjeUKatama field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (prijavljujeTakmicenjeUKatamaNew != null) {
                prijavljujeTakmicenjeUKatamaNew = em.getReference(prijavljujeTakmicenjeUKatamaNew.getClass(), prijavljujeTakmicenjeUKatamaNew.getPrijavljujeTakmicenjeUKatamaPK());
                izvodjenjeKate.setPrijavljujeTakmicenjeUKatama(prijavljujeTakmicenjeUKatamaNew);
            }
            izvodjenjeKate = em.merge(izvodjenjeKate);
            if (prijavljujeTakmicenjeUKatamaOld != null && !prijavljujeTakmicenjeUKatamaOld.equals(prijavljujeTakmicenjeUKatamaNew)) {
                prijavljujeTakmicenjeUKatamaOld.setIzvodjenjeKate(null);
                prijavljujeTakmicenjeUKatamaOld = em.merge(prijavljujeTakmicenjeUKatamaOld);
            }
            if (prijavljujeTakmicenjeUKatamaNew != null && !prijavljujeTakmicenjeUKatamaNew.equals(prijavljujeTakmicenjeUKatamaOld)) {
                prijavljujeTakmicenjeUKatamaNew.setIzvodjenjeKate(izvodjenjeKate);
                prijavljujeTakmicenjeUKatamaNew = em.merge(prijavljujeTakmicenjeUKatamaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IzvodjenjeKatePK id = izvodjenjeKate.getIzvodjenjeKatePK();
                if (findIzvodjenjeKate(id) == null) {
                    throw new NonexistentEntityException("The izvodjenjeKate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IzvodjenjeKatePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            IzvodjenjeKate izvodjenjeKate;
            try {
                izvodjenjeKate = em.getReference(IzvodjenjeKate.class, id);
                izvodjenjeKate.getIzvodjenjeKatePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The izvodjenjeKate with id " + id + " no longer exists.", enfe);
            }
            PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama = izvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
            if (prijavljujeTakmicenjeUKatama != null) {
                prijavljujeTakmicenjeUKatama.setIzvodjenjeKate(null);
                prijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatama);
            }
            em.remove(izvodjenjeKate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<IzvodjenjeKate> findIzvodjenjeKateEntities() {
        return findIzvodjenjeKateEntities(true, -1, -1);
    }

    public List<IzvodjenjeKate> findIzvodjenjeKateEntities(int maxResults, int firstResult) {
        return findIzvodjenjeKateEntities(false, maxResults, firstResult);
    }

    private List<IzvodjenjeKate> findIzvodjenjeKateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(IzvodjenjeKate.class));
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

    public IzvodjenjeKate findIzvodjenjeKate(IzvodjenjeKatePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(IzvodjenjeKate.class, id);
        } finally {
            em.close();
        }
    }

    public int getIzvodjenjeKateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<IzvodjenjeKate> rt = cq.from(IzvodjenjeKate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
