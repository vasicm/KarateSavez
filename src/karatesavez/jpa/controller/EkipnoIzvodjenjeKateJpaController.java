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
import karatesavez.jpa.entity.PrijavljujeKateEkipno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.EkipnoIzvodjenjeKate;
import karatesavez.jpa.entity.EkipnoIzvodjenjeKatePK;

/**
 *
 * @author Marko
 */
public class EkipnoIzvodjenjeKateJpaController implements Serializable {

    public EkipnoIzvodjenjeKateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK() == null) {
            ekipnoIzvodjenjeKate.setEkipnoIzvodjenjeKatePK(new EkipnoIzvodjenjeKatePK());
        }
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDKategorije(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDKategorije());
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDEkipe(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDEkipe());
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDTakmicenja(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDTakmicenja());
        List<String> illegalOrphanMessages = null;
        PrijavljujeKateEkipno prijavljujeKateEkipnoOrphanCheck = ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
        if (prijavljujeKateEkipnoOrphanCheck != null) {
            EkipnoIzvodjenjeKate oldEkipnoIzvodjenjeKateOfPrijavljujeKateEkipno = prijavljujeKateEkipnoOrphanCheck.getEkipnoIzvodjenjeKate();
            if (oldEkipnoIzvodjenjeKateOfPrijavljujeKateEkipno != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PrijavljujeKateEkipno " + prijavljujeKateEkipnoOrphanCheck + " already has an item of type EkipnoIzvodjenjeKate whose prijavljujeKateEkipno column cannot be null. Please make another selection for the prijavljujeKateEkipno field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeKateEkipno prijavljujeKateEkipno = ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
            if (prijavljujeKateEkipno != null) {
                prijavljujeKateEkipno = em.getReference(prijavljujeKateEkipno.getClass(), prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK());
                ekipnoIzvodjenjeKate.setPrijavljujeKateEkipno(prijavljujeKateEkipno);
            }
            em.persist(ekipnoIzvodjenjeKate);
            if (prijavljujeKateEkipno != null) {
                prijavljujeKateEkipno.setEkipnoIzvodjenjeKate(ekipnoIzvodjenjeKate);
                prijavljujeKateEkipno = em.merge(prijavljujeKateEkipno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEkipnoIzvodjenjeKate(ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK()) != null) {
                throw new PreexistingEntityException("EkipnoIzvodjenjeKate " + ekipnoIzvodjenjeKate + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDKategorije(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDKategorije());
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDEkipe(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDEkipe());
        ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK().setIDTakmicenja(ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno().getPrijavljujeKateEkipnoPK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnoIzvodjenjeKate persistentEkipnoIzvodjenjeKate = em.find(EkipnoIzvodjenjeKate.class, ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK());
            PrijavljujeKateEkipno prijavljujeKateEkipnoOld = persistentEkipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
            PrijavljujeKateEkipno prijavljujeKateEkipnoNew = ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
            List<String> illegalOrphanMessages = null;
            if (prijavljujeKateEkipnoNew != null && !prijavljujeKateEkipnoNew.equals(prijavljujeKateEkipnoOld)) {
                EkipnoIzvodjenjeKate oldEkipnoIzvodjenjeKateOfPrijavljujeKateEkipno = prijavljujeKateEkipnoNew.getEkipnoIzvodjenjeKate();
                if (oldEkipnoIzvodjenjeKateOfPrijavljujeKateEkipno != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PrijavljujeKateEkipno " + prijavljujeKateEkipnoNew + " already has an item of type EkipnoIzvodjenjeKate whose prijavljujeKateEkipno column cannot be null. Please make another selection for the prijavljujeKateEkipno field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (prijavljujeKateEkipnoNew != null) {
                prijavljujeKateEkipnoNew = em.getReference(prijavljujeKateEkipnoNew.getClass(), prijavljujeKateEkipnoNew.getPrijavljujeKateEkipnoPK());
                ekipnoIzvodjenjeKate.setPrijavljujeKateEkipno(prijavljujeKateEkipnoNew);
            }
            ekipnoIzvodjenjeKate = em.merge(ekipnoIzvodjenjeKate);
            if (prijavljujeKateEkipnoOld != null && !prijavljujeKateEkipnoOld.equals(prijavljujeKateEkipnoNew)) {
                prijavljujeKateEkipnoOld.setEkipnoIzvodjenjeKate(null);
                prijavljujeKateEkipnoOld = em.merge(prijavljujeKateEkipnoOld);
            }
            if (prijavljujeKateEkipnoNew != null && !prijavljujeKateEkipnoNew.equals(prijavljujeKateEkipnoOld)) {
                prijavljujeKateEkipnoNew.setEkipnoIzvodjenjeKate(ekipnoIzvodjenjeKate);
                prijavljujeKateEkipnoNew = em.merge(prijavljujeKateEkipnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EkipnoIzvodjenjeKatePK id = ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK();
                if (findEkipnoIzvodjenjeKate(id) == null) {
                    throw new NonexistentEntityException("The ekipnoIzvodjenjeKate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EkipnoIzvodjenjeKatePK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate;
            try {
                ekipnoIzvodjenjeKate = em.getReference(EkipnoIzvodjenjeKate.class, id);
                ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ekipnoIzvodjenjeKate with id " + id + " no longer exists.", enfe);
            }
            PrijavljujeKateEkipno prijavljujeKateEkipno = ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
            if (prijavljujeKateEkipno != null) {
                prijavljujeKateEkipno.setEkipnoIzvodjenjeKate(null);
                prijavljujeKateEkipno = em.merge(prijavljujeKateEkipno);
            }
            em.remove(ekipnoIzvodjenjeKate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EkipnoIzvodjenjeKate> findEkipnoIzvodjenjeKateEntities() {
        return findEkipnoIzvodjenjeKateEntities(true, -1, -1);
    }

    public List<EkipnoIzvodjenjeKate> findEkipnoIzvodjenjeKateEntities(int maxResults, int firstResult) {
        return findEkipnoIzvodjenjeKateEntities(false, maxResults, firstResult);
    }

    private List<EkipnoIzvodjenjeKate> findEkipnoIzvodjenjeKateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EkipnoIzvodjenjeKate.class));
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

    public EkipnoIzvodjenjeKate findEkipnoIzvodjenjeKate(EkipnoIzvodjenjeKatePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EkipnoIzvodjenjeKate.class, id);
        } finally {
            em.close();
        }
    }

    public int getEkipnoIzvodjenjeKateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EkipnoIzvodjenjeKate> rt = cq.from(EkipnoIzvodjenjeKate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
