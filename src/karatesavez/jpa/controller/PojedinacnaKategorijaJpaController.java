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
import karatesavez.jpa.entity.BorbePojedinacno;
import karatesavez.jpa.entity.Kategorija;
import karatesavez.jpa.entity.KatePojedinacno;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PojedinacnaKategorija;

/**
 *
 * @author Marko
 */
public class PojedinacnaKategorijaJpaController implements Serializable {

    public PojedinacnaKategorijaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PojedinacnaKategorija pojedinacnaKategorija) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Kategorija kategorijaOrphanCheck = pojedinacnaKategorija.getKategorija();
        if (kategorijaOrphanCheck != null) {
            PojedinacnaKategorija oldPojedinacnaKategorijaOfKategorija = kategorijaOrphanCheck.getPojedinacnaKategorija();
            if (oldPojedinacnaKategorijaOfKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Kategorija " + kategorijaOrphanCheck + " already has an item of type PojedinacnaKategorija whose kategorija column cannot be null. Please make another selection for the kategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BorbePojedinacno borbePojedinacno = pojedinacnaKategorija.getBorbePojedinacno();
            if (borbePojedinacno != null) {
                borbePojedinacno = em.getReference(borbePojedinacno.getClass(), borbePojedinacno.getIDKategorije());
                pojedinacnaKategorija.setBorbePojedinacno(borbePojedinacno);
            }
            Kategorija kategorija = pojedinacnaKategorija.getKategorija();
            if (kategorija != null) {
                kategorija = em.getReference(kategorija.getClass(), kategorija.getIDKategorije());
                pojedinacnaKategorija.setKategorija(kategorija);
            }
            KatePojedinacno katePojedinacno = pojedinacnaKategorija.getKatePojedinacno();
            if (katePojedinacno != null) {
                katePojedinacno = em.getReference(katePojedinacno.getClass(), katePojedinacno.getIDKategorije());
                pojedinacnaKategorija.setKatePojedinacno(katePojedinacno);
            }
            em.persist(pojedinacnaKategorija);
            if (borbePojedinacno != null) {
                PojedinacnaKategorija oldPojedinacnaKategorijaOfBorbePojedinacno = borbePojedinacno.getPojedinacnaKategorija();
                if (oldPojedinacnaKategorijaOfBorbePojedinacno != null) {
                    oldPojedinacnaKategorijaOfBorbePojedinacno.setBorbePojedinacno(null);
                    oldPojedinacnaKategorijaOfBorbePojedinacno = em.merge(oldPojedinacnaKategorijaOfBorbePojedinacno);
                }
                borbePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorija);
                borbePojedinacno = em.merge(borbePojedinacno);
            }
            if (kategorija != null) {
                kategorija.setPojedinacnaKategorija(pojedinacnaKategorija);
                kategorija = em.merge(kategorija);
            }
            if (katePojedinacno != null) {
                PojedinacnaKategorija oldPojedinacnaKategorijaOfKatePojedinacno = katePojedinacno.getPojedinacnaKategorija();
                if (oldPojedinacnaKategorijaOfKatePojedinacno != null) {
                    oldPojedinacnaKategorijaOfKatePojedinacno.setKatePojedinacno(null);
                    oldPojedinacnaKategorijaOfKatePojedinacno = em.merge(oldPojedinacnaKategorijaOfKatePojedinacno);
                }
                katePojedinacno.setPojedinacnaKategorija(pojedinacnaKategorija);
                katePojedinacno = em.merge(katePojedinacno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPojedinacnaKategorija(pojedinacnaKategorija.getIDKategorije()) != null) {
                throw new PreexistingEntityException("PojedinacnaKategorija " + pojedinacnaKategorija + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PojedinacnaKategorija pojedinacnaKategorija) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PojedinacnaKategorija persistentPojedinacnaKategorija = em.find(PojedinacnaKategorija.class, pojedinacnaKategorija.getIDKategorije());
            BorbePojedinacno borbePojedinacnoOld = persistentPojedinacnaKategorija.getBorbePojedinacno();
            BorbePojedinacno borbePojedinacnoNew = pojedinacnaKategorija.getBorbePojedinacno();
            Kategorija kategorijaOld = persistentPojedinacnaKategorija.getKategorija();
            Kategorija kategorijaNew = pojedinacnaKategorija.getKategorija();
            KatePojedinacno katePojedinacnoOld = persistentPojedinacnaKategorija.getKatePojedinacno();
            KatePojedinacno katePojedinacnoNew = pojedinacnaKategorija.getKatePojedinacno();
            List<String> illegalOrphanMessages = null;
            if (borbePojedinacnoOld != null && !borbePojedinacnoOld.equals(borbePojedinacnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain BorbePojedinacno " + borbePojedinacnoOld + " since its pojedinacnaKategorija field is not nullable.");
            }
            if (kategorijaNew != null && !kategorijaNew.equals(kategorijaOld)) {
                PojedinacnaKategorija oldPojedinacnaKategorijaOfKategorija = kategorijaNew.getPojedinacnaKategorija();
                if (oldPojedinacnaKategorijaOfKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Kategorija " + kategorijaNew + " already has an item of type PojedinacnaKategorija whose kategorija column cannot be null. Please make another selection for the kategorija field.");
                }
            }
            if (katePojedinacnoOld != null && !katePojedinacnoOld.equals(katePojedinacnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain KatePojedinacno " + katePojedinacnoOld + " since its pojedinacnaKategorija field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (borbePojedinacnoNew != null) {
                borbePojedinacnoNew = em.getReference(borbePojedinacnoNew.getClass(), borbePojedinacnoNew.getIDKategorije());
                pojedinacnaKategorija.setBorbePojedinacno(borbePojedinacnoNew);
            }
            if (kategorijaNew != null) {
                kategorijaNew = em.getReference(kategorijaNew.getClass(), kategorijaNew.getIDKategorije());
                pojedinacnaKategorija.setKategorija(kategorijaNew);
            }
            if (katePojedinacnoNew != null) {
                katePojedinacnoNew = em.getReference(katePojedinacnoNew.getClass(), katePojedinacnoNew.getIDKategorije());
                pojedinacnaKategorija.setKatePojedinacno(katePojedinacnoNew);
            }
            pojedinacnaKategorija = em.merge(pojedinacnaKategorija);
            if (borbePojedinacnoNew != null && !borbePojedinacnoNew.equals(borbePojedinacnoOld)) {
                PojedinacnaKategorija oldPojedinacnaKategorijaOfBorbePojedinacno = borbePojedinacnoNew.getPojedinacnaKategorija();
                if (oldPojedinacnaKategorijaOfBorbePojedinacno != null) {
                    oldPojedinacnaKategorijaOfBorbePojedinacno.setBorbePojedinacno(null);
                    oldPojedinacnaKategorijaOfBorbePojedinacno = em.merge(oldPojedinacnaKategorijaOfBorbePojedinacno);
                }
                borbePojedinacnoNew.setPojedinacnaKategorija(pojedinacnaKategorija);
                borbePojedinacnoNew = em.merge(borbePojedinacnoNew);
            }
            if (kategorijaOld != null && !kategorijaOld.equals(kategorijaNew)) {
                kategorijaOld.setPojedinacnaKategorija(null);
                kategorijaOld = em.merge(kategorijaOld);
            }
            if (kategorijaNew != null && !kategorijaNew.equals(kategorijaOld)) {
                kategorijaNew.setPojedinacnaKategorija(pojedinacnaKategorija);
                kategorijaNew = em.merge(kategorijaNew);
            }
            if (katePojedinacnoNew != null && !katePojedinacnoNew.equals(katePojedinacnoOld)) {
                PojedinacnaKategorija oldPojedinacnaKategorijaOfKatePojedinacno = katePojedinacnoNew.getPojedinacnaKategorija();
                if (oldPojedinacnaKategorijaOfKatePojedinacno != null) {
                    oldPojedinacnaKategorijaOfKatePojedinacno.setKatePojedinacno(null);
                    oldPojedinacnaKategorijaOfKatePojedinacno = em.merge(oldPojedinacnaKategorijaOfKatePojedinacno);
                }
                katePojedinacnoNew.setPojedinacnaKategorija(pojedinacnaKategorija);
                katePojedinacnoNew = em.merge(katePojedinacnoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pojedinacnaKategorija.getIDKategorije();
                if (findPojedinacnaKategorija(id) == null) {
                    throw new NonexistentEntityException("The pojedinacnaKategorija with id " + id + " no longer exists.");
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
            PojedinacnaKategorija pojedinacnaKategorija;
            try {
                pojedinacnaKategorija = em.getReference(PojedinacnaKategorija.class, id);
                pojedinacnaKategorija.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pojedinacnaKategorija with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            BorbePojedinacno borbePojedinacnoOrphanCheck = pojedinacnaKategorija.getBorbePojedinacno();
            if (borbePojedinacnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PojedinacnaKategorija (" + pojedinacnaKategorija + ") cannot be destroyed since the BorbePojedinacno " + borbePojedinacnoOrphanCheck + " in its borbePojedinacno field has a non-nullable pojedinacnaKategorija field.");
            }
            KatePojedinacno katePojedinacnoOrphanCheck = pojedinacnaKategorija.getKatePojedinacno();
            if (katePojedinacnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PojedinacnaKategorija (" + pojedinacnaKategorija + ") cannot be destroyed since the KatePojedinacno " + katePojedinacnoOrphanCheck + " in its katePojedinacno field has a non-nullable pojedinacnaKategorija field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Kategorija kategorija = pojedinacnaKategorija.getKategorija();
            if (kategorija != null) {
                kategorija.setPojedinacnaKategorija(null);
                kategorija = em.merge(kategorija);
            }
            em.remove(pojedinacnaKategorija);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PojedinacnaKategorija> findPojedinacnaKategorijaEntities() {
        return findPojedinacnaKategorijaEntities(true, -1, -1);
    }

    public List<PojedinacnaKategorija> findPojedinacnaKategorijaEntities(int maxResults, int firstResult) {
        return findPojedinacnaKategorijaEntities(false, maxResults, firstResult);
    }

    private List<PojedinacnaKategorija> findPojedinacnaKategorijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PojedinacnaKategorija.class));
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

    public PojedinacnaKategorija findPojedinacnaKategorija(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PojedinacnaKategorija.class, id);
        } finally {
            em.close();
        }
    }

    public int getPojedinacnaKategorijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PojedinacnaKategorija> rt = cq.from(PojedinacnaKategorija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
