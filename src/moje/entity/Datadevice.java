/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Honza
 */
@Entity
@Table(name = "DATADEVICE")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Datadevice.findAll", query = "SELECT d FROM Datadevice d")
  , @NamedQuery(name = "Datadevice.findById", query = "SELECT d FROM Datadevice d WHERE d.id = :id")
  , @NamedQuery(name = "Datadevice.findByBuilding", query = "SELECT d FROM Datadevice d WHERE d.building = :building")
  , @NamedQuery(name = "Datadevice.findByName", query = "SELECT d FROM Datadevice d WHERE d.name = :name")
  , @NamedQuery(name = "Datadevice.findByNote", query = "SELECT d FROM Datadevice d WHERE d.note = :note")
  , @NamedQuery(name = "Datadevice.findByOutputcount", query = "SELECT d FROM Datadevice d WHERE d.outputcount = :outputcount")})
public class Datadevice implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "BUILDING", length = 50)
  private String building;
  @Column(name = "NAME", length = 60)
  private String name;
  @Column(name = "NOTE", length = 150)
  private String note;
  @Column(name = "OUTPUTCOUNT")
  private Integer outputcount;
  @OneToMany(mappedBy = "deviceId", fetch = FetchType.EAGER)
  private List<Dataoutput> dataoutputList;

  public Datadevice() {
  }

  public Datadevice(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getOutputcount() {
    return outputcount;
  }

  public void setOutputcount(Integer outputcount) {
    this.outputcount = outputcount;
  }

  @XmlTransient
  public List<Dataoutput> getDataoutputList() {
    return dataoutputList;
  }

  public void setDataoutputList(List<Dataoutput> dataoutputList) {
    this.dataoutputList = dataoutputList;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Datadevice)) {
      return false;
    }
    Datadevice other = (Datadevice) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Datadevice[ id=" + id + " ]";
  }
  
}
