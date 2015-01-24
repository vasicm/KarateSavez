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
import karatesavez.jpa.entity.PrijavljujeKateEkipno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.KateEkipno;

/**
 *
 * @author Marko
 */
public class KateEkipnoJpaController implements Serializable {

    public KateEkipnoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(KateEkipno kateEkipno) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (kateEkipno.getPrijavljujeKateEkipnoCollection() == null) {
            kateEkipno.setPrijavljujeKateEkipnoCollection(new ArrayList<PrijavljujeKateEkipno>());
        }
        List<String> illegalOrphanMessages = null;
        EkipnaKategorija ekipnaKategorijaOrphanCheck = kateEkipno.getEkipnaKategorija();
        if (ekipnaKategorijaOrphanCheck != null) {
            KateEkipno oldKateEkipnoOfEkipnaKategorija = ekipnaKategorijaOrphanCheck.getKateEkipno();
            if (oldKateEkipnoOfEkipnaKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The EkipnaKategorija " + ekipnaKategorijaOrphanCheck + " already has an item of type KateEkipno whose ekipnaKategorija column cannot be null. Please make another selection for the ekipnaKategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaKategorija ekipnaKategorija = kateEkipno.getEkipnaKategorija();
            if (ekipnaKategorija != null) {
                ekipnaKategorija = em.getReference(ekipnaKategorija.getClass(), ekipnaKategorija.getIDKategorije());
                kateEkipno.setEkipnaKategorija(ekipnaKategorija);
            }
            Collection<PrijavljujeKateEkipno> attachedPrijavljujeKateEkipnoCollection = new ArrayList<PrijavljujeKateEkipno>();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach : kateEkipno.getPrijavljujeKateEkipnoCollection()) {
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach = em.getReference(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach.getClass(), prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach.getPrijavljujeKateEkipnoPK());
                attachedPrijavljujeKateEkipnoCollection.add(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach);
            }
            kateEkipno.setPrijavljujeKateEkipnoCollection(attachedPrijavljujeKateEkipnoCollection);
            em.persist(kateEkipno);
            if (ekipnaKategorija != null) {
                ekipnaKategorija.setKateEkipno(kateEkipno);
                ekipnaKategorija = em.merge(ekipnaKategorija);
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno : kateEkipno.getPrijavljujeKateEkipnoCollection()) {
                KateEkipno oldKateEkipnoOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.getKateEkipno();
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.setKateEkipno(kateEkipno);
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = em.merge(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                if (oldKateEkipnoOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno != null) {
                    oldKateEkipnoOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                    oldKateEkipnoOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = em.merge(oldKateEkipnoOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findKateEkipno(kateEkipno.getIDKategorije()) != null) {
                throw new PreexistingEntityException("KateEkipno " + kateEkipno + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(KateEkipno kateEkipno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            KateEkipno persistentKateEkipno = em.find(KateEkipno.class, kateEkipno.getIDKategorije());
            EkipnaKategorija ekipnaKategorijaOld = persistentKateEkipno.getEkipnaKategorija();
            EkipnaKategorija ekipnaKategorijaNew = kateEkipno.getEkipnaKategorija();
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionOld = persistentKateEkipno.getPrijavljujeKateEkipnoCollection();
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionNew = kateEkipno.getPrijavljujeKateEkipnoCollection();
            List<String> illegalOrphanMessages = null;
            if (ekipnaKategorijaNew != null && !ekipnaKategorijaNew.equals(ekipnaKategorijaOld)) {
                KateEkipno oldKateEkipnoOfEkipnaKategorija = ekipnaKategorijaNew.getKateEkipno();
                if (oldKateEkipnoOfEkipnaKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The EkipnaKategorija " + ekipnaKategorijaNew + " already has an item of type KateEkipno whose ekipnaKategorija column cannot be null. Please make another selection for the ekipnaKategorija field.");
                }
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionOld) {
                if (!prijavljujeKateEkipnoCollectionNew.contains(prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeKateEkipno " + prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno + " since its kateEkipno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ekipnaKategorijaNew != null) {
                ekipnaKategorijaNew = em.getReference(ekipnaKategorijaNew.getClass(), ekipnaKategorijaNew.getIDKategorije());
                kateEkipno.setEkipnaKategorija(ekipnaKategorijaNew);
            }
            Collection<PrijavljujeKateEkipno> attachedPrijavljujeKateEkipnoCollectionNew = new ArrayList<PrijavljujeKateEkipno>();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach : prijavljujeKateEkipnoCollectionNew) {
                prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach = em.getReference(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach.getClass(), prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach.getPrijavljujeKateEkipnoPK());
                attachedPrijavljujeKateEkipnoCollectionNew.add(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach);
            }
            prijavljujeKateEkipnoCollectionNew = attachedPrijavljujeKateEkipnoCollectionNew;
            kateEkipno.setPrijavljujeKateEkipnoCollection(prijavljujeKateEkipnoCollectionNew);
            kateEkipno = em.merge(kateEkipno);
            if (ekipnaKategorijaOld != null && !ekipnaKategorijaOld.equals(ekipnaKategorijaNew)) {
                ekipnaKategorijaOld.setKateEkipno(null);
                ekipnaKategorijaOld = em.merge(ekipnaKategorijaOld);
            }
            if (ekipnaKategorijaNew != null && !ekipnaKategorijaNew.equals(ekipnaKategorijaOld)) {
                ekipnaKategorijaNew.setKateEkipno(kateEkipno);
                ekipnaKategorijaNew = em.merge(ekipnaKategorijaNew);
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionNew) {
                if (!prijavljujeKateEkipnoCollectionOld.contains(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno)) {
                    KateEkipno oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.getKateEkipno();
                    prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.setKateEkipno(kateEkipno);
                    prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = em.merge(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                    if (oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno != null && !oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.equals(kateEkipno)) {
                        oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                        oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = em.merge(oldKateEkipnoOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = kateEkipno.getIDKategorije();
                if (findKateEkipno(id) == null) {
                    throw new NonexistentEntityException("The kateEkipno with id " + id + " no longer exists.");
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
            KateEkipno kateEkipno;
            try {
                kateEkipno = em.getReference(KateEkipno.class, id);
                kateEkipno.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The kateEkipno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionOrphanCheck = kateEkipno.getPrijavljujeKateEkipnoCollection();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionOrphanCheckPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This KateEkipno (" + kateEkipno + ") cannot be destroyed since the PrijavljujeKateEkipno " + prijavljujeKateEkipnoCollectionOrphanCheckPrijavljujeKateEkipno + " in its prijavljujeKateEkipnoCollection field has a non-nullable kateEkipno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            EkipnaKategorija ekipnaKategorija = kateEkipno.getEkipnaKategorija();
            if (ekipnaKategorija != null) {
                ekipnaKategorija.setKateEkipno(null);
                ekipnaKategorija = em.merge(ekipnaKategorija);
            }
            em.remove(kateEkipno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<KateEkipno> findKateEkipnoEntities() {
        return findKateEkipnoEntities(true, -1, -1);
    }

    public List<KateEkipno> findKateEkipnoEntities(int maxResults, int firstResult) {
        return findKateEkipnoEntities(false, maxResults, firstResult);
    }

    private List<KateEkipno> findKateEkipnoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(KateEkipno.class));
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

    public KateEkipno findKateEkipno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(KateEkipno.class, id);
        } finally {
            em.close();
        }
    }

    public int getKateEkipnoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<KateEkipno> rt = cq.from(KateEkipno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
