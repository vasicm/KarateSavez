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
import karatesavez.jpa.entity.Borba;
import karatesavez.jpa.entity.BorbaPK;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUBorbama;

/**
 *
 * @author Marko
 */
public class BorbaJpaController implements Serializable {

    public BorbaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Borba borba) throws PreexistingEntityException, Exception {
        if (borba.getBorbaPK() == null) {
            borba.setBorbaPK(new BorbaPK());
        }
        borba.getBorbaPK().setJMBPlavi(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getJmb());
        borba.getBorbaPK().setJMBCrveni(borba.getPrijavljujeTakmicenjeUBorbama().getPrijavljujeTakmicenjeUBorbamaPK().getJmb());
        borba.getBorbaPK().setIDKategorije(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getIDKategorije());
        borba.getBorbaPK().setIDTakmicenja(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama = borba.getPrijavljujeTakmicenjeUBorbama();
            if (prijavljujeTakmicenjeUBorbama != null) {
                prijavljujeTakmicenjeUBorbama = em.getReference(prijavljujeTakmicenjeUBorbama.getClass(), prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK());
                borba.setPrijavljujeTakmicenjeUBorbama(prijavljujeTakmicenjeUBorbama);
            }
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1 = borba.getPrijavljujeTakmicenjeUBorbama1();
            if (prijavljujeTakmicenjeUBorbama1 != null) {
                prijavljujeTakmicenjeUBorbama1 = em.getReference(prijavljujeTakmicenjeUBorbama1.getClass(), prijavljujeTakmicenjeUBorbama1.getPrijavljujeTakmicenjeUBorbamaPK());
                borba.setPrijavljujeTakmicenjeUBorbama1(prijavljujeTakmicenjeUBorbama1);
            }
            em.persist(borba);
            if (prijavljujeTakmicenjeUBorbama != null) {
                prijavljujeTakmicenjeUBorbama.getBorbaCollection().add(borba);
                prijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbama);
            }
            if (prijavljujeTakmicenjeUBorbama1 != null) {
                prijavljujeTakmicenjeUBorbama1.getBorbaCollection().add(borba);
                prijavljujeTakmicenjeUBorbama1 = em.merge(prijavljujeTakmicenjeUBorbama1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBorba(borba.getBorbaPK()) != null) {
                throw new PreexistingEntityException("Borba " + borba + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Borba borba) throws NonexistentEntityException, Exception {
        borba.getBorbaPK().setJMBPlavi(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getJmb());
        borba.getBorbaPK().setJMBCrveni(borba.getPrijavljujeTakmicenjeUBorbama().getPrijavljujeTakmicenjeUBorbamaPK().getJmb());
        borba.getBorbaPK().setIDKategorije(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getIDKategorije());
        borba.getBorbaPK().setIDTakmicenja(borba.getPrijavljujeTakmicenjeUBorbama1().getPrijavljujeTakmicenjeUBorbamaPK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Borba persistentBorba = em.find(Borba.class, borba.getBorbaPK());
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaOld = persistentBorba.getPrijavljujeTakmicenjeUBorbama();
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaNew = borba.getPrijavljujeTakmicenjeUBorbama();
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1Old = persistentBorba.getPrijavljujeTakmicenjeUBorbama1();
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1New = borba.getPrijavljujeTakmicenjeUBorbama1();
            if (prijavljujeTakmicenjeUBorbamaNew != null) {
                prijavljujeTakmicenjeUBorbamaNew = em.getReference(prijavljujeTakmicenjeUBorbamaNew.getClass(), prijavljujeTakmicenjeUBorbamaNew.getPrijavljujeTakmicenjeUBorbamaPK());
                borba.setPrijavljujeTakmicenjeUBorbama(prijavljujeTakmicenjeUBorbamaNew);
            }
            if (prijavljujeTakmicenjeUBorbama1New != null) {
                prijavljujeTakmicenjeUBorbama1New = em.getReference(prijavljujeTakmicenjeUBorbama1New.getClass(), prijavljujeTakmicenjeUBorbama1New.getPrijavljujeTakmicenjeUBorbamaPK());
                borba.setPrijavljujeTakmicenjeUBorbama1(prijavljujeTakmicenjeUBorbama1New);
            }
            borba = em.merge(borba);
            if (prijavljujeTakmicenjeUBorbamaOld != null && !prijavljujeTakmicenjeUBorbamaOld.equals(prijavljujeTakmicenjeUBorbamaNew)) {
                prijavljujeTakmicenjeUBorbamaOld.getBorbaCollection().remove(borba);
                prijavljujeTakmicenjeUBorbamaOld = em.merge(prijavljujeTakmicenjeUBorbamaOld);
            }
            if (prijavljujeTakmicenjeUBorbamaNew != null && !prijavljujeTakmicenjeUBorbamaNew.equals(prijavljujeTakmicenjeUBorbamaOld)) {
                prijavljujeTakmicenjeUBorbamaNew.getBorbaCollection().add(borba);
                prijavljujeTakmicenjeUBorbamaNew = em.merge(prijavljujeTakmicenjeUBorbamaNew);
            }
            if (prijavljujeTakmicenjeUBorbama1Old != null && !prijavljujeTakmicenjeUBorbama1Old.equals(prijavljujeTakmicenjeUBorbama1New)) {
                prijavljujeTakmicenjeUBorbama1Old.getBorbaCollection().remove(borba);
                prijavljujeTakmicenjeUBorbama1Old = em.merge(prijavljujeTakmicenjeUBorbama1Old);
            }
            if (prijavljujeTakmicenjeUBorbama1New != null && !prijavljujeTakmicenjeUBorbama1New.equals(prijavljujeTakmicenjeUBorbama1Old)) {
                prijavljujeTakmicenjeUBorbama1New.getBorbaCollection().add(borba);
                prijavljujeTakmicenjeUBorbama1New = em.merge(prijavljujeTakmicenjeUBorbama1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BorbaPK id = borba.getBorbaPK();
                if (findBorba(id) == null) {
                    throw new NonexistentEntityException("The borba with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BorbaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Borba borba;
            try {
                borba = em.getReference(Borba.class, id);
                borba.getBorbaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The borba with id " + id + " no longer exists.", enfe);
            }
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama = borba.getPrijavljujeTakmicenjeUBorbama();
            if (prijavljujeTakmicenjeUBorbama != null) {
                prijavljujeTakmicenjeUBorbama.getBorbaCollection().remove(borba);
                prijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbama);
            }
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1 = borba.getPrijavljujeTakmicenjeUBorbama1();
            if (prijavljujeTakmicenjeUBorbama1 != null) {
                prijavljujeTakmicenjeUBorbama1.getBorbaCollection().remove(borba);
                prijavljujeTakmicenjeUBorbama1 = em.merge(prijavljujeTakmicenjeUBorbama1);
            }
            em.remove(borba);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Borba> findBorbaEntities() {
        return findBorbaEntities(true, -1, -1);
    }

    public List<Borba> findBorbaEntities(int maxResults, int firstResult) {
        return findBorbaEntities(false, maxResults, firstResult);
    }

    private List<Borba> findBorbaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Borba.class));
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

    public Borba findBorba(BorbaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Borba.class, id);
        } finally {
            em.close();
        }
    }

    public int getBorbaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Borba> rt = cq.from(Borba.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
