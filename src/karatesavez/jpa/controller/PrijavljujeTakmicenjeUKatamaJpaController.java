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
import karatesavez.jpa.entity.KatePojedinacno;
import karatesavez.jpa.entity.UcescePojedinca;
import karatesavez.jpa.entity.IzvodjenjeKate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUKatama;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUKatamaPK;

/**
 *
 * @author Marko
 */
public class PrijavljujeTakmicenjeUKatamaJpaController implements Serializable {

    public PrijavljujeTakmicenjeUKatamaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama) throws PreexistingEntityException, Exception {
        if (prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK() == null) {
            prijavljujeTakmicenjeUKatama.setPrijavljujeTakmicenjeUKatamaPK(new PrijavljujeTakmicenjeUKatamaPK());
        }
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setIDTakmicenja(prijavljujeTakmicenjeUKatama.getUcescePojedinca().getUcescePojedincaPK().getIDTakmicenja());
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setIDKategorije(prijavljujeTakmicenjeUKatama.getKatePojedinacno().getIDKategorije());
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setJmb(prijavljujeTakmicenjeUKatama.getUcescePojedinca().getUcescePojedincaPK().getJmb());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KatePojedinacno katePojedinacno = prijavljujeTakmicenjeUKatama.getKatePojedinacno();
            if (katePojedinacno != null) {
                katePojedinacno = em.getReference(katePojedinacno.getClass(), katePojedinacno.getIDKategorije());
                prijavljujeTakmicenjeUKatama.setKatePojedinacno(katePojedinacno);
            }
            UcescePojedinca ucescePojedinca = prijavljujeTakmicenjeUKatama.getUcescePojedinca();
            if (ucescePojedinca != null) {
                ucescePojedinca = em.getReference(ucescePojedinca.getClass(), ucescePojedinca.getUcescePojedincaPK());
                prijavljujeTakmicenjeUKatama.setUcescePojedinca(ucescePojedinca);
            }
            IzvodjenjeKate izvodjenjeKate = prijavljujeTakmicenjeUKatama.getIzvodjenjeKate();
            if (izvodjenjeKate != null) {
                izvodjenjeKate = em.getReference(izvodjenjeKate.getClass(), izvodjenjeKate.getIzvodjenjeKatePK());
                prijavljujeTakmicenjeUKatama.setIzvodjenjeKate(izvodjenjeKate);
            }
            em.persist(prijavljujeTakmicenjeUKatama);
            if (katePojedinacno != null) {
                katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection().add(prijavljujeTakmicenjeUKatama);
                katePojedinacno = em.merge(katePojedinacno);
            }
            if (ucescePojedinca != null) {
                ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection().add(prijavljujeTakmicenjeUKatama);
                ucescePojedinca = em.merge(ucescePojedinca);
            }
            if (izvodjenjeKate != null) {
                PrijavljujeTakmicenjeUKatama oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate = izvodjenjeKate.getPrijavljujeTakmicenjeUKatama();
                if (oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate != null) {
                    oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate.setIzvodjenjeKate(null);
                    oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate = em.merge(oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate);
                }
                izvodjenjeKate.setPrijavljujeTakmicenjeUKatama(prijavljujeTakmicenjeUKatama);
                izvodjenjeKate = em.merge(izvodjenjeKate);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrijavljujeTakmicenjeUKatama(prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK()) != null) {
                throw new PreexistingEntityException("PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatama + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama) throws IllegalOrphanException, NonexistentEntityException, Exception {
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setIDTakmicenja(prijavljujeTakmicenjeUKatama.getUcescePojedinca().getUcescePojedincaPK().getIDTakmicenja());
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setIDKategorije(prijavljujeTakmicenjeUKatama.getKatePojedinacno().getIDKategorije());
        prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK().setJmb(prijavljujeTakmicenjeUKatama.getUcescePojedinca().getUcescePojedincaPK().getJmb());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUKatama persistentPrijavljujeTakmicenjeUKatama = em.find(PrijavljujeTakmicenjeUKatama.class, prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK());
            KatePojedinacno katePojedinacnoOld = persistentPrijavljujeTakmicenjeUKatama.getKatePojedinacno();
            KatePojedinacno katePojedinacnoNew = prijavljujeTakmicenjeUKatama.getKatePojedinacno();
            UcescePojedinca ucescePojedincaOld = persistentPrijavljujeTakmicenjeUKatama.getUcescePojedinca();
            UcescePojedinca ucescePojedincaNew = prijavljujeTakmicenjeUKatama.getUcescePojedinca();
            IzvodjenjeKate izvodjenjeKateOld = persistentPrijavljujeTakmicenjeUKatama.getIzvodjenjeKate();
            IzvodjenjeKate izvodjenjeKateNew = prijavljujeTakmicenjeUKatama.getIzvodjenjeKate();
            List<String> illegalOrphanMessages = null;
            if (izvodjenjeKateOld != null && !izvodjenjeKateOld.equals(izvodjenjeKateNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain IzvodjenjeKate " + izvodjenjeKateOld + " since its prijavljujeTakmicenjeUKatama field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (katePojedinacnoNew != null) {
                katePojedinacnoNew = em.getReference(katePojedinacnoNew.getClass(), katePojedinacnoNew.getIDKategorije());
                prijavljujeTakmicenjeUKatama.setKatePojedinacno(katePojedinacnoNew);
            }
            if (ucescePojedincaNew != null) {
                ucescePojedincaNew = em.getReference(ucescePojedincaNew.getClass(), ucescePojedincaNew.getUcescePojedincaPK());
                prijavljujeTakmicenjeUKatama.setUcescePojedinca(ucescePojedincaNew);
            }
            if (izvodjenjeKateNew != null) {
                izvodjenjeKateNew = em.getReference(izvodjenjeKateNew.getClass(), izvodjenjeKateNew.getIzvodjenjeKatePK());
                prijavljujeTakmicenjeUKatama.setIzvodjenjeKate(izvodjenjeKateNew);
            }
            prijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatama);
            if (katePojedinacnoOld != null && !katePojedinacnoOld.equals(katePojedinacnoNew)) {
                katePojedinacnoOld.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatama);
                katePojedinacnoOld = em.merge(katePojedinacnoOld);
            }
            if (katePojedinacnoNew != null && !katePojedinacnoNew.equals(katePojedinacnoOld)) {
                katePojedinacnoNew.getPrijavljujeTakmicenjeUKatamaCollection().add(prijavljujeTakmicenjeUKatama);
                katePojedinacnoNew = em.merge(katePojedinacnoNew);
            }
            if (ucescePojedincaOld != null && !ucescePojedincaOld.equals(ucescePojedincaNew)) {
                ucescePojedincaOld.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatama);
                ucescePojedincaOld = em.merge(ucescePojedincaOld);
            }
            if (ucescePojedincaNew != null && !ucescePojedincaNew.equals(ucescePojedincaOld)) {
                ucescePojedincaNew.getPrijavljujeTakmicenjeUKatamaCollection().add(prijavljujeTakmicenjeUKatama);
                ucescePojedincaNew = em.merge(ucescePojedincaNew);
            }
            if (izvodjenjeKateNew != null && !izvodjenjeKateNew.equals(izvodjenjeKateOld)) {
                PrijavljujeTakmicenjeUKatama oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate = izvodjenjeKateNew.getPrijavljujeTakmicenjeUKatama();
                if (oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate != null) {
                    oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate.setIzvodjenjeKate(null);
                    oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate = em.merge(oldPrijavljujeTakmicenjeUKatamaOfIzvodjenjeKate);
                }
                izvodjenjeKateNew.setPrijavljujeTakmicenjeUKatama(prijavljujeTakmicenjeUKatama);
                izvodjenjeKateNew = em.merge(izvodjenjeKateNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrijavljujeTakmicenjeUKatamaPK id = prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK();
                if (findPrijavljujeTakmicenjeUKatama(id) == null) {
                    throw new NonexistentEntityException("The prijavljujeTakmicenjeUKatama with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrijavljujeTakmicenjeUKatamaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama;
            try {
                prijavljujeTakmicenjeUKatama = em.getReference(PrijavljujeTakmicenjeUKatama.class, id);
                prijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prijavljujeTakmicenjeUKatama with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            IzvodjenjeKate izvodjenjeKateOrphanCheck = prijavljujeTakmicenjeUKatama.getIzvodjenjeKate();
            if (izvodjenjeKateOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeTakmicenjeUKatama (" + prijavljujeTakmicenjeUKatama + ") cannot be destroyed since the IzvodjenjeKate " + izvodjenjeKateOrphanCheck + " in its izvodjenjeKate field has a non-nullable prijavljujeTakmicenjeUKatama field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KatePojedinacno katePojedinacno = prijavljujeTakmicenjeUKatama.getKatePojedinacno();
            if (katePojedinacno != null) {
                katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatama);
                katePojedinacno = em.merge(katePojedinacno);
            }
            UcescePojedinca ucescePojedinca = prijavljujeTakmicenjeUKatama.getUcescePojedinca();
            if (ucescePojedinca != null) {
                ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatama);
                ucescePojedinca = em.merge(ucescePojedinca);
            }
            em.remove(prijavljujeTakmicenjeUKatama);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrijavljujeTakmicenjeUKatama> findPrijavljujeTakmicenjeUKatamaEntities() {
        return findPrijavljujeTakmicenjeUKatamaEntities(true, -1, -1);
    }

    public List<PrijavljujeTakmicenjeUKatama> findPrijavljujeTakmicenjeUKatamaEntities(int maxResults, int firstResult) {
        return findPrijavljujeTakmicenjeUKatamaEntities(false, maxResults, firstResult);
    }

    private List<PrijavljujeTakmicenjeUKatama> findPrijavljujeTakmicenjeUKatamaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrijavljujeTakmicenjeUKatama.class));
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

    public PrijavljujeTakmicenjeUKatama findPrijavljujeTakmicenjeUKatama(PrijavljujeTakmicenjeUKatamaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrijavljujeTakmicenjeUKatama.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrijavljujeTakmicenjeUKatamaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrijavljujeTakmicenjeUKatama> rt = cq.from(PrijavljujeTakmicenjeUKatama.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
