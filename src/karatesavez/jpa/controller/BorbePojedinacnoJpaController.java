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
import karatesavez.jpa.entity.PrijavljujeTakmicenjeUBorbama;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.BorbePojedinacno;

/**
 *
 * @author Marko
 */
public class BorbePojedinacnoJpaController implements Serializable {

    public BorbePojedinacnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BorbePojedinacno borbePojedinacno) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection() == null) {
            borbePojedinacno.setPrijavljujeTakmicenjeUBorbamaCollection(new ArrayList<PrijavljujeTakmicenjeUBorbama>());
        }
        List<String> illegalOrphanMessages = null;
        PojedinacnaKategorija pojedinacnaKategorijaOrphanCheck = borbePojedinacno.getPojedinacnaKategorija();
        if (pojedinacnaKategorijaOrphanCheck != null) {
            BorbePojedinacno oldBorbePojedinacnoOfPojedinacnaKategorija = pojedinacnaKategorijaOrphanCheck.getBorbePojedinacno();
            if (oldBorbePojedinacnoOfPojedinacnaKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The PojedinacnaKategorija " + pojedinacnaKategorijaOrphanCheck + " already has an item of type BorbePojedinacno whose pojedinacnaKategorija column cannot be null. Please make another selection for the pojedinacnaKategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PojedinacnaKategorija pojedinacnaKategorija = borbePojedinacno.getPojedinacnaKategorija();
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija = em.getReference(pojedinacnaKategorija.getClass(), pojedinacnaKategorija.getIDKategorije());
                borbePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorija);
            }
            Collection<PrijavljujeTakmicenjeUBorbama> attachedPrijavljujeTakmicenjeUBorbamaCollection = new ArrayList<PrijavljujeTakmicenjeUBorbama>();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach : borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection()) {
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach = em.getReference(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach.getClass(), prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach.getPrijavljujeTakmicenjeUBorbamaPK());
                attachedPrijavljujeTakmicenjeUBorbamaCollection.add(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbamaToAttach);
            }
            borbePojedinacno.setPrijavljujeTakmicenjeUBorbamaCollection(attachedPrijavljujeTakmicenjeUBorbamaCollection);
            em.persist(borbePojedinacno);
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija.setBorbePojedinacno(borbePojedinacno);
                pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            }
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama : borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection()) {
                BorbePojedinacno oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.setBorbePojedinacno(borbePojedinacno);
                prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                if (oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama != null) {
                    oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                    oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama = em.merge(oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionPrijavljujeTakmicenjeUBorbama);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBorbePojedinacno(borbePojedinacno.getIDKategorije()) != null) {
                throw new PreexistingEntityException("BorbePojedinacno " + borbePojedinacno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BorbePojedinacno borbePojedinacno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BorbePojedinacno persistentBorbePojedinacno = em.find(BorbePojedinacno.class, borbePojedinacno.getIDKategorije());
            PojedinacnaKategorija pojedinacnaKategorijaOld = persistentBorbePojedinacno.getPojedinacnaKategorija();
            PojedinacnaKategorija pojedinacnaKategorijaNew = borbePojedinacno.getPojedinacnaKategorija();
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionOld = persistentBorbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection();
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionNew = borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection();
            List<String> illegalOrphanMessages = null;
            if (pojedinacnaKategorijaNew != null && !pojedinacnaKategorijaNew.equals(pojedinacnaKategorijaOld)) {
                BorbePojedinacno oldBorbePojedinacnoOfPojedinacnaKategorija = pojedinacnaKategorijaNew.getBorbePojedinacno();
                if (oldBorbePojedinacnoOfPojedinacnaKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The PojedinacnaKategorija " + pojedinacnaKategorijaNew + " already has an item of type BorbePojedinacno whose pojedinacnaKategorija column cannot be null. Please make another selection for the pojedinacnaKategorija field.");
                }
            }
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionOld) {
                if (!prijavljujeTakmicenjeUBorbamaCollectionNew.contains(prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeTakmicenjeUBorbama " + prijavljujeTakmicenjeUBorbamaCollectionOldPrijavljujeTakmicenjeUBorbama + " since its borbePojedinacno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pojedinacnaKategorijaNew != null) {
                pojedinacnaKategorijaNew = em.getReference(pojedinacnaKategorijaNew.getClass(), pojedinacnaKategorijaNew.getIDKategorije());
                borbePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorijaNew);
            }
            Collection<PrijavljujeTakmicenjeUBorbama> attachedPrijavljujeTakmicenjeUBorbamaCollectionNew = new ArrayList<PrijavljujeTakmicenjeUBorbama>();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach : prijavljujeTakmicenjeUBorbamaCollectionNew) {
                prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach = em.getReference(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach.getClass(), prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach.getPrijavljujeTakmicenjeUBorbamaPK());
                attachedPrijavljujeTakmicenjeUBorbamaCollectionNew.add(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbamaToAttach);
            }
            prijavljujeTakmicenjeUBorbamaCollectionNew = attachedPrijavljujeTakmicenjeUBorbamaCollectionNew;
            borbePojedinacno.setPrijavljujeTakmicenjeUBorbamaCollection(prijavljujeTakmicenjeUBorbamaCollectionNew);
            borbePojedinacno = em.merge(borbePojedinacno);
            if (pojedinacnaKategorijaOld != null && !pojedinacnaKategorijaOld.equals(pojedinacnaKategorijaNew)) {
                pojedinacnaKategorijaOld.setBorbePojedinacno(null);
                pojedinacnaKategorijaOld = em.merge(pojedinacnaKategorijaOld);
            }
            if (pojedinacnaKategorijaNew != null && !pojedinacnaKategorijaNew.equals(pojedinacnaKategorijaOld)) {
                pojedinacnaKategorijaNew.setBorbePojedinacno(borbePojedinacno);
                pojedinacnaKategorijaNew = em.merge(pojedinacnaKategorijaNew);
            }
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionNew) {
                if (!prijavljujeTakmicenjeUBorbamaCollectionOld.contains(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama)) {
                    BorbePojedinacno oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.getBorbePojedinacno();
                    prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.setBorbePojedinacno(borbePojedinacno);
                    prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = em.merge(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                    if (oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama != null && !oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.equals(borbePojedinacno)) {
                        oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama.getPrijavljujeTakmicenjeUBorbamaCollection().remove(prijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                        oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama = em.merge(oldBorbePojedinacnoOfPrijavljujeTakmicenjeUBorbamaCollectionNewPrijavljujeTakmicenjeUBorbama);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = borbePojedinacno.getIDKategorije();
                if (findBorbePojedinacno(id) == null) {
                    throw new NonexistentEntityException("The borbePojedinacno with id " + id + " no longer exists.");
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
            BorbePojedinacno borbePojedinacno;
            try {
                borbePojedinacno = em.getReference(BorbePojedinacno.class, id);
                borbePojedinacno.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The borbePojedinacno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollectionOrphanCheck = borbePojedinacno.getPrijavljujeTakmicenjeUBorbamaCollection();
            for (PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbamaCollectionOrphanCheckPrijavljujeTakmicenjeUBorbama : prijavljujeTakmicenjeUBorbamaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BorbePojedinacno (" + borbePojedinacno + ") cannot be destroyed since the PrijavljujeTakmicenjeUBorbama " + prijavljujeTakmicenjeUBorbamaCollectionOrphanCheckPrijavljujeTakmicenjeUBorbama + " in its prijavljujeTakmicenjeUBorbamaCollection field has a non-nullable borbePojedinacno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PojedinacnaKategorija pojedinacnaKategorija = borbePojedinacno.getPojedinacnaKategorija();
            if (pojedinacnaKategorija != null) {
                pojedinacnaKategorija.setBorbePojedinacno(null);
                pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            }
            em.remove(borbePojedinacno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BorbePojedinacno> findBorbePojedinacnoEntities() {
        return findBorbePojedinacnoEntities(true, -1, -1);
    }

    public List<BorbePojedinacno> findBorbePojedinacnoEntities(int maxResults, int firstResult) {
        return findBorbePojedinacnoEntities(false, maxResults, firstResult);
    }

    private List<BorbePojedinacno> findBorbePojedinacnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BorbePojedinacno.class));
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

    public BorbePojedinacno findBorbePojedinacno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BorbePojedinacno.class, id);
        } finally {
            em.close();
        }
    }

    public int getBorbePojedinacnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BorbePojedinacno> rt = cq.from(BorbePojedinacno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
