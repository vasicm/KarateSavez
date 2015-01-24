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
import karatesavez.jpa.entity.EkipnaKategorija;
import karatesavez.jpa.entity.PojedinacnaKategorija;
import karatesavez.jpa.entity.TakmicenjeKategorija;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Kategorija;

/**
 *
 * @author Marko
 */
public class KategorijaJpaController implements Serializable {

    public KategorijaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Kategorija kategorija) throws PreexistingEntityException, Exception {
        if (kategorija.getTakmicenjeKategorijaCollection() == null) {
            kategorija.setTakmicenjeKategorijaCollection(new ArrayList<TakmicenjeKategorija>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaKategorija ekipnaKategorija = kategorija.getEkipnaKategorija();
            if (ekipnaKategorija != null) {
                ekipnaKategorija = em.getReference(ekipnaKategorija.getClass(), ekipnaKategorija.getIDKategorije());
                kategorija.setEkipnaKategorija(ekipnaKategorija);
            }
            PojedinacnaKategorija pojedinacnaKategorija = kategorija.getPojedinacnaKategorija();
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija = em.getReference(pojedinacnaKategorija.getClass(), pojedinacnaKategorija.getIDKategorije());
                kategorija.setPojedinacnaKategorija(pojedinacnaKategorija);
            }
            Collection<TakmicenjeKategorija> attachedTakmicenjeKategorijaCollection = new ArrayList<TakmicenjeKategorija>();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach : kategorija.getTakmicenjeKategorijaCollection()) {
                takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach = em.getReference(takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach.getClass(), takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach.getTakmicenjeKategorijaPK());
                attachedTakmicenjeKategorijaCollection.add(takmicenjeKategorijaCollectionTakmicenjeKategorijaToAttach);
            }
            kategorija.setTakmicenjeKategorijaCollection(attachedTakmicenjeKategorijaCollection);
            em.persist(kategorija);
            if (ekipnaKategorija != null) {
                Kategorija oldKategorijaOfEkipnaKategorija = ekipnaKategorija.getKategorija();
                if (oldKategorijaOfEkipnaKategorija != null) {
                    oldKategorijaOfEkipnaKategorija.setEkipnaKategorija(null);
                    oldKategorijaOfEkipnaKategorija = em.merge(oldKategorijaOfEkipnaKategorija);
                }
                ekipnaKategorija.setKategorija(kategorija);
                ekipnaKategorija = em.merge(ekipnaKategorija);
            }
            if (pojedinacnaKategorija != null) {
                Kategorija oldKategorijaOfPojedinacnaKategorija = pojedinacnaKategorija.getKategorija();
                if (oldKategorijaOfPojedinacnaKategorija != null) {
                    oldKategorijaOfPojedinacnaKategorija.setPojedinacnaKategorija(null);
                    oldKategorijaOfPojedinacnaKategorija = em.merge(oldKategorijaOfPojedinacnaKategorija);
                }
                pojedinacnaKategorija.setKategorija(kategorija);
                pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            }
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionTakmicenjeKategorija : kategorija.getTakmicenjeKategorijaCollection()) {
                Kategorija oldKategorijaOfTakmicenjeKategorijaCollectionTakmicenjeKategorija = takmicenjeKategorijaCollectionTakmicenjeKategorija.getKategorija();
                takmicenjeKategorijaCollectionTakmicenjeKategorija.setKategorija(kategorija);
                takmicenjeKategorijaCollectionTakmicenjeKategorija = em.merge(takmicenjeKategorijaCollectionTakmicenjeKategorija);
                if (oldKategorijaOfTakmicenjeKategorijaCollectionTakmicenjeKategorija != null) {
                    oldKategorijaOfTakmicenjeKategorijaCollectionTakmicenjeKategorija.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorijaCollectionTakmicenjeKategorija);
                    oldKategorijaOfTakmicenjeKategorijaCollectionTakmicenjeKategorija = em.merge(oldKategorijaOfTakmicenjeKategorijaCollectionTakmicenjeKategorija);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKategorija(kategorija.getIDKategorije()) != null) {
                throw new PreexistingEntityException("Kategorija " + kategorija + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Kategorija kategorija) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kategorija persistentKategorija = em.find(Kategorija.class, kategorija.getIDKategorije());
            EkipnaKategorija ekipnaKategorijaOld = persistentKategorija.getEkipnaKategorija();
            EkipnaKategorija ekipnaKategorijaNew = kategorija.getEkipnaKategorija();
            PojedinacnaKategorija pojedinacnaKategorijaOld = persistentKategorija.getPojedinacnaKategorija();
            PojedinacnaKategorija pojedinacnaKategorijaNew = kategorija.getPojedinacnaKategorija();
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionOld = persistentKategorija.getTakmicenjeKategorijaCollection();
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionNew = kategorija.getTakmicenjeKategorijaCollection();
            List<String> illegalOrphanMessages = null;
            if (ekipnaKategorijaOld != null && !ekipnaKategorijaOld.equals(ekipnaKategorijaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EkipnaKategorija " + ekipnaKategorijaOld + " since its kategorija field is not nullable.");
            }
            if (pojedinacnaKategorijaOld != null && !pojedinacnaKategorijaOld.equals(pojedinacnaKategorijaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PojedinacnaKategorija " + pojedinacnaKategorijaOld + " since its kategorija field is not nullable.");
            }
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionOldTakmicenjeKategorija : takmicenjeKategorijaCollectionOld) {
                if (!takmicenjeKategorijaCollectionNew.contains(takmicenjeKategorijaCollectionOldTakmicenjeKategorija)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TakmicenjeKategorija " + takmicenjeKategorijaCollectionOldTakmicenjeKategorija + " since its kategorija field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ekipnaKategorijaNew != null) {
                ekipnaKategorijaNew = em.getReference(ekipnaKategorijaNew.getClass(), ekipnaKategorijaNew.getIDKategorije());
                kategorija.setEkipnaKategorija(ekipnaKategorijaNew);
            }
            if (pojedinacnaKategorijaNew != null) {
                pojedinacnaKategorijaNew = em.getReference(pojedinacnaKategorijaNew.getClass(), pojedinacnaKategorijaNew.getIDKategorije());
                kategorija.setPojedinacnaKategorija(pojedinacnaKategorijaNew);
            }
            Collection<TakmicenjeKategorija> attachedTakmicenjeKategorijaCollectionNew = new ArrayList<TakmicenjeKategorija>();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach : takmicenjeKategorijaCollectionNew) {
                takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach = em.getReference(takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach.getClass(), takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach.getTakmicenjeKategorijaPK());
                attachedTakmicenjeKategorijaCollectionNew.add(takmicenjeKategorijaCollectionNewTakmicenjeKategorijaToAttach);
            }
            takmicenjeKategorijaCollectionNew = attachedTakmicenjeKategorijaCollectionNew;
            kategorija.setTakmicenjeKategorijaCollection(takmicenjeKategorijaCollectionNew);
            kategorija = em.merge(kategorija);
            if (ekipnaKategorijaNew != null && !ekipnaKategorijaNew.equals(ekipnaKategorijaOld)) {
                Kategorija oldKategorijaOfEkipnaKategorija = ekipnaKategorijaNew.getKategorija();
                if (oldKategorijaOfEkipnaKategorija != null) {
                    oldKategorijaOfEkipnaKategorija.setEkipnaKategorija(null);
                    oldKategorijaOfEkipnaKategorija = em.merge(oldKategorijaOfEkipnaKategorija);
                }
                ekipnaKategorijaNew.setKategorija(kategorija);
                ekipnaKategorijaNew = em.merge(ekipnaKategorijaNew);
            }
            if (pojedinacnaKategorijaNew != null && !pojedinacnaKategorijaNew.equals(pojedinacnaKategorijaOld)) {
                Kategorija oldKategorijaOfPojedinacnaKategorija = pojedinacnaKategorijaNew.getKategorija();
                if (oldKategorijaOfPojedinacnaKategorija != null) {
                    oldKategorijaOfPojedinacnaKategorija.setPojedinacnaKategorija(null);
                    oldKategorijaOfPojedinacnaKategorija = em.merge(oldKategorijaOfPojedinacnaKategorija);
                }
                pojedinacnaKategorijaNew.setKategorija(kategorija);
                pojedinacnaKategorijaNew = em.merge(pojedinacnaKategorijaNew);
            }
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionNewTakmicenjeKategorija : takmicenjeKategorijaCollectionNew) {
                if (!takmicenjeKategorijaCollectionOld.contains(takmicenjeKategorijaCollectionNewTakmicenjeKategorija)) {
                    Kategorija oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija = takmicenjeKategorijaCollectionNewTakmicenjeKategorija.getKategorija();
                    takmicenjeKategorijaCollectionNewTakmicenjeKategorija.setKategorija(kategorija);
                    takmicenjeKategorijaCollectionNewTakmicenjeKategorija = em.merge(takmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                    if (oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija != null && !oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija.equals(kategorija)) {
                        oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                        oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija = em.merge(oldKategorijaOfTakmicenjeKategorijaCollectionNewTakmicenjeKategorija);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kategorija.getIDKategorije();
                if (findKategorija(id) == null) {
                    throw new NonexistentEntityException("The kategorija with id " + id + " no longer exists.");
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
            Kategorija kategorija;
            try {
                kategorija = em.getReference(Kategorija.class, id);
                kategorija.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kategorija with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EkipnaKategorija ekipnaKategorijaOrphanCheck = kategorija.getEkipnaKategorija();
            if (ekipnaKategorijaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kategorija (" + kategorija + ") cannot be destroyed since the EkipnaKategorija " + ekipnaKategorijaOrphanCheck + " in its ekipnaKategorija field has a non-nullable kategorija field.");
            }
            PojedinacnaKategorija pojedinacnaKategorijaOrphanCheck = kategorija.getPojedinacnaKategorija();
            if (pojedinacnaKategorijaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kategorija (" + kategorija + ") cannot be destroyed since the PojedinacnaKategorija " + pojedinacnaKategorijaOrphanCheck + " in its pojedinacnaKategorija field has a non-nullable kategorija field.");
            }
            Collection<TakmicenjeKategorija> takmicenjeKategorijaCollectionOrphanCheck = kategorija.getTakmicenjeKategorijaCollection();
            for (TakmicenjeKategorija takmicenjeKategorijaCollectionOrphanCheckTakmicenjeKategorija : takmicenjeKategorijaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Kategorija (" + kategorija + ") cannot be destroyed since the TakmicenjeKategorija " + takmicenjeKategorijaCollectionOrphanCheckTakmicenjeKategorija + " in its takmicenjeKategorijaCollection field has a non-nullable kategorija field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(kategorija);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Kategorija> findKategorijaEntities() {
        return findKategorijaEntities(true, -1, -1);
    }

    public List<Kategorija> findKategorijaEntities(int maxResults, int firstResult) {
        return findKategorijaEntities(false, maxResults, firstResult);
    }

    private List<Kategorija> findKategorijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Kategorija.class));
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

    public Kategorija findKategorija(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Kategorija.class, id);
        } finally {
            em.close();
        }
    }

    public int getKategorijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Kategorija> rt = cq.from(Kategorija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
