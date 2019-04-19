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
import moje.entity.Cabheadoutput;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Cablehead;

/**
 *
 * @author Honza
 */
public class CableheadJpaController implements Serializable {

  public CableheadJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Cablehead cablehead) {
    if (cablehead.getCabheadoutputList() == null) {
      cablehead.setCabheadoutputList(new ArrayList<Cabheadoutput>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Cabheadoutput> attachedCabheadoutputList = new ArrayList<Cabheadoutput>();
      for (Cabheadoutput cabheadoutputListCabheadoutputToAttach : cablehead.getCabheadoutputList()) {
        cabheadoutputListCabheadoutputToAttach = em.getReference(cabheadoutputListCabheadoutputToAttach.getClass(), cabheadoutputListCabheadoutputToAttach.getId());
        attachedCabheadoutputList.add(cabheadoutputListCabheadoutputToAttach);
      }
      cablehead.setCabheadoutputList(attachedCabheadoutputList);
      em.persist(cablehead);
      for (Cabheadoutput cabheadoutputListCabheadoutput : cablehead.getCabheadoutputList()) {
        Cablehead oldCableheadIdOfCabheadoutputListCabheadoutput = cabheadoutputListCabheadoutput.getCableheadId();
        cabheadoutputListCabheadoutput.setCableheadId(cablehead);
        cabheadoutputListCabheadoutput = em.merge(cabheadoutputListCabheadoutput);
        if (oldCableheadIdOfCabheadoutputListCabheadoutput != null) {
          oldCableheadIdOfCabheadoutputListCabheadoutput.getCabheadoutputList().remove(cabheadoutputListCabheadoutput);
          oldCableheadIdOfCabheadoutputListCabheadoutput = em.merge(oldCableheadIdOfCabheadoutputListCabheadoutput);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Cablehead cablehead) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Cablehead persistentCablehead = em.find(Cablehead.class, cablehead.getId());
      List<Cabheadoutput> cabheadoutputListOld = persistentCablehead.getCabheadoutputList();
      List<Cabheadoutput> cabheadoutputListNew = cablehead.getCabheadoutputList();
      List<Cabheadoutput> attachedCabheadoutputListNew = new ArrayList<Cabheadoutput>();
      for (Cabheadoutput cabheadoutputListNewCabheadoutputToAttach : cabheadoutputListNew) {
        cabheadoutputListNewCabheadoutputToAttach = em.getReference(cabheadoutputListNewCabheadoutputToAttach.getClass(), cabheadoutputListNewCabheadoutputToAttach.getId());
        attachedCabheadoutputListNew.add(cabheadoutputListNewCabheadoutputToAttach);
      }
      cabheadoutputListNew = attachedCabheadoutputListNew;
      cablehead.setCabheadoutputList(cabheadoutputListNew);
      cablehead = em.merge(cablehead);
      for (Cabheadoutput cabheadoutputListOldCabheadoutput : cabheadoutputListOld) {
        if (!cabheadoutputListNew.contains(cabheadoutputListOldCabheadoutput)) {
          cabheadoutputListOldCabheadoutput.setCableheadId(null);
          cabheadoutputListOldCabheadoutput = em.merge(cabheadoutputListOldCabheadoutput);
        }
      }
      for (Cabheadoutput cabheadoutputListNewCabheadoutput : cabheadoutputListNew) {
        if (!cabheadoutputListOld.contains(cabheadoutputListNewCabheadoutput)) {
          Cablehead oldCableheadIdOfCabheadoutputListNewCabheadoutput = cabheadoutputListNewCabheadoutput.getCableheadId();
          cabheadoutputListNewCabheadoutput.setCableheadId(cablehead);
          cabheadoutputListNewCabheadoutput = em.merge(cabheadoutputListNewCabheadoutput);
          if (oldCableheadIdOfCabheadoutputListNewCabheadoutput != null && !oldCableheadIdOfCabheadoutputListNewCabheadoutput.equals(cablehead)) {
            oldCableheadIdOfCabheadoutputListNewCabheadoutput.getCabheadoutputList().remove(cabheadoutputListNewCabheadoutput);
            oldCableheadIdOfCabheadoutputListNewCabheadoutput = em.merge(oldCableheadIdOfCabheadoutputListNewCabheadoutput);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = cablehead.getId();
        if (findCablehead(id) == null) {
          throw new NonexistentEntityException("The cablehead with id " + id + " no longer exists.");
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
      Cablehead cablehead;
      try {
        cablehead = em.getReference(Cablehead.class, id);
        cablehead.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The cablehead with id " + id + " no longer exists.", enfe);
      }
      List<Cabheadoutput> cabheadoutputList = cablehead.getCabheadoutputList();
      for (Cabheadoutput cabheadoutputListCabheadoutput : cabheadoutputList) {
        cabheadoutputListCabheadoutput.setCableheadId(null);
        cabheadoutputListCabheadoutput = em.merge(cabheadoutputListCabheadoutput);
      }
      em.remove(cablehead);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Cablehead> findCableheadEntities() {
    return findCableheadEntities(true, -1, -1);
  }

  public List<Cablehead> findCableheadEntities(int maxResults, int firstResult) {
    return findCableheadEntities(false, maxResults, firstResult);
  }

  private List<Cablehead> findCableheadEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Cablehead.class));
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

  public Cablehead findCablehead(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Cablehead.class, id);
    } finally {
      em.close();
    }
  }

  public int getCableheadCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Cablehead> rt = cq.from(Cablehead.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
