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
import moje.entity.Pbxoutput;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Pbx;

/**
 *
 * @author Honza
 */
public class PbxJpaController implements Serializable {

  public PbxJpaController(EntityManagerFactory emf) {
    this.emf = emf;
  }
  private EntityManagerFactory emf = null;

  public EntityManager getEntityManager() {
    return emf.createEntityManager();
  }

  public void create(Pbx pbx) {
    if (pbx.getPbxoutputList() == null) {
      pbx.setPbxoutputList(new ArrayList<Pbxoutput>());
    }
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      List<Pbxoutput> attachedPbxoutputList = new ArrayList<Pbxoutput>();
      for (Pbxoutput pbxoutputListPbxoutputToAttach : pbx.getPbxoutputList()) {
        pbxoutputListPbxoutputToAttach = em.getReference(pbxoutputListPbxoutputToAttach.getClass(), pbxoutputListPbxoutputToAttach.getId());
        attachedPbxoutputList.add(pbxoutputListPbxoutputToAttach);
      }
      pbx.setPbxoutputList(attachedPbxoutputList);
      em.persist(pbx);
      for (Pbxoutput pbxoutputListPbxoutput : pbx.getPbxoutputList()) {
        Pbx oldPbxIdOfPbxoutputListPbxoutput = pbxoutputListPbxoutput.getPbxId();
        pbxoutputListPbxoutput.setPbxId(pbx);
        pbxoutputListPbxoutput = em.merge(pbxoutputListPbxoutput);
        if (oldPbxIdOfPbxoutputListPbxoutput != null) {
          oldPbxIdOfPbxoutputListPbxoutput.getPbxoutputList().remove(pbxoutputListPbxoutput);
          oldPbxIdOfPbxoutputListPbxoutput = em.merge(oldPbxIdOfPbxoutputListPbxoutput);
        }
      }
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public void edit(Pbx pbx) throws NonexistentEntityException, Exception {
    EntityManager em = null;
    try {
      em = getEntityManager();
      em.getTransaction().begin();
      Pbx persistentPbx = em.find(Pbx.class, pbx.getId());
      List<Pbxoutput> pbxoutputListOld = persistentPbx.getPbxoutputList();
      List<Pbxoutput> pbxoutputListNew = pbx.getPbxoutputList();
      List<Pbxoutput> attachedPbxoutputListNew = new ArrayList<Pbxoutput>();
      for (Pbxoutput pbxoutputListNewPbxoutputToAttach : pbxoutputListNew) {
        pbxoutputListNewPbxoutputToAttach = em.getReference(pbxoutputListNewPbxoutputToAttach.getClass(), pbxoutputListNewPbxoutputToAttach.getId());
        attachedPbxoutputListNew.add(pbxoutputListNewPbxoutputToAttach);
      }
      pbxoutputListNew = attachedPbxoutputListNew;
      pbx.setPbxoutputList(pbxoutputListNew);
      pbx = em.merge(pbx);
      for (Pbxoutput pbxoutputListOldPbxoutput : pbxoutputListOld) {
        if (!pbxoutputListNew.contains(pbxoutputListOldPbxoutput)) {
          pbxoutputListOldPbxoutput.setPbxId(null);
          pbxoutputListOldPbxoutput = em.merge(pbxoutputListOldPbxoutput);
        }
      }
      for (Pbxoutput pbxoutputListNewPbxoutput : pbxoutputListNew) {
        if (!pbxoutputListOld.contains(pbxoutputListNewPbxoutput)) {
          Pbx oldPbxIdOfPbxoutputListNewPbxoutput = pbxoutputListNewPbxoutput.getPbxId();
          pbxoutputListNewPbxoutput.setPbxId(pbx);
          pbxoutputListNewPbxoutput = em.merge(pbxoutputListNewPbxoutput);
          if (oldPbxIdOfPbxoutputListNewPbxoutput != null && !oldPbxIdOfPbxoutputListNewPbxoutput.equals(pbx)) {
            oldPbxIdOfPbxoutputListNewPbxoutput.getPbxoutputList().remove(pbxoutputListNewPbxoutput);
            oldPbxIdOfPbxoutputListNewPbxoutput = em.merge(oldPbxIdOfPbxoutputListNewPbxoutput);
          }
        }
      }
      em.getTransaction().commit();
    } catch (Exception ex) {
      String msg = ex.getLocalizedMessage();
      if (msg == null || msg.length() == 0) {
        Integer id = pbx.getId();
        if (findPbx(id) == null) {
          throw new NonexistentEntityException("The pbx with id " + id + " no longer exists.");
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
      Pbx pbx;
      try {
        pbx = em.getReference(Pbx.class, id);
        pbx.getId();
      } catch (EntityNotFoundException enfe) {
        throw new NonexistentEntityException("The pbx with id " + id + " no longer exists.", enfe);
      }
      List<Pbxoutput> pbxoutputList = pbx.getPbxoutputList();
      for (Pbxoutput pbxoutputListPbxoutput : pbxoutputList) {
        pbxoutputListPbxoutput.setPbxId(null);
        pbxoutputListPbxoutput = em.merge(pbxoutputListPbxoutput);
      }
      em.remove(pbx);
      em.getTransaction().commit();
    } finally {
      if (em != null) {
        em.close();
      }
    }
  }

  public List<Pbx> findPbxEntities() {
    return findPbxEntities(true, -1, -1);
  }

  public List<Pbx> findPbxEntities(int maxResults, int firstResult) {
    return findPbxEntities(false, maxResults, firstResult);
  }

  private List<Pbx> findPbxEntities(boolean all, int maxResults, int firstResult) {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      cq.select(cq.from(Pbx.class));
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

  public Pbx findPbx(Integer id) {
    EntityManager em = getEntityManager();
    try {
      return em.find(Pbx.class, id);
    } finally {
      em.close();
    }
  }

  public int getPbxCount() {
    EntityManager em = getEntityManager();
    try {
      CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
      Root<Pbx> rt = cq.from(Pbx.class);
      cq.select(em.getCriteriaBuilder().count(rt));
      Query q = em.createQuery(cq);
      return ((Long) q.getSingleResult()).intValue();
    } finally {
      em.close();
    }
  }
  
}
