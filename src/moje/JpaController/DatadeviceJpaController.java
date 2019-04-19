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
import moje.entity.Dataoutput;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Datadevice;

/**
 *
 * @author Honza
 */
public class DatadeviceJpaController implements Serializable {

  public DatadeviceJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Datadevice datadevice) {
    if (datadevice.getDataoutputList() == null) {
      datadevice.setDataoutputList(new ArrayList<Dataoutput>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Dataoutput> attachedDataoutputList = new ArrayList<Dataoutput>();
      for (Dataoutput dataoutputListDataoutputToAttach : datadevice.getDataoutputList()) {
        dataoutputListDataoutputToAttach = em.getReference(dataoutputListDataoutputToAttach.getClass(), dataoutputListDataoutputToAttach.getId());
        attachedDataoutputList.add(dataoutputListDataoutputToAttach);
      }
      datadevice.setDataoutputList(attachedDataoutputList);
      em.persist(datadevice);
      for (Dataoutput dataoutputListDataoutput : datadevice.getDataoutputList()) {
        Datadevice oldDeviceIdOfDataoutputListDataoutput = dataoutputListDataoutput.getDeviceId();
        dataoutputListDataoutput.setDeviceId(datadevice);
        dataoutputListDataoutput = em.merge(dataoutputListDataoutput);
        if (oldDeviceIdOfDataoutputListDataoutput != null) {
          oldDeviceIdOfDataoutputListDataoutput.getDataoutputList().remove(dataoutputListDataoutput);
          oldDeviceIdOfDataoutputListDataoutput = em.merge(oldDeviceIdOfDataoutputListDataoutput);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Datadevice datadevice) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Datadevice persistentDatadevice = em.find(Datadevice.class, datadevice.getId());
      List<Dataoutput> dataoutputListOld = persistentDatadevice.getDataoutputList();
      List<Dataoutput> dataoutputListNew = datadevice.getDataoutputList();
      List<Dataoutput> attachedDataoutputListNew = new ArrayList<Dataoutput>();
      for (Dataoutput dataoutputListNewDataoutputToAttach : dataoutputListNew) {
        dataoutputListNewDataoutputToAttach = em.getReference(dataoutputListNewDataoutputToAttach.getClass(), dataoutputListNewDataoutputToAttach.getId());
        attachedDataoutputListNew.add(dataoutputListNewDataoutputToAttach);
      }
      dataoutputListNew = attachedDataoutputListNew;
      datadevice.setDataoutputList(dataoutputListNew);
      datadevice = em.merge(datadevice);
      for (Dataoutput dataoutputListOldDataoutput : dataoutputListOld) {
        if (!dataoutputListNew.contains(dataoutputListOldDataoutput)) {
          dataoutputListOldDataoutput.setDeviceId(null);
          dataoutputListOldDataoutput = em.merge(dataoutputListOldDataoutput);
        }
      }
      for (Dataoutput dataoutputListNewDataoutput : dataoutputListNew) {
        if (!dataoutputListOld.contains(dataoutputListNewDataoutput)) {
          Datadevice oldDeviceIdOfDataoutputListNewDataoutput = dataoutputListNewDataoutput.getDeviceId();
          dataoutputListNewDataoutput.setDeviceId(datadevice);
          dataoutputListNewDataoutput = em.merge(dataoutputListNewDataoutput);
          if (oldDeviceIdOfDataoutputListNewDataoutput != null && !oldDeviceIdOfDataoutputListNewDataoutput.equals(datadevice)) {
            oldDeviceIdOfDataoutputListNewDataoutput.getDataoutputList().remove(dataoutputListNewDataoutput);
            oldDeviceIdOfDataoutputListNewDataoutput = em.merge(oldDeviceIdOfDataoutputListNewDataoutput);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = datadevice.getId();
        if (findDatadevice(id) == null) {
          throw new NonexistentEntityException("The datadevice with id " + id + " no longer exists.");
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
      Datadevice datadevice;
      try {
        datadevice = em.getReference(Datadevice.class, id);
        datadevice.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The datadevice with id " + id + " no longer exists.", enfe);
      }
      List<Dataoutput> dataoutputList = datadevice.getDataoutputList();
      for (Dataoutput dataoutputListDataoutput : dataoutputList) {
        dataoutputListDataoutput.setDeviceId(null);
        dataoutputListDataoutput = em.merge(dataoutputListDataoutput);
      }
      em.remove(datadevice);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Datadevice> findDatadeviceEntities() {
    return findDatadeviceEntities(true, -1, -1);
  }

  public List<Datadevice> findDatadeviceEntities(int maxResults, int firstResult) {
    return findDatadeviceEntities(false, maxResults, firstResult);
  }

  private List<Datadevice> findDatadeviceEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Datadevice.class));
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

  public Datadevice findDatadevice(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Datadevice.class, id);
    } finally {
      em.close();
    }
  }

  public int getDatadeviceCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Datadevice> rt = cq.from(Datadevice.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
