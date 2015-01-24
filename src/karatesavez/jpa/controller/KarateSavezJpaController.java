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
import karatesavez.jpa.entity.KarateKamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.KarateSavez;

/**
 *
 * @author Marko
 */
public class KarateSavezJpaController implements Serializable {

    public KarateSavezJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KarateSavez karateSavez) throws PreexistingEntityException, Exception {
        if (karateSavez.getKarateKampCollection() == null) {
            karateSavez.setKarateKampCollection(new ArrayList<KarateKamp>());
        }
        if (karateSavez.getTakmicenjeCollection() == null) {
            karateSavez.setTakmicenjeCollection(new ArrayList<Takmicenje>());
        }
        if (karateSavez.getKarateKlubCollection() == null) {
            karateSavez.setKarateKlubCollection(new ArrayList<KarateKlub>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<KarateKamp> attachedKarateKampCollection = new ArrayList<KarateKamp>();
            for (KarateKamp karateKampCollectionKarateKampToAttach : karateSavez.getKarateKampCollection()) {
                karateKampCollectionKarateKampToAttach = em.getReference(karateKampCollectionKarateKampToAttach.getClass(), karateKampCollectionKarateKampToAttach.getIDKampa());
                attachedKarateKampCollection.add(karateKampCollectionKarateKampToAttach);
            }
            karateSavez.setKarateKampCollection(attachedKarateKampCollection);
            Collection<Takmicenje> attachedTakmicenjeCollection = new ArrayList<Takmicenje>();
            for (Takmicenje takmicenjeCollectionTakmicenjeToAttach : karateSavez.getTakmicenjeCollection()) {
                takmicenjeCollectionTakmicenjeToAttach = em.getReference(takmicenjeCollectionTakmicenjeToAttach.getClass(), takmicenjeCollectionTakmicenjeToAttach.getIDTakmicenja());
                attachedTakmicenjeCollection.add(takmicenjeCollectionTakmicenjeToAttach);
            }
            karateSavez.setTakmicenjeCollection(attachedTakmicenjeCollection);
            Collection<KarateKlub> attachedKarateKlubCollection = new ArrayList<KarateKlub>();
            for (KarateKlub karateKlubCollectionKarateKlubToAttach : karateSavez.getKarateKlubCollection()) {
                karateKlubCollectionKarateKlubToAttach = em.getReference(karateKlubCollectionKarateKlubToAttach.getClass(), karateKlubCollectionKarateKlubToAttach.getIDKluba());
                attachedKarateKlubCollection.add(karateKlubCollectionKarateKlubToAttach);
            }
            karateSavez.setKarateKlubCollection(attachedKarateKlubCollection);
            em.persist(karateSavez);
            for (KarateKamp karateKampCollectionKarateKamp : karateSavez.getKarateKampCollection()) {
                KarateSavez oldIDSavezaOfKarateKampCollectionKarateKamp = karateKampCollectionKarateKamp.getIDSaveza();
                karateKampCollectionKarateKamp.setIDSaveza(karateSavez);
                karateKampCollectionKarateKamp = em.merge(karateKampCollectionKarateKamp);
                if (oldIDSavezaOfKarateKampCollectionKarateKamp != null) {
                    oldIDSavezaOfKarateKampCollectionKarateKamp.getKarateKampCollection().remove(karateKampCollectionKarateKamp);
                    oldIDSavezaOfKarateKampCollectionKarateKamp = em.merge(oldIDSavezaOfKarateKampCollectionKarateKamp);
                }
            }
            for (Takmicenje takmicenjeCollectionTakmicenje : karateSavez.getTakmicenjeCollection()) {
                KarateSavez oldIDSavezaOfTakmicenjeCollectionTakmicenje = takmicenjeCollectionTakmicenje.getIDSaveza();
                takmicenjeCollectionTakmicenje.setIDSaveza(karateSavez);
                takmicenjeCollectionTakmicenje = em.merge(takmicenjeCollectionTakmicenje);
                if (oldIDSavezaOfTakmicenjeCollectionTakmicenje != null) {
                    oldIDSavezaOfTakmicenjeCollectionTakmicenje.getTakmicenjeCollection().remove(takmicenjeCollectionTakmicenje);
                    oldIDSavezaOfTakmicenjeCollectionTakmicenje = em.merge(oldIDSavezaOfTakmicenjeCollectionTakmicenje);
                }
            }
            for (KarateKlub karateKlubCollectionKarateKlub : karateSavez.getKarateKlubCollection()) {
                KarateSavez oldIDSavezaOfKarateKlubCollectionKarateKlub = karateKlubCollectionKarateKlub.getIDSaveza();
                karateKlubCollectionKarateKlub.setIDSaveza(karateSavez);
                karateKlubCollectionKarateKlub = em.merge(karateKlubCollectionKarateKlub);
                if (oldIDSavezaOfKarateKlubCollectionKarateKlub != null) {
                    oldIDSavezaOfKarateKlubCollectionKarateKlub.getKarateKlubCollection().remove(karateKlubCollectionKarateKlub);
                    oldIDSavezaOfKarateKlubCollectionKarateKlub = em.merge(oldIDSavezaOfKarateKlubCollectionKarateKlub);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKarateSavez(karateSavez.getIDSaveza()) != null) {
                throw new PreexistingEntityException("KarateSavez " + karateSavez + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KarateSavez karateSavez) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateSavez persistentKarateSavez = em.find(KarateSavez.class, karateSavez.getIDSaveza());
            Collection<KarateKamp> karateKampCollectionOld = persistentKarateSavez.getKarateKampCollection();
            Collection<KarateKamp> karateKampCollectionNew = karateSavez.getKarateKampCollection();
            Collection<Takmicenje> takmicenjeCollectionOld = persistentKarateSavez.getTakmicenjeCollection();
            Collection<Takmicenje> takmicenjeCollectionNew = karateSavez.getTakmicenjeCollection();
            Collection<KarateKlub> karateKlubCollectionOld = persistentKarateSavez.getKarateKlubCollection();
            Collection<KarateKlub> karateKlubCollectionNew = karateSavez.getKarateKlubCollection();
            List<String> illegalOrphanMessages = null;
            for (KarateKamp karateKampCollectionOldKarateKamp : karateKampCollectionOld) {
                if (!karateKampCollectionNew.contains(karateKampCollectionOldKarateKamp)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KarateKamp " + karateKampCollectionOldKarateKamp + " since its IDSaveza field is not nullable.");
                }
            }
            for (KarateKlub karateKlubCollectionOldKarateKlub : karateKlubCollectionOld) {
                if (!karateKlubCollectionNew.contains(karateKlubCollectionOldKarateKlub)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain KarateKlub " + karateKlubCollectionOldKarateKlub + " since its IDSaveza field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<KarateKamp> attachedKarateKampCollectionNew = new ArrayList<KarateKamp>();
            for (KarateKamp karateKampCollectionNewKarateKampToAttach : karateKampCollectionNew) {
                karateKampCollectionNewKarateKampToAttach = em.getReference(karateKampCollectionNewKarateKampToAttach.getClass(), karateKampCollectionNewKarateKampToAttach.getIDKampa());
                attachedKarateKampCollectionNew.add(karateKampCollectionNewKarateKampToAttach);
            }
            karateKampCollectionNew = attachedKarateKampCollectionNew;
            karateSavez.setKarateKampCollection(karateKampCollectionNew);
            Collection<Takmicenje> attachedTakmicenjeCollectionNew = new ArrayList<Takmicenje>();
            for (Takmicenje takmicenjeCollectionNewTakmicenjeToAttach : takmicenjeCollectionNew) {
                takmicenjeCollectionNewTakmicenjeToAttach = em.getReference(takmicenjeCollectionNewTakmicenjeToAttach.getClass(), takmicenjeCollectionNewTakmicenjeToAttach.getIDTakmicenja());
                attachedTakmicenjeCollectionNew.add(takmicenjeCollectionNewTakmicenjeToAttach);
            }
            takmicenjeCollectionNew = attachedTakmicenjeCollectionNew;
            karateSavez.setTakmicenjeCollection(takmicenjeCollectionNew);
            Collection<KarateKlub> attachedKarateKlubCollectionNew = new ArrayList<KarateKlub>();
            for (KarateKlub karateKlubCollectionNewKarateKlubToAttach : karateKlubCollectionNew) {
                karateKlubCollectionNewKarateKlubToAttach = em.getReference(karateKlubCollectionNewKarateKlubToAttach.getClass(), karateKlubCollectionNewKarateKlubToAttach.getIDKluba());
                attachedKarateKlubCollectionNew.add(karateKlubCollectionNewKarateKlubToAttach);
            }
            karateKlubCollectionNew = attachedKarateKlubCollectionNew;
            karateSavez.setKarateKlubCollection(karateKlubCollectionNew);
            karateSavez = em.merge(karateSavez);
            for (KarateKamp karateKampCollectionNewKarateKamp : karateKampCollectionNew) {
                if (!karateKampCollectionOld.contains(karateKampCollectionNewKarateKamp)) {
                    KarateSavez oldIDSavezaOfKarateKampCollectionNewKarateKamp = karateKampCollectionNewKarateKamp.getIDSaveza();
                    karateKampCollectionNewKarateKamp.setIDSaveza(karateSavez);
                    karateKampCollectionNewKarateKamp = em.merge(karateKampCollectionNewKarateKamp);
                    if (oldIDSavezaOfKarateKampCollectionNewKarateKamp != null && !oldIDSavezaOfKarateKampCollectionNewKarateKamp.equals(karateSavez)) {
                        oldIDSavezaOfKarateKampCollectionNewKarateKamp.getKarateKampCollection().remove(karateKampCollectionNewKarateKamp);
                        oldIDSavezaOfKarateKampCollectionNewKarateKamp = em.merge(oldIDSavezaOfKarateKampCollectionNewKarateKamp);
                    }
                }
            }
            for (Takmicenje takmicenjeCollectionOldTakmicenje : takmicenjeCollectionOld) {
                if (!takmicenjeCollectionNew.contains(takmicenjeCollectionOldTakmicenje)) {
                    takmicenjeCollectionOldTakmicenje.setIDSaveza(null);
                    takmicenjeCollectionOldTakmicenje = em.merge(takmicenjeCollectionOldTakmicenje);
                }
            }
            for (Takmicenje takmicenjeCollectionNewTakmicenje : takmicenjeCollectionNew) {
                if (!takmicenjeCollectionOld.contains(takmicenjeCollectionNewTakmicenje)) {
                    KarateSavez oldIDSavezaOfTakmicenjeCollectionNewTakmicenje = takmicenjeCollectionNewTakmicenje.getIDSaveza();
                    takmicenjeCollectionNewTakmicenje.setIDSaveza(karateSavez);
                    takmicenjeCollectionNewTakmicenje = em.merge(takmicenjeCollectionNewTakmicenje);
                    if (oldIDSavezaOfTakmicenjeCollectionNewTakmicenje != null && !oldIDSavezaOfTakmicenjeCollectionNewTakmicenje.equals(karateSavez)) {
                        oldIDSavezaOfTakmicenjeCollectionNewTakmicenje.getTakmicenjeCollection().remove(takmicenjeCollectionNewTakmicenje);
                        oldIDSavezaOfTakmicenjeCollectionNewTakmicenje = em.merge(oldIDSavezaOfTakmicenjeCollectionNewTakmicenje);
                    }
                }
            }
            for (KarateKlub karateKlubCollectionNewKarateKlub : karateKlubCollectionNew) {
                if (!karateKlubCollectionOld.contains(karateKlubCollectionNewKarateKlub)) {
                    KarateSavez oldIDSavezaOfKarateKlubCollectionNewKarateKlub = karateKlubCollectionNewKarateKlub.getIDSaveza();
                    karateKlubCollectionNewKarateKlub.setIDSaveza(karateSavez);
                    karateKlubCollectionNewKarateKlub = em.merge(karateKlubCollectionNewKarateKlub);
                    if (oldIDSavezaOfKarateKlubCollectionNewKarateKlub != null && !oldIDSavezaOfKarateKlubCollectionNewKarateKlub.equals(karateSavez)) {
                        oldIDSavezaOfKarateKlubCollectionNewKarateKlub.getKarateKlubCollection().remove(karateKlubCollectionNewKarateKlub);
                        oldIDSavezaOfKarateKlubCollectionNewKarateKlub = em.merge(oldIDSavezaOfKarateKlubCollectionNewKarateKlub);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = karateSavez.getIDSaveza();
                if (findKarateSavez(id) == null) {
                    throw new NonexistentEntityException("The karateSavez with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateSavez karateSavez;
            try {
                karateSavez = em.getReference(KarateSavez.class, id);
                karateSavez.getIDSaveza();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The karateSavez with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<KarateKamp> karateKampCollectionOrphanCheck = karateSavez.getKarateKampCollection();
            for (KarateKamp karateKampCollectionOrphanCheckKarateKamp : karateKampCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateSavez (" + karateSavez + ") cannot be destroyed since the KarateKamp " + karateKampCollectionOrphanCheckKarateKamp + " in its karateKampCollection field has a non-nullable IDSaveza field.");
            }
            Collection<KarateKlub> karateKlubCollectionOrphanCheck = karateSavez.getKarateKlubCollection();
            for (KarateKlub karateKlubCollectionOrphanCheckKarateKlub : karateKlubCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateSavez (" + karateSavez + ") cannot be destroyed since the KarateKlub " + karateKlubCollectionOrphanCheckKarateKlub + " in its karateKlubCollection field has a non-nullable IDSaveza field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Takmicenje> takmicenjeCollection = karateSavez.getTakmicenjeCollection();
            for (Takmicenje takmicenjeCollectionTakmicenje : takmicenjeCollection) {
                takmicenjeCollectionTakmicenje.setIDSaveza(null);
                takmicenjeCollectionTakmicenje = em.merge(takmicenjeCollectionTakmicenje);
            }
            em.remove(karateSavez);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KarateSavez> findKarateSavezEntities() {
        return findKarateSavezEntities(true, -1, -1);
    }

    public List<KarateSavez> findKarateSavezEntities(int maxResults, int firstResult) {
        return findKarateSavezEntities(false, maxResults, firstResult);
    }

    private List<KarateSavez> findKarateSavezEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KarateSavez.class));
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

    public KarateSavez findKarateSavez(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KarateSavez.class, id);
        } finally {
            em.close();
        }
    }

    public int getKarateSavezCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KarateSavez> rt = cq.from(KarateSavez.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
