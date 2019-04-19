/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.appLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import moje.JpaController.CabheadoutputJpaController;
import moje.JpaController.exceptions.NonexistentEntityException;
import moje.entity.Cabheadoutput;

/**
 *
 * @author Honza
 */
public class CabHeadOutputBO {
  
      private static EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("a33PU");

  public static void deleteOutputsWithoutCableHeadID() {
    CabheadoutputJpaController cont = new CabheadoutputJpaController(emf);
    List<Cabheadoutput> outL = cont.findAllCabHeadOutputsWithoutCableHeadID();
    for (Cabheadoutput cabheadoutput : outL) {
      try {
        cont.destroy(cabheadoutput.getId());
      } catch (NonexistentEntityException ex) {
        Logger.getLogger(CabHeadOutputBO.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }

  public static List<Cabheadoutput> findAllCabHeadOutputsWithoutCableHeadID() {
    CabheadoutputJpaController cont = new CabheadoutputJpaController(emf);
    List<Cabheadoutput> ret = cont.findAllCabHeadOutputsWithoutCableHeadID();
    return ret;
  }
  
  public static List<Cabheadoutput> findAllCabHeadOutputsByParameter(String attribute , String value){
    CabheadoutputJpaController cont = new CabheadoutputJpaController(emf);
    List<Cabheadoutput>ret = cont.findAllCabHeadOutputsByParameter(attribute, value);
    return ret;
  }

  public static List<Cabheadoutput> getAllCabHeadsOutputs() {
    CabheadoutputJpaController cont = new CabheadoutputJpaController(emf);
    List<Cabheadoutput> ret = cont.findCabheadoutputEntities();
    return ret;
  }
  
}
