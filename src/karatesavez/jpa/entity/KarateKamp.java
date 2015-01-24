/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "karate_kamp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KarateKamp.findAll", query = "SELECT k FROM KarateKamp k"),
    @NamedQuery(name = "KarateKamp.findByPocetakKampa", query = "SELECT k FROM KarateKamp k WHERE k.pocetakKampa = :pocetakKampa"),
    @NamedQuery(name = "KarateKamp.findByKrajKampa", query = "SELECT k FROM KarateKamp k WHERE k.krajKampa = :krajKampa"),
    @NamedQuery(name = "KarateKamp.findByIDKampa", query = "SELECT k FROM KarateKamp k WHERE k.iDKampa = :iDKampa"),
    @NamedQuery(name = "KarateKamp.findByMjesto", query = "SELECT k FROM KarateKamp k WHERE k.mjesto = :mjesto"),
    @NamedQuery(name = "KarateKamp.findByCijena", query = "SELECT k FROM KarateKamp k WHERE k.cijena = :cijena")})
public class KarateKamp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "PocetakKampa")
    @Temporal(TemporalType.DATE)
    private Date pocetakKampa;
    @Basic(optional = false)
    @Column(name = "KrajKampa")
    @Temporal(TemporalType.DATE)
    private Date krajKampa;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKampa")
    private Integer iDKampa;
    @Basic(optional = false)
    @Column(name = "Mjesto")
    private String mjesto;
    @Basic(optional = false)
    @Column(name = "Cijena")
    private double cijena;
    @JoinColumn(name = "IDSaveza", referencedColumnName = "IDSaveza")
    @ManyToOne(optional = false)
    private KarateSavez iDSaveza;

    public KarateKamp() {
    }

    public KarateKamp(Integer iDKampa) {
        this.iDKampa = iDKampa;
    }

    public KarateKamp(Integer iDKampa, Date pocetakKampa, Date krajKampa, String mjesto, double cijena) {
        this.iDKampa = iDKampa;
        this.pocetakKampa = pocetakKampa;
        this.krajKampa = krajKampa;
        this.mjesto = mjesto;
        this.cijena = cijena;
    }

    public Date getPocetakKampa() {
        return pocetakKampa;
    }

    public void setPocetakKampa(Date pocetakKampa) {
        this.pocetakKampa = pocetakKampa;
    }

    public Date getKrajKampa() {
        return krajKampa;
    }

    public void setKrajKampa(Date krajKampa) {
        this.krajKampa = krajKampa;
    }

    public Integer getIDKampa() {
        return iDKampa;
    }

    public void setIDKampa(Integer iDKampa) {
        this.iDKampa = iDKampa;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public double getCijena() {
        return cijena;
    }

    public void setCijena(double cijena) {
        this.cijena = cijena;
    }

    public KarateSavez getIDSaveza() {
        return iDSaveza;
    }

    public void setIDSaveza(KarateSavez iDSaveza) {
        this.iDSaveza = iDSaveza;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKampa != null ? iDKampa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KarateKamp)) {
            return false;
        }
        KarateKamp other = (KarateKamp) object;
        if ((this.iDKampa == null && other.iDKampa != null) || (this.iDKampa != null && !this.iDKampa.equals(other.iDKampa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.KarateKamp[ iDKampa=" + iDKampa + " ]";
    }
    
}
