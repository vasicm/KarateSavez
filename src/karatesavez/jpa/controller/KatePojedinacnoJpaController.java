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
import karatesavez.jpa.entity.PojedinacnaKategorija;
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUKatama;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.KatePojedinacno;

/**
 *
 * @author Marko
 */
public class KatePojedinacnoJpaController implements Serializable {

    public KatePojedinacnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KatePojedinacno katePojedinacno) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection() == null) {
            katePojedinacno.setPrijavljujeTakmicenjeUKatamaCollection(new ArrayList<PrijavljujeTakmicenjeUKatama>());
        }
        List<String> illegalOrphanMessages = null;
        PojedinacnaKategorija pojedinacnaKategorijaOrphanCheck = katePojedinacno.getPojedinacnaKategorija();
        if (pojedinacnaKategorijaOrphanCheck != null) {
            KatePojedinacno oldKatePojedinacnoOfPojedinacnaKategorija = pojedinacnaKategorijaOrphanCheck.getKatePojedinacno();
            if (oldKatePojedinacnoOfPojedinacnaKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PojedinacnaKategorija " + pojedinacnaKategorijaOrphanCheck + " already has an item of type KatePojedinacno whose pojedinacnaKategorija column cannot be null. Please make another selection for the pojedinacnaKategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PojedinacnaKategorija pojedinacnaKategorija = katePojedinacno.getPojedinacnaKategorija();
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija = em.getReference(pojedinacnaKategorija.getClass(), pojedinacnaKategorija.getIDKategorije());
                katePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorija);
            }
            Collection<PrijavljujeTakmicenjeUKatama> attachedPrijavljujeTakmicenjeUKatamaCollection = new ArrayList<PrijavljujeTakmicenjeUKatama>();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach : katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection()) {
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach = em.getReference(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach.getClass(), prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach.getPrijavljujeTakmicenjeUKatamaPK());
                attachedPrijavljujeTakmicenjeUKatamaCollection.add(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatamaToAttach);
            }
            katePojedinacno.setPrijavljujeTakmicenjeUKatamaCollection(attachedPrijavljujeTakmicenjeUKatamaCollection);
            em.persist(katePojedinacno);
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija.setKatePojedinacno(katePojedinacno);
                pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama : katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection()) {
                KatePojedinacno oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.getKatePojedinacno();
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.setKatePojedinacno(katePojedinacno);
                prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                if (oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama != null) {
                    oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                    oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama = em.merge(oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionPrijavljujeTakmicenjeUKatama);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKatePojedinacno(katePojedinacno.getIDKategorije()) != null) {
                throw new PreexistingEntityException("KatePojedinacno " + katePojedinacno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KatePojedinacno katePojedinacno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KatePojedinacno persistentKatePojedinacno = em.find(KatePojedinacno.class, katePojedinacno.getIDKategorije());
            PojedinacnaKategorija pojedinacnaKategorijaOld = persistentKatePojedinacno.getPojedinacnaKategorija();
            PojedinacnaKategorija pojedinacnaKategorijaNew = katePojedinacno.getPojedinacnaKategorija();
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionOld = persistentKatePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection();
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionNew = katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection();
            List<String> illegalOrphanMessages = null;
            if (pojedinacnaKategorijaNew != null && !pojedinacnaKategorijaNew.equals(pojedinacnaKategorijaOld)) {
                KatePojedinacno oldKatePojedinacnoOfPojedinacnaKategorija = pojedinacnaKategorijaNew.getKatePojedinacno();
                if (oldKatePojedinacnoOfPojedinacnaKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PojedinacnaKategorija " + pojedinacnaKategorijaNew + " already has an item of type KatePojedinacno whose pojedinacnaKategorija column cannot be null. Please make another selection for the pojedinacnaKategorija field.");
                }
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionOld) {
                if (!prijavljujeTakmicenjeUKatamaCollectionNew.contains(prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaCollectionOldPrijavljujeTakmicenjeUKatama + " since its katePojedinacno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pojedinacnaKategorijaNew != null) {
                pojedinacnaKategorijaNew = em.getReference(pojedinacnaKategorijaNew.getClass(), pojedinacnaKategorijaNew.getIDKategorije());
                katePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorijaNew);
            }
            Collection<PrijavljujeTakmicenjeUKatama> attachedPrijavljujeTakmicenjeUKatamaCollectionNew = new ArrayList<PrijavljujeTakmicenjeUKatama>();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach : prijavljujeTakmicenjeUKatamaCollectionNew) {
                prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach = em.getReference(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach.getClass(), prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach.getPrijavljujeTakmicenjeUKatamaPK());
                attachedPrijavljujeTakmicenjeUKatamaCollectionNew.add(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatamaToAttach);
            }
            prijavljujeTakmicenjeUKatamaCollectionNew = attachedPrijavljujeTakmicenjeUKatamaCollectionNew;
            katePojedinacno.setPrijavljujeTakmicenjeUKatamaCollection(prijavljujeTakmicenjeUKatamaCollectionNew);
            katePojedinacno = em.merge(katePojedinacno);
            if (pojedinacnaKategorijaOld != null && !pojedinacnaKategorijaOld.equals(pojedinacnaKategorijaNew)) {
                pojedinacnaKategorijaOld.setKatePojedinacno(null);
                pojedinacnaKategorijaOld = em.merge(pojedinacnaKategorijaOld);
            }
            if (pojedinacnaKategorijaNew != null && !pojedinacnaKategorijaNew.equals(pojedinacnaKategorijaOld)) {
                pojedinacnaKategorijaNew.setKatePojedinacno(katePojedinacno);
                pojedinacnaKategorijaNew = em.merge(pojedinacnaKategorijaNew);
            }
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionNew) {
                if (!prijavljujeTakmicenjeUKatamaCollectionOld.contains(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama)) {
                    KatePojedinacno oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.getKatePojedinacno();
                    prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.setKatePojedinacno(katePojedinacno);
                    prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = em.merge(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                    if (oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama != null && !oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.equals(katePojedinacno)) {
                        oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama.getPrijavljujeTakmicenjeUKatamaCollection().remove(prijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                        oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama = em.merge(oldKatePojedinacnoOfPrijavljujeTakmicenjeUKatamaCollectionNewPrijavljujeTakmicenjeUKatama);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = katePojedinacno.getIDKategorije();
                if (findKatePojedinacno(id) == null) {
                    throw new NonexistentEntityException("The katePojedinacno with id " + id + " no longer exists.");
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
            KatePojedinacno katePojedinacno;
            try {
                katePojedinacno = em.getReference(KatePojedinacno.class, id);
                katePojedinacno.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The katePojedinacno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollectionOrphanCheck = katePojedinacno.getPrijavljujeTakmicenjeUKatamaCollection();
            for (PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatamaCollectionOrphanCheckPrijavljujeTakmicenjeUKatama : prijavljujeTakmicenjeUKatamaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KatePojedinacno (" + katePojedinacno + ") cannot be destroyed since the PrijavljujeTakmicenjeUKatama " + prijavljujeTakmicenjeUKatamaCollectionOrphanCheckPrijavljujeTakmicenjeUKatama + " in its prijavljujeTakmicenjeUKatamaCollection field has a non-nullable katePojedinacno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PojedinacnaKategorija pojedinacnaKategorija = katePojedinacno.getPojedinacnaKategorija();
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija.setKatePojedinacno(null);
                pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            }
            em.remove(katePojedinacno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KatePojedinacno> findKatePojedinacnoEntities() {
        return findKatePojedinacnoEntities(true, -1, -1);
    }

    public List<KatePojedinacno> findKatePojedinacnoEntities(int maxResults, int firstResult) {
        return findKatePojedinacnoEntities(false, maxResults, firstResult);
    }

    private List<KatePojedinacno> findKatePojedinacnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KatePojedinacno.class));
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

    public KatePojedinacno findKatePojedinacno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KatePojedinacno.class, id);
        } finally {
            em.close();
        }
    }

    public int getKatePojedinacnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KatePojedinacno> rt = cq.from(KatePojedinacno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
