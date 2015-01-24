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
import karatesavez.jpa.entity.Takmicenje;
import karatesavez.jpa.entity.Ekipa;
import karatesavez.jpa.entity.PrijavljujeBorbeEkipno;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.PrijavljujeKateEkipno;
import karatesavez.jpa.entity.UcesceEkipe;
import karatesavez.jpa.entity.UcesceEkipePK;

/**
 *
 * @author Marko
 */
public class UcesceEkipeJpaController implements Serializable {

    public UcesceEkipeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UcesceEkipe ucesceEkipe) throws PreexistingEntityException, Exception {
        if (ucesceEkipe.getUcesceEkipePK() == null) {
            ucesceEkipe.setUcesceEkipePK(new UcesceEkipePK());
        }
        if (ucesceEkipe.getPrijavljujeBorbeEkipnoCollection() == null) {
            ucesceEkipe.setPrijavljujeBorbeEkipnoCollection(new ArrayList<PrijavljujeBorbeEkipno>());
        }
        if (ucesceEkipe.getPrijavljujeKateEkipnoCollection() == null) {
            ucesceEkipe.setPrijavljujeKateEkipnoCollection(new ArrayList<PrijavljujeKateEkipno>());
        }
        ucesceEkipe.getUcesceEkipePK().setIDTakmicenja(ucesceEkipe.getTakmicenje().getIDTakmicenja());
        ucesceEkipe.getUcesceEkipePK().setIDEkipe(ucesceEkipe.getEkipa().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Takmicenje takmicenje = ucesceEkipe.getTakmicenje();
            if (takmicenje != null) {
                takmicenje = em.getReference(takmicenje.getClass(), takmicenje.getIDTakmicenja());
                ucesceEkipe.setTakmicenje(takmicenje);
            }
            Ekipa ekipa = ucesceEkipe.getEkipa();
            if (ekipa != null) {
                ekipa = em.getReference(ekipa.getClass(), ekipa.getIDEkipe());
                ucesceEkipe.setEkipa(ekipa);
            }
            Collection<PrijavljujeBorbeEkipno> attachedPrijavljujeBorbeEkipnoCollection = new ArrayList<PrijavljujeBorbeEkipno>();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach : ucesceEkipe.getPrijavljujeBorbeEkipnoCollection()) {
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach = em.getReference(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach.getClass(), prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach.getPrijavljujeBorbeEkipnoPK());
                attachedPrijavljujeBorbeEkipnoCollection.add(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipnoToAttach);
            }
            ucesceEkipe.setPrijavljujeBorbeEkipnoCollection(attachedPrijavljujeBorbeEkipnoCollection);
            Collection<PrijavljujeKateEkipno> attachedPrijavljujeKateEkipnoCollection = new ArrayList<PrijavljujeKateEkipno>();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach : ucesceEkipe.getPrijavljujeKateEkipnoCollection()) {
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach = em.getReference(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach.getClass(), prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach.getPrijavljujeKateEkipnoPK());
                attachedPrijavljujeKateEkipnoCollection.add(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipnoToAttach);
            }
            ucesceEkipe.setPrijavljujeKateEkipnoCollection(attachedPrijavljujeKateEkipnoCollection);
            em.persist(ucesceEkipe);
            if (takmicenje != null) {
                takmicenje.getUcesceEkipeCollection().add(ucesceEkipe);
                takmicenje = em.merge(takmicenje);
            }
            if (ekipa != null) {
                ekipa.getUcesceEkipeCollection().add(ucesceEkipe);
                ekipa = em.merge(ekipa);
            }
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno : ucesceEkipe.getPrijavljujeBorbeEkipnoCollection()) {
                UcesceEkipe oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.getUcesceEkipe();
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.setUcesceEkipe(ucesceEkipe);
                prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                if (oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno != null) {
                    oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                    oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno = em.merge(oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionPrijavljujeBorbeEkipno);
                }
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno : ucesceEkipe.getPrijavljujeKateEkipnoCollection()) {
                UcesceEkipe oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.getUcesceEkipe();
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.setUcesceEkipe(ucesceEkipe);
                prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = em.merge(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                if (oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno != null) {
                    oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                    oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno = em.merge(oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionPrijavljujeKateEkipno);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUcesceEkipe(ucesceEkipe.getUcesceEkipePK()) != null) {
                throw new PreexistingEntityException("UcesceEkipe " + ucesceEkipe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UcesceEkipe ucesceEkipe) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ucesceEkipe.getUcesceEkipePK().setIDTakmicenja(ucesceEkipe.getTakmicenje().getIDTakmicenja());
        ucesceEkipe.getUcesceEkipePK().setIDEkipe(ucesceEkipe.getEkipa().getIDEkipe());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UcesceEkipe persistentUcesceEkipe = em.find(UcesceEkipe.class, ucesceEkipe.getUcesceEkipePK());
            Takmicenje takmicenjeOld = persistentUcesceEkipe.getTakmicenje();
            Takmicenje takmicenjeNew = ucesceEkipe.getTakmicenje();
            Ekipa ekipaOld = persistentUcesceEkipe.getEkipa();
            Ekipa ekipaNew = ucesceEkipe.getEkipa();
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionOld = persistentUcesceEkipe.getPrijavljujeBorbeEkipnoCollection();
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionNew = ucesceEkipe.getPrijavljujeBorbeEkipnoCollection();
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionOld = persistentUcesceEkipe.getPrijavljujeKateEkipnoCollection();
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionNew = ucesceEkipe.getPrijavljujeKateEkipnoCollection();
            List<String> illegalOrphanMessages = null;
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionOld) {
                if (!prijavljujeBorbeEkipnoCollectionNew.contains(prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeBorbeEkipno " + prijavljujeBorbeEkipnoCollectionOldPrijavljujeBorbeEkipno + " since its ucesceEkipe field is not nullable.");
                }
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionOld) {
                if (!prijavljujeKateEkipnoCollectionNew.contains(prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PrijavljujeKateEkipno " + prijavljujeKateEkipnoCollectionOldPrijavljujeKateEkipno + " since its ucesceEkipe field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (takmicenjeNew != null) {
                takmicenjeNew = em.getReference(takmicenjeNew.getClass(), takmicenjeNew.getIDTakmicenja());
                ucesceEkipe.setTakmicenje(takmicenjeNew);
            }
            if (ekipaNew != null) {
                ekipaNew = em.getReference(ekipaNew.getClass(), ekipaNew.getIDEkipe());
                ucesceEkipe.setEkipa(ekipaNew);
            }
            Collection<PrijavljujeBorbeEkipno> attachedPrijavljujeBorbeEkipnoCollectionNew = new ArrayList<PrijavljujeBorbeEkipno>();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach : prijavljujeBorbeEkipnoCollectionNew) {
                prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach = em.getReference(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach.getClass(), prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach.getPrijavljujeBorbeEkipnoPK());
                attachedPrijavljujeBorbeEkipnoCollectionNew.add(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipnoToAttach);
            }
            prijavljujeBorbeEkipnoCollectionNew = attachedPrijavljujeBorbeEkipnoCollectionNew;
            ucesceEkipe.setPrijavljujeBorbeEkipnoCollection(prijavljujeBorbeEkipnoCollectionNew);
            Collection<PrijavljujeKateEkipno> attachedPrijavljujeKateEkipnoCollectionNew = new ArrayList<PrijavljujeKateEkipno>();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach : prijavljujeKateEkipnoCollectionNew) {
                prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach = em.getReference(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach.getClass(), prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach.getPrijavljujeKateEkipnoPK());
                attachedPrijavljujeKateEkipnoCollectionNew.add(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipnoToAttach);
            }
            prijavljujeKateEkipnoCollectionNew = attachedPrijavljujeKateEkipnoCollectionNew;
            ucesceEkipe.setPrijavljujeKateEkipnoCollection(prijavljujeKateEkipnoCollectionNew);
            ucesceEkipe = em.merge(ucesceEkipe);
            if (takmicenjeOld != null && !takmicenjeOld.equals(takmicenjeNew)) {
                takmicenjeOld.getUcesceEkipeCollection().remove(ucesceEkipe);
                takmicenjeOld = em.merge(takmicenjeOld);
            }
            if (takmicenjeNew != null && !takmicenjeNew.equals(takmicenjeOld)) {
                takmicenjeNew.getUcesceEkipeCollection().add(ucesceEkipe);
                takmicenjeNew = em.merge(takmicenjeNew);
            }
            if (ekipaOld != null && !ekipaOld.equals(ekipaNew)) {
                ekipaOld.getUcesceEkipeCollection().remove(ucesceEkipe);
                ekipaOld = em.merge(ekipaOld);
            }
            if (ekipaNew != null && !ekipaNew.equals(ekipaOld)) {
                ekipaNew.getUcesceEkipeCollection().add(ucesceEkipe);
                ekipaNew = em.merge(ekipaNew);
            }
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionNew) {
                if (!prijavljujeBorbeEkipnoCollectionOld.contains(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno)) {
                    UcesceEkipe oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.getUcesceEkipe();
                    prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.setUcesceEkipe(ucesceEkipe);
                    prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = em.merge(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                    if (oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno != null && !oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.equals(ucesceEkipe)) {
                        oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno.getPrijavljujeBorbeEkipnoCollection().remove(prijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                        oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno = em.merge(oldUcesceEkipeOfPrijavljujeBorbeEkipnoCollectionNewPrijavljujeBorbeEkipno);
                    }
                }
            }
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionNew) {
                if (!prijavljujeKateEkipnoCollectionOld.contains(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno)) {
                    UcesceEkipe oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.getUcesceEkipe();
                    prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.setUcesceEkipe(ucesceEkipe);
                    prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = em.merge(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                    if (oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno != null && !oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.equals(ucesceEkipe)) {
                        oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno.getPrijavljujeKateEkipnoCollection().remove(prijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                        oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno = em.merge(oldUcesceEkipeOfPrijavljujeKateEkipnoCollectionNewPrijavljujeKateEkipno);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UcesceEkipePK id = ucesceEkipe.getUcesceEkipePK();
                if (findUcesceEkipe(id) == null) {
                    throw new NonexistentEntityException("The ucesceEkipe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UcesceEkipePK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UcesceEkipe ucesceEkipe;
            try {
                ucesceEkipe = em.getReference(UcesceEkipe.class, id);
                ucesceEkipe.getUcesceEkipePK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ucesceEkipe with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollectionOrphanCheck = ucesceEkipe.getPrijavljujeBorbeEkipnoCollection();
            for (PrijavljujeBorbeEkipno prijavljujeBorbeEkipnoCollectionOrphanCheckPrijavljujeBorbeEkipno : prijavljujeBorbeEkipnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UcesceEkipe (" + ucesceEkipe + ") cannot be destroyed since the PrijavljujeBorbeEkipno " + prijavljujeBorbeEkipnoCollectionOrphanCheckPrijavljujeBorbeEkipno + " in its prijavljujeBorbeEkipnoCollection field has a non-nullable ucesceEkipe field.");
            }
            Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollectionOrphanCheck = ucesceEkipe.getPrijavljujeKateEkipnoCollection();
            for (PrijavljujeKateEkipno prijavljujeKateEkipnoCollectionOrphanCheckPrijavljujeKateEkipno : prijavljujeKateEkipnoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UcesceEkipe (" + ucesceEkipe + ") cannot be destroyed since the PrijavljujeKateEkipno " + prijavljujeKateEkipnoCollectionOrphanCheckPrijavljujeKateEkipno + " in its prijavljujeKateEkipnoCollection field has a non-nullable ucesceEkipe field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Takmicenje takmicenje = ucesceEkipe.getTakmicenje();
            if (takmicenje != null) {
                takmicenje.getUcesceEkipeCollection().remove(ucesceEkipe);
                takmicenje = em.merge(takmicenje);
            }
            Ekipa ekipa = ucesceEkipe.getEkipa();
            if (ekipa != null) {
                ekipa.getUcesceEkipeCollection().remove(ucesceEkipe);
                ekipa = em.merge(ekipa);
            }
            em.remove(ucesceEkipe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UcesceEkipe> findUcesceEkipeEntities() {
        return findUcesceEkipeEntities(true, -1, -1);
    }

    public List<UcesceEkipe> findUcesceEkipeEntities(int maxResults, int firstResult) {
        return findUcesceEkipeEntities(false, maxResults, firstResult);
    }

    private List<UcesceEkipe> findUcesceEkipeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UcesceEkipe.class));
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

    public UcesceEkipe findUcesceEkipe(UcesceEkipePK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UcesceEkipe.class, id);
        } finally {
            em.close();
        }
    }

    public int getUcesceEkipeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UcesceEkipe> rt = cq.from(UcesceEkipe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
