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
@Table(name = "PBXOUTPUT")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Pbxoutput.findAll", query = "SELECT p FROM Pbxoutput p")
  , @NamedQuery(name = "Pbxoutput.findById", query = "SELECT p FROM Pbxoutput p WHERE p.id = :id")
  , @NamedQuery(name = "Pbxoutput.findByPbxout", query = "SELECT p FROM Pbxoutput p WHERE p.pbxout = :pbxout")
  , @NamedQuery(name = "Pbxoutput.findByNote", query = "SELECT p FROM Pbxoutput p WHERE p.note = :note")
  , @NamedQuery(name = "Pbxoutput.findByPhonenumber", query = "SELECT p FROM Pbxoutput p WHERE p.phonenumber = :phonenumber")})
public class Pbxoutput implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "PBXOUT")
  private Integer pbxout;
  @Column(name = "NOTE", length = 100)
  private String note;
  @Column(name = "PHONENUMBER")
  private Integer phonenumber;
  @JoinColumn(name = "PBX_ID", referencedColumnName = "ID")
  @ManyToOne(fetch = FetchType.EAGER)
  private Pbx pbxId;

  public Pbxoutput() {
  }

  public Pbxoutput(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getPbxout() {
    return pbxout;
  }

  public void setPbxout(Integer pbxout) {
    this.pbxout = pbxout;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public Integer getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(Integer phonenumber) {
    this.phonenumber = phonenumber;
  }

  public Pbx getPbxId() {
    return pbxId;
  }

  public void setPbxId(Pbx pbxId) {
    this.pbxId = pbxId;
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
    if (!(object instanceof Pbxoutput)) {
      return false;
    }
    Pbxoutput other = (Pbxoutput) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Pbxoutput[ id=" + id + " ]";
  }
  
}
