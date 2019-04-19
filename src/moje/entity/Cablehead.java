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
@Table(name = "CABLEHEAD")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Cablehead.findAll", query = "SELECT c FROM Cablehead c")
  , @NamedQuery(name = "Cablehead.findById", query = "SELECT c FROM Cablehead c WHERE c.id = :id")
  , @NamedQuery(name = "Cablehead.findByBuilding", query = "SELECT c FROM Cablehead c WHERE c.building = :building")
  , @NamedQuery(name = "Cablehead.findByName", query = "SELECT c FROM Cablehead c WHERE c.name = :name")
  , @NamedQuery(name = "Cablehead.findByNote", query = "SELECT c FROM Cablehead c WHERE c.note = :note")
  , @NamedQuery(name = "Cablehead.findByOutputcount", query = "SELECT c FROM Cablehead c WHERE c.outputcount = :outputcount")})
public class Cablehead implements Serializable {

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
  @OneToMany(mappedBy = "cableheadId", fetch = FetchType.EAGER)
  private List<Cabheadoutput> cabheadoutputList;

  public Cablehead() {
  }

  public Cablehead(Integer id) {
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
  public List<Cabheadoutput> getCabheadoutputList() {
    return cabheadoutputList;
  }

  public void setCabheadoutputList(List<Cabheadoutput> cabheadoutputList) {
    this.cabheadoutputList = cabheadoutputList;
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
    if (!(object instanceof Cablehead)) {
      return false;
    }
    Cablehead other = (Cablehead) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Cablehead[ id=" + id + " ]";
  }
  
}
