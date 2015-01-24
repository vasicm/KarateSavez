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
import karatesavez.jpa.entity.Sudija;
import karatesavez.jpa.entity.Takmicar;
import karatesavez.jpa.entity.Trener;
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.Polaganje;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import karatesavez.jpa.controller.exceptions.IllegalOrphanException;
import karatesavez.jpa.controller.exceptions.NonexistentEntityException;
import karatesavez.jpa.controller.exceptions.PreexistingEntityException;
import karatesavez.jpa.entity.Clan;

/**
 *
 * @author Marko
 */
public class ClanJpaController implements Serializable {

    public ClanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Clan clan) throws PreexistingEntityException, Exception {
        if (clan.getPolaganjeCollection() == null) {
            clan.setPolaganjeCollection(new ArrayList<Polaganje>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Sudija sudija = clan.getSudija();
            if (sudija != null) {
                sudija = em.getReference(sudija.getClass(), sudija.getJmb());
                clan.setSudija(sudija);
            }
            Takmicar takmicar = clan.getTakmicar();
            if (takmicar != null) {
                takmicar = em.getReference(takmicar.getClass(), takmicar.getJmb());
                clan.setTakmicar(takmicar);
            }
            Trener trener = clan.getTrener();
            if (trener != null) {
                trener = em.getReference(trener.getClass(), trener.getJmb());
                clan.setTrener(trener);
            }
            KarateKlub IDKluba = clan.getIDKluba();
            if (IDKluba != null) {
                IDKluba = em.getReference(IDKluba.getClass(), IDKluba.getIDKluba());
                clan.setIDKluba(IDKluba);
            }
            Collection<Polaganje> attachedPolaganjeCollection = new ArrayList<Polaganje>();
            for (Polaganje polaganjeCollectionPolaganjeToAttach : clan.getPolaganjeCollection()) {
                polaganjeCollectionPolaganjeToAttach = em.getReference(polaganjeCollectionPolaganjeToAttach.getClass(), polaganjeCollectionPolaganjeToAttach.getPolaganjePK());
                attachedPolaganjeCollection.add(polaganjeCollectionPolaganjeToAttach);
            }
            clan.setPolaganjeCollection(attachedPolaganjeCollection);
            em.persist(clan);
            if (sudija != null) {
                Clan oldClanOfSudija = sudija.getClan();
                if (oldClanOfSudija != null) {
                    oldClanOfSudija.setSudija(null);
                    oldClanOfSudija = em.merge(oldClanOfSudija);
                }
                sudija.setClan(clan);
                sudija = em.merge(sudija);
            }
            if (takmicar != null) {
                Clan oldClanOfTakmicar = takmicar.getClan();
                if (oldClanOfTakmicar != null) {
                    oldClanOfTakmicar.setTakmicar(null);
                    oldClanOfTakmicar = em.merge(oldClanOfTakmicar);
                }
                takmicar.setClan(clan);
                takmicar = em.merge(takmicar);
            }
            if (trener != null) {
                Clan oldClanOfTrener = trener.getClan();
                if (oldClanOfTrener != null) {
                    oldClanOfTrener.setTrener(null);
                    oldClanOfTrener = em.merge(oldClanOfTrener);
                }
                trener.setClan(clan);
                trener = em.merge(trener);
            }
            if (IDKluba != null) {
                IDKluba.getClanCollection().add(clan);
                IDKluba = em.merge(IDKluba);
            }
            for (Polaganje polaganjeCollectionPolaganje : clan.getPolaganjeCollection()) {
                Clan oldClanOfPolaganjeCollectionPolaganje = polaganjeCollectionPolaganje.getClan();
                polaganjeCollectionPolaganje.setClan(clan);
                polaganjeCollectionPolaganje = em.merge(polaganjeCollectionPolaganje);
                if (oldClanOfPolaganjeCollectionPolaganje != null) {
                    oldClanOfPolaganjeCollectionPolaganje.getPolaganjeCollection().remove(polaganjeCollectionPolaganje);
                    oldClanOfPolaganjeCollectionPolaganje = em.merge(oldClanOfPolaganjeCollectionPolaganje);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClan(clan.getJmb()) != null) {
                throw new PreexistingEntityException("Clan " + clan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Clan clan) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clan persistentClan = em.find(Clan.class, clan.getJmb());
            Sudija sudijaOld = persistentClan.getSudija();
            Sudija sudijaNew = clan.getSudija();
            Takmicar takmicarOld = persistentClan.getTakmicar();
            Takmicar takmicarNew = clan.getTakmicar();
            Trener trenerOld = persistentClan.getTrener();
            Trener trenerNew = clan.getTrener();
            KarateKlub IDKlubaOld = persistentClan.getIDKluba();
            KarateKlub IDKlubaNew = clan.getIDKluba();
            Collection<Polaganje> polaganjeCollectionOld = persistentClan.getPolaganjeCollection();
            Collection<Polaganje> polaganjeCollectionNew = clan.getPolaganjeCollection();
            List<String> illegalOrphanMessages = null;
            if (sudijaOld != null && !sudijaOld.equals(sudijaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Sudija " + sudijaOld + " since its clan field is not nullable.");
            }
            if (takmicarOld != null && !takmicarOld.equals(takmicarNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Takmicar " + takmicarOld + " since its clan field is not nullable.");
            }
            if (trenerOld != null && !trenerOld.equals(trenerNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Trener " + trenerOld + " since its clan field is not nullable.");
            }
            for (Polaganje polaganjeCollectionOldPolaganje : polaganjeCollectionOld) {
                if (!polaganjeCollectionNew.contains(polaganjeCollectionOldPolaganje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Polaganje " + polaganjeCollectionOldPolaganje + " since its clan field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (sudijaNew != null) {
                sudijaNew = em.getReference(sudijaNew.getClass(), sudijaNew.getJmb());
                clan.setSudija(sudijaNew);
            }
            if (takmicarNew != null) {
                takmicarNew = em.getReference(takmicarNew.getClass(), takmicarNew.getJmb());
                clan.setTakmicar(takmicarNew);
            }
            if (trenerNew != null) {
                trenerNew = em.getReference(trenerNew.getClass(), trenerNew.getJmb());
                clan.setTrener(trenerNew);
            }
            if (IDKlubaNew != null) {
                IDKlubaNew = em.getReference(IDKlubaNew.getClass(), IDKlubaNew.getIDKluba());
                clan.setIDKluba(IDKlubaNew);
            }
            Collection<Polaganje> attachedPolaganjeCollectionNew = new ArrayList<Polaganje>();
            for (Polaganje polaganjeCollectionNewPolaganjeToAttach : polaganjeCollectionNew) {
                polaganjeCollectionNewPolaganjeToAttach = em.getReference(polaganjeCollectionNewPolaganjeToAttach.getClass(), polaganjeCollectionNewPolaganjeToAttach.getPolaganjePK());
                attachedPolaganjeCollectionNew.add(polaganjeCollectionNewPolaganjeToAttach);
            }
            polaganjeCollectionNew = attachedPolaganjeCollectionNew;
            clan.setPolaganjeCollection(polaganjeCollectionNew);
            clan = em.merge(clan);
            if (sudijaNew != null && !sudijaNew.equals(sudijaOld)) {
                Clan oldClanOfSudija = sudijaNew.getClan();
                if (oldClanOfSudija != null) {
                    oldClanOfSudija.setSudija(null);
                    oldClanOfSudija = em.merge(oldClanOfSudija);
                }
                sudijaNew.setClan(clan);
                sudijaNew = em.merge(sudijaNew);
            }
            if (takmicarNew != null && !takmicarNew.equals(takmicarOld)) {
                Clan oldClanOfTakmicar = takmicarNew.getClan();
                if (oldClanOfTakmicar != null) {
                    oldClanOfTakmicar.setTakmicar(null);
                    oldClanOfTakmicar = em.merge(oldClanOfTakmicar);
                }
                takmicarNew.setClan(clan);
                takmicarNew = em.merge(takmicarNew);
            }
            if (trenerNew != null && !trenerNew.equals(trenerOld)) {
                Clan oldClanOfTrener = trenerNew.getClan();
                if (oldClanOfTrener != null) {
                    oldClanOfTrener.setTrener(null);
                    oldClanOfTrener = em.merge(oldClanOfTrener);
                }
                trenerNew.setClan(clan);
                trenerNew = em.merge(trenerNew);
            }
            if (IDKlubaOld != null && !IDKlubaOld.equals(IDKlubaNew)) {
                IDKlubaOld.getClanCollection().remove(clan);
                IDKlubaOld = em.merge(IDKlubaOld);
            }
            if (IDKlubaNew != null && !IDKlubaNew.equals(IDKlubaOld)) {
                IDKlubaNew.getClanCollection().add(clan);
                IDKlubaNew = em.merge(IDKlubaNew);
            }
            for (Polaganje polaganjeCollectionNewPolaganje : polaganjeCollectionNew) {
                if (!polaganjeCollectionOld.contains(polaganjeCollectionNewPolaganje)) {
                    Clan oldClanOfPolaganjeCollectionNewPolaganje = polaganjeCollectionNewPolaganje.getClan();
                    polaganjeCollectionNewPolaganje.setClan(clan);
                    polaganjeCollectionNewPolaganje = em.merge(polaganjeCollectionNewPolaganje);
                    if (oldClanOfPolaganjeCollectionNewPolaganje != null && !oldClanOfPolaganjeCollectionNewPolaganje.equals(clan)) {
                        oldClanOfPolaganjeCollectionNewPolaganje.getPolaganjeCollection().remove(polaganjeCollectionNewPolaganje);
                        oldClanOfPolaganjeCollectionNewPolaganje = em.merge(oldClanOfPolaganjeCollectionNewPolaganje);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clan.getJmb();
                if (findClan(id) == null) {
                    throw new NonexistentEntityException("The clan with id " + id + " no longer exists.");
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
            Clan clan;
            try {
                clan = em.getReference(Clan.class, id);
                clan.getJmb();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clan with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Sudija sudijaOrphanCheck = clan.getSudija();
            if (sudijaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clan (" + clan + ") cannot be destroyed since the Sudija " + sudijaOrphanCheck + " in its sudija field has a non-nullable clan field.");
            }
            Takmicar takmicarOrphanCheck = clan.getTakmicar();
            if (takmicarOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clan (" + clan + ") cannot be destroyed since the Takmicar " + takmicarOrphanCheck + " in its takmicar field has a non-nullable clan field.");
            }
            Trener trenerOrphanCheck = clan.getTrener();
            if (trenerOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clan (" + clan + ") cannot be destroyed since the Trener " + trenerOrphanCheck + " in its trener field has a non-nullable clan field.");
            }
            Collection<Polaganje> polaganjeCollectionOrphanCheck = clan.getPolaganjeCollection();
            for (Polaganje polaganjeCollectionOrphanCheckPolaganje : polaganjeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Clan (" + clan + ") cannot be destroyed since the Polaganje " + polaganjeCollectionOrphanCheckPolaganje + " in its polaganjeCollection field has a non-nullable clan field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            KarateKlub IDKluba = clan.getIDKluba();
            if (IDKluba != null) {
                IDKluba.getClanCollection().remove(clan);
                IDKluba = em.merge(IDKluba);
            }
            em.remove(clan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Clan> findClanEntities() {
        return findClanEntities(true, -1, -1);
    }

    public List<Clan> findClanEntities(int maxResults, int firstResult) {
        return findClanEntities(false, maxResults, firstResult);
    }

    private List<Clan> findClanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Clan.class));
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

    public Clan findClan(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Clan.class, id);
        } finally {
            em.close();
        }
    }

    public int getClanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Clan> rt = cq.from(Clan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
