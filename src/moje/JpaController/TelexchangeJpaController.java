/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.JpaController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import moje.entity.Hwposition;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Telexchange;

/**
 *
 * @author Honza
 */
public class TelexchangeJpaController implements Serializable {

  public TelexchangeJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Telexchange telexchange) {
    if (telexchange.getHwpositionList() == null) {
      telexchange.setHwpositionList(new ArrayList<Hwposition>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Hwposition> attachedHwpositionList = new ArrayList<Hwposition>();
      for (Hwposition hwpositionListHwpositionToAttach : telexchange.getHwpositionList()) {
        hwpositionListHwpositionToAttach = em.getReference(hwpositionListHwpositionToAttach.getClass(), hwpositionListHwpositionToAttach.getId());
        attachedHwpositionList.add(hwpositionListHwpositionToAttach);
      }
      telexchange.setHwpositionList(attachedHwpositionList);
      em.persist(telexchange);
      for (Hwposition hwpositionListHwposition : telexchange.getHwpositionList()) {
        Telexchange oldTelechangeIdOfHwpositionListHwposition = hwpositionListHwposition.getTelechangeId();
        hwpositionListHwposition.setTelechangeId(telexchange);
        hwpositionListHwposition = em.merge(hwpositionListHwposition);
        if (oldTelechangeIdOfHwpositionListHwposition != null) {
          oldTelechangeIdOfHwpositionListHwposition.getHwpositionList().remove(hwpositionListHwposition);
          oldTelechangeIdOfHwpositionListHwposition = em.merge(oldTelechangeIdOfHwpositionListHwposition);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Telexchange telexchange) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Telexchange persistentTelexchange = em.find(Telexchange.class, telexchange.getId());
      List<Hwposition> hwpositionListOld = persistentTelexchange.getHwpositionList();
      List<Hwposition> hwpositionListNew = telexchange.getHwpositionList();
      List<Hwposition> attachedHwpositionListNew = new ArrayList<Hwposition>();
      for (Hwposition hwpositionListNewHwpositionToAttach : hwpositionListNew) {
        hwpositionListNewHwpositionToAttach = em.getReference(hwpositionListNewHwpositionToAttach.getClass(), hwpositionListNewHwpositionToAttach.getId());
        attachedHwpositionListNew.add(hwpositionListNewHwpositionToAttach);
      }
      hwpositionListNew = attachedHwpositionListNew;
      telexchange.setHwpositionList(hwpositionListNew);
      telexchange = em.merge(telexchange);
      for (Hwposition hwpositionListOldHwposition : hwpositionListOld) {
        if (!hwpositionListNew.contains(hwpositionListOldHwposition)) {
          hwpositionListOldHwposition.setTelechangeId(null);
          hwpositionListOldHwposition = em.merge(hwpositionListOldHwposition);
        }
      }
      for (Hwposition hwpositionListNewHwposition : hwpositionListNew) {
        if (!hwpositionListOld.contains(hwpositionListNewHwposition)) {
          Telexchange oldTelechangeIdOfHwpositionListNewHwposition = hwpositionListNewHwposition.getTelechangeId();
          hwpositionListNewHwposition.setTelechangeId(telexchange);
          hwpositionListNewHwposition = em.merge(hwpositionListNewHwposition);
          if (oldTelechangeIdOfHwpositionListNewHwposition != null && !oldTelechangeIdOfHwpositionListNewHwposition.equals(telexchange)) {
            oldTelechangeIdOfHwpositionListNewHwposition.getHwpositionList().remove(hwpositionListNewHwposition);
            oldTelechangeIdOfHwpositionListNewHwposition = em.merge(oldTelechangeIdOfHwpositionListNewHwposition);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = telexchange.getId();
        if (findTelexchange(id) == null) {
          throw new NonexistentEntityException("The telexchange with id " + id + " no longer exists.");
        }
      }
      throw ex;
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void destroy(Integer id) throws NonexistentEntityException {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Telexchange telexchange;
      try {
        telexchange = em.getReference(Telexchange.class, id);
        telexchange.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The telexchange with id " + id + " no longer exists.", enfe);
      }
      List<Hwposition> hwpositionList = telexchange.getHwpositionList();
      for (Hwposition hwpositionListHwposition : hwpositionList) {
        hwpositionListHwposition.setTelechangeId(null);
        hwpositionListHwposition = em.merge(hwpositionListHwposition);
      }
      em.remove(telexchange);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Telexchange> findTelexchangeEntities() {
    return findTelexchangeEntities(true, -1, -1);
  }

  public List<Telexchange> findTelexchangeEntities(int maxResults, int firstResult) {
    return findTelexchangeEntities(false, maxResults, firstResult);
  }

  private List<Telexchange> findTelexchangeEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Telexchange.class));
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

  public Telexchange findTelexchange(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Telexchange.class, id);
    } finally {
      em.close();
    }
  }

  public int getTelexchangeCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Telexchange> rt = cq.from(Telexchange.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
