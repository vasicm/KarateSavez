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
import karatesavez.jpa.entity.BorbeEkipno;
import karatesavez.jpa.entity.UcesceEkipe;
import karatesavez.jpa.entity.EkipnaBorba;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeBorbeEkipno;
import karatesavez.jpa.entity.PrijavljujeBorbeEkipnoPK;

/**
 *
 * @author Marko
 */
public class PrijavljujeBorbeEkipnoJpaController implements Serializable {

    public PrijavljujeBorbeEkipnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrijavljujeBorbeEkipno prijavljujeBorbeEkipno) throws PreexistingEntityException, Exception {
        if (prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK() == null) {
            prijavljujeBorbeEkipno.setPrijavljujeBorbeEkipnoPK(new PrijavljujeBorbeEkipnoPK());
        }
        if (prijavljujeBorbeEkipno.getEkipnaBorbaCollection() == null) {
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection(new ArrayList<EkipnaBorba>());
        }
        if (prijavljujeBorbeEkipno.getEkipnaBorbaCollection1() == null) {
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection1(new ArrayList<EkipnaBorba>());
        }
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDKategorije(prijavljujeBorbeEkipno.getBorbeEkipno().getIDKategorije());
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDTakmicenja(prijavljujeBorbeEkipno.getUcesceEkipe().getUcesceEkipePK().getIDTakmicenja());
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDEkipe(prijavljujeBorbeEkipno.getUcesceEkipe().getUcesceEkipePK().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BorbeEkipno borbeEkipno = prijavljujeBorbeEkipno.getBorbeEkipno();
            if (borbeEkipno != null) {
                borbeEkipno = em.getReference(borbeEkipno.getClass(), borbeEkipno.getIDKategorije());
                prijavljujeBorbeEkipno.setBorbeEkipno(borbeEkipno);
            }
            UcesceEkipe ucesceEkipe = prijavljujeBorbeEkipno.getUcesceEkipe();
            if (ucesceEkipe != null) {
                ucesceEkipe = em.getReference(ucesceEkipe.getClass(), ucesceEkipe.getUcesceEkipePK());
                prijavljujeBorbeEkipno.setUcesceEkipe(ucesceEkipe);
            }
            Collection<EkipnaBorba> attachedEkipnaBorbaCollection = new ArrayList<EkipnaBorba>();
            for (EkipnaBorba ekipnaBorbaCollectionEkipnaBorbaToAttach : prijavljujeBorbeEkipno.getEkipnaBorbaCollection()) {
                ekipnaBorbaCollectionEkipnaBorbaToAttach = em.getReference(ekipnaBorbaCollectionEkipnaBorbaToAttach.getClass(), ekipnaBorbaCollectionEkipnaBorbaToAttach.getEkipnaBorbaPK());
                attachedEkipnaBorbaCollection.add(ekipnaBorbaCollectionEkipnaBorbaToAttach);
            }
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection(attachedEkipnaBorbaCollection);
            Collection<EkipnaBorba> attachedEkipnaBorbaCollection1 = new ArrayList<EkipnaBorba>();
            for (EkipnaBorba ekipnaBorbaCollection1EkipnaBorbaToAttach : prijavljujeBorbeEkipno.getEkipnaBorbaCollection1()) {
                ekipnaBorbaCollection1EkipnaBorbaToAttach = em.getReference(ekipnaBorbaCollection1EkipnaBorbaToAttach.getClass(), ekipnaBorbaCollection1EkipnaBorbaToAttach.getEkipnaBorbaPK());
                attachedEkipnaBorbaCollection1.add(ekipnaBorbaCollection1EkipnaBorbaToAttach);
            }
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection1(attachedEkipnaBorbaCollection1);
            em.persist(prijavljujeBorbeEkipno);
            if (borbeEkipno != null) {
                borbeEkipno.getPrijavljujeBorbeEkipnoCollection().add(prijavljujeBorbeEkipno);
                borbeEkipno = em.merge(borbeEkipno);
            }
            if (ucesceEkipe != null) {
                ucesceEkipe.getPrijavljujeBorbeEkipnoCollection().add(prijavljujeBorbeEkipno);
                ucesceEkipe = em.merge(ucesceEkipe);
            }
            for (EkipnaBorba ekipnaBorbaCollectionEkipnaBorba : prijavljujeBorbeEkipno.getEkipnaBorbaCollection()) {
                PrijavljujeBorbeEkipno oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionEkipnaBorba = ekipnaBorbaCollectionEkipnaBorba.getPrijavljujeBorbeEkipno();
                ekipnaBorbaCollectionEkipnaBorba.setPrijavljujeBorbeEkipno(prijavljujeBorbeEkipno);
                ekipnaBorbaCollectionEkipnaBorba = em.merge(ekipnaBorbaCollectionEkipnaBorba);
                if (oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionEkipnaBorba != null) {
                    oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionEkipnaBorba.getEkipnaBorbaCollection().remove(ekipnaBorbaCollectionEkipnaBorba);
                    oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionEkipnaBorba = em.merge(oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionEkipnaBorba);
                }
            }
            for (EkipnaBorba ekipnaBorbaCollection1EkipnaBorba : prijavljujeBorbeEkipno.getEkipnaBorbaCollection1()) {
                PrijavljujeBorbeEkipno oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1EkipnaBorba = ekipnaBorbaCollection1EkipnaBorba.getPrijavljujeBorbeEkipno1();
                ekipnaBorbaCollection1EkipnaBorba.setPrijavljujeBorbeEkipno1(prijavljujeBorbeEkipno);
                ekipnaBorbaCollection1EkipnaBorba = em.merge(ekipnaBorbaCollection1EkipnaBorba);
                if (oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1EkipnaBorba != null) {
                    oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1EkipnaBorba.getEkipnaBorbaCollection1().remove(ekipnaBorbaCollection1EkipnaBorba);
                    oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1EkipnaBorba = em.merge(oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1EkipnaBorba);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrijavljujeBorbeEkipno(prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK()) != null) {
                throw new PreexistingEntityException("PrijavljujeBorbeEkipno " + prijavljujeBorbeEkipno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrijavljujeBorbeEkipno prijavljujeBorbeEkipno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDKategorije(prijavljujeBorbeEkipno.getBorbeEkipno().getIDKategorije());
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDTakmicenja(prijavljujeBorbeEkipno.getUcesceEkipe().getUcesceEkipePK().getIDTakmicenja());
        prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK().setIDEkipe(prijavljujeBorbeEkipno.getUcesceEkipe().getUcesceEkipePK().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeBorbeEkipno persistentPrijavljujeBorbeEkipno = em.find(PrijavljujeBorbeEkipno.class, prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK());
            BorbeEkipno borbeEkipnoOld = persistentPrijavljujeBorbeEkipno.getBorbeEkipno();
            BorbeEkipno borbeEkipnoNew = prijavljujeBorbeEkipno.getBorbeEkipno();
            UcesceEkipe ucesceEkipeOld = persistentPrijavljujeBorbeEkipno.getUcesceEkipe();
            UcesceEkipe ucesceEkipeNew = prijavljujeBorbeEkipno.getUcesceEkipe();
            Collection<EkipnaBorba> ekipnaBorbaCollectionOld = persistentPrijavljujeBorbeEkipno.getEkipnaBorbaCollection();
            Collection<EkipnaBorba> ekipnaBorbaCollectionNew = prijavljujeBorbeEkipno.getEkipnaBorbaCollection();
            Collection<EkipnaBorba> ekipnaBorbaCollection1Old = persistentPrijavljujeBorbeEkipno.getEkipnaBorbaCollection1();
            Collection<EkipnaBorba> ekipnaBorbaCollection1New = prijavljujeBorbeEkipno.getEkipnaBorbaCollection1();
            List<String> illegalOrphanMessages = null;
            for (EkipnaBorba ekipnaBorbaCollectionOldEkipnaBorba : ekipnaBorbaCollectionOld) {
                if (!ekipnaBorbaCollectionNew.contains(ekipnaBorbaCollectionOldEkipnaBorba)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EkipnaBorba " + ekipnaBorbaCollectionOldEkipnaBorba + " since its prijavljujeBorbeEkipno field is not nullable.");
                }
            }
            for (EkipnaBorba ekipnaBorbaCollection1OldEkipnaBorba : ekipnaBorbaCollection1Old) {
                if (!ekipnaBorbaCollection1New.contains(ekipnaBorbaCollection1OldEkipnaBorba)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EkipnaBorba " + ekipnaBorbaCollection1OldEkipnaBorba + " since its prijavljujeBorbeEkipno1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (borbeEkipnoNew != null) {
                borbeEkipnoNew = em.getReference(borbeEkipnoNew.getClass(), borbeEkipnoNew.getIDKategorije());
                prijavljujeBorbeEkipno.setBorbeEkipno(borbeEkipnoNew);
            }
            if (ucesceEkipeNew != null) {
                ucesceEkipeNew = em.getReference(ucesceEkipeNew.getClass(), ucesceEkipeNew.getUcesceEkipePK());
                prijavljujeBorbeEkipno.setUcesceEkipe(ucesceEkipeNew);
            }
            Collection<EkipnaBorba> attachedEkipnaBorbaCollectionNew = new ArrayList<EkipnaBorba>();
            for (EkipnaBorba ekipnaBorbaCollectionNewEkipnaBorbaToAttach : ekipnaBorbaCollectionNew) {
                ekipnaBorbaCollectionNewEkipnaBorbaToAttach = em.getReference(ekipnaBorbaCollectionNewEkipnaBorbaToAttach.getClass(), ekipnaBorbaCollectionNewEkipnaBorbaToAttach.getEkipnaBorbaPK());
                attachedEkipnaBorbaCollectionNew.add(ekipnaBorbaCollectionNewEkipnaBorbaToAttach);
            }
            ekipnaBorbaCollectionNew = attachedEkipnaBorbaCollectionNew;
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection(ekipnaBorbaCollectionNew);
            Collection<EkipnaBorba> attachedEkipnaBorbaCollection1New = new ArrayList<EkipnaBorba>();
            for (EkipnaBorba ekipnaBorbaCollection1NewEkipnaBorbaToAttach : ekipnaBorbaCollection1New) {
                ekipnaBorbaCollection1NewEkipnaBorbaToAttach = em.getReference(ekipnaBorbaCollection1NewEkipnaBorbaToAttach.getClass(), ekipnaBorbaCollection1NewEkipnaBorbaToAttach.getEkipnaBorbaPK());
                attachedEkipnaBorbaCollection1New.add(ekipnaBorbaCollection1NewEkipnaBorbaToAttach);
            }
            ekipnaBorbaCollection1New = attachedEkipnaBorbaCollection1New;
            prijavljujeBorbeEkipno.setEkipnaBorbaCollection1(ekipnaBorbaCollection1New);
            prijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipno);
            if (borbeEkipnoOld != null && !borbeEkipnoOld.equals(borbeEkipnoNew)) {
                borbeEkipnoOld.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipno);
                borbeEkipnoOld = em.merge(borbeEkipnoOld);
            }
            if (borbeEkipnoNew != null && !borbeEkipnoNew.equals(borbeEkipnoOld)) {
                borbeEkipnoNew.getPrijavljujeBorbeEkipnoCollection().add(prijavljujeBorbeEkipno);
                borbeEkipnoNew = em.merge(borbeEkipnoNew);
            }
            if (ucesceEkipeOld != null && !ucesceEkipeOld.equals(ucesceEkipeNew)) {
                ucesceEkipeOld.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipno);
                ucesceEkipeOld = em.merge(ucesceEkipeOld);
            }
            if (ucesceEkipeNew != null && !ucesceEkipeNew.equals(ucesceEkipeOld)) {
                ucesceEkipeNew.getPrijavljujeBorbeEkipnoCollection().add(prijavljujeBorbeEkipno);
                ucesceEkipeNew = em.merge(ucesceEkipeNew);
            }
            for (EkipnaBorba ekipnaBorbaCollectionNewEkipnaBorba : ekipnaBorbaCollectionNew) {
                if (!ekipnaBorbaCollectionOld.contains(ekipnaBorbaCollectionNewEkipnaBorba)) {
                    PrijavljujeBorbeEkipno oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba = ekipnaBorbaCollectionNewEkipnaBorba.getPrijavljujeBorbeEkipno();
                    ekipnaBorbaCollectionNewEkipnaBorba.setPrijavljujeBorbeEkipno(prijavljujeBorbeEkipno);
                    ekipnaBorbaCollectionNewEkipnaBorba = em.merge(ekipnaBorbaCollectionNewEkipnaBorba);
                    if (oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba != null && !oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba.equals(prijavljujeBorbeEkipno)) {
                        oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba.getEkipnaBorbaCollection().remove(ekipnaBorbaCollectionNewEkipnaBorba);
                        oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba = em.merge(oldPrijavljujeBorbeEkipnoOfEkipnaBorbaCollectionNewEkipnaBorba);
                    }
                }
            }
            for (EkipnaBorba ekipnaBorbaCollection1NewEkipnaBorba : ekipnaBorbaCollection1New) {
                if (!ekipnaBorbaCollection1Old.contains(ekipnaBorbaCollection1NewEkipnaBorba)) {
                    PrijavljujeBorbeEkipno oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba = ekipnaBorbaCollection1NewEkipnaBorba.getPrijavljujeBorbeEkipno1();
                    ekipnaBorbaCollection1NewEkipnaBorba.setPrijavljujeBorbeEkipno1(prijavljujeBorbeEkipno);
                    ekipnaBorbaCollection1NewEkipnaBorba = em.merge(ekipnaBorbaCollection1NewEkipnaBorba);
                    if (oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba != null && !oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba.equals(prijavljujeBorbeEkipno)) {
                        oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba.getEkipnaBorbaCollection1().remove(ekipnaBorbaCollection1NewEkipnaBorba);
                        oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba = em.merge(oldPrijavljujeBorbeEkipno1OfEkipnaBorbaCollection1NewEkipnaBorba);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrijavljujeBorbeEkipnoPK id = prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK();
                if (findPrijavljujeBorbeEkipno(id) == null) {
                    throw new NonexistentEntityException("The prijavljujeBorbeEkipno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrijavljujeBorbeEkipnoPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeBorbeEkipno prijavljujeBorbeEkipno;
            try {
                prijavljujeBorbeEkipno = em.getReference(PrijavljujeBorbeEkipno.class, id);
                prijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prijavljujeBorbeEkipno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EkipnaBorba> ekipnaBorbaCollectionOrphanCheck = prijavljujeBorbeEkipno.getEkipnaBorbaCollection();
            for (EkipnaBorba ekipnaBorbaCollectionOrphanCheckEkipnaBorba : ekipnaBorbaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeBorbeEkipno (" + prijavljujeBorbeEkipno + ") cannot be destroyed since the EkipnaBorba " + ekipnaBorbaCollectionOrphanCheckEkipnaBorba + " in its ekipnaBorbaCollection field has a non-nullable prijavljujeBorbeEkipno field.");
            }
            Collection<EkipnaBorba> ekipnaBorbaCollection1OrphanCheck = prijavljujeBorbeEkipno.getEkipnaBorbaCollection1();
            for (EkipnaBorba ekipnaBorbaCollection1OrphanCheckEkipnaBorba : ekipnaBorbaCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeBorbeEkipno (" + prijavljujeBorbeEkipno + ") cannot be destroyed since the EkipnaBorba " + ekipnaBorbaCollection1OrphanCheckEkipnaBorba + " in its ekipnaBorbaCollection1 field has a non-nullable prijavljujeBorbeEkipno1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            BorbeEkipno borbeEkipno = prijavljujeBorbeEkipno.getBorbeEkipno();
            if (borbeEkipno != null) {
                borbeEkipno.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipno);
                borbeEkipno = em.merge(borbeEkipno);
            }
            UcesceEkipe ucesceEkipe = prijavljujeBorbeEkipno.getUcesceEkipe();
            if (ucesceEkipe != null) {
                ucesceEkipe.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipno);
                ucesceEkipe = em.merge(ucesceEkipe);
            }
            em.remove(prijavljujeBorbeEkipno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrijavljujeBorbeEkipno> findPrijavljujeBorbeEkipnoEntities() {
        return findPrijavljujeBorbeEkipnoEntities(true, -1, -1);
    }

    public List<PrijavljujeBorbeEkipno> findPrijavljujeBorbeEkipnoEntities(int maxResults, int firstResult) {
        return findPrijavljujeBorbeEkipnoEntities(false, maxResults, firstResult);
    }

    private List<PrijavljujeBorbeEkipno> findPrijavljujeBorbeEkipnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrijavljujeBorbeEkipno.class));
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

    public PrijavljujeBorbeEkipno findPrijavljujeBorbeEkipno(PrijavljujeBorbeEkipnoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrijavljujeBorbeEkipno.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrijavljujeBorbeEkipnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrijavljujeBorbeEkipno> rt = cq.from(PrijavljujeBorbeEkipno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
