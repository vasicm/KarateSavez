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
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.TakmicenjeKategorija;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.UcesceEkipe;
import karatesavez.jpa.entity.UcescePojedinca;

/**
 *
 * @author Marko
 */
public class TakmicenjeJpaController implements Serializable {

    public TakmicenjeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Takmicenje takmicenje) throws PreexistingEntityException, Exception {
        if (takmicenje.getTakmicenjeKategorijaCollection() == null) {
            takmicenje.setTakmicenjeKategorijaCollection(new ArrayList<TakmicenjeKategorija>());
        }
        if (takmicenje.getUcesceEkipeCollection() == null) {
            takmicenje.setUcesceEkipeCollection(new ArrayList<UcesceEkipe>());
        }
        if (takmicenje.getUcescePojedincaCollection() == null) {
            takmicenje.setUcescePojedincaCollection(new ArrayList<UcescePojedinca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateSavez IDSaveza = takmicenje.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza = em.getReference(IDSaveza.getClass(), IDSaveza.getIDSaveza());
                takmicenje.setIDSaveza(IDSaveza);
            }
            KarateKlub IDKluba = takmicenje.getIDKluba();
            if (IDKluba != null) {
                IDKluba = em.getReference(IDKluba.getClass(), IDKluba.getIDKluba());
                takmicenje.setIDKluba(IDKluba);
            }
            Collection<TakmicenjeKategorija> attachedTakmicenjeKategorijaCollection = new ArrayList<TakmicenjeKategorija>();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach : takmicenje.getTakmicenjeKategorijaCollection()) {
                takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach = em.getReference(takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach.getClass(), takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach.getTakmicenjeKategorijaPK());
                attachedTakmicenjeKategorijaCollection.add(takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach);
            }
            takmicenje.setTakmicenjeKategorijaCollection(attachedTakmicenjeKategorijaCollection);
            Collection<UcesceEkipe> attachedUcesceEkipeCollection = new ArrayList<UcesceEkipe>();
            for (UcesceEkipe ucesceEkipeCollectionUcesceEkipeToAttach : takmicenje.getUcesceEkipeCollection()) {
                ucesceEkipeCollectionUcesceEkipeToAttach = em.getReference(ucesceEkipeCollectionUcesceEkipeToAttach.getClass(), ucesceEkipeCollectionUcesceEkipeToAttach.getUcesceEkipePK());
                attachedUcesceEkipeCollection.add(ucesceEkipeCollectionUcesceEkipeToAttach);
            }
            takmicenje.setUcesceEkipeCollection(attachedUcesceEkipeCollection);
            Collection<UcescePojedinca> attachedUcescePojedincaCollection = new ArrayList<UcescePojedinca>();
            for (UcescePojedinca ucescePojedincaCollectionUcescePojedincaToAttach : takmicenje.getUcescePojedincaCollection()) {
                ucescePojedincaCollectionUcescePojedincaToAttach = em.getReference(ucescePojedincaCollectionUcescePojedincaToAttach.getClass(), ucescePojedincaCollectionUcescePojedincaToAttach.getUcescePojedincaPK());
                attachedUcescePojedincaCollection.add(ucescePojedincaCollectionUcescePojedincaToAttach);
            }
            takmicenje.setUcescePojedincaCollection(attachedUcescePojedincaCollection);
            em.persist(takmicenje);
            if (IDSaveza != null) {
                IDSaveza.getTakmicenjeCollection().add(takmicenje);
                IDSaveza = em.merge(IDSaveza);
            }
            if (IDKluba != null) {
                IDKluba.getTakmicenjeCollection().add(takmicenje);
                IDKluba = em.merge(IDKluba);
            }
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionTakmicenjeKategorija : takmicenje.getTakmicenjeKategorijaCollection()) {
                Takmicenje oldTakmicenjeOfTakmicenjeKategorijaCollectionTakmicenjeKategorija = takmicenjeKategorijaCollectionTakmicenjeKategorija.getTakmicenje();
                takmicenjeKategorijaCollectionTakmicenjeKategorija.setTakmicenje(takmicenje);
                takmicenjeKategorijaCollectionTakmicenjeKategorija = em.merge(takmicenjeKategorijaCollectionTakmicenjeKategorija);
                if (oldTakmicenjeOfTakmicenjeKategorijaCollectionTakmicenjeKategorija != null) {
                    oldTakmicenjeOfTakmicenjeKategorijaCollectionTakmicenjeKategorija.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorijaCollectionTakmicenjeKategorija);
                    oldTakmicenjeOfTakmicenjeKategorijaCollectionTakmicenjeKategorija = em.merge(oldTakmicenjeOfTakmicenjeKategorijaCollectionTakmicenjeKategorija);
                }
            }
            for (UcesceEkipe ucesceEkipeCollectionUcesceEkipe : takmicenje.getUcesceEkipeCollection()) {
                Takmicenje oldTakmicenjeOfUcesceEkipeCollectionUcesceEkipe = ucesceEkipeCollectionUcesceEkipe.getTakmicenje();
                ucesceEkipeCollectionUcesceEkipe.setTakmicenje(takmicenje);
                ucesceEkipeCollectionUcesceEkipe = em.merge(ucesceEkipeCollectionUcesceEkipe);
                if (oldTakmicenjeOfUcesceEkipeCollectionUcesceEkipe != null) {
                    oldTakmicenjeOfUcesceEkipeCollectionUcesceEkipe.getUcesceEkipeCollection().remove(ucesceEkipeCollectionUcesceEkipe);
                    oldTakmicenjeOfUcesceEkipeCollectionUcesceEkipe = em.merge(oldTakmicenjeOfUcesceEkipeCollectionUcesceEkipe);
                }
            }
            for (UcescePojedinca ucescePojedincaCollectionUcescePojedinca : takmicenje.getUcescePojedincaCollection()) {
                Takmicenje oldTakmicenjeOfUcescePojedincaCollectionUcescePojedinca = ucescePojedincaCollectionUcescePojedinca.getTakmicenje();
                ucescePojedincaCollectionUcescePojedinca.setTakmicenje(takmicenje);
                ucescePojedincaCollectionUcescePojedinca = em.merge(ucescePojedincaCollectionUcescePojedinca);
                if (oldTakmicenjeOfUcescePojedincaCollectionUcescePojedinca != null) {
                    oldTakmicenjeOfUcescePojedincaCollectionUcescePojedinca.getUcescePojedincaCollection().remove(ucescePojedincaCollectionUcescePojedinca);
                    oldTakmicenjeOfUcescePojedincaCollectionUcescePojedinca = em.merge(oldTakmicenjeOfUcescePojedincaCollectionUcescePojedinca);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTakmicenje(takmicenje.getIDTakmicenja()) != null) {
                throw new PreexistingEntityException("Takmicenje " + takmicenje + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Takmicenje takmicenje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Takmicenje persistentTakmicenje = em.find(Takmicenje.class, takmicenje.getIDTakmicenja());
            KarateSavez IDSavezaOld = persistentTakmicenje.getIDSaveza();
            KarateSavez IDSavezaNew = takmicenje.getIDSaveza();
            KarateKlub IDKlubaOld = persistentTakmicenje.getIDKluba();
            KarateKlub IDKlubaNew = takmicenje.getIDKluba();
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionOld = persistentTakmicenje.getTakmicenjeKategorijaCollection();
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionNew = takmicenje.getTakmicenjeKategorijaCollection();
            Collection<UcesceEkipe> ucesceEkipeCollectionOld = persistentTakmicenje.getUcesceEkipeCollection();
            Collection<UcesceEkipe> ucesceEkipeCollectionNew = takmicenje.getUcesceEkipeCollection();
            Collection<UcescePojedinca> ucescePojedincaCollectionOld = persistentTakmicenje.getUcescePojedincaCollection();
            Collection<UcescePojedinca> ucescePojedincaCollectionNew = takmicenje.getUcescePojedincaCollection();
            List<String> illegalOrphanMessages = null;
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionOldTakmicenjeKategorija : takmicenjeKategorijaCollectionOld) {
                if (!takmicenjeKategorijaCollectionNew.contains(takmicenjeKategorijaCollectionOldTakmicenjeKategorija)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TakmicenjeKategorija " + takmicenjeKategorijaCollectionOldTakmicenjeKategorija + " since its takmicenje field is not nullable.");
                }
            }
            for (UcesceEkipe ucesceEkipeCollectionOldUcesceEkipe : ucesceEkipeCollectionOld) {
                if (!ucesceEkipeCollectionNew.contains(ucesceEkipeCollectionOldUcesceEkipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UcesceEkipe " + ucesceEkipeCollectionOldUcesceEkipe + " since its takmicenje field is not nullable.");
                }
            }
            for (UcescePojedinca ucescePojedincaCollectionOldUcescePojedinca : ucescePojedincaCollectionOld) {
                if (!ucescePojedincaCollectionNew.contains(ucescePojedincaCollectionOldUcescePojedinca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UcescePojedinca " + ucescePojedincaCollectionOldUcescePojedinca + " since its takmicenje field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDSavezaNew != null) {
                IDSavezaNew = em.getReference(IDSavezaNew.getClass(), IDSavezaNew.getIDSaveza());
                takmicenje.setIDSaveza(IDSavezaNew);
            }
            if (IDKlubaNew != null) {
                IDKlubaNew = em.getReference(IDKlubaNew.getClass(), IDKlubaNew.getIDKluba());
                takmicenje.setIDKluba(IDKlubaNew);
            }
            Collection<TakmicenjeKategorija> attachedTakmicenjeKategorijaCollectionNew = new ArrayList<TakmicenjeKategorija>();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach : takmicenjeKategorijaCollectionNew) {
                takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach = em.getReference(takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach.getClass(), takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach.getTakmicenjeKategorijaPK());
                attachedTakmicenjeKategorijaCollectionNew.add(takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach);
            }
            takmicenjeKategorijaCollectionNew = attachedTakmicenjeKategorijaCollectionNew;
            takmicenje.setTakmicenjeKategorijaCollection(takmicenjeKategorijaCollectionNew);
            Collection<UcesceEkipe> attachedUcesceEkipeCollectionNew = new ArrayList<UcesceEkipe>();
            for (UcesceEkipe ucesceEkipeCollectionNewUcesceEkipeToAttach : ucesceEkipeCollectionNew) {
                ucesceEkipeCollectionNewUcesceEkipeToAttach = em.getReference(ucesceEkipeCollectionNewUcesceEkipeToAttach.getClass(), ucesceEkipeCollectionNewUcesceEkipeToAttach.getUcesceEkipePK());
                attachedUcesceEkipeCollectionNew.add(ucesceEkipeCollectionNewUcesceEkipeToAttach);
            }
            ucesceEkipeCollectionNew = attachedUcesceEkipeCollectionNew;
            takmicenje.setUcesceEkipeCollection(ucesceEkipeCollectionNew);
            Collection<UcescePojedinca> attachedUcescePojedincaCollectionNew = new ArrayList<UcescePojedinca>();
            for (UcescePojedinca ucescePojedincaCollectionNewUcescePojedincaToAttach : ucescePojedincaCollectionNew) {
                ucescePojedincaCollectionNewUcescePojedincaToAttach = em.getReference(ucescePojedincaCollectionNewUcescePojedincaToAttach.getClass(), ucescePojedincaCollectionNewUcescePojedincaToAttach.getUcescePojedincaPK());
                attachedUcescePojedincaCollectionNew.add(ucescePojedincaCollectionNewUcescePojedincaToAttach);
            }
            ucescePojedincaCollectionNew = attachedUcescePojedincaCollectionNew;
            takmicenje.setUcescePojedincaCollection(ucescePojedincaCollectionNew);
            takmicenje = em.merge(takmicenje);
            if (IDSavezaOld != null && !IDSavezaOld.equals(IDSavezaNew)) {
                IDSavezaOld.getTakmicenjeCollection().remove(takmicenje);
                IDSavezaOld = em.merge(IDSavezaOld);
            }
            if (IDSavezaNew != null && !IDSavezaNew.equals(IDSavezaOld)) {
                IDSavezaNew.getTakmicenjeCollection().add(takmicenje);
                IDSavezaNew = em.merge(IDSavezaNew);
            }
            if (IDKlubaOld != null && !IDKlubaOld.equals(IDKlubaNew)) {
                IDKlubaOld.getTakmicenjeCollection().remove(takmicenje);
                IDKlubaOld = em.merge(IDKlubaOld);
            }
            if (IDKlubaNew != null && !IDKlubaNew.equals(IDKlubaOld)) {
                IDKlubaNew.getTakmicenjeCollection().add(takmicenje);
                IDKlubaNew = em.merge(IDKlubaNew);
            }
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionNewTakmicenjeKategorija : takmicenjeKategorijaCollectionNew) {
                if (!takmicenjeKategorijaCollectionOld.contains(takmicenjeKategorijaCollectionNewTakmicenjeKategorija)) {
                    Takmicenje oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija = takmicenjeKategorijaCollectionNewTakmicenjeKategorija.getTakmicenje();
                    takmicenjeKategorijaCollectionNewTakmicenjeKategorija.setTakmicenje(takmicenje);
                    takmicenjeKategorijaCollectionNewTakmicenjeKategorija = em.merge(takmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                    if (oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija != null && !oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija.equals(takmicenje)) {
                        oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                        oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija = em.merge(oldTakmicenjeOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                    }
                }
            }
            for (UcesceEkipe ucesceEkipeCollectionNewUcesceEkipe : ucesceEkipeCollectionNew) {
                if (!ucesceEkipeCollectionOld.contains(ucesceEkipeCollectionNewUcesceEkipe)) {
                    Takmicenje oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe = ucesceEkipeCollectionNewUcesceEkipe.getTakmicenje();
                    ucesceEkipeCollectionNewUcesceEkipe.setTakmicenje(takmicenje);
                    ucesceEkipeCollectionNewUcesceEkipe = em.merge(ucesceEkipeCollectionNewUcesceEkipe);
                    if (oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe != null && !oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe.equals(takmicenje)) {
                        oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe.getUcesceEkipeCollection().remove(ucesceEkipeCollectionNewUcesceEkipe);
                        oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe = em.merge(oldTakmicenjeOfUcesceEkipeCollectionNewUcesceEkipe);
                    }
                }
            }
            for (UcescePojedinca ucescePojedincaCollectionNewUcescePojedinca : ucescePojedincaCollectionNew) {
                if (!ucescePojedincaCollectionOld.contains(ucescePojedincaCollectionNewUcescePojedinca)) {
                    Takmicenje oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca = ucescePojedincaCollectionNewUcescePojedinca.getTakmicenje();
                    ucescePojedincaCollectionNewUcescePojedinca.setTakmicenje(takmicenje);
                    ucescePojedincaCollectionNewUcescePojedinca = em.merge(ucescePojedincaCollectionNewUcescePojedinca);
                    if (oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca != null && !oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca.equals(takmicenje)) {
                        oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca.getUcescePojedincaCollection().remove(ucescePojedincaCollectionNewUcescePojedinca);
                        oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca = em.merge(oldTakmicenjeOfUcescePojedincaCollectionNewUcescePojedinca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = takmicenje.getIDTakmicenja();
                if (findTakmicenje(id) == null) {
                    throw new NonexistentEntityException("The takmicenje with id " + id + " no longer exists.");
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
            Takmicenje takmicenje;
            try {
                takmicenje = em.getReference(Takmicenje.class, id);
                takmicenje.getIDTakmicenja();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The takmicenje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionOrphanCheck = takmicenje.getTakmicenjeKategorijaCollection();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionOrphanCheckTakmicenjeKategorija : takmicenjeKategorijaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Takmicenje (" + takmicenje + ") cannot be destroyed since the TakmicenjeKategorija " + takmicenjeKategorijaCollectionOrphanCheckTakmicenjeKategorija + " in its takmicenjeKategorijaCollection field has a non-nullable takmicenje field.");
            }
            Collection<UcesceEkipe> ucesceEkipeCollectionOrphanCheck = takmicenje.getUcesceEkipeCollection();
            for (UcesceEkipe ucesceEkipeCollectionOrphanCheckUcesceEkipe : ucesceEkipeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Takmicenje (" + takmicenje + ") cannot be destroyed since the UcesceEkipe " + ucesceEkipeCollectionOrphanCheckUcesceEkipe + " in its ucesceEkipeCollection field has a non-nullable takmicenje field.");
            }
            Collection<UcescePojedinca> ucescePojedincaCollectionOrphanCheck = takmicenje.getUcescePojedincaCollection();
            for (UcescePojedinca ucescePojedincaCollectionOrphanCheckUcescePojedinca : ucescePojedincaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Takmicenje (" + takmicenje + ") cannot be destroyed since the UcescePojedinca " + ucescePojedincaCollectionOrphanCheckUcescePojedinca + " in its ucescePojedincaCollection field has a non-nullable takmicenje field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KarateSavez IDSaveza = takmicenje.getIDSaveza();
            if (IDSaveza != null) {
                IDSaveza.getTakmicenjeCollection().remove(takmicenje);
                IDSaveza = em.merge(IDSaveza);
            }
            KarateKlub IDKluba = takmicenje.getIDKluba();
            if (IDKluba != null) {
                IDKluba.getTakmicenjeCollection().remove(takmicenje);
                IDKluba = em.merge(IDKluba);
            }
            em.remove(takmicenje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Takmicenje> findTakmicenjeEntities() {
        return findTakmicenjeEntities(true, -1, -1);
    }

    public List<Takmicenje> findTakmicenjeEntities(int maxResults, int firstResult) {
        return findTakmicenjeEntities(false, maxResults, firstResult);
    }

    private List<Takmicenje> findTakmicenjeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Takmicenje.class));
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

    public Takmicenje findTakmicenje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Takmicenje.class, id);
        } finally {
            em.close();
        }
    }

    public int getTakmicenjeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Takmicenje> rt = cq.from(Takmicenje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
