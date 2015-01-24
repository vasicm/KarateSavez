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
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.Polaganje;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Ispit;
import karatesavez.jpa.entity.IspitPK;

/**
 *
 * @author Marko
 */
public class IspitJpaController implements Serializable {

    public IspitJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ispit ispit) throws PreexistingEntityException, Exception {
        if (ispit.getIspitPK() == null) {
            ispit.setIspitPK(new IspitPK());
        }
        if (ispit.getPolaganjeCollection() == null) {
            ispit.setPolaganjeCollection(new ArrayList<Polaganje>());
        }
        ispit.getIspitPK().setIDKluba(ispit.getKarateKlub().getIDKluba());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKlub karateKlub = ispit.getKarateKlub();
            if (karateKlub != null) {
                karateKlub = em.getReference(karateKlub.getClass(), karateKlub.getIDKluba());
                ispit.setKarateKlub(karateKlub);
            }
            Collection<Polaganje> attachedPolaganjeCollection = new ArrayList<Polaganje>();
            for (Polaganje polaganjeCollectionPolaganjeToAttach : ispit.getPolaganjeCollection()) {
                polaganjeCollectionPolaganjeToAttach = em.getReference(polaganjeCollectionPolaganjeToAttach.getClass(), polaganjeCollectionPolaganjeToAttach.getPolaganjePK());
                attachedPolaganjeCollection.add(polaganjeCollectionPolaganjeToAttach);
            }
            ispit.setPolaganjeCollection(attachedPolaganjeCollection);
            em.persist(ispit);
            if (karateKlub != null) {
                karateKlub.getIspitCollection().add(ispit);
                karateKlub = em.merge(karateKlub);
            }
            for (Polaganje polaganjeCollectionPolaganje : ispit.getPolaganjeCollection()) {
                Ispit oldIspitOfPolaganjeCollectionPolaganje = polaganjeCollectionPolaganje.getIspit();
                polaganjeCollectionPolaganje.setIspit(ispit);
                polaganjeCollectionPolaganje = em.merge(polaganjeCollectionPolaganje);
                if (oldIspitOfPolaganjeCollectionPolaganje != null) {
                    oldIspitOfPolaganjeCollectionPolaganje.getPolaganjeCollection().remove(polaganjeCollectionPolaganje);
                    oldIspitOfPolaganjeCollectionPolaganje = em.merge(oldIspitOfPolaganjeCollectionPolaganje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findIspit(ispit.getIspitPK()) != null) {
                throw new PreexistingEntityException("Ispit " + ispit + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ispit ispit) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ispit.getIspitPK().setIDKluba(ispit.getKarateKlub().getIDKluba());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ispit persistentIspit = em.find(Ispit.class, ispit.getIspitPK());
            KarateKlub karateKlubOld = persistentIspit.getKarateKlub();
            KarateKlub karateKlubNew = ispit.getKarateKlub();
            Collection<Polaganje> polaganjeCollectionOld = persistentIspit.getPolaganjeCollection();
            Collection<Polaganje> polaganjeCollectionNew = ispit.getPolaganjeCollection();
            List<String> illegalOrphanMessages = null;
            for (Polaganje polaganjeCollectionOldPolaganje : polaganjeCollectionOld) {
                if (!polaganjeCollectionNew.contains(polaganjeCollectionOldPolaganje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Polaganje " + polaganjeCollectionOldPolaganje + " since its ispit field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (karateKlubNew != null) {
                karateKlubNew = em.getReference(karateKlubNew.getClass(), karateKlubNew.getIDKluba());
                ispit.setKarateKlub(karateKlubNew);
            }
            Collection<Polaganje> attachedPolaganjeCollectionNew = new ArrayList<Polaganje>();
            for (Polaganje polaganjeCollectionNewPolaganjeToAttach : polaganjeCollectionNew) {
                polaganjeCollectionNewPolaganjeToAttach = em.getReference(polaganjeCollectionNewPolaganjeToAttach.getClass(), polaganjeCollectionNewPolaganjeToAttach.getPolaganjePK());
                attachedPolaganjeCollectionNew.add(polaganjeCollectionNewPolaganjeToAttach);
            }
            polaganjeCollectionNew = attachedPolaganjeCollectionNew;
            ispit.setPolaganjeCollection(polaganjeCollectionNew);
            ispit = em.merge(ispit);
            if (karateKlubOld != null && !karateKlubOld.equals(karateKlubNew)) {
                karateKlubOld.getIspitCollection().remove(ispit);
                karateKlubOld = em.merge(karateKlubOld);
            }
            if (karateKlubNew != null && !karateKlubNew.equals(karateKlubOld)) {
                karateKlubNew.getIspitCollection().add(ispit);
                karateKlubNew = em.merge(karateKlubNew);
            }
            for (Polaganje polaganjeCollectionNewPolaganje : polaganjeCollectionNew) {
                if (!polaganjeCollectionOld.contains(polaganjeCollectionNewPolaganje)) {
                    Ispit oldIspitOfPolaganjeCollectionNewPolaganje = polaganjeCollectionNewPolaganje.getIspit();
                    polaganjeCollectionNewPolaganje.setIspit(ispit);
                    polaganjeCollectionNewPolaganje = em.merge(polaganjeCollectionNewPolaganje);
                    if (oldIspitOfPolaganjeCollectionNewPolaganje != null && !oldIspitOfPolaganjeCollectionNewPolaganje.equals(ispit)) {
                        oldIspitOfPolaganjeCollectionNewPolaganje.getPolaganjeCollection().remove(polaganjeCollectionNewPolaganje);
                        oldIspitOfPolaganjeCollectionNewPolaganje = em.merge(oldIspitOfPolaganjeCollectionNewPolaganje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                IspitPK id = ispit.getIspitPK();
                if (findIspit(id) == null) {
                    throw new NonexistentEntityException("The ispit with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(IspitPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ispit ispit;
            try {
                ispit = em.getReference(Ispit.class, id);
                ispit.getIspitPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ispit with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Polaganje> polaganjeCollectionOrphanCheck = ispit.getPolaganjeCollection();
            for (Polaganje polaganjeCollectionOrphanCheckPolaganje : polaganjeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ispit (" + ispit + ") cannot be destroyed since the Polaganje " + polaganjeCollectionOrphanCheckPolaganje + " in its polaganjeCollection field has a non-nullable ispit field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KarateKlub karateKlub = ispit.getKarateKlub();
            if (karateKlub != null) {
                karateKlub.getIspitCollection().remove(ispit);
                karateKlub = em.merge(karateKlub);
            }
            em.remove(ispit);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ispit> findIspitEntities() {
        return findIspitEntities(true, -1, -1);
    }

    public List<Ispit> findIspitEntities(int maxResults, int firstResult) {
        return findIspitEntities(false, maxResults, firstResult);
    }

    private List<Ispit> findIspitEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ispit.class));
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

    public Ispit findIspit(IspitPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ispit.class, id);
        } finally {
            em.close();
        }
    }

    public int getIspitCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ispit> rt = cq.from(Ispit.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
