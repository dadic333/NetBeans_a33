/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.JpaController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Cabheadoutput;
import moje.entity.Cablehead;
import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.queries.DeleteObjectQuery;

/**
 *
 * @author Honza
 */
public class CabheadoutputJpaController implements Serializable {

  public CabheadoutputJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Cabheadoutput cabheadoutput) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Cablehead cableheadId = cabheadoutput.getCableheadId();
      if (cableheadId != null) {
        cableheadId = em.getReference(cableheadId.getClass(), cableheadId.getId());
        cabheadoutput.setCableheadId(cableheadId);
      }
      em.persist(cabheadoutput);
      if (cableheadId != null) {
        cableheadId.getCabheadoutputList().add(cabheadoutput);
        cableheadId = em.merge(cableheadId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Cabheadoutput cabheadoutput) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Cabheadoutput persistentCabheadoutput = em.find(Cabheadoutput.class, cabheadoutput.getId());
      Cablehead cableheadIdOld = persistentCabheadoutput.getCableheadId();
      Cablehead cableheadIdNew = cabheadoutput.getCableheadId();
      if (cableheadIdNew != null) {
        cableheadIdNew = em.getReference(cableheadIdNew.getClass(), cableheadIdNew.getId());
        cabheadoutput.setCableheadId(cableheadIdNew);
      }
      cabheadoutput = em.merge(cabheadoutput);
      if (cableheadIdOld != null && !cableheadIdOld.equals(cableheadIdNew)) {
        cableheadIdOld.getCabheadoutputList().remove(cabheadoutput);
        cableheadIdOld = em.merge(cableheadIdOld);
      }
      if (cableheadIdNew != null && !cableheadIdNew.equals(cableheadIdOld)) {
        cableheadIdNew.getCabheadoutputList().add(cabheadoutput);
        cableheadIdNew = em.merge(cableheadIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = cabheadoutput.getId();
        if (findCabheadoutput(id) == null) {
          throw new NonexistentEntityException("The cabheadoutput with id " + id + " no longer exists.");
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
      Cabheadoutput cabheadoutput;
      try {
        cabheadoutput = em.getReference(Cabheadoutput.class, id);
        cabheadoutput.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The cabheadoutput with id " + id + " no longer exists.", enfe);
      }
      Cablehead cableheadId = cabheadoutput.getCableheadId();
      if (cableheadId != null) {
        cableheadId.getCabheadoutputList().remove(cabheadoutput);
        cableheadId = em.merge(cableheadId);
      }
      em.remove(cabheadoutput);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Cabheadoutput> findCabheadoutputEntities() {
    return findCabheadoutputEntities(true, -1, -1);
  }

  public List<Cabheadoutput> findCabheadoutputEntities(int maxResults, int firstResult) {
    return findCabheadoutputEntities(false, maxResults, firstResult);
  }

  private List<Cabheadoutput> findCabheadoutputEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Cabheadoutput.class));
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

  public Cabheadoutput findCabheadoutput(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Cabheadoutput.class, id);
    } finally {
      em.close();
    }
  }

  public int getCabheadoutputCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Cabheadoutput> rt = cq.from(Cabheadoutput.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }

  public List<Cabheadoutput> findAllCabHeadOutputsWithoutCableHeadID() {
    EntityManager em = getEntityManager();
    List<Cabheadoutput> ret;
    em.getTransaction().begin();
    TypedQuery<Cabheadoutput> q = em.createQuery(
            "SELECT u FROM Cabheadoutput u WHERE u.cableheadId = null", Cabheadoutput.class);
    
    ret = q.getResultList();    
    
    em.getTransaction().commit();
    em.close();
    return ret;
  }
  public List<Cabheadoutput> findAllCabHeadOutputsByParameter(String attribute, String value) {
    EntityManager em = getEntityManager();
    String a= "Cabheadoutput";
    int i= 5;
    em.getTransaction().begin();
//    List<Cabheadoutput> ret = new ArrayList<>();
//    Query q = em.createQuery(
//            "SELECT c FROM CABHEADOUTPUT c WHERE c.ID  = :value");
////    q.setParameter("attribute", attribute);
//    q.setParameter("value", 5);
//    ret = q.getResultList();
    Query query = em.createQuery("SELECT e FROM Cabheadoutput e WHERE e.cabheadout = :cout");
//    query.setParameter("Cabheadoutput", a);
    query.setParameter("cout", i);

    List<Cabheadoutput> ret = query.getResultList();
    em.getTransaction().commit();
    em.close();
    return ret;
  }
//  public List<Films> findFilmsByParameter(String attribute, String value) {
//    EntityManager em = getEntityManager();
//    List<Films> ret;
//    
//    //    Query q = em.createQuery("SELECT u FROM Films u ORDER BY LOWER(u.?1)");
//    //    q.setParameter(1, parameter);
//    TypedQuery<Films> q = em.createQuery(
//            "SELECT u FROM Films u Where u." + attribute + " = ?2", Films.class);
//    //            "SELECT u FROM Films u Where u.countryname = ?2", Films.class);
//    //    q.setParameter(1, attribute);
//    q.setParameter(2, value);
//    ret = q.getResultList();
//    
//    em.close();
//    
//    return ret;
//  }
}