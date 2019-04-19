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
import moje.entity.Datadevice;
import moje.entity.Dataoutput;

/**
 *
 * @author Honza
 */
public class DataoutputJpaController implements Serializable {

  public DataoutputJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Dataoutput dataoutput) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Datadevice deviceId = dataoutput.getDeviceId();
      if (deviceId != null) {
        deviceId = em.getReference(deviceId.getClass(), deviceId.getId());
        dataoutput.setDeviceId(deviceId);
      }
      em.persist(dataoutput);
      if (deviceId != null) {
        deviceId.getDataoutputList().add(dataoutput);
        deviceId = em.merge(deviceId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Dataoutput dataoutput) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Dataoutput persistentDataoutput = em.find(Dataoutput.class, dataoutput.getId());
      Datadevice deviceIdOld = persistentDataoutput.getDeviceId();
      Datadevice deviceIdNew = dataoutput.getDeviceId();
      if (deviceIdNew != null) {
        deviceIdNew = em.getReference(deviceIdNew.getClass(), deviceIdNew.getId());
        dataoutput.setDeviceId(deviceIdNew);
      }
      dataoutput = em.merge(dataoutput);
      if (deviceIdOld != null && !deviceIdOld.equals(deviceIdNew)) {
        deviceIdOld.getDataoutputList().remove(dataoutput);
        deviceIdOld = em.merge(deviceIdOld);
      }
      if (deviceIdNew != null && !deviceIdNew.equals(deviceIdOld)) {
        deviceIdNew.getDataoutputList().add(dataoutput);
        deviceIdNew = em.merge(deviceIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = dataoutput.getId();
        if (findDataoutput(id) == null) {
          throw new NonexistentEntityException("The dataoutput with id " + id + " no longer exists.");
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
      Dataoutput dataoutput;
      try {
        dataoutput = em.getReference(Dataoutput.class, id);
        dataoutput.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The dataoutput with id " + id + " no longer exists.", enfe);
      }
      Datadevice deviceId = dataoutput.getDeviceId();
      if (deviceId != null) {
        deviceId.getDataoutputList().remove(dataoutput);
        deviceId = em.merge(deviceId);
      }
      em.remove(dataoutput);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Dataoutput> findDataoutputEntities() {
    return findDataoutputEntities(true, -1, -1);
  }

  public List<Dataoutput> findDataoutputEntities(int maxResults, int firstResult) {
    return findDataoutputEntities(false, maxResults, firstResult);
  }

  private List<Dataoutput> findDataoutputEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Dataoutput.class));
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

  public Dataoutput findDataoutput(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Dataoutput.class, id);
    } finally {
      em.close();
    }
  }

  public int getDataoutputCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Dataoutput> rt = cq.from(Dataoutput.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
