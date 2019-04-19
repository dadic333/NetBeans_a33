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
import moje.entity.Pbx;
import moje.entity.Pbxoutput;

/**
 *
 * @author Honza
 */
public class PbxoutputJpaController implements Serializable {

  public PbxoutputJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Pbxoutput pbxoutput) {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Pbx pbxId = pbxoutput.getPbxId();
      if (pbxId != null) {
        pbxId = em.getReference(pbxId.getClass(), pbxId.getId());
        pbxoutput.setPbxId(pbxId);
      }
      em.persist(pbxoutput);
      if (pbxId != null) {
        pbxId.getPbxoutputList().add(pbxoutput);
        pbxId = em.merge(pbxId);
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Pbxoutput pbxoutput) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Pbxoutput persistentPbxoutput = em.find(Pbxoutput.class, pbxoutput.getId());
      Pbx pbxIdOld = persistentPbxoutput.getPbxId();
      Pbx pbxIdNew = pbxoutput.getPbxId();
      if (pbxIdNew != null) {
        pbxIdNew = em.getReference(pbxIdNew.getClass(), pbxIdNew.getId());
        pbxoutput.setPbxId(pbxIdNew);
      }
      pbxoutput = em.merge(pbxoutput);
      if (pbxIdOld != null && !pbxIdOld.equals(pbxIdNew)) {
        pbxIdOld.getPbxoutputList().remove(pbxoutput);
        pbxIdOld = em.merge(pbxIdOld);
      }
      if (pbxIdNew != null && !pbxIdNew.equals(pbxIdOld)) {
        pbxIdNew.getPbxoutputList().add(pbxoutput);
        pbxIdNew = em.merge(pbxIdNew);
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = pbxoutput.getId();
        if (findPbxoutput(id) == null) {
          throw new NonexistentEntityException("The pbxoutput with id " + id + " no longer exists.");
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
      Pbxoutput pbxoutput;
      try {
        pbxoutput = em.getReference(Pbxoutput.class, id);
        pbxoutput.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The pbxoutput with id " + id + " no longer exists.", enfe);
      }
      Pbx pbxId = pbxoutput.getPbxId();
      if (pbxId != null) {
        pbxId.getPbxoutputList().remove(pbxoutput);
        pbxId = em.merge(pbxId);
      }
      em.remove(pbxoutput);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Pbxoutput> findPbxoutputEntities() {
    return findPbxoutputEntities(true, -1, -1);
  }

  public List<Pbxoutput> findPbxoutputEntities(int maxResults, int firstResult) {
    return findPbxoutputEntities(false, maxResults, firstResult);
  }

  private List<Pbxoutput> findPbxoutputEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Pbxoutput.class));
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

  public Pbxoutput findPbxoutput(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Pbxoutput.class, id);
    } finally {
      em.close();
    }
  }

  public int getPbxoutputCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Pbxoutput> rt = cq.from(Pbxoutput.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
