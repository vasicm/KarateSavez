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
import karatesavez.jpa.entity.BorbeEkipno;
import karatesavez.jpa.entity.KateEkipno;
import karatesavez.jpa.entity.Kategorija;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.EkipnaKategorija;

/**
 *
 * @author Marko
 */
public class EkipnaKategorijaJpaController implements Serializable {

    public EkipnaKategorijaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EkipnaKategorija ekipnaKategorija) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Kategorija kategorijaOrphanCheck = ekipnaKategorija.getKategorija();
        if (kategorijaOrphanCheck != null) {
            EkipnaKategorija oldEkipnaKategorijaOfKategorija = kategorijaOrphanCheck.getEkipnaKategorija();
            if (oldEkipnaKategorijaOfKategorija != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Kategorija " + kategorijaOrphanCheck + " already has an item of type EkipnaKategorija whose kategorija column cannot be null. Please make another selection for the kategorija field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BorbeEkipno borbeEkipno = ekipnaKategorija.getBorbeEkipno();
            if (borbeEkipno != null) {
                borbeEkipno = em.getReference(borbeEkipno.getClass(), borbeEkipno.getIDKategorije());
                ekipnaKategorija.setBorbeEkipno(borbeEkipno);
            }
            KateEkipno kateEkipno = ekipnaKategorija.getKateEkipno();
            if (kateEkipno != null) {
                kateEkipno = em.getReference(kateEkipno.getClass(), kateEkipno.getIDKategorije());
                ekipnaKategorija.setKateEkipno(kateEkipno);
            }
            Kategorija kategorija = ekipnaKategorija.getKategorija();
            if (kategorija != null) {
                kategorija = em.getReference(kategorija.getClass(), kategorija.getIDKategorije());
                ekipnaKategorija.setKategorija(kategorija);
            }
            em.persist(ekipnaKategorija);
            if (borbeEkipno != null) {
                EkipnaKategorija oldEkipnaKategorijaOfBorbeEkipno = borbeEkipno.getEkipnaKategorija();
                if (oldEkipnaKategorijaOfBorbeEkipno != null) {
                    oldEkipnaKategorijaOfBorbeEkipno.setBorbeEkipno(null);
                    oldEkipnaKategorijaOfBorbeEkipno = em.merge(oldEkipnaKategorijaOfBorbeEkipno);
                }
                borbeEkipno.setEkipnaKategorija(ekipnaKategorija);
                borbeEkipno = em.merge(borbeEkipno);
            }
            if (kateEkipno != null) {
                EkipnaKategorija oldEkipnaKategorijaOfKateEkipno = kateEkipno.getEkipnaKategorija();
                if (oldEkipnaKategorijaOfKateEkipno != null) {
                    oldEkipnaKategorijaOfKateEkipno.setKateEkipno(null);
                    oldEkipnaKategorijaOfKateEkipno = em.merge(oldEkipnaKategorijaOfKateEkipno);
                }
                kateEkipno.setEkipnaKategorija(ekipnaKategorija);
                kateEkipno = em.merge(kateEkipno);
            }
            if (kategorija != null) {
                kategorija.setEkipnaKategorija(ekipnaKategorija);
                kategorija = em.merge(kategorija);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEkipnaKategorija(ekipnaKategorija.getIDKategorije()) != null) {
                throw new PreexistingEntityException("EkipnaKategorija " + ekipnaKategorija + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EkipnaKategorija ekipnaKategorija) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EkipnaKategorija persistentEkipnaKategorija = em.find(EkipnaKategorija.class, ekipnaKategorija.getIDKategorije());
            BorbeEkipno borbeEkipnoOld = persistentEkipnaKategorija.getBorbeEkipno();
            BorbeEkipno borbeEkipnoNew = ekipnaKategorija.getBorbeEkipno();
            KateEkipno kateEkipnoOld = persistentEkipnaKategorija.getKateEkipno();
            KateEkipno kateEkipnoNew = ekipnaKategorija.getKateEkipno();
            Kategorija kategorijaOld = persistentEkipnaKategorija.getKategorija();
            Kategorija kategorijaNew = ekipnaKategorija.getKategorija();
            List<String> illegalOrphanMessages = null;
            if (borbeEkipnoOld != null && !borbeEkipnoOld.equals(borbeEkipnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain BorbeEkipno " + borbeEkipnoOld + " since its ekipnaKategorija field is not nullable.");
            }
            if (kateEkipnoOld != null && !kateEkipnoOld.equals(kateEkipnoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain KateEkipno " + kateEkipnoOld + " since its ekipnaKategorija field is not nullable.");
            }
            if (kategorijaNew != null && !kategorijaNew.equals(kategorijaOld)) {
                EkipnaKategorija oldEkipnaKategorijaOfKategorija = kategorijaNew.getEkipnaKategorija();
                if (oldEkipnaKategorijaOfKategorija != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Kategorija " + kategorijaNew + " already has an item of type EkipnaKategorija whose kategorija column cannot be null. Please make another selection for the kategorija field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (borbeEkipnoNew != null) {
                borbeEkipnoNew = em.getReference(borbeEkipnoNew.getClass(), borbeEkipnoNew.getIDKategorije());
                ekipnaKategorija.setBorbeEkipno(borbeEkipnoNew);
            }
            if (kateEkipnoNew != null) {
                kateEkipnoNew = em.getReference(kateEkipnoNew.getClass(), kateEkipnoNew.getIDKategorije());
                ekipnaKategorija.setKateEkipno(kateEkipnoNew);
            }
            if (kategorijaNew != null) {
                kategorijaNew = em.getReference(kategorijaNew.getClass(), kategorijaNew.getIDKategorije());
                ekipnaKategorija.setKategorija(kategorijaNew);
            }
            ekipnaKategorija = em.merge(ekipnaKategorija);
            if (borbeEkipnoNew != null && !borbeEkipnoNew.equals(borbeEkipnoOld)) {
                EkipnaKategorija oldEkipnaKategorijaOfBorbeEkipno = borbeEkipnoNew.getEkipnaKategorija();
                if (oldEkipnaKategorijaOfBorbeEkipno != null) {
                    oldEkipnaKategorijaOfBorbeEkipno.setBorbeEkipno(null);
                    oldEkipnaKategorijaOfBorbeEkipno = em.merge(oldEkipnaKategorijaOfBorbeEkipno);
                }
                borbeEkipnoNew.setEkipnaKategorija(ekipnaKategorija);
                borbeEkipnoNew = em.merge(borbeEkipnoNew);
            }
            if (kateEkipnoNew != null && !kateEkipnoNew.equals(kateEkipnoOld)) {
                EkipnaKategorija oldEkipnaKategorijaOfKateEkipno = kateEkipnoNew.getEkipnaKategorija();
                if (oldEkipnaKategorijaOfKateEkipno != null) {
                    oldEkipnaKategorijaOfKateEkipno.setKateEkipno(null);
                    oldEkipnaKategorijaOfKateEkipno = em.merge(oldEkipnaKategorijaOfKateEkipno);
                }
                kateEkipnoNew.setEkipnaKategorija(ekipnaKategorija);
                kateEkipnoNew = em.merge(kateEkipnoNew);
            }
            if (kategorijaOld != null && !kategorijaOld.equals(kategorijaNew)) {
                kategorijaOld.setEkipnaKategorija(null);
                kategorijaOld = em.merge(kategorijaOld);
            }
            if (kategorijaNew != null && !kategorijaNew.equals(kategorijaOld)) {
                kategorijaNew.setEkipnaKategorija(ekipnaKategorija);
                kategorijaNew = em.merge(kategorijaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ekipnaKategorija.getIDKategorije();
                if (findEkipnaKategorija(id) == null) {
                    throw new NonexistentEntityException("The ekipnaKategorija with id " + id + " no longer exists.");
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
            EkipnaKategorija ekipnaKategorija;
            try {
                ekipnaKategorija = em.getReference(EkipnaKategorija.class, id);
                ekipnaKategorija.getIDKategorije();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ekipnaKategorija with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            BorbeEkipno borbeEkipnoOrphanCheck = ekipnaKategorija.getBorbeEkipno();
            if (borbeEkipnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EkipnaKategorija (" + ekipnaKategorija + ") cannot be destroyed since the BorbeEkipno " + borbeEkipnoOrphanCheck + " in its borbeEkipno field has a non-nullable ekipnaKategorija field.");
            }
            KateEkipno kateEkipnoOrphanCheck = ekipnaKategorija.getKateEkipno();
            if (kateEkipnoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This EkipnaKategorija (" + ekipnaKategorija + ") cannot be destroyed since the KateEkipno " + kateEkipnoOrphanCheck + " in its kateEkipno field has a non-nullable ekipnaKategorija field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Kategorija kategorija = ekipnaKategorija.getKategorija();
            if (kategorija != null) {
                kategorija.setEkipnaKategorija(null);
                kategorija = em.merge(kategorija);
            }
            em.remove(ekipnaKategorija);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EkipnaKategorija> findEkipnaKategorijaEntities() {
        return findEkipnaKategorijaEntities(true, -1, -1);
    }

    public List<EkipnaKategorija> findEkipnaKategorijaEntities(int maxResults, int firstResult) {
        return findEkipnaKategorijaEntities(false, maxResults, firstResult);
    }

    private List<EkipnaKategorija> findEkipnaKategorijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EkipnaKategorija.class));
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

    public EkipnaKategorija findEkipnaKategorija(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EkipnaKategorija.class, id);
        } finally {
            em.close();
        }
    }

    public int getEkipnaKategorijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EkipnaKategorija> rt = cq.from(EkipnaKategorija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
