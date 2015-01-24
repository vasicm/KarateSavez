/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Kategorija;
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.TakmicenjeKategorija;
import karatesavez.jpa.entity.TakmicenjeKategorijaPK;

/**
 *
 * @author Marko
 */
public class TakmicenjeKategorijaJpaController implements Serializable {

    public TakmicenjeKategorijaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TakmicenjeKategorija takmicenjeKategorija) throws PreexistingEntityException, Exception {
        if (takmicenjeKategorija.getTakmicenjeKategorijaPK() == null) {
            takmicenjeKategorija.setTakmicenjeKategorijaPK(new TakmicenjeKategorijaPK());
        }
        takmicenjeKategorija.getTakmicenjeKategorijaPK().setIDKategorije(takmicenjeKategorija.getKategorija().getIDKategorije());
        takmicenjeKategorija.getTakmicenjeKategorijaPK().setIDTakmicenja(takmicenjeKategorija.getTakmicenje().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Kategorija kategorija = takmicenjeKategorija.getKategorija();
            if (kategorija != null) {
                kategorija = em.getReference(kategorija.getClass(), kategorija.getIDKategorije());
                takmicenjeKategorija.setKategorija(kategorija);
            }
            Takmicenje takmicenje = takmicenjeKategorija.getTakmicenje();
            if (takmicenje != null) {
                takmicenje = em.getReference(takmicenje.getClass(), takmicenje.getIDTakmicenja());
                takmicenjeKategorija.setTakmicenje(takmicenje);
            }
            em.persist(takmicenjeKategorija);
            if (kategorija != null) {
                kategorija.getTakmicenjeKategorijaCollection().add(takmicenjeKategorija);
                kategorija = em.merge(kategorija);
            }
            if (takmicenje != null) {
                takmicenje.getTakmicenjeKategorijaCollection().add(takmicenjeKategorija);
                takmicenje = em.merge(takmicenje);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTakmicenjeKategorija(takmicenjeKategorija.getTakmicenjeKategorijaPK()) != null) {
                throw new PreexistingEntityException("TakmicenjeKategorija " + takmicenjeKategorija + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TakmicenjeKategorija takmicenjeKategorija) throws NonexistentEntityException, Exception {
        takmicenjeKategorija.getTakmicenjeKategorijaPK().setIDKategorije(takmicenjeKategorija.getKategorija().getIDKategorije());
        takmicenjeKategorija.getTakmicenjeKategorijaPK().setIDTakmicenja(takmicenjeKategorija.getTakmicenje().getIDTakmicenja());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TakmicenjeKategorija persistentTakmicenjeKategorija = em.find(TakmicenjeKategorija.class, takmicenjeKategorija.getTakmicenjeKategorijaPK());
            Kategorija kategorijaOld = persistentTakmicenjeKategorija.getKategorija();
            Kategorija kategorijaNew = takmicenjeKategorija.getKategorija();
            Takmicenje takmicenjeOld = persistentTakmicenjeKategorija.getTakmicenje();
            Takmicenje takmicenjeNew = takmicenjeKategorija.getTakmicenje();
            if (kategorijaNew != null) {
                kategorijaNew = em.getReference(kategorijaNew.getClass(), kategorijaNew.getIDKategorije());
                takmicenjeKategorija.setKategorija(kategorijaNew);
            }
            if (takmicenjeNew != null) {
                takmicenjeNew = em.getReference(takmicenjeNew.getClass(), takmicenjeNew.getIDTakmicenja());
                takmicenjeKategorija.setTakmicenje(takmicenjeNew);
            }
            takmicenjeKategorija = em.merge(takmicenjeKategorija);
            if (kategorijaOld != null && !kategorijaOld.equals(kategorijaNew)) {
                kategorijaOld.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorija);
                kategorijaOld = em.merge(kategorijaOld);
            }
            if (kategorijaNew != null && !kategorijaNew.equals(kategorijaOld)) {
                kategorijaNew.getTakmicenjeKategorijaCollection().add(takmicenjeKategorija);
                kategorijaNew = em.merge(kategorijaNew);
            }
            if (takmicenjeOld != null && !takmicenjeOld.equals(takmicenjeNew)) {
                takmicenjeOld.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorija);
                takmicenjeOld = em.merge(takmicenjeOld);
            }
            if (takmicenjeNew != null && !takmicenjeNew.equals(takmicenjeOld)) {
                takmicenjeNew.getTakmicenjeKategorijaCollection().add(takmicenjeKategorija);
                takmicenjeNew = em.merge(takmicenjeNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TakmicenjeKategorijaPK id = takmicenjeKategorija.getTakmicenjeKategorijaPK();
                if (findTakmicenjeKategorija(id) == null) {
                    throw new NonexistentEntityException("The takmicenjeKategorija with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TakmicenjeKategorijaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TakmicenjeKategorija takmicenjeKategorija;
            try {
                takmicenjeKategorija = em.getReference(TakmicenjeKategorija.class, id);
                takmicenjeKategorija.getTakmicenjeKategorijaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The takmicenjeKategorija with id " + id + " no longer exists.", enfe);
            }
            Kategorija kategorija = takmicenjeKategorija.getKategorija();
            if (kategorija != null) {
                kategorija.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorija);
                kategorija = em.merge(kategorija);
            }
            Takmicenje takmicenje = takmicenjeKategorija.getTakmicenje();
            if (takmicenje != null) {
                takmicenje.getTakmicenjeKategorijaCollection().remove(takmicenjeKategorija);
                takmicenje = em.merge(takmicenje);
            }
            em.remove(takmicenjeKategorija);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TakmicenjeKategorija> findTakmicenjeKategorijaEntities() {
        return findTakmicenjeKategorijaEntities(true, -1, -1);
    }

    public List<TakmicenjeKategorija> findTakmicenjeKategorijaEntities(int maxResults, int firstResult) {
        return findTakmicenjeKategorijaEntities(false, maxResults, firstResult);
    }

    private List<TakmicenjeKategorija> findTakmicenjeKategorijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TakmicenjeKategorija.class));
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

    public TakmicenjeKategorija findTakmicenjeKategorija(TakmicenjeKategorijaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TakmicenjeKategorija.class, id);
        } finally {
            em.close();
        }
    }

    public int getTakmicenjeKategorijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TakmicenjeKategorija> rt = cq.from(TakmicenjeKategorija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
