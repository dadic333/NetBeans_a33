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
@Table(name = "PBX")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Pbx.findAll", query = "SELECT p FROM Pbx p")
  , @NamedQuery(name = "Pbx.findById", query = "SELECT p FROM Pbx p WHERE p.id = :id")
  , @NamedQuery(name = "Pbx.findByBuilding", query = "SELECT p FROM Pbx p WHERE p.building = :building")
  , @NamedQuery(name = "Pbx.findByName", query = "SELECT p FROM Pbx p WHERE p.name = :name")
  , @NamedQuery(name = "Pbx.findByNote", query = "SELECT p FROM Pbx p WHERE p.note = :note")
  , @NamedQuery(name = "Pbx.findByOutputcount", query = "SELECT p FROM Pbx p WHERE p.outputcount = :outputcount")})
public class Pbx implements Serializable {

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
  @OneToMany(mappedBy = "pbxId", fetch = FetchType.EAGER)
  private List<Pbxoutput> pbxoutputList;

  public Pbx() {
  }

  public Pbx(Integer id) {
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
  public List<Pbxoutput> getPbxoutputList() {
    return pbxoutputList;
  }

  public void setPbxoutputList(List<Pbxoutput> pbxoutputList) {
    this.pbxoutputList = pbxoutputList;
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
    if (!(object instanceof Pbx)) {
      return false;
    }
    Pbx other = (Pbx) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Pbx[ id=" + id + " ]";
  }
  
}
