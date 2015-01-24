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
import karatesavez.jpa.entity.KarateSavez;
import karatesavez.jpa.entity.Ispit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Ekipa;
import karatesavez.jpa.entity.Trening;
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.Clan;
import karatesavez.jpa.entity.KarateKlub;

/**
 *
 * @author Marko
 */
public class KarateKlubJpaController implements Serializable {

    public KarateKlubJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KarateKlub karateKlub) throws PreexistingEntityException, Exception {
        if (karateKlub.getIspitCollection() == null) {
            karateKlub.setIspitCollection(new ArrayList<Ispit>());
        }
        if (karateKlub.getEkipaCollection() == null) {
            karateKlub.setEkipaCollection(new ArrayList<Ekipa>());
        }
        if (karateKlub.getTreningCollection() == null) {
            karateKlub.setTreningCollection(new ArrayList<Trening>());
        }
        if (karateKlub.getTakmicenjeCollection() == null) {
            karateKlub.setTakmicenjeCollection(new ArrayList<Takmicenje>());
        }
        if (karateKlub.getClanCollection() == null) {
            karateKlub.setClanCollection(new ArrayList<Clan>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateSavez IDSaveza = karateKlub.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza = em.getReference(IDSaveza.getClass(), IDSaveza.getIDSaveza());
                karateKlub.setIDSaveza(IDSaveza);
            }
            Collection<Ispit> attachedIspitCollection = new ArrayList<Ispit>();
            for (Ispit ispitCollectionIspitToAttach : karateKlub.getIspitCollection()) {
                ispitCollectionIspitToAttach = em.getReference(ispitCollectionIspitToAttach.getClass(), ispitCollectionIspitToAttach.getIspitPK());
                attachedIspitCollection.add(ispitCollectionIspitToAttach);
            }
            karateKlub.setIspitCollection(attachedIspitCollection);
            Collection<Ekipa> attachedEkipaCollection = new ArrayList<Ekipa>();
            for (Ekipa ekipaCollectionEkipaToAttach : karateKlub.getEkipaCollection()) {
                ekipaCollectionEkipaToAttach = em.getReference(ekipaCollectionEkipaToAttach.getClass(), ekipaCollectionEkipaToAttach.getIDEkipe());
                attachedEkipaCollection.add(ekipaCollectionEkipaToAttach);
            }
            karateKlub.setEkipaCollection(attachedEkipaCollection);
            Collection<Trening> attachedTreningCollection = new ArrayList<Trening>();
            for (Trening treningCollectionTreningToAttach : karateKlub.getTreningCollection()) {
                treningCollectionTreningToAttach = em.getReference(treningCollectionTreningToAttach.getClass(), treningCollectionTreningToAttach.getTreningPK());
                attachedTreningCollection.add(treningCollectionTreningToAttach);
            }
            karateKlub.setTreningCollection(attachedTreningCollection);
            Collection<Takmicenje> attachedTakmicenjeCollection = new ArrayList<Takmicenje>();
            for (Takmicenje takmicenjeCollectionTakmicenjeToAttach : karateKlub.getTakmicenjeCollection()) {
                takmicenjeCollectionTakmicenjeToAttach = em.getReference(takmicenjeCollectionTakmicenjeToAttach.getClass(), takmicenjeCollectionTakmicenjeToAttach.getIDTakmicenja());
                attachedTakmicenjeCollection.add(takmicenjeCollectionTakmicenjeToAttach);
            }
            karateKlub.setTakmicenjeCollection(attachedTakmicenjeCollection);
            Collection<Clan> attachedClanCollection = new ArrayList<Clan>();
            for (Clan clanCollectionClanToAttach : karateKlub.getClanCollection()) {
                clanCollectionClanToAttach = em.getReference(clanCollectionClanToAttach.getClass(), clanCollectionClanToAttach.getJmb());
                attachedClanCollection.add(clanCollectionClanToAttach);
            }
            karateKlub.setClanCollection(attachedClanCollection);
            em.persist(karateKlub);
            if (IDSaveza != null) {
                IDSaveza.getKarateKlubCollection().add(karateKlub);
                IDSaveza = em.merge(IDSaveza);
            }
            for (Ispit ispitCollectionIspit : karateKlub.getIspitCollection()) {
                KarateKlub oldKarateKlubOfIspitCollectionIspit = ispitCollectionIspit.getKarateKlub();
                ispitCollectionIspit.setKarateKlub(karateKlub);
                ispitCollectionIspit = em.merge(ispitCollectionIspit);
                if (oldKarateKlubOfIspitCollectionIspit != null) {
                    oldKarateKlubOfIspitCollectionIspit.getIspitCollection().remove(ispitCollectionIspit);
                    oldKarateKlubOfIspitCollectionIspit = em.merge(oldKarateKlubOfIspitCollectionIspit);
                }
            }
            for (Ekipa ekipaCollectionEkipa : karateKlub.getEkipaCollection()) {
                KarateKlub oldIDKlubaOfEkipaCollectionEkipa = ekipaCollectionEkipa.getIDKluba();
                ekipaCollectionEkipa.setIDKluba(karateKlub);
                ekipaCollectionEkipa = em.merge(ekipaCollectionEkipa);
                if (oldIDKlubaOfEkipaCollectionEkipa != null) {
                    oldIDKlubaOfEkipaCollectionEkipa.getEkipaCollection().remove(ekipaCollectionEkipa);
                    oldIDKlubaOfEkipaCollectionEkipa = em.merge(oldIDKlubaOfEkipaCollectionEkipa);
                }
            }
            for (Trening treningCollectionTrening : karateKlub.getTreningCollection()) {
                KarateKlub oldKarateKlubOfTreningCollectionTrening = treningCollectionTrening.getKarateKlub();
                treningCollectionTrening.setKarateKlub(karateKlub);
                treningCollectionTrening = em.merge(treningCollectionTrening);
                if (oldKarateKlubOfTreningCollectionTrening != null) {
                    oldKarateKlubOfTreningCollectionTrening.getTreningCollection().remove(treningCollectionTrening);
                    oldKarateKlubOfTreningCollectionTrening = em.merge(oldKarateKlubOfTreningCollectionTrening);
                }
            }
            for (Takmicenje takmicenjeCollectionTakmicenje : karateKlub.getTakmicenjeCollection()) {
                KarateKlub oldIDKlubaOfTakmicenjeCollectionTakmicenje = takmicenjeCollectionTakmicenje.getIDKluba();
                takmicenjeCollectionTakmicenje.setIDKluba(karateKlub);
                takmicenjeCollectionTakmicenje = em.merge(takmicenjeCollectionTakmicenje);
                if (oldIDKlubaOfTakmicenjeCollectionTakmicenje != null) {
                    oldIDKlubaOfTakmicenjeCollectionTakmicenje.getTakmicenjeCollection().remove(takmicenjeCollectionTakmicenje);
                    oldIDKlubaOfTakmicenjeCollectionTakmicenje = em.merge(oldIDKlubaOfTakmicenjeCollectionTakmicenje);
                }
            }
            for (Clan clanCollectionClan : karateKlub.getClanCollection()) {
                KarateKlub oldIDKlubaOfClanCollectionClan = clanCollectionClan.getIDKluba();
                clanCollectionClan.setIDKluba(karateKlub);
                clanCollectionClan = em.merge(clanCollectionClan);
                if (oldIDKlubaOfClanCollectionClan != null) {
                    oldIDKlubaOfClanCollectionClan.getClanCollection().remove(clanCollectionClan);
                    oldIDKlubaOfClanCollectionClan = em.merge(oldIDKlubaOfClanCollectionClan);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKarateKlub(karateKlub.getIDKluba()) != null) {
                throw new PreexistingEntityException("KarateKlub " + karateKlub + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KarateKlub karateKlub) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKlub persistentKarateKlub = em.find(KarateKlub.class, karateKlub.getIDKluba());
            KarateSavez IDSavezaOld = persistentKarateKlub.getIDSaveza();
            KarateSavez IDSavezaNew = karateKlub.getIDSaveza();
            Collection<Ispit> ispitCollectionOld = persistentKarateKlub.getIspitCollection();
            Collection<Ispit> ispitCollectionNew = karateKlub.getIspitCollection();
            Collection<Ekipa> ekipaCollectionOld = persistentKarateKlub.getEkipaCollection();
            Collection<Ekipa> ekipaCollectionNew = karateKlub.getEkipaCollection();
            Collection<Trening> treningCollectionOld = persistentKarateKlub.getTreningCollection();
            Collection<Trening> treningCollectionNew = karateKlub.getTreningCollection();
            Collection<Takmicenje> takmicenjeCollectionOld = persistentKarateKlub.getTakmicenjeCollection();
            Collection<Takmicenje> takmicenjeCollectionNew = karateKlub.getTakmicenjeCollection();
            Collection<Clan> clanCollectionOld = persistentKarateKlub.getClanCollection();
            Collection<Clan> clanCollectionNew = karateKlub.getClanCollection();
            List<String> illegalOrphanMessages = null;
            for (Ispit ispitCollectionOldIspit : ispitCollectionOld) {
                if (!ispitCollectionNew.contains(ispitCollectionOldIspit)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ispit " + ispitCollectionOldIspit + " since its karateKlub field is not nullable.");
                }
            }
            for (Ekipa ekipaCollectionOldEkipa : ekipaCollectionOld) {
                if (!ekipaCollectionNew.contains(ekipaCollectionOldEkipa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ekipa " + ekipaCollectionOldEkipa + " since its IDKluba field is not nullable.");
                }
            }
            for (Trening treningCollectionOldTrening : treningCollectionOld) {
                if (!treningCollectionNew.contains(treningCollectionOldTrening)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Trening " + treningCollectionOldTrening + " since its karateKlub field is not nullable.");
                }
            }
            for (Clan clanCollectionOldClan : clanCollectionOld) {
                if (!clanCollectionNew.contains(clanCollectionOldClan)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Clan " + clanCollectionOldClan + " since its IDKluba field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDSavezaNew != null) {
                IDSavezaNew = em.getReference(IDSavezaNew.getClass(), IDSavezaNew.getIDSaveza());
                karateKlub.setIDSaveza(IDSavezaNew);
            }
            Collection<Ispit> attachedIspitCollectionNew = new ArrayList<Ispit>();
            for (Ispit ispitCollectionNewIspitToAttach : ispitCollectionNew) {
                ispitCollectionNewIspitToAttach = em.getReference(ispitCollectionNewIspitToAttach.getClass(), ispitCollectionNewIspitToAttach.getIspitPK());
                attachedIspitCollectionNew.add(ispitCollectionNewIspitToAttach);
            }
            ispitCollectionNew = attachedIspitCollectionNew;
            karateKlub.setIspitCollection(ispitCollectionNew);
            Collection<Ekipa> attachedEkipaCollectionNew = new ArrayList<Ekipa>();
            for (Ekipa ekipaCollectionNewEkipaToAttach : ekipaCollectionNew) {
                ekipaCollectionNewEkipaToAttach = em.getReference(ekipaCollectionNewEkipaToAttach.getClass(), ekipaCollectionNewEkipaToAttach.getIDEkipe());
                attachedEkipaCollectionNew.add(ekipaCollectionNewEkipaToAttach);
            }
            ekipaCollectionNew = attachedEkipaCollectionNew;
            karateKlub.setEkipaCollection(ekipaCollectionNew);
            Collection<Trening> attachedTreningCollectionNew = new ArrayList<Trening>();
            for (Trening treningCollectionNewTreningToAttach : treningCollectionNew) {
                treningCollectionNewTreningToAttach = em.getReference(treningCollectionNewTreningToAttach.getClass(), treningCollectionNewTreningToAttach.getTreningPK());
                attachedTreningCollectionNew.add(treningCollectionNewTreningToAttach);
            }
            treningCollectionNew = attachedTreningCollectionNew;
            karateKlub.setTreningCollection(treningCollectionNew);
            Collection<Takmicenje> attachedTakmicenjeCollectionNew = new ArrayList<Takmicenje>();
            for (Takmicenje takmicenjeCollectionNewTakmicenjeToAttach : takmicenjeCollectionNew) {
                takmicenjeCollectionNewTakmicenjeToAttach = em.getReference(takmicenjeCollectionNewTakmicenjeToAttach.getClass(), takmicenjeCollectionNewTakmicenjeToAttach.getIDTakmicenja());
                attachedTakmicenjeCollectionNew.add(takmicenjeCollectionNewTakmicenjeToAttach);
            }
            takmicenjeCollectionNew = attachedTakmicenjeCollectionNew;
            karateKlub.setTakmicenjeCollection(takmicenjeCollectionNew);
            Collection<Clan> attachedClanCollectionNew = new ArrayList<Clan>();
            for (Clan clanCollectionNewClanToAttach : clanCollectionNew) {
                clanCollectionNewClanToAttach = em.getReference(clanCollectionNewClanToAttach.getClass(), clanCollectionNewClanToAttach.getJmb());
                attachedClanCollectionNew.add(clanCollectionNewClanToAttach);
            }
            clanCollectionNew = attachedClanCollectionNew;
            karateKlub.setClanCollection(clanCollectionNew);
            karateKlub = em.merge(karateKlub);
            if (IDSavezaOld != null && !IDSavezaOld.equals(IDSavezaNew)) {
                IDSavezaOld.getKarateKlubCollection().remove(karateKlub);
                IDSavezaOld = em.merge(IDSavezaOld);
            }
            if (IDSavezaNew != null && !IDSavezaNew.equals(IDSavezaOld)) {
                IDSavezaNew.getKarateKlubCollection().add(karateKlub);
                IDSavezaNew = em.merge(IDSavezaNew);
            }
            for (Ispit ispitCollectionNewIspit : ispitCollectionNew) {
                if (!ispitCollectionOld.contains(ispitCollectionNewIspit)) {
                    KarateKlub oldKarateKlubOfIspitCollectionNewIspit = ispitCollectionNewIspit.getKarateKlub();
                    ispitCollectionNewIspit.setKarateKlub(karateKlub);
                    ispitCollectionNewIspit = em.merge(ispitCollectionNewIspit);
                    if (oldKarateKlubOfIspitCollectionNewIspit != null && !oldKarateKlubOfIspitCollectionNewIspit.equals(karateKlub)) {
                        oldKarateKlubOfIspitCollectionNewIspit.getIspitCollection().remove(ispitCollectionNewIspit);
                        oldKarateKlubOfIspitCollectionNewIspit = em.merge(oldKarateKlubOfIspitCollectionNewIspit);
                    }
                }
            }
            for (Ekipa ekipaCollectionNewEkipa : ekipaCollectionNew) {
                if (!ekipaCollectionOld.contains(ekipaCollectionNewEkipa)) {
                    KarateKlub oldIDKlubaOfEkipaCollectionNewEkipa = ekipaCollectionNewEkipa.getIDKluba();
                    ekipaCollectionNewEkipa.setIDKluba(karateKlub);
                    ekipaCollectionNewEkipa = em.merge(ekipaCollectionNewEkipa);
                    if (oldIDKlubaOfEkipaCollectionNewEkipa != null && !oldIDKlubaOfEkipaCollectionNewEkipa.equals(karateKlub)) {
                        oldIDKlubaOfEkipaCollectionNewEkipa.getEkipaCollection().remove(ekipaCollectionNewEkipa);
                        oldIDKlubaOfEkipaCollectionNewEkipa = em.merge(oldIDKlubaOfEkipaCollectionNewEkipa);
                    }
                }
            }
            for (Trening treningCollectionNewTrening : treningCollectionNew) {
                if (!treningCollectionOld.contains(treningCollectionNewTrening)) {
                    KarateKlub oldKarateKlubOfTreningCollectionNewTrening = treningCollectionNewTrening.getKarateKlub();
                    treningCollectionNewTrening.setKarateKlub(karateKlub);
                    treningCollectionNewTrening = em.merge(treningCollectionNewTrening);
                    if (oldKarateKlubOfTreningCollectionNewTrening != null && !oldKarateKlubOfTreningCollectionNewTrening.equals(karateKlub)) {
                        oldKarateKlubOfTreningCollectionNewTrening.getTreningCollection().remove(treningCollectionNewTrening);
                        oldKarateKlubOfTreningCollectionNewTrening = em.merge(oldKarateKlubOfTreningCollectionNewTrening);
                    }
                }
            }
            for (Takmicenje takmicenjeCollectionOldTakmicenje : takmicenjeCollectionOld) {
                if (!takmicenjeCollectionNew.contains(takmicenjeCollectionOldTakmicenje)) {
                    takmicenjeCollectionOldTakmicenje.setIDKluba(null);
                    takmicenjeCollectionOldTakmicenje = em.merge(takmicenjeCollectionOldTakmicenje);
                }
            }
            for (Takmicenje takmicenjeCollectionNewTakmicenje : takmicenjeCollectionNew) {
                if (!takmicenjeCollectionOld.contains(takmicenjeCollectionNewTakmicenje)) {
                    KarateKlub oldIDKlubaOfTakmicenjeCollectionNewTakmicenje = takmicenjeCollectionNewTakmicenje.getIDKluba();
                    takmicenjeCollectionNewTakmicenje.setIDKluba(karateKlub);
                    takmicenjeCollectionNewTakmicenje = em.merge(takmicenjeCollectionNewTakmicenje);
                    if (oldIDKlubaOfTakmicenjeCollectionNewTakmicenje != null && !oldIDKlubaOfTakmicenjeCollectionNewTakmicenje.equals(karateKlub)) {
                        oldIDKlubaOfTakmicenjeCollectionNewTakmicenje.getTakmicenjeCollection().remove(takmicenjeCollectionNewTakmicenje);
                        oldIDKlubaOfTakmicenjeCollectionNewTakmicenje = em.merge(oldIDKlubaOfTakmicenjeCollectionNewTakmicenje);
                    }
                }
            }
            for (Clan clanCollectionNewClan : clanCollectionNew) {
                if (!clanCollectionOld.contains(clanCollectionNewClan)) {
                    KarateKlub oldIDKlubaOfClanCollectionNewClan = clanCollectionNewClan.getIDKluba();
                    clanCollectionNewClan.setIDKluba(karateKlub);
                    clanCollectionNewClan = em.merge(clanCollectionNewClan);
                    if (oldIDKlubaOfClanCollectionNewClan != null && !oldIDKlubaOfClanCollectionNewClan.equals(karateKlub)) {
                        oldIDKlubaOfClanCollectionNewClan.getClanCollection().remove(clanCollectionNewClan);
                        oldIDKlubaOfClanCollectionNewClan = em.merge(oldIDKlubaOfClanCollectionNewClan);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = karateKlub.getIDKluba();
                if (findKarateKlub(id) == null) {
                    throw new NonexistentEntityException("The karateKlub with id " + id + " no longer exists.");
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
            KarateKlub karateKlub;
            try {
                karateKlub = em.getReference(KarateKlub.class, id);
                karateKlub.getIDKluba();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The karateKlub with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Ispit> ispitCollectionOrphanCheck = karateKlub.getIspitCollection();
            for (Ispit ispitCollectionOrphanCheckIspit : ispitCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateKlub (" + karateKlub + ") cannot be destroyed since the Ispit " + ispitCollectionOrphanCheckIspit + " in its ispitCollection field has a non-nullable karateKlub field.");
            }
            Collection<Ekipa> ekipaCollectionOrphanCheck = karateKlub.getEkipaCollection();
            for (Ekipa ekipaCollectionOrphanCheckEkipa : ekipaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateKlub (" + karateKlub + ") cannot be destroyed since the Ekipa " + ekipaCollectionOrphanCheckEkipa + " in its ekipaCollection field has a non-nullable IDKluba field.");
            }
            Collection<Trening> treningCollectionOrphanCheck = karateKlub.getTreningCollection();
            for (Trening treningCollectionOrphanCheckTrening : treningCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateKlub (" + karateKlub + ") cannot be destroyed since the Trening " + treningCollectionOrphanCheckTrening + " in its treningCollection field has a non-nullable karateKlub field.");
            }
            Collection<Clan> clanCollectionOrphanCheck = karateKlub.getClanCollection();
            for (Clan clanCollectionOrphanCheckClan : clanCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KarateKlub (" + karateKlub + ") cannot be destroyed since the Clan " + clanCollectionOrphanCheckClan + " in its clanCollection field has a non-nullable IDKluba field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KarateSavez IDSaveza = karateKlub.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza.getKarateKlubCollection().remove(karateKlub);
                IDSaveza = em.merge(IDSaveza);
            }
            Collection<Takmicenje> takmicenjeCollection = karateKlub.getTakmicenjeCollection();
            for (Takmicenje takmicenjeCollectionTakmicenje : takmicenjeCollection) {
                takmicenjeCollectionTakmicenje.setIDKluba(null);
                takmicenjeCollectionTakmicenje = em.merge(takmicenjeCollectionTakmicenje);
            }
            em.remove(karateKlub);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KarateKlub> findKarateKlubEntities() {
        return findKarateKlubEntities(true, -1, -1);
    }

    public List<KarateKlub> findKarateKlubEntities(int maxResults, int firstResult) {
        return findKarateKlubEntities(false, maxResults, firstResult);
    }

    private List<KarateKlub> findKarateKlubEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KarateKlub.class));
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

    public KarateKlub findKarateKlub(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KarateKlub.class, id);
        } finally {
            em.close();
        }
    }

    public int getKarateKlubCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KarateKlub> rt = cq.from(KarateKlub.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
