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
import karatesavez.jpa.entity.KateEkipno;
import karatesavez.jpa.entity.UcesceEkipe;
import karatesavez.jpa.entity.EkipnoIzvodjenjeKate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeKateEkipno;
import karatesavez.jpa.entity.PrijavljujeKateEkipnoPK;

/**
 *
 * @author Marko
 */
public class PrijavljujeKateEkipnoJpaController implements Serializable {

    public PrijavljujeKateEkipnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrijavljujeKateEkipno prijavljujeKateEkipno) throws PreexistingEntityException, Exception {
        if (prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK() == null) {
            prijavljujeKateEkipno.setPrijavljujeKateEkipnoPK(new PrijavljujeKateEkipnoPK());
        }
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDEkipe(prijavljujeKateEkipno.getUcesceEkipe().getUcesceEkipePK().getIDEkipe());
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDKategorije(prijavljujeKateEkipno.getKateEkipno().getIDKategorije());
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDTakmicenja(prijavljujeKateEkipno.getUcesceEkipe().getUcesceEkipePK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KateEkipno kateEkipno = prijavljujeKateEkipno.getKateEkipno();
            if (kateEkipno != null) {
                kateEkipno = em.getReference(kateEkipno.getClass(), kateEkipno.getIDKategorije());
                prijavljujeKateEkipno.setKateEkipno(kateEkipno);
            }
            UcesceEkipe ucesceEkipe = prijavljujeKateEkipno.getUcesceEkipe();
            if (ucesceEkipe != null) {
                ucesceEkipe = em.getReference(ucesceEkipe.getClass(), ucesceEkipe.getUcesceEkipePK());
                prijavljujeKateEkipno.setUcesceEkipe(ucesceEkipe);
            }
            EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate = prijavljujeKateEkipno.getEkipnoIzvodjenjeKate();
            if (ekipnoIzvodjenjeKate != null) {
                ekipnoIzvodjenjeKate = em.getReference(ekipnoIzvodjenjeKate.getClass(), ekipnoIzvodjenjeKate.getEkipnoIzvodjenjeKatePK());
                prijavljujeKateEkipno.setEkipnoIzvodjenjeKate(ekipnoIzvodjenjeKate);
            }
            em.persist(prijavljujeKateEkipno);
            if (kateEkipno != null) {
                kateEkipno.getPrijavljujeKateEkipnoCollection().add(prijavljujeKateEkipno);
                kateEkipno = em.merge(kateEkipno);
            }
            if (ucesceEkipe != null) {
                ucesceEkipe.getPrijavljujeKateEkipnoCollection().add(prijavljujeKateEkipno);
                ucesceEkipe = em.merge(ucesceEkipe);
            }
            if (ekipnoIzvodjenjeKate != null) {
                PrijavljujeKateEkipno oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate = ekipnoIzvodjenjeKate.getPrijavljujeKateEkipno();
                if (oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate != null) {
                    oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate.setEkipnoIzvodjenjeKate(null);
                    oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate = em.merge(oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate);
                }
                ekipnoIzvodjenjeKate.setPrijavljujeKateEkipno(prijavljujeKateEkipno);
                ekipnoIzvodjenjeKate = em.merge(ekipnoIzvodjenjeKate);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrijavljujeKateEkipno(prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK()) != null) {
                throw new PreexistingEntityException("PrijavljujeKateEkipno " + prijavljujeKateEkipno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrijavljujeKateEkipno prijavljujeKateEkipno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDEkipe(prijavljujeKateEkipno.getUcesceEkipe().getUcesceEkipePK().getIDEkipe());
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDKategorije(prijavljujeKateEkipno.getKateEkipno().getIDKategorije());
        prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK().setIDTakmicenja(prijavljujeKateEkipno.getUcesceEkipe().getUcesceEkipePK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeKateEkipno persistentPrijavljujeKateEkipno = em.find(PrijavljujeKateEkipno.class, prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK());
            KateEkipno kateEkipnoOld = persistentPrijavljujeKateEkipno.getKateEkipno();
            KateEkipno kateEkipnoNew = prijavljujeKateEkipno.getKateEkipno();
            UcesceEkipe ucesceEkipeOld = persistentPrijavljujeKateEkipno.getUcesceEkipe();
            UcesceEkipe ucesceEkipeNew = prijavljujeKateEkipno.getUcesceEkipe();
            EkipnoIzvodjenjeKate ekipnoIzvodjenjeKateOld = persistentPrijavljujeKateEkipno.getEkipnoIzvodjenjeKate();
            EkipnoIzvodjenjeKate ekipnoIzvodjenjeKateNew = prijavljujeKateEkipno.getEkipnoIzvodjenjeKate();
            List<String> illegalOrphanMessages = null;
            if (ekipnoIzvodjenjeKateOld != null && !ekipnoIzvodjenjeKateOld.equals(ekipnoIzvodjenjeKateNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EkipnoIzvodjenjeKate " + ekipnoIzvodjenjeKateOld + " since its prijavljujeKateEkipno field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (kateEkipnoNew != null) {
                kateEkipnoNew = em.getReference(kateEkipnoNew.getClass(), kateEkipnoNew.getIDKategorije());
                prijavljujeKateEkipno.setKateEkipno(kateEkipnoNew);
            }
            if (ucesceEkipeNew != null) {
                ucesceEkipeNew = em.getReference(ucesceEkipeNew.getClass(), ucesceEkipeNew.getUcesceEkipePK());
                prijavljujeKateEkipno.setUcesceEkipe(ucesceEkipeNew);
            }
            if (ekipnoIzvodjenjeKateNew != null) {
                ekipnoIzvodjenjeKateNew = em.getReference(ekipnoIzvodjenjeKateNew.getClass(), ekipnoIzvodjenjeKateNew.getEkipnoIzvodjenjeKatePK());
                prijavljujeKateEkipno.setEkipnoIzvodjenjeKate(ekipnoIzvodjenjeKateNew);
            }
            prijavljujeKateEkipno = em.merge(prijavljujeKateEkipno);
            if (kateEkipnoOld != null && !kateEkipnoOld.equals(kateEkipnoNew)) {
                kateEkipnoOld.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipno);
                kateEkipnoOld = em.merge(kateEkipnoOld);
            }
            if (kateEkipnoNew != null && !kateEkipnoNew.equals(kateEkipnoOld)) {
                kateEkipnoNew.getPrijavljujeKateEkipnoCollection().add(prijavljujeKateEkipno);
                kateEkipnoNew = em.merge(kateEkipnoNew);
            }
            if (ucesceEkipeOld != null && !ucesceEkipeOld.equals(ucesceEkipeNew)) {
                ucesceEkipeOld.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipno);
                ucesceEkipeOld = em.merge(ucesceEkipeOld);
            }
            if (ucesceEkipeNew != null && !ucesceEkipeNew.equals(ucesceEkipeOld)) {
                ucesceEkipeNew.getPrijavljujeKateEkipnoCollection().add(prijavljujeKateEkipno);
                ucesceEkipeNew = em.merge(ucesceEkipeNew);
            }
            if (ekipnoIzvodjenjeKateNew != null && !ekipnoIzvodjenjeKateNew.equals(ekipnoIzvodjenjeKateOld)) {
                PrijavljujeKateEkipno oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate = ekipnoIzvodjenjeKateNew.getPrijavljujeKateEkipno();
                if (oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate != null) {
                    oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate.setEkipnoIzvodjenjeKate(null);
                    oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate = em.merge(oldPrijavljujeKateEkipnoOfEkipnoIzvodjenjeKate);
                }
                ekipnoIzvodjenjeKateNew.setPrijavljujeKateEkipno(prijavljujeKateEkipno);
                ekipnoIzvodjenjeKateNew = em.merge(ekipnoIzvodjenjeKateNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrijavljujeKateEkipnoPK id = prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK();
                if (findPrijavljujeKateEkipno(id) == null) {
                    throw new NonexistentEntityException("The prijavljujeKateEkipno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrijavljujeKateEkipnoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeKateEkipno prijavljujeKateEkipno;
            try {
                prijavljujeKateEkipno = em.getReference(PrijavljujeKateEkipno.class, id);
                prijavljujeKateEkipno.getPrijavljujeKateEkipnoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prijavljujeKateEkipno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EkipnoIzvodjenjeKate ekipnoIzvodjenjeKateOrphanCheck = prijavljujeKateEkipno.getEkipnoIzvodjenjeKate();
            if (ekipnoIzvodjenjeKateOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeKateEkipno (" + prijavljujeKateEkipno + ") cannot be destroyed since the EkipnoIzvodjenjeKate " + ekipnoIzvodjenjeKateOrphanCheck + " in its ekipnoIzvodjenjeKate field has a non-nullable prijavljujeKateEkipno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KateEkipno kateEkipno = prijavljujeKateEkipno.getKateEkipno();
            if (kateEkipno != null) {
                kateEkipno.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipno);
                kateEkipno = em.merge(kateEkipno);
            }
            UcesceEkipe ucesceEkipe = prijavljujeKateEkipno.getUcesceEkipe();
            if (ucesceEkipe != null) {
                ucesceEkipe.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipno);
                ucesceEkipe = em.merge(ucesceEkipe);
            }
            em.remove(prijavljujeKateEkipno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrijavljujeKateEkipno> findPrijavljujeKateEkipnoEntities() {
        return findPrijavljujeKateEkipnoEntities(true, -1, -1);
    }

    public List<PrijavljujeKateEkipno> findPrijavljujeKateEkipnoEntities(int maxResults, int firstResult) {
        return findPrijavljujeKateEkipnoEntities(false, maxResults, firstResult);
    }

    private List<PrijavljujeKateEkipno> findPrijavljujeKateEkipnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrijavljujeKateEkipno.class));
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

    public PrijavljujeKateEkipno findPrijavljujeKateEkipno(PrijavljujeKateEkipnoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrijavljujeKateEkipno.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrijavljujeKateEkipnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrijavljujeKateEkipno> rt = cq.from(PrijavljujeKateEkipno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
