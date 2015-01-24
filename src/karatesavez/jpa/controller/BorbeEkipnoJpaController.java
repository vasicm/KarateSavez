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
import karatesavez.jpa.entity.PrijavljujeBorbeEkipno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.BorbeEkipno;

/**
 *
 * @author Marko
 */
public class BorbeEkipnoJpaController implements Serializable {

    public BorbeEkipnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BorbeEkipno borbeEkipno) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (borbeEkipno.getPrijavljujeBorbeEkipnoCollection() == null) {
            borbeEkipno.setPrijavljujeBorbeEkipnoCollection(new ArrayList<PrijavljujeBorbeEkipno>());
        }
        List<String> illegalOrphanMessages = null;
        EkipnaKategorija ekipnaKategorijaOrphanCheck = borbeEkipno.getEkipnaKategorija();
        if (ekipnaKategorijaOrphanCheck != null) {
            BorbeEkipno oldBorbeEkipnoOfEkipnaKategorija = ekipnaKategorijaOrphanCheck.getBorbeEkipno();
            if (oldBorbeEkipnoOfEkipnaKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The EkipnaKategorija " + ekipnaKategorijaOrphanCheck + " already has an item of type BorbeEkipno whose ekipnaKategorija column cannot be null. Please make another selection for the ekipnaKategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaKategorija ekipnaKategorija = borbeEkipno.getEkipnaKategorija();
            if (ekipnaKategorija != null) {
                ekipnaKategorija = em.getReference(ekipnaKategorija.getClass(), ekipnaKategorija.getIDKategorije());
                borbeEkipno.setEkipnaKategorija(ekipnaKategorija);
            }
            Collection<PrijavljujeBorbeEkipno> attachedPrijavljujeBorbeEkipnoCollection = new ArrayList<PrijavljujeBorbeEkipno>();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach : borbeEkipno.getPrijavljujeBorbeEkipnoCollection()) {
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach = em.getReference(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach.getClass(), prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach.getPrijavljujeBorbeEkipnoPK());
                attachedPrijavljujeBorbeEkipnoCollection.add(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach);
            }
            borbeEkipno.setPrijavljujeBorbeEkipnoCollection(attachedPrijavljujeBorbeEkipnoCollection);
            em.persist(borbeEkipno);
            if (ekipnaKategorija != null) {
                ekipnaKategorija.setBorbeEkipno(borbeEkipno);
                ekipnaKategorija = em.merge(ekipnaKategorija);
            }
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno : borbeEkipno.getPrijavljujeBorbeEkipnoCollection()) {
                BorbeEkipno oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.getBorbeEkipno();
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.setBorbeEkipno(borbeEkipno);
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                if (oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno != null) {
                    oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                    oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = em.merge(oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findBorbeEkipno(borbeEkipno.getIDKategorije()) != null) {
                throw new PreexistingEntityException("BorbeEkipno " + borbeEkipno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BorbeEkipno borbeEkipno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BorbeEkipno persistentBorbeEkipno = em.find(BorbeEkipno.class, borbeEkipno.getIDKategorije());
            EkipnaKategorija ekipnaKategorijaOld = persistentBorbeEkipno.getEkipnaKategorija();
            EkipnaKategorija ekipnaKategorijaNew = borbeEkipno.getEkipnaKategorija();
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionOld = persistentBorbeEkipno.getPrijavljujeBorbeEkipnoCollection();
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionNew = borbeEkipno.getPrijavljujeBorbeEkipnoCollection();
            List<String> illegalOrphanMessages = null;
            if (ekipnaKategorijaNew != null && !ekipnaKategorijaNew.equals(ekipnaKategorijaOld)) {
                BorbeEkipno oldBorbeEkipnoOfEkipnaKategorija = ekipnaKategorijaNew.getBorbeEkipno();
                if (oldBorbeEkipnoOfEkipnaKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The EkipnaKategorija " + ekipnaKategorijaNew + " already has an item of type BorbeEkipno whose ekipnaKategorija column cannot be null. Please make another selection for the ekipnaKategorija field.");
                }
            }
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionOld) {
                if (!prijavljujeBorbeEkipnoCollectionNew.contains(prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeBorbeEkipno " + prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno + " since its borbeEkipno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ekipnaKategorijaNew != null) {
                ekipnaKategorijaNew = em.getReference(ekipnaKategorijaNew.getClass(), ekipnaKategorijaNew.getIDKategorije());
                borbeEkipno.setEkipnaKategorija(ekipnaKategorijaNew);
            }
            Collection<PrijavljujeBorbeEkipno> attachedPrijavljujeBorbeEkipnoCollectionNew = new ArrayList<PrijavljujeBorbeEkipno>();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach : prijavljujeBorbeEkipnoCollectionNew) {
                prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach = em.getReference(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach.getClass(), prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach.getPrijavljujeBorbeEkipnoPK());
                attachedPrijavljujeBorbeEkipnoCollectionNew.add(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach);
            }
            prijavljujeBorbeEkipnoCollectionNew = attachedPrijavljujeBorbeEkipnoCollectionNew;
            borbeEkipno.setPrijavljujeBorbeEkipnoCollection(prijavljujeBorbeEkipnoCollectionNew);
            borbeEkipno = em.merge(borbeEkipno);
            if (ekipnaKategorijaOld != null && !ekipnaKategorijaOld.equals(ekipnaKategorijaNew)) {
                ekipnaKategorijaOld.setBorbeEkipno(null);
                ekipnaKategorijaOld = em.merge(ekipnaKategorijaOld);
            }
            if (ekipnaKategorijaNew != null && !ekipnaKategorijaNew.equals(ekipnaKategorijaOld)) {
                ekipnaKategorijaNew.setBorbeEkipno(borbeEkipno);
                ekipnaKategorijaNew = em.merge(ekipnaKategorijaNew);
            }
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionNew) {
                if (!prijavljujeBorbeEkipnoCollectionOld.contains(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno)) {
                    BorbeEkipno oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.getBorbeEkipno();
                    prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.setBorbeEkipno(borbeEkipno);
                    prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                    if (oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno != null && !oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.equals(borbeEkipno)) {
                        oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                        oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = em.merge(oldBorbeEkipnoOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = borbeEkipno.getIDKategorije();
                if (findBorbeEkipno(id) == null) {
                    throw new NonexistentEntityException("The borbeEkipno with id " + id + " no longer exists.");
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
            BorbeEkipno borbeEkipno;
            try {
                borbeEkipno = em.getReference(BorbeEkipno.class, id);
                borbeEkipno.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The borbeEkipno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionOrphanCheck = borbeEkipno.getPrijavljujeBorbeEkipnoCollection();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionOrphanCheckPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This BorbeEkipno (" + borbeEkipno + ") cannot be destroyed since the PrijavljujeBorbeEkipno " + prijavljujeBorbeEkipnoCollectionOrphanCheckPrijavljujeBorbeEkipno + " in its prijavljujeBorbeEkipnoCollection field has a non-nullable borbeEkipno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EkipnaKategorija ekipnaKategorija = borbeEkipno.getEkipnaKategorija();
            if (ekipnaKategorija != null) {
                ekipnaKategorija.setBorbeEkipno(null);
                ekipnaKategorija = em.merge(ekipnaKategorija);
            }
            em.remove(borbeEkipno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BorbeEkipno> findBorbeEkipnoEntities() {
        return findBorbeEkipnoEntities(true, -1, -1);
    }

    public List<BorbeEkipno> findBorbeEkipnoEntities(int maxResults, int firstResult) {
        return findBorbeEkipnoEntities(false, maxResults, firstResult);
    }

    private List<BorbeEkipno> findBorbeEkipnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BorbeEkipno.class));
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

    public BorbeEkipno findBorbeEkipno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BorbeEkipno.class, id);
        } finally {
            em.close();
        }
    }

    public int getBorbeEkipnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BorbeEkipno> rt = cq.from(BorbeEkipno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
