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
import karatesavez.jpa.entity.EkipnaBorba;
import karatesavez.jpa.entity.EkipnaBorbaPK;
import karatesavez.jpa.entity.PrijavljujeBorbeEkipno;

/**
 *
 * @author Marko
 */
public class EkipnaBorbaJpaController implements Serializable {

    public EkipnaBorbaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EkipnaBorba ekipnaBorba) throws PreexistingEntityException, Exception {
        if (ekipnaBorba.getEkipnaBorbaPK() == null) {
            ekipnaBorba.setEkipnaBorbaPK(new EkipnaBorbaPK());
        }
        ekipnaBorba.getEkipnaBorbaPK().setIDKategorije(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDKategorije());
        ekipnaBorba.getEkipnaBorbaPK().setIDTakmicenja(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDTakmicenja());
        ekipnaBorba.getEkipnaBorbaPK().setIDEkipeCrveni(ekipnaBorba.getPrijavljujeBorbeEkipno().getPrijavljujeBorbeEkipnoPK().getIDEkipe());
        ekipnaBorba.getEkipnaBorbaPK().setIDEkipePlavi(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno = ekipnaBorba.getPrijavljujeBorbeEkipno();
            if (prijavljujeBorbeEkipno != null) {
                prijavljujeBorbeEkipno = em.getReference(prijavljujeBorbeEkipno.getClass(), prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK());
                ekipnaBorba.setPrijavljujeBorbeEkipno(prijavljujeBorbeEkipno);
            }
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1 = ekipnaBorba.getPrijavljujeBorbeEkipno1();
            if (prijavljujeBorbeEkipno1 != null) {
                prijavljujeBorbeEkipno1 = em.getReference(prijavljujeBorbeEkipno1.getClass(), prijavljujeBorbeEkipno1.getPrijavljujeBorbeEkipnoPK());
                ekipnaBorba.setPrijavljujeBorbeEkipno1(prijavljujeBorbeEkipno1);
            }
            em.persist(ekipnaBorba);
            if (prijavljujeBorbeEkipno != null) {
                prijavljujeBorbeEkipno.getEkipnaBorbaCollection().add(ekipnaBorba);
                prijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipno);
            }
            if (prijavljujeBorbeEkipno1 != null) {
                prijavljujeBorbeEkipno1.getEkipnaBorbaCollection().add(ekipnaBorba);
                prijavljujeBorbeEkipno1 = em.merge(prijavljujeBorbeEkipno1);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEkipnaBorba(ekipnaBorba.getEkipnaBorbaPK()) != null) {
                throw new PreexistingEntityException("EkipnaBorba " + ekipnaBorba + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EkipnaBorba ekipnaBorba) throws NonexistentEntityException, Exception {
        ekipnaBorba.getEkipnaBorbaPK().setIDKategorije(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDKategorije());
        ekipnaBorba.getEkipnaBorbaPK().setIDTakmicenja(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDTakmicenja());
        ekipnaBorba.getEkipnaBorbaPK().setIDEkipeCrveni(ekipnaBorba.getPrijavljujeBorbeEkipno().getPrijavljujeBorbeEkipnoPK().getIDEkipe());
        ekipnaBorba.getEkipnaBorbaPK().setIDEkipePlavi(ekipnaBorba.getPrijavljujeBorbeEkipno1().getPrijavljujeBorbeEkipnoPK().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaBorba persistentEkipnaBorba = em.find(EkipnaBorba.class, ekipnaBorba.getEkipnaBorbaPK());
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoOld = persistentEkipnaBorba.getPrijavljujeBorbeEkipno();
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoNew = ekipnaBorba.getPrijavljujeBorbeEkipno();
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1Old = persistentEkipnaBorba.getPrijavljujeBorbeEkipno1();
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1New = ekipnaBorba.getPrijavljujeBorbeEkipno1();
            if (prijavljujeBorbeEkipnoNew != null) {
                prijavljujeBorbeEkipnoNew = em.getReference(prijavljujeBorbeEkipnoNew.getClass(), prijavljujeBorbeEkipnoNew.getPrijavljujeBorbeEkipnoPK());
                ekipnaBorba.setPrijavljujeBorbeEkipno(prijavljujeBorbeEkipnoNew);
            }
            if (prijavljujeBorbeEkipno1New != null) {
                prijavljujeBorbeEkipno1New = em.getReference(prijavljujeBorbeEkipno1New.getClass(), prijavljujeBorbeEkipno1New.getPrijavljujeBorbeEkipnoPK());
                ekipnaBorba.setPrijavljujeBorbeEkipno1(prijavljujeBorbeEkipno1New);
            }
            ekipnaBorba = em.merge(ekipnaBorba);
            if (prijavljujeBorbeEkipnoOld != null && !prijavljujeBorbeEkipnoOld.equals(prijavljujeBorbeEkipnoNew)) {
                prijavljujeBorbeEkipnoOld.getEkipnaBorbaCollection().remove(ekipnaBorba);
                prijavljujeBorbeEkipnoOld = em.merge(prijavljujeBorbeEkipnoOld);
            }
            if (prijavljujeBorbeEkipnoNew != null && !prijavljujeBorbeEkipnoNew.equals(prijavljujeBorbeEkipnoOld)) {
                prijavljujeBorbeEkipnoNew.getEkipnaBorbaCollection().add(ekipnaBorba);
                prijavljujeBorbeEkipnoNew = em.merge(prijavljujeBorbeEkipnoNew);
            }
            if (prijavljujeBorbeEkipno1Old != null && !prijavljujeBorbeEkipno1Old.equals(prijavljujeBorbeEkipno1New)) {
                prijavljujeBorbeEkipno1Old.getEkipnaBorbaCollection().remove(ekipnaBorba);
                prijavljujeBorbeEkipno1Old = em.merge(prijavljujeBorbeEkipno1Old);
            }
            if (prijavljujeBorbeEkipno1New != null && !prijavljujeBorbeEkipno1New.equals(prijavljujeBorbeEkipno1Old)) {
                prijavljujeBorbeEkipno1New.getEkipnaBorbaCollection().add(ekipnaBorba);
                prijavljujeBorbeEkipno1New = em.merge(prijavljujeBorbeEkipno1New);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EkipnaBorbaPK id = ekipnaBorba.getEkipnaBorbaPK();
                if (findEkipnaBorba(id) == null) {
                    throw new NonexistentEntityException("The ekipnaBorba with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EkipnaBorbaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaBorba ekipnaBorba;
            try {
                ekipnaBorba = em.getReference(EkipnaBorba.class, id);
                ekipnaBorba.getEkipnaBorbaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ekipnaBorba with id " + id + " no longer exists.", enfe);
            }
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno = ekipnaBorba.getPrijavljujeBorbeEkipno();
            if (prijavljujeBorbeEkipno != null) {
                prijavljujeBorbeEkipno.getEkipnaBorbaCollection().remove(ekipnaBorba);
                prijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipno);
            }
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1 = ekipnaBorba.getPrijavljujeBorbeEkipno1();
            if (prijavljujeBorbeEkipno1 != null) {
                prijavljujeBorbeEkipno1.getEkipnaBorbaCollection().remove(ekipnaBorba);
                prijavljujeBorbeEkipno1 = em.merge(prijavljujeBorbeEkipno1);
            }
            em.remove(ekipnaBorba);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EkipnaBorba> findEkipnaBorbaEntities() {
        return findEkipnaBorbaEntities(true, -1, -1);
    }

    public List<EkipnaBorba> findEkipnaBorbaEntities(int maxResults, int firstResult) {
        return findEkipnaBorbaEntities(false, maxResults, firstResult);
    }

    private List<EkipnaBorba> findEkipnaBorbaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EkipnaBorba.class));
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

    public EkipnaBorba findEkipnaBorba(EkipnaBorbaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EkipnaBorba.class, id);
        } finally {
            em.close();
        }
    }

    public int getEkipnaBorbaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EkipnaBorba> rt = cq.from(EkipnaBorba.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
