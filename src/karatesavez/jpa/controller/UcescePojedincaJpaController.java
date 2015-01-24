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
import karatesavez.jpa.entity.Takmicar;
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUBorbama;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUKatama;
import karatesavez.jpa.entity.UcescePojedinca;
import karatesavez.jpa.entity.UcescePojedincaPK;

/**
 *
 * @author Marko
 */
public class UcescePojedincaJpaController implements Serializable {

    public UcescePojedincaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UcescePojedinca ucescePojedinca) throws PreexistingEntityException, Exception {
        if (ucescePojedinca.getUcescePojedincaPK() == null) {
            ucescePojedinca.setUcescePojedincaPK(new UcescePojedincaPK());
        }
        if (ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection() == null) {
            ucescePojedinca.setPrijavljujeTakmicenjeUBorbamaCollection(new ArrayList<PrijavljujeTakmicenjeUBorbama>());
        }
        if (ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection() == null) {
            ucescePojedinca.setPrijavljujeTakmicenjeUKatamaCollection(new ArrayList<PrijavljujeTakmicenjeUKatama>());
        }
        ucescePojedinca.getUcescePojedincaPK().setJmb(ucescePojedinca.getTakmicar().getJmb());
        ucescePojedinca.getUcescePojedincaPK().setIDTakmicenja(ucescePojedinca.getTakmicenje().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Takmicar takmicar = ucescePojedinca.getTakmicar();
            if (takmicar != null) {
                takmicar = em.getReference(takmicar.getClass(), takmicar.getJmb());
                ucescePojedinca.setTakmicar(takmicar);
            }
            Takmicenje takmicenje = ucescePojedinca.getTakmicenje();
            if (takmicenje != null) {
                takmicenje = em.getReference(takmicenje.getClass(), takmicenje.getIDTakmicenja());
                ucescePojedinca.setTakmicenje(takmicenje);
            }
            Collection<PrijavljujeTakmicenjeUBorbama> attachedPrijavljujeTakmicenjeUBorbamaCollection = new ArrayList<PrijavljujeTakmicenjeUBorbama>();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach : ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection()) {
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach = em.getReference(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach.getClass(), prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach.getPrijavljujeTakmicenjeUBorbamaPK());
                attachedPrijavljujeTakmicenjeUBorbamaCollection.add(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach);
            }
            ucescePojedinca.setPrijavljujeTakmicenjeUBorbamaCollection(attachedPrijavljujeTakmicenjeUBorbamaCollection);
            Collection<PrijavljujeTakmicenjeUKatama> attachedPrijavljujeTakmicenjeUKatamaCollection = new ArrayList<PrijavljujeTakmicenjeUKatama>();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach : ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection()) {
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach = em.getReference(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach.getClass(), prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach.getPrijavljujeTakmicenjeUKatamaPK());
                attachedPrijavljujeTakmicenjeUKatamaCollection.add(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach);
            }
            ucescePojedinca.setPrijavljujeTakmicenjeUKatamaCollection(attachedPrijavljujeTakmicenjeUKatamaCollection);
            em.persist(ucescePojedinca);
            if (takmicar != null) {
                takmicar.getUcescePojedincaCollection().add(ucescePojedinca);
                takmicar = em.merge(takmicar);
            }
            if (takmicenje != null) {
                takmicenje.getUcescePojedincaCollection().add(ucescePojedinca);
                takmicenje = em.merge(takmicenje);
            }
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama : ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection()) {
                UcescePojedinca oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.getUcescePojedinca();
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.setUcescePojedinca(ucescePojedinca);
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                if (oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama != null) {
                    oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                    oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = em.merge(oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                }
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama : ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection()) {
                UcescePojedinca oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.getUcescePojedinca();
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.setUcescePojedinca(ucescePojedinca);
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                if (oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama != null) {
                    oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                    oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = em.merge(oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUcescePojedinca(ucescePojedinca.getUcescePojedincaPK()) != null) {
                throw new PreexistingEntityException("UcescePojedinca " + ucescePojedinca + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UcescePojedinca ucescePojedinca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ucescePojedinca.getUcescePojedincaPK().setJmb(ucescePojedinca.getTakmicar().getJmb());
        ucescePojedinca.getUcescePojedincaPK().setIDTakmicenja(ucescePojedinca.getTakmicenje().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UcescePojedinca persistentUcescePojedinca = em.find(UcescePojedinca.class, ucescePojedinca.getUcescePojedincaPK());
            Takmicar takmicarOld = persistentUcescePojedinca.getTakmicar();
            Takmicar takmicarNew = ucescePojedinca.getTakmicar();
            Takmicenje takmicenjeOld = persistentUcescePojedinca.getTakmicenje();
            Takmicenje takmicenjeNew = ucescePojedinca.getTakmicenje();
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionOld = persistentUcescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection();
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionNew = ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection();
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionOld = persistentUcescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection();
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionNew = ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection();
            List<String> illegalOrphanMessages = null;
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionOld) {
                if (!prijavljujeTakmicenjeUBorbamaCollectionNew.contains(prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeTakmicenjeUBorbama " + prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama + " since its ucescePojedinca field is not nullable.");
                }
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionOld) {
                if (!prijavljujeTakmicenjeUKatamaCollectionNew.contains(prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama + " since its ucescePojedinca field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (takmicarNew != null) {
                takmicarNew = em.getReference(takmicarNew.getClass(), takmicarNew.getJmb());
                ucescePojedinca.setTakmicar(takmicarNew);
            }
            if (takmicenjeNew != null) {
                takmicenjeNew = em.getReference(takmicenjeNew.getClass(), takmicenjeNew.getIDTakmicenja());
                ucescePojedinca.setTakmicenje(takmicenjeNew);
            }
            Collection<PrijavljujeTakmicenjeUBorbama> attachedPrijavljujeTakmicenjeUBorbamaCollectionNew = new ArrayList<PrijavljujeTakmicenjeUBorbama>();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach : prijavljujeTakmicenjeUBorbamaCollectionNew) {
                prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach = em.getReference(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach.getClass(), prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach.getPrijavljujeTakmicenjeUBorbamaPK());
                attachedPrijavljujeTakmicenjeUBorbamaCollectionNew.add(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach);
            }
            prijavljujeTakmicenjeUBorbamaCollectionNew = attachedPrijavljujeTakmicenjeUBorbamaCollectionNew;
            ucescePojedinca.setPrijavljujeTakmicenjeUBorbamaCollection(prijavljujeTakmicenjeUBorbamaCollectionNew);
            Collection<PrijavljujeTakmicenjeUKatama> attachedPrijavljujeTakmicenjeUKatamaCollectionNew = new ArrayList<PrijavljujeTakmicenjeUKatama>();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach : prijavljujeTakmicenjeUKatamaCollectionNew) {
                prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach = em.getReference(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach.getClass(), prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach.getPrijavljujeTakmicenjeUKatamaPK());
                attachedPrijavljujeTakmicenjeUKatamaCollectionNew.add(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach);
            }
            prijavljujeTakmicenjeUKatamaCollectionNew = attachedPrijavljujeTakmicenjeUKatamaCollectionNew;
            ucescePojedinca.setPrijavljujeTakmicenjeUKatamaCollection(prijavljujeTakmicenjeUKatamaCollectionNew);
            ucescePojedinca = em.merge(ucescePojedinca);
            if (takmicarOld != null && !takmicarOld.equals(takmicarNew)) {
                takmicarOld.getUcescePojedincaCollection().remove(ucescePojedinca);
                takmicarOld = em.merge(takmicarOld);
            }
            if (takmicarNew != null && !takmicarNew.equals(takmicarOld)) {
                takmicarNew.getUcescePojedincaCollection().add(ucescePojedinca);
                takmicarNew = em.merge(takmicarNew);
            }
            if (takmicenjeOld != null && !takmicenjeOld.equals(takmicenjeNew)) {
                takmicenjeOld.getUcescePojedincaCollection().remove(ucescePojedinca);
                takmicenjeOld = em.merge(takmicenjeOld);
            }
            if (takmicenjeNew != null && !takmicenjeNew.equals(takmicenjeOld)) {
                takmicenjeNew.getUcescePojedincaCollection().add(ucescePojedinca);
                takmicenjeNew = em.merge(takmicenjeNew);
            }
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionNew) {
                if (!prijavljujeTakmicenjeUBorbamaCollectionOld.contains(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama)) {
                    UcescePojedinca oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.getUcescePojedinca();
                    prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.setUcescePojedinca(ucescePojedinca);
                    prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                    if (oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama != null && !oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.equals(ucescePojedinca)) {
                        oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                        oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = em.merge(oldUcescePojedincaOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                    }
                }
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionNew) {
                if (!prijavljujeTakmicenjeUKatamaCollectionOld.contains(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama)) {
                    UcescePojedinca oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.getUcescePojedinca();
                    prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.setUcescePojedinca(ucescePojedinca);
                    prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                    if (oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama != null && !oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.equals(ucescePojedinca)) {
                        oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                        oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = em.merge(oldUcescePojedincaOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UcescePojedincaPK id = ucescePojedinca.getUcescePojedincaPK();
                if (findUcescePojedinca(id) == null) {
                    throw new NonexistentEntityException("The ucescePojedinca with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UcescePojedincaPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UcescePojedinca ucescePojedinca;
            try {
                ucescePojedinca = em.getReference(UcescePojedinca.class, id);
                ucescePojedinca.getUcescePojedincaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ucescePojedinca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionOrphanCheck = ucescePojedinca.getPrijavljujeTakmicenjeUBorbamaCollection();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionOrphanCheckPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UcescePojedinca (" + ucescePojedinca + ") cannot be destroyed since the PrijavljujeTakmicenjeUBorbama " + prijavljujeTakmicenjeUBorbamaCollectionOrphanCheckPrijavljujeTakmicenjeUBorbama + " in its prijavljujeTakmicenjeUBorbamaCollection field has a non-nullable ucescePojedinca field.");
            }
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionOrphanCheck = ucescePojedinca.getPrijavljujeTakmicenjeUKatamaCollection();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionOrphanCheckPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UcescePojedinca (" + ucescePojedinca + ") cannot be destroyed since the PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaCollectionOrphanCheckPrijavljujeTakmicenjeUKatama + " in its prijavljujeTakmicenjeUKatamaCollection field has a non-nullable ucescePojedinca field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Takmicar takmicar = ucescePojedinca.getTakmicar();
            if (takmicar != null) {
                takmicar.getUcescePojedincaCollection().remove(ucescePojedinca);
                takmicar = em.merge(takmicar);
            }
            Takmicenje takmicenje = ucescePojedinca.getTakmicenje();
            if (takmicenje != null) {
                takmicenje.getUcescePojedincaCollection().remove(ucescePojedinca);
                takmicenje = em.merge(takmicenje);
            }
            em.remove(ucescePojedinca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UcescePojedinca> findUcescePojedincaEntities() {
        return findUcescePojedincaEntities(true, -1, -1);
    }

    public List<UcescePojedinca> findUcescePojedincaEntities(int maxResults, int firstResult) {
        return findUcescePojedincaEntities(false, maxResults, firstResult);
    }

    private List<UcescePojedinca> findUcescePojedincaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UcescePojedinca.class));
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

    public UcescePojedinca findUcescePojedinca(UcescePojedincaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UcescePojedinca.class, id);
        } finally {
            em.close();
        }
    }

    public int getUcescePojedincaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UcescePojedinca> rt = cq.from(UcescePojedinca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
