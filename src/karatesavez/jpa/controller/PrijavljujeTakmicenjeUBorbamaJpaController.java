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
import karatesavez.jpa.entity.UcescePojedinca;
import karatesavez.jpa.entity.BorbePojedinacno;
import karatesavez.jpa.entity.Borba;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUBorbama;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUBorbamaPK;

/**
 *
 * @author Marko
 */
public class PrijavljujeTakmicenjeUBorbamaJpaController implements Serializable {

    public PrijavljujeTakmicenjeUBorbamaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama) throws PreexistingEntityException, Exception {
        if (prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK() == null) {
            prijavljujeTakmicenjeUBorbama.setPrijavljujeTakmicenjeUBorbamaPK(new PrijavljujeTakmicenjeUBorbamaPK());
        }
        if (prijavljujeTakmicenjeUBorbama.getBorbaCollection() == null) {
            prijavljujeTakmicenjeUBorbama.setBorbaCollection(new ArrayList<Borba>());
        }
        if (prijavljujeTakmicenjeUBorbama.getBorbaCollection1() == null) {
            prijavljujeTakmicenjeUBorbama.setBorbaCollection1(new ArrayList<Borba>());
        }
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setJmb(prijavljujeTakmicenjeUBorbama.getUcescePojedinca().getUcescePojedincaPK().getJmb());
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setIDKategorije(prijavljujeTakmicenjeUBorbama.getBorbePojedinacno().getIDKategorije());
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setIDTakmicenja(prijavljujeTakmicenjeUBorbama.getUcescePojedinca().getUcescePojedincaPK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UcescePojedinca ucescePojedinca = prijavljujeTakmicenjeUBorbama.getUcescePojedinca();
            if (ucescePojedinca != null) {
                ucescePojedinca = em.getReference(ucescePojedinca.getClass(), ucescePojedinca.getUcescePojedincaPK());
                prijavljujeTakmicenjeUBorbama.setUcescePojedinca(ucescePojedinca);
            }
            BorbePojedinacno borbePojedinacno = prijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
            if (borbePojedinacno != null) {
                borbePojedinacno = em.getReference(borbePojedinacno.getClass(), borbePojedinacno.getIDKategorije());
                prijavljujeTakmicenjeUBorbama.setBorbePojedinacno(borbePojedinacno);
            }
            Collection<Borba> attachedBorbaCollection = new ArrayList<Borba>();
            for (Borba borbaCollectionBorbaToAttach : prijavljujeTakmicenjeUBorbama.getBorbaCollection()) {
                borbaCollectionBorbaToAttach = em.getReference(borbaCollectionBorbaToAttach.getClass(), borbaCollectionBorbaToAttach.getBorbaPK());
                attachedBorbaCollection.add(borbaCollectionBorbaToAttach);
            }
            prijavljujeTakmicenjeUBorbama.setBorbaCollection(attachedBorbaCollection);
            Collection<Borba> attachedBorbaCollection1 = new ArrayList<Borba>();
            for (Borba borbaCollection1BorbaToAttach : prijavljujeTakmicenjeUBorbama.getBorbaCollection1()) {
                borbaCollection1BorbaToAttach = em.getReference(borbaCollection1BorbaToAttach.getClass(), borbaCollection1BorbaToAttach.getBorbaPK());
                attachedBorbaCollection1.add(borbaCollection1BorbaToAttach);
            }
            prijavljujeTakmicenjeUBorbama.setBorbaCollection1(attachedBorbaCollection1);
            em.persist(prijavljujeTakmicenjeUBorbama);
            if (ucescePojedinca != null) {
                ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection().add(prijavljujeTakmicenjeUBorbama);
                ucescePojedinca = em.merge(ucescePojedinca);
            }
            if (borbePojedinacno != null) {
                borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection().add(prijavljujeTakmicenjeUBorbama);
                borbePojedinacno = em.merge(borbePojedinacno);
            }
            for (Borba borbaCollectionBorba : prijavljujeTakmicenjeUBorbama.getBorbaCollection()) {
                PrijavljujeTakmicenjeUBorbama oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionBorba = borbaCollectionBorba.getPrijavljujeTakmicenjeUBorbama();
                borbaCollectionBorba.setPrijavljujeTakmicenjeUBorbama(prijavljujeTakmicenjeUBorbama);
                borbaCollectionBorba = em.merge(borbaCollectionBorba);
                if (oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionBorba != null) {
                    oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionBorba.getBorbaCollection().remove(borbaCollectionBorba);
                    oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionBorba = em.merge(oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionBorba);
                }
            }
            for (Borba borbaCollection1Borba : prijavljujeTakmicenjeUBorbama.getBorbaCollection1()) {
                PrijavljujeTakmicenjeUBorbama oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1Borba = borbaCollection1Borba.getPrijavljujeTakmicenjeUBorbama1();
                borbaCollection1Borba.setPrijavljujeTakmicenjeUBorbama1(prijavljujeTakmicenjeUBorbama);
                borbaCollection1Borba = em.merge(borbaCollection1Borba);
                if (oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1Borba != null) {
                    oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1Borba.getBorbaCollection1().remove(borbaCollection1Borba);
                    oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1Borba = em.merge(oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1Borba);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrijavljujeTakmicenjeUBorbama(prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK()) != null) {
                throw new PreexistingEntityException("PrijavljujeTakmicenjeUBorbama " + prijavljujeTakmicenjeUBorbama + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama) throws IllegalOrphanException, NonexistentEntityException, Exception {
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setJmb(prijavljujeTakmicenjeUBorbama.getUcescePojedinca().getUcescePojedincaPK().getJmb());
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setIDKategorije(prijavljujeTakmicenjeUBorbama.getBorbePojedinacno().getIDKategorije());
        prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK().setIDTakmicenja(prijavljujeTakmicenjeUBorbama.getUcescePojedinca().getUcescePojedincaPK().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUBorbama persistentPrijavljujeTakmicenjeUBorbama = em.find(PrijavljujeTakmicenjeUBorbama.class, prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK());
            UcescePojedinca ucescePojedincaOld = persistentPrijavljujeTakmicenjeUBorbama.getUcescePojedinca();
            UcescePojedinca ucescePojedincaNew = prijavljujeTakmicenjeUBorbama.getUcescePojedinca();
            BorbePojedinacno borbePojedinacnoOld = persistentPrijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
            BorbePojedinacno borbePojedinacnoNew = prijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
            Collection<Borba> borbaCollectionOld = persistentPrijavljujeTakmicenjeUBorbama.getBorbaCollection();
            Collection<Borba> borbaCollectionNew = prijavljujeTakmicenjeUBorbama.getBorbaCollection();
            Collection<Borba> borbaCollection1Old = persistentPrijavljujeTakmicenjeUBorbama.getBorbaCollection1();
            Collection<Borba> borbaCollection1New = prijavljujeTakmicenjeUBorbama.getBorbaCollection1();
            List<String> illegalOrphanMessages = null;
            for (Borba borbaCollectionOldBorba : borbaCollectionOld) {
                if (!borbaCollectionNew.contains(borbaCollectionOldBorba)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Borba " + borbaCollectionOldBorba + " since its prijavljujeTakmicenjeUBorbama field is not nullable.");
                }
            }
            for (Borba borbaCollection1OldBorba : borbaCollection1Old) {
                if (!borbaCollection1New.contains(borbaCollection1OldBorba)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Borba " + borbaCollection1OldBorba + " since its prijavljujeTakmicenjeUBorbama1 field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ucescePojedincaNew != null) {
                ucescePojedincaNew = em.getReference(ucescePojedincaNew.getClass(), ucescePojedincaNew.getUcescePojedincaPK());
                prijavljujeTakmicenjeUBorbama.setUcescePojedinca(ucescePojedincaNew);
            }
            if (borbePojedinacnoNew != null) {
                borbePojedinacnoNew = em.getReference(borbePojedinacnoNew.getClass(), borbePojedinacnoNew.getIDKategorije());
                prijavljujeTakmicenjeUBorbama.setBorbePojedinacno(borbePojedinacnoNew);
            }
            Collection<Borba> attachedBorbaCollectionNew = new ArrayList<Borba>();
            for (Borba borbaCollectionNewBorbaToAttach : borbaCollectionNew) {
                borbaCollectionNewBorbaToAttach = em.getReference(borbaCollectionNewBorbaToAttach.getClass(), borbaCollectionNewBorbaToAttach.getBorbaPK());
                attachedBorbaCollectionNew.add(borbaCollectionNewBorbaToAttach);
            }
            borbaCollectionNew = attachedBorbaCollectionNew;
            prijavljujeTakmicenjeUBorbama.setBorbaCollection(borbaCollectionNew);
            Collection<Borba> attachedBorbaCollection1New = new ArrayList<Borba>();
            for (Borba borbaCollection1NewBorbaToAttach : borbaCollection1New) {
                borbaCollection1NewBorbaToAttach = em.getReference(borbaCollection1NewBorbaToAttach.getClass(), borbaCollection1NewBorbaToAttach.getBorbaPK());
                attachedBorbaCollection1New.add(borbaCollection1NewBorbaToAttach);
            }
            borbaCollection1New = attachedBorbaCollection1New;
            prijavljujeTakmicenjeUBorbama.setBorbaCollection1(borbaCollection1New);
            prijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbama);
            if (ucescePojedincaOld != null && !ucescePojedincaOld.equals(ucescePojedincaNew)) {
                ucescePojedincaOld.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbama);
                ucescePojedincaOld = em.merge(ucescePojedincaOld);
            }
            if (ucescePojedincaNew != null && !ucescePojedincaNew.equals(ucescePojedincaOld)) {
                ucescePojedincaNew.getPrijavljujeTakmicenjeUBorbamaCollection().add(prijavljujeTakmicenjeUBorbama);
                ucescePojedincaNew = em.merge(ucescePojedincaNew);
            }
            if (borbePojedinacnoOld != null && !borbePojedinacnoOld.equals(borbePojedinacnoNew)) {
                borbePojedinacnoOld.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbama);
                borbePojedinacnoOld = em.merge(borbePojedinacnoOld);
            }
            if (borbePojedinacnoNew != null && !borbePojedinacnoNew.equals(borbePojedinacnoOld)) {
                borbePojedinacnoNew.getPrijavljujeTakmicenjeUBorbamaCollection().add(prijavljujeTakmicenjeUBorbama);
                borbePojedinacnoNew = em.merge(borbePojedinacnoNew);
            }
            for (Borba borbaCollectionNewBorba : borbaCollectionNew) {
                if (!borbaCollectionOld.contains(borbaCollectionNewBorba)) {
                    PrijavljujeTakmicenjeUBorbama oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba = borbaCollectionNewBorba.getPrijavljujeTakmicenjeUBorbama();
                    borbaCollectionNewBorba.setPrijavljujeTakmicenjeUBorbama(prijavljujeTakmicenjeUBorbama);
                    borbaCollectionNewBorba = em.merge(borbaCollectionNewBorba);
                    if (oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba != null && !oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba.equals(prijavljujeTakmicenjeUBorbama)) {
                        oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba.getBorbaCollection().remove(borbaCollectionNewBorba);
                        oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba = em.merge(oldPrijavljujeTakmicenjeUBorbamaOfBorbaCollectionNewBorba);
                    }
                }
            }
            for (Borba borbaCollection1NewBorba : borbaCollection1New) {
                if (!borbaCollection1Old.contains(borbaCollection1NewBorba)) {
                    PrijavljujeTakmicenjeUBorbama oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba = borbaCollection1NewBorba.getPrijavljujeTakmicenjeUBorbama1();
                    borbaCollection1NewBorba.setPrijavljujeTakmicenjeUBorbama1(prijavljujeTakmicenjeUBorbama);
                    borbaCollection1NewBorba = em.merge(borbaCollection1NewBorba);
                    if (oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba != null && !oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba.equals(prijavljujeTakmicenjeUBorbama)) {
                        oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba.getBorbaCollection1().remove(borbaCollection1NewBorba);
                        oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba = em.merge(oldPrijavljujeTakmicenjeUBorbama1OfBorbaCollection1NewBorba);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PrijavljujeTakmicenjeUBorbamaPK id = prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK();
                if (findPrijavljujeTakmicenjeUBorbama(id) == null) {
                    throw new NonexistentEntityException("The prijavljujeTakmicenjeUBorbama with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PrijavljujeTakmicenjeUBorbamaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama;
            try {
                prijavljujeTakmicenjeUBorbama = em.getReference(PrijavljujeTakmicenjeUBorbama.class, id);
                prijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The prijavljujeTakmicenjeUBorbama with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Borba> borbaCollectionOrphanCheck = prijavljujeTakmicenjeUBorbama.getBorbaCollection();
            for (Borba borbaCollectionOrphanCheckBorba : borbaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeTakmicenjeUBorbama (" + prijavljujeTakmicenjeUBorbama + ") cannot be destroyed since the Borba " + borbaCollectionOrphanCheckBorba + " in its borbaCollection field has a non-nullable prijavljujeTakmicenjeUBorbama field.");
            }
            Collection<Borba> borbaCollection1OrphanCheck = prijavljujeTakmicenjeUBorbama.getBorbaCollection1();
            for (Borba borbaCollection1OrphanCheckBorba : borbaCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PrijavljujeTakmicenjeUBorbama (" + prijavljujeTakmicenjeUBorbama + ") cannot be destroyed since the Borba " + borbaCollection1OrphanCheckBorba + " in its borbaCollection1 field has a non-nullable prijavljujeTakmicenjeUBorbama1 field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            UcescePojedinca ucescePojedinca = prijavljujeTakmicenjeUBorbama.getUcescePojedinca();
            if (ucescePojedinca != null) {
                ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbama);
                ucescePojedinca = em.merge(ucescePojedinca);
            }
            BorbePojedinacno borbePojedinacno = prijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
            if (borbePojedinacno != null) {
                borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbama);
                borbePojedinacno = em.merge(borbePojedinacno);
            }
            em.remove(prijavljujeTakmicenjeUBorbama);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrijavljujeTakmicenjeUBorbama> findPrijavljujeTakmicenjeUBorbamaEntities() {
        return findPrijavljujeTakmicenjeUBorbamaEntities(true, -1, -1);
    }

    public List<PrijavljujeTakmicenjeUBorbama> findPrijavljujeTakmicenjeUBorbamaEntities(int maxResults, int firstResult) {
        return findPrijavljujeTakmicenjeUBorbamaEntities(false, maxResults, firstResult);
    }

    private List<PrijavljujeTakmicenjeUBorbama> findPrijavljujeTakmicenjeUBorbamaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrijavljujeTakmicenjeUBorbama.class));
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

    public PrijavljujeTakmicenjeUBorbama findPrijavljujeTakmicenjeUBorbama(PrijavljujeTakmicenjeUBorbamaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrijavljujeTakmicenjeUBorbama.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrijavljujeTakmicenjeUBorbamaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrijavljujeTakmicenjeUBorbama> rt = cq.from(PrijavljujeTakmicenjeUBorbama.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
