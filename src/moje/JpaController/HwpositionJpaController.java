/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.JpaController;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Hwposition;
import moje.entity.Telexchange;

/**
 *
 * @author Honza
 */
public class HwpositionJpaController implements Serializable {

  public HwpositionJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Hwposition hwposition) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Telexchange telechangeId = hwposition.getTelechangeId();
      if (telechangeId != null) {
        telechangeId = em.getReference(telechangeId.getClass(), telechangeId.getId());
        hwposition.setTelechangeId(telechangeId);
      }
      em.persist(hwposition);
      if (telechangeId != null) {
        telechangeId.getHwpositionList().add(hwposition);
        telechangeId = em.merge(telechangeId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Hwposition hwposition) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Hwposition persistentHwposition = em.find(Hwposition.class, hwposition.getId());
      Telexchange telechangeIdOld = persistentHwposition.getTelechangeId();
      Telexchange telechangeIdNew = hwposition.getTelechangeId();
      if (telechangeIdNew != null) {
        telechangeIdNew = em.getReference(telechangeIdNew.getClass(), telechangeIdNew.getId());
        hwposition.setTelechangeId(telechangeIdNew);
      }
      hwposition = em.merge(hwposition);
      if (telechangeIdOld != null && !telechangeIdOld.equals(telechangeIdNew)) {
        telechangeIdOld.getHwpositionList().remove(hwposition);
        telechangeIdOld = em.merge(telechangeIdOld);
      }
      if (telechangeIdNew != null && !telechangeIdNew.equals(telechangeIdOld)) {
        telechangeIdNew.getHwpositionList().add(hwposition);
        telechangeIdNew = em.merge(telechangeIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = hwposition.getId();
        if (findHwposition(id) == null) {
          throw new NonexistentEntityException("The hwposition with id " + id + " no longer exists.");
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
      Hwposition hwposition;
      try {
        hwposition = em.getReference(Hwposition.class, id);
        hwposition.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The hwposition with id " + id + " no longer exists.", enfe);
      }
      Telexchange telechangeId = hwposition.getTelechangeId();
      if (telechangeId != null) {
        telechangeId.getHwpositionList().remove(hwposition);
        telechangeId = em.merge(telechangeId);
      }
      em.remove(hwposition);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Hwposition> findHwpositionEntities() {
    return findHwpositionEntities(true, -1, -1);
  }

  public List<Hwposition> findHwpositionEntities(int maxResults, int firstResult) {
    return findHwpositionEntities(false, maxResults, firstResult);
  }

  private List<Hwposition> findHwpositionEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Hwposition.class));
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

  public Hwposition findHwposition(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Hwposition.class, id);
    } finally {
      em.close();
    }
  }

  public int getHwpositionCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Hwposition> rt = cq.from(Hwposition.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
