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
@Table(name = "CABHEADOUTPUT")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Cabheadoutput.findAll", query = "SELECT c FROM Cabheadoutput c")
  , @NamedQuery(name = "Cabheadoutput.findById", query = "SELECT c FROM Cabheadoutput c WHERE c.id = :id")
  , @NamedQuery(name = "Cabheadoutput.findByCabheadout", query = "SELECT c FROM Cabheadoutput c WHERE c.cabheadout = :cabheadout")
  , @NamedQuery(name = "Cabheadoutput.findByNote", query = "SELECT c FROM Cabheadoutput c WHERE c.note = :note")
  , @NamedQuery(name = "Cabheadoutput.findByPhonenumber", query = "SELECT c FROM Cabheadoutput c WHERE c.phonenumber = :phonenumber")})
public class Cabheadoutput implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID", nullable = false)
  private Integer id;
  @Column(name = "CABHEADOUT")
  private Integer cabheadout;
  @Column(name = "NOTE", length = 100)
  private String note;
  @Column(name = "PHONENUMBER")
  private Integer phonenumber;
  @JoinColumn(name = "CABLEHEAD_ID", referencedColumnName = "ID")
  @ManyToOne(fetch = FetchType.EAGER)
  private Cablehead cableheadId;

  public Cabheadoutput() {
  }

  public Cabheadoutput(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCabheadout() {
    return cabheadout;
  }

  public void setCabheadout(Integer cabheadout) {
    this.cabheadout = cabheadout;
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

  public Cablehead getCableheadId() {
    return cableheadId;
  }

  public void setCableheadId(Cablehead cableheadId) {
    this.cableheadId = cableheadId;
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
    if (!(object instanceof Cabheadoutput)) {
      return false;
    }
    Cabheadoutput other = (Cabheadoutput) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "moje.entity.Cabheadoutput[ id=" + id + " ]";
  }
  
}
