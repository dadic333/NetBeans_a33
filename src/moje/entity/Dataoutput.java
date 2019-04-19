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
@Table(name = "DATAOUTPUT")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Dataoutput.findAll", query = "SELECT d FROM Dataoutput d")
  , @NamedQuery(name = "Dataoutput.findById", query = "SELECT d FROM Dataoutput d WHERE d.id = :id")
  , @NamedQuery(name = "Dataoutput.findByDatadevout", query = "SELECT d FROM Dataoutput d WHERE d.datadevout = :datadevout")
  , @NamedQuery(name = "Dataoutput.findByMac", query = "SELECT d FROM Dataoutput d WHERE d.mac = :mac")
  , @NamedQuery(name = "Dataoutput.findByNote", query = "SELECT d FROM Dataoutput d WHERE d.note = :note")
  , @NamedQuery(name = "Dataoutput.findByPhonenumber", query = "SELECT d FROM Dataoutput d WHERE d.phonenumber = :phonenumber")})
public class Dataoutput implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "DATADEVOUT")
  private Integer datadevout;
  @Column(name = "MAC", length = 20)
  private String mac;
  @Column(name = "NOTE", length = 100)
  private String note;
  @Column(name = "PHONENUMBER")
  private Integer phonenumber;
  @JoinColumn(name = "DEVICE_ID", referencedColumnName = "ID")
  @ManyToOne(fetch = FetchType.EAGER)
  private Datadevice deviceId;

  public Dataoutput() {
  }

  public Dataoutput(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDatadevout() {
    return datadevout;
  }

  public void setDatadevout(Integer datadevout) {
    this.datadevout = datadevout;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
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

  public Datadevice getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(Datadevice deviceId) {
    this.deviceId = deviceId;
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
    if (!(object instanceof Dataoutput)) {
      return false;
    }
    Dataoutput other = (Dataoutput) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Dataoutput[ id=" + id + " ]";
  }
  
}
