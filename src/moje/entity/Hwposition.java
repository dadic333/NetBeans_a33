/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moje.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Honza
 */
@Entity
@Table(name = "HWPOSITION")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Hwposition.findAll", query = "SELECT h FROM Hwposition h")
  , @NamedQuery(name = "Hwposition.findById", query = "SELECT h FROM Hwposition h WHERE h.id = :id")
  , @NamedQuery(name = "Hwposition.findByTelexchangeoutput", query = "SELECT h FROM Hwposition h WHERE h.telexchangeoutput = :telexchangeoutput")
  , @NamedQuery(name = "Hwposition.findByName", query = "SELECT h FROM Hwposition h WHERE h.name = :name")
  , @NamedQuery(name = "Hwposition.findByNote", query = "SELECT h FROM Hwposition h WHERE h.note = :note")
  , @NamedQuery(name = "Hwposition.findByPhonenumber", query = "SELECT h FROM Hwposition h WHERE h.phonenumber = :phonenumber")
  , @NamedQuery(name = "Hwposition.findByTechnologytype", query = "SELECT h FROM Hwposition h WHERE h.technologytype = :technologytype")})
public class Hwposition implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "TELEXCHANGEOUTPUT")
  private Integer telexchangeoutput;
  @Column(name = "NAME", length = 10)
  private String name;
  @Column(name = "NOTE", length = 100)
  private String note;
  @Column(name = "PHONENUMBER")
  private Character phonenumber;
  @Column(name = "TECHNOLOGYTYPE")
  private Character technologytype;
  @JoinColumn(name = "TELECHANGE_ID", referencedColumnName = "ID")
  @ManyToOne(fetch = FetchType.EAGER)
  private Telexchange telechangeId;

  public Hwposition() {
  }

  public Hwposition(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getTelexchangeoutput() {
    return telexchangeoutput;
  }

  public void setTelexchangeoutput(Integer telexchangeoutput) {
    this.telexchangeoutput = telexchangeoutput;
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

  public Character getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(Character phonenumber) {
    this.phonenumber = phonenumber;
  }

  public Character getTechnologytype() {
    return technologytype;
  }

  public void setTechnologytype(Character technologytype) {
    this.technologytype = technologytype;
  }

  public Telexchange getTelechangeId() {
    return telechangeId;
  }

  public void setTelechangeId(Telexchange telechangeId) {
    this.telechangeId = telechangeId;
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
    if (!(object instanceof Hwposition)) {
      return false;
    }
    Hwposition other = (Hwposition) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Hwposition[ id=" + id + " ]";
  }
  
}
