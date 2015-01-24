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
import karatesavez.jpa.entity.Takmicar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Ekipa;
import karatesavez.jpa.entity.UcesceEkipe;

/**
 *
 * @author Marko
 */
public class EkipaJpaController implements Serializable {

    public EkipaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ekipa ekipa) throws PreexistingEntityException, Exception {
        if (ekipa.getTakmicarCollection() == null) {
            ekipa.setTakmicarCollection(new ArrayList<Takmicar>());
        }
        if (ekipa.getUcesceEkipeCollection() == null) {
            ekipa.setUcesceEkipeCollection(new ArrayList<UcesceEkipe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KarateKlub IDKluba = ekipa.getIDKluba();
            if (IDKluba != null) {
                IDKluba = em.getReference(IDKluba.getClass(), IDKluba.getIDKluba());
                ekipa.setIDKluba(IDKluba);
            }
            Collection<Takmicar> attachedTakmicarCollection = new ArrayList<Takmicar>();
            for (Takmicar takmicarCollectionTakmicarToAttach : ekipa.getTakmicarCollection()) {
                takmicarCollectionTakmicarToAttach = em.getReference(takmicarCollectionTakmicarToAttach.getClass(), takmicarCollectionTakmicarToAttach.getJmb());
                attachedTakmicarCollection.add(takmicarCollectionTakmicarToAttach);
            }
            ekipa.setTakmicarCollection(attachedTakmicarCollection);
            Collection<UcesceEkipe> attachedUcesceEkipeCollection = new ArrayList<UcesceEkipe>();
            for (UcesceEkipe ucesceEkipeCollectionUcesceEkipeToAttach : ekipa.getUcesceEkipeCollection()) {
                ucesceEkipeCollectionUcesceEkipeToAttach = em.getReference(ucesceEkipeCollectionUcesceEkipeToAttach.getClass(), ucesceEkipeCollectionUcesceEkipeToAttach.getUcesceEkipePK());
                attachedUcesceEkipeCollection.add(ucesceEkipeCollectionUcesceEkipeToAttach);
            }
            ekipa.setUcesceEkipeCollection(attachedUcesceEkipeCollection);
            em.persist(ekipa);
            if (IDKluba != null) {
                IDKluba.getEkipaCollection().add(ekipa);
                IDKluba = em.merge(IDKluba);
            }
            for (Takmicar takmicarCollectionTakmicar : ekipa.getTakmicarCollection()) {
                Ekipa oldIDEkipeOfTakmicarCollectionTakmicar = takmicarCollectionTakmicar.getIDEkipe();
                takmicarCollectionTakmicar.setIDEkipe(ekipa);
                takmicarCollectionTakmicar = em.merge(takmicarCollectionTakmicar);
                if (oldIDEkipeOfTakmicarCollectionTakmicar != null) {
                    oldIDEkipeOfTakmicarCollectionTakmicar.getTakmicarCollection().remove(takmicarCollectionTakmicar);
                    oldIDEkipeOfTakmicarCollectionTakmicar = em.merge(oldIDEkipeOfTakmicarCollectionTakmicar);
                }
            }
            for (UcesceEkipe ucesceEkipeCollectionUcesceEkipe : ekipa.getUcesceEkipeCollection()) {
                Ekipa oldEkipaOfUcesceEkipeCollectionUcesceEkipe = ucesceEkipeCollectionUcesceEkipe.getEkipa();
                ucesceEkipeCollectionUcesceEkipe.setEkipa(ekipa);
                ucesceEkipeCollectionUcesceEkipe = em.merge(ucesceEkipeCollectionUcesceEkipe);
                if (oldEkipaOfUcesceEkipeCollectionUcesceEkipe != null) {
                    oldEkipaOfUcesceEkipeCollectionUcesceEkipe.getUcesceEkipeCollection().remove(ucesceEkipeCollectionUcesceEkipe);
                    oldEkipaOfUcesceEkipeCollectionUcesceEkipe = em.merge(oldEkipaOfUcesceEkipeCollectionUcesceEkipe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEkipa(ekipa.getIDEkipe()) != null) {
                throw new PreexistingEntityException("Ekipa " + ekipa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ekipa ekipa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ekipa persistentEkipa = em.find(Ekipa.class, ekipa.getIDEkipe());
            KarateKlub IDKlubaOld = persistentEkipa.getIDKluba();
            KarateKlub IDKlubaNew = ekipa.getIDKluba();
            Collection<Takmicar> takmicarCollectionOld = persistentEkipa.getTakmicarCollection();
            Collection<Takmicar> takmicarCollectionNew = ekipa.getTakmicarCollection();
            Collection<UcesceEkipe> ucesceEkipeCollectionOld = persistentEkipa.getUcesceEkipeCollection();
            Collection<UcesceEkipe> ucesceEkipeCollectionNew = ekipa.getUcesceEkipeCollection();
            List<String> illegalOrphanMessages = null;
            for (UcesceEkipe ucesceEkipeCollectionOldUcesceEkipe : ucesceEkipeCollectionOld) {
                if (!ucesceEkipeCollectionNew.contains(ucesceEkipeCollectionOldUcesceEkipe)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UcesceEkipe " + ucesceEkipeCollectionOldUcesceEkipe + " since its ekipa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDKlubaNew != null) {
                IDKlubaNew = em.getReference(IDKlubaNew.getClass(), IDKlubaNew.getIDKluba());
                ekipa.setIDKluba(IDKlubaNew);
            }
            Collection<Takmicar> attachedTakmicarCollectionNew = new ArrayList<Takmicar>();
            for (Takmicar takmicarCollectionNewTakmicarToAttach : takmicarCollectionNew) {
                takmicarCollectionNewTakmicarToAttach = em.getReference(takmicarCollectionNewTakmicarToAttach.getClass(), takmicarCollectionNewTakmicarToAttach.getJmb());
                attachedTakmicarCollectionNew.add(takmicarCollectionNewTakmicarToAttach);
            }
            takmicarCollectionNew = attachedTakmicarCollectionNew;
            ekipa.setTakmicarCollection(takmicarCollectionNew);
            Collection<UcesceEkipe> attachedUcesceEkipeCollectionNew = new ArrayList<UcesceEkipe>();
            for (UcesceEkipe ucesceEkipeCollectionNewUcesceEkipeToAttach : ucesceEkipeCollectionNew) {
                ucesceEkipeCollectionNewUcesceEkipeToAttach = em.getReference(ucesceEkipeCollectionNewUcesceEkipeToAttach.getClass(), ucesceEkipeCollectionNewUcesceEkipeToAttach.getUcesceEkipePK());
                attachedUcesceEkipeCollectionNew.add(ucesceEkipeCollectionNewUcesceEkipeToAttach);
            }
            ucesceEkipeCollectionNew = attachedUcesceEkipeCollectionNew;
            ekipa.setUcesceEkipeCollection(ucesceEkipeCollectionNew);
            ekipa = em.merge(ekipa);
            if (IDKlubaOld != null && !IDKlubaOld.equals(IDKlubaNew)) {
                IDKlubaOld.getEkipaCollection().remove(ekipa);
                IDKlubaOld = em.merge(IDKlubaOld);
            }
            if (IDKlubaNew != null && !IDKlubaNew.equals(IDKlubaOld)) {
                IDKlubaNew.getEkipaCollection().add(ekipa);
                IDKlubaNew = em.merge(IDKlubaNew);
            }
            for (Takmicar takmicarCollectionOldTakmicar : takmicarCollectionOld) {
                if (!takmicarCollectionNew.contains(takmicarCollectionOldTakmicar)) {
                    takmicarCollectionOldTakmicar.setIDEkipe(null);
                    takmicarCollectionOldTakmicar = em.merge(takmicarCollectionOldTakmicar);
                }
            }
            for (Takmicar takmicarCollectionNewTakmicar : takmicarCollectionNew) {
                if (!takmicarCollectionOld.contains(takmicarCollectionNewTakmicar)) {
                    Ekipa oldIDEkipeOfTakmicarCollectionNewTakmicar = takmicarCollectionNewTakmicar.getIDEkipe();
                    takmicarCollectionNewTakmicar.setIDEkipe(ekipa);
                    takmicarCollectionNewTakmicar = em.merge(takmicarCollectionNewTakmicar);
                    if (oldIDEkipeOfTakmicarCollectionNewTakmicar != null && !oldIDEkipeOfTakmicarCollectionNewTakmicar.equals(ekipa)) {
                        oldIDEkipeOfTakmicarCollectionNewTakmicar.getTakmicarCollection().remove(takmicarCollectionNewTakmicar);
                        oldIDEkipeOfTakmicarCollectionNewTakmicar = em.merge(oldIDEkipeOfTakmicarCollectionNewTakmicar);
                    }
                }
            }
            for (UcesceEkipe ucesceEkipeCollectionNewUcesceEkipe : ucesceEkipeCollectionNew) {
                if (!ucesceEkipeCollectionOld.contains(ucesceEkipeCollectionNewUcesceEkipe)) {
                    Ekipa oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe = ucesceEkipeCollectionNewUcesceEkipe.getEkipa();
                    ucesceEkipeCollectionNewUcesceEkipe.setEkipa(ekipa);
                    ucesceEkipeCollectionNewUcesceEkipe = em.merge(ucesceEkipeCollectionNewUcesceEkipe);
                    if (oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe != null && !oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe.equals(ekipa)) {
                        oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe.getUcesceEkipeCollection().remove(ucesceEkipeCollectionNewUcesceEkipe);
                        oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe = em.merge(oldEkipaOfUcesceEkipeCollectionNewUcesceEkipe);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ekipa.getIDEkipe();
                if (findEkipa(id) == null) {
                    throw new NonexistentEntityException("The ekipa with id " + id + " no longer exists.");
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
            Ekipa ekipa;
            try {
                ekipa = em.getReference(Ekipa.class, id);
                ekipa.getIDEkipe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ekipa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UcesceEkipe> ucesceEkipeCollectionOrphanCheck = ekipa.getUcesceEkipeCollection();
            for (UcesceEkipe ucesceEkipeCollectionOrphanCheckUcesceEkipe : ucesceEkipeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ekipa (" + ekipa + ") cannot be destroyed since the UcesceEkipe " + ucesceEkipeCollectionOrphanCheckUcesceEkipe + " in its ucesceEkipeCollection field has a non-nullable ekipa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KarateKlub IDKluba = ekipa.getIDKluba();
            if (IDKluba != null) {
                IDKluba.getEkipaCollection().remove(ekipa);
                IDKluba = em.merge(IDKluba);
            }
            Collection<Takmicar> takmicarCollection = ekipa.getTakmicarCollection();
            for (Takmicar takmicarCollectionTakmicar : takmicarCollection) {
                takmicarCollectionTakmicar.setIDEkipe(null);
                takmicarCollectionTakmicar = em.merge(takmicarCollectionTakmicar);
            }
            em.remove(ekipa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ekipa> findEkipaEntities() {
        return findEkipaEntities(true, -1, -1);
    }

    public List<Ekipa> findEkipaEntities(int maxResults, int firstResult) {
        return findEkipaEntities(false, maxResults, firstResult);
    }

    private List<Ekipa> findEkipaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ekipa.class));
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

    public Ekipa findEkipa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ekipa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEkipaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ekipa> rt = cq.from(Ekipa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
