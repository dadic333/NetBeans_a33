/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a33;

import java.util.ArrayList;
import java.util.List;
import moje.appLayer.CabHeadOutputBO;
import moje.appLayer.CableHeadBO;
import moje.entity.Cabheadoutput;
import moje.entity.Cablehead;

/**
 *
 * @author Honza
 */
public class A33 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    
//    getAllCableHeads();
 
    createNewCaleHeadAndCHOutputs("T25", "Českobratrská 1 25", "5.NP, dveře 519", 50);
 
//    readCableHeadAllParam(1);
//    deleteCableHeadAndOutpustByID(7);
//    getAllCableHeads();
//    findAllCabHeadOutputsWithoutCableHeadID();
//    deleteOutputsWithoutCableHeadID();
    

//    getAllCabHeadsOutputs();
//    findAllCabHeadOutputsByParameter("Cabheadoutput", "5");
//    readCableHeadAllParam(5);

  }
  
  
  
  private static void createNewCaleHeadAndCHOutputs(String name, String building, String note, int outputCout) {
    Cablehead newCH = CableHeadBO.createNewCaleHeadAndCHOutputs(name, building, note, outputCout);
    System.out.println("NEW CableHead:");
    readCableHeadAllParam(newCH.getId());
  }
  
  private static void readCableHeadAllParam(int id) {
  Cablehead newCableHead = CableHeadBO.getCableheadByID(id);
    System.out.println(".....Read all Cablehead Parameters......");
  System.out.println("Výpis kabelové hlavy s ID= "+id);   // cable reading
  System.out.println("id= "+newCableHead.getId()+"; name= "+newCableHead.getName()
          +"; building= "+newCableHead.getBuilding()+"; note= "
          + newCableHead.getNote()+"; outputCount= "+newCableHead.getOutputcount()
          +"; getCabheadoutputList().size()"+newCableHead.getCabheadoutputList().size());
  System.out.println("Má výstupy:");
  // cableHeadList reading
  readCabHeadOutputList(newCableHead.getCabheadoutputList());
  }
    
  private static void readCabHeadOutputList (List<Cabheadoutput>cableHeadOutputList){
  if (cableHeadOutputList.isEmpty()){
    System.out.println("NO CableHeadOutputs!!!");
  }else {
    for (Cabheadoutput ch : cableHeadOutputList) {
      System.out.println("CableHeadOutput id= "+ch.getId()+"; output= "+ch.getCabheadout()
              +"; note= "+ch.getNote()+"; phoneNumber= "+ch.getPhonenumber()
              +"; owner cableHead ID= "+ch.getCableheadId());
      }
    }
  }
    
  private static void getAllCableHeads() {
    System.out.println("All Cable Heads:");
    List<Cablehead> ret = CableHeadBO.getAllCableHeads();
    for (Cablehead cabHead : ret) {
      System.out.println("ID= "+cabHead.getId()+"; name= "+cabHead.getName()
              +"; building= "+cabHead.getBuilding()+"; note= "+cabHead.getNote()
              +"; outputs cout= "+cabHead.getOutputcount()
              +"; output List size= "+cabHead.getCabheadoutputList().size());
    }
    System.out.println("END__________________________________________________________________________________END");
  }

  private static void deleteCableHeadAndOutpustByCableHeadID(int id) {
     CableHeadBO.deleteCableHeadAndOutpustByCableHeadID(id);
  }

  private static void findAllCabHeadOutputsWithoutCableHeadID() {
    List<Cabheadoutput> retL = CabHeadOutputBO.findAllCabHeadOutputsWithoutCableHeadID();
    System.out.println("........CableHeads without CableHead ID........");
    for (Cabheadoutput cabheadoutput : retL) {
      System.out.println("ID= "+cabheadoutput.getId()+"; out= "+cabheadoutput.getCabheadout()
          +"; note= "+cabheadoutput.getNote()+"; phone number= "+cabheadoutput.getPhonenumber()
          +"; CableHead ID= "+cabheadoutput.getCableheadId());
    }
  }

  private static void deleteOutputsWithoutCableHeadID() {
    CabHeadOutputBO.deleteOutputsWithoutCableHeadID();
  }

  private static void deleteCableHeadAndOutpustByID(int id) {
    CableHeadBO.deleteCableHeadAndOutpustByCableHeadID(id);
  }

  private static void findAllCabHeadOutputsByParameter(String attribute, String value) {
    List<Cabheadoutput> retL = CabHeadOutputBO.findAllCabHeadOutputsByParameter(attribute,  value);
    System.out.println("........CableHeads by CableHead ID........");
    for (Cabheadoutput cabheadoutput : retL) {
      System.out.println("ID= "+cabheadoutput.getId()+"; out= "+cabheadoutput.getCabheadout()
          +"; note= "+cabheadoutput.getNote()+"; phone number= "+cabheadoutput.getPhonenumber()
          +"; CableHead ID= "+cabheadoutput.getCableheadId());
    }
  }

  private static void getAllCabHeadsOutputs() {
    System.out.println("All CableHeads Outputs:");
    List<Cabheadoutput> ret = CabHeadOutputBO.getAllCabHeadsOutputs();
    for (Cabheadoutput outs : ret) {
      System.out.println("ID= "+outs.getId()+"; name= "+outs.getCabheadout()
              +"; note= "+outs.getNote()+"; Phone NUmber= "+outs.getPhonenumber()
              +"; outputs cout= "+outs.getCableheadId());
    }
    System.out.println("END__________________________________________________________________________________END");
  }


}
