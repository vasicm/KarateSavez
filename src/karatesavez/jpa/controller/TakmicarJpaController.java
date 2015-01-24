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
import karatesavez.jpa.entity.Ekipa;
import karatesavez.jpa.entity.Clan;
import karatesavez.jpa.entity.UcescePojedinca;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Takmicar;

/**
 *
 * @author Marko
 */
public class TakmicarJpaController implements Serializable {

    public TakmicarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Takmicar takmicar) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (takmicar.getUcescePojedincaCollection() == null) {
            takmicar.setUcescePojedincaCollection(new ArrayList<UcescePojedinca>());
        }
        List<String> illegalOrphanMessages = null;
        Clan clanOrphanCheck = takmicar.getClan();
        if (clanOrphanCheck != null) {
            Takmicar oldTakmicarOfClan = clanOrphanCheck.getTakmicar();
            if (oldTakmicarOfClan != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Clan " + clanOrphanCheck + " already has an item of type Takmicar whose clan column cannot be null. Please make another selection for the clan field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ekipa IDEkipe = takmicar.getIDEkipe();
            if (IDEkipe != null) {
                IDEkipe = em.getReference(IDEkipe.getClass(), IDEkipe.getIDEkipe());
                takmicar.setIDEkipe(IDEkipe);
            }
            Clan clan = takmicar.getClan();
            if (clan != null) {
                clan = em.getReference(clan.getClass(), clan.getJmb());
                takmicar.setClan(clan);
            }
            Collection<UcescePojedinca> attachedUcescePojedincaCollection = new ArrayList<UcescePojedinca>();
            for (UcescePojedinca ucescePojedincaCollectionUcescePojedincaToAttach : takmicar.getUcescePojedincaCollection()) {
                ucescePojedincaCollectionUcescePojedincaToAttach = em.getReference(ucescePojedincaCollectionUcescePojedincaToAttach.getClass(), ucescePojedincaCollectionUcescePojedincaToAttach.getUcescePojedincaPK());
                attachedUcescePojedincaCollection.add(ucescePojedincaCollectionUcescePojedincaToAttach);
            }
            takmicar.setUcescePojedincaCollection(attachedUcescePojedincaCollection);
            em.persist(takmicar);
            if (IDEkipe != null) {
                IDEkipe.getTakmicarCollection().add(takmicar);
                IDEkipe = em.merge(IDEkipe);
            }
            if (clan != null) {
                clan.setTakmicar(takmicar);
                clan = em.merge(clan);
            }
            for (UcescePojedinca ucescePojedincaCollectionUcescePojedinca : takmicar.getUcescePojedincaCollection()) {
                Takmicar oldTakmicarOfUcescePojedincaCollectionUcescePojedinca = ucescePojedincaCollectionUcescePojedinca.getTakmicar();
                ucescePojedincaCollectionUcescePojedinca.setTakmicar(takmicar);
                ucescePojedincaCollectionUcescePojedinca = em.merge(ucescePojedincaCollectionUcescePojedinca);
                if (oldTakmicarOfUcescePojedincaCollectionUcescePojedinca != null) {
                    oldTakmicarOfUcescePojedincaCollectionUcescePojedinca.getUcescePojedincaCollection().remove(ucescePojedincaCollectionUcescePojedinca);
                    oldTakmicarOfUcescePojedincaCollectionUcescePojedinca = em.merge(oldTakmicarOfUcescePojedincaCollectionUcescePojedinca);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTakmicar(takmicar.getJmb()) != null) {
                throw new PreexistingEntityException("Takmicar " + takmicar + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Takmicar takmicar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Takmicar persistentTakmicar = em.find(Takmicar.class, takmicar.getJmb());
            Ekipa IDEkipeOld = persistentTakmicar.getIDEkipe();
            Ekipa IDEkipeNew = takmicar.getIDEkipe();
            Clan clanOld = persistentTakmicar.getClan();
            Clan clanNew = takmicar.getClan();
            Collection<UcescePojedinca> ucescePojedincaCollectionOld = persistentTakmicar.getUcescePojedincaCollection();
            Collection<UcescePojedinca> ucescePojedincaCollectionNew = takmicar.getUcescePojedincaCollection();
            List<String> illegalOrphanMessages = null;
            if (clanNew != null && !clanNew.equals(clanOld)) {
                Takmicar oldTakmicarOfClan = clanNew.getTakmicar();
                if (oldTakmicarOfClan != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Clan " + clanNew + " already has an item of type Takmicar whose clan column cannot be null. Please make another selection for the clan field.");
                }
            }
            for (UcescePojedinca ucescePojedincaCollectionOldUcescePojedinca : ucescePojedincaCollectionOld) {
                if (!ucescePojedincaCollectionNew.contains(ucescePojedincaCollectionOldUcescePojedinca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UcescePojedinca " + ucescePojedincaCollectionOldUcescePojedinca + " since its takmicar field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (IDEkipeNew != null) {
                IDEkipeNew = em.getReference(IDEkipeNew.getClass(), IDEkipeNew.getIDEkipe());
                takmicar.setIDEkipe(IDEkipeNew);
            }
            if (clanNew != null) {
                clanNew = em.getReference(clanNew.getClass(), clanNew.getJmb());
                takmicar.setClan(clanNew);
            }
            Collection<UcescePojedinca> attachedUcescePojedincaCollectionNew = new ArrayList<UcescePojedinca>();
            for (UcescePojedinca ucescePojedincaCollectionNewUcescePojedincaToAttach : ucescePojedincaCollectionNew) {
                ucescePojedincaCollectionNewUcescePojedincaToAttach = em.getReference(ucescePojedincaCollectionNewUcescePojedincaToAttach.getClass(), ucescePojedincaCollectionNewUcescePojedincaToAttach.getUcescePojedincaPK());
                attachedUcescePojedincaCollectionNew.add(ucescePojedincaCollectionNewUcescePojedincaToAttach);
            }
            ucescePojedincaCollectionNew = attachedUcescePojedincaCollectionNew;
            takmicar.setUcescePojedincaCollection(ucescePojedincaCollectionNew);
            takmicar = em.merge(takmicar);
            if (IDEkipeOld != null && !IDEkipeOld.equals(IDEkipeNew)) {
                IDEkipeOld.getTakmicarCollection().remove(takmicar);
                IDEkipeOld = em.merge(IDEkipeOld);
            }
            if (IDEkipeNew != null && !IDEkipeNew.equals(IDEkipeOld)) {
                IDEkipeNew.getTakmicarCollection().add(takmicar);
                IDEkipeNew = em.merge(IDEkipeNew);
            }
            if (clanOld != null && !clanOld.equals(clanNew)) {
                clanOld.setTakmicar(null);
                clanOld = em.merge(clanOld);
            }
            if (clanNew != null && !clanNew.equals(clanOld)) {
                clanNew.setTakmicar(takmicar);
                clanNew = em.merge(clanNew);
            }
            for (UcescePojedinca ucescePojedincaCollectionNewUcescePojedinca : ucescePojedincaCollectionNew) {
                if (!ucescePojedincaCollectionOld.contains(ucescePojedincaCollectionNewUcescePojedinca)) {
                    Takmicar oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca = ucescePojedincaCollectionNewUcescePojedinca.getTakmicar();
                    ucescePojedincaCollectionNewUcescePojedinca.setTakmicar(takmicar);
                    ucescePojedincaCollectionNewUcescePojedinca = em.merge(ucescePojedincaCollectionNewUcescePojedinca);
                    if (oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca != null && !oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca.equals(takmicar)) {
                        oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca.getUcescePojedincaCollection().remove(ucescePojedincaCollectionNewUcescePojedinca);
                        oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca = em.merge(oldTakmicarOfUcescePojedincaCollectionNewUcescePojedinca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = takmicar.getJmb();
                if (findTakmicar(id) == null) {
                    throw new NonexistentEntityException("The takmicar with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Takmicar takmicar;
            try {
                takmicar = em.getReference(Takmicar.class, id);
                takmicar.getJmb();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The takmicar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UcescePojedinca> ucescePojedincaCollectionOrphanCheck = takmicar.getUcescePojedincaCollection();
            for (UcescePojedinca ucescePojedincaCollectionOrphanCheckUcescePojedinca : ucescePojedincaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Takmicar (" + takmicar + ") cannot be destroyed since the UcescePojedinca " + ucescePojedincaCollectionOrphanCheckUcescePojedinca + " in its ucescePojedincaCollection field has a non-nullable takmicar field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ekipa IDEkipe = takmicar.getIDEkipe();
            if (IDEkipe != null) {
                IDEkipe.getTakmicarCollection().remove(takmicar);
                IDEkipe = em.merge(IDEkipe);
            }
            Clan clan = takmicar.getClan();
            if (clan != null) {
                clan.setTakmicar(null);
                clan = em.merge(clan);
            }
            em.remove(takmicar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Takmicar> findTakmicarEntities() {
        return findTakmicarEntities(true, -1, -1);
    }

    public List<Takmicar> findTakmicarEntities(int maxResults, int firstResult) {
        return findTakmicarEntities(false, maxResults, firstResult);
    }

    private List<Takmicar> findTakmicarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Takmicar.class));
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

    public Takmicar findTakmicar(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Takmicar.class, id);
        } finally {
            em.close();
        }
    }

    public int getTakmicarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Takmicar> rt = cq.from(Takmicar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
