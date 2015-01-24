/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "karate_savez")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KarateSavez.findAll", query = "SELECT k FROM KarateSavez k"),
    @NamedQuery(name = "KarateSavez.findByNazivSaveza", query = "SELECT k FROM KarateSavez k WHERE k.nazivSaveza = :nazivSaveza"),
    @NamedQuery(name = "KarateSavez.findByOpis", query = "SELECT k FROM KarateSavez k WHERE k.opis = :opis"),
    @NamedQuery(name = "KarateSavez.findByIDSaveza", query = "SELECT k FROM KarateSavez k WHERE k.iDSaveza = :iDSaveza"),
    @NamedQuery(name = "KarateSavez.findBySjediste", query = "SELECT k FROM KarateSavez k WHERE k.sjediste = :sjediste")})
public class KarateSavez implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NazivSaveza")
    private String nazivSaveza;
    @Column(name = "Opis")
    private String opis;
    @Id
    @Basic(optional = false)
    @Column(name = "IDSaveza")
    private Integer iDSaveza;
    @Basic(optional = false)
    @Column(name = "Sjediste")
    private String sjediste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDSaveza")
    private Collection<KarateKamp> karateKampCollection;
    @OneToMany(mappedBy = "iDSaveza")
    private Collection<Takmicenje> takmicenjeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDSaveza")
    private Collection<KarateKlub> karateKlubCollection;

    public KarateSavez() {
    }

    public KarateSavez(Integer iDSaveza) {
        this.iDSaveza = iDSaveza;
    }

    public KarateSavez(Integer iDSaveza, String nazivSaveza, String sjediste) {
        this.iDSaveza = iDSaveza;
        this.nazivSaveza = nazivSaveza;
        this.sjediste = sjediste;
    }

    public String getNazivSaveza() {
        return nazivSaveza;
    }

    public void setNazivSaveza(String nazivSaveza) {
        this.nazivSaveza = nazivSaveza;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Integer getIDSaveza() {
        return iDSaveza;
    }

    public void setIDSaveza(Integer iDSaveza) {
        this.iDSaveza = iDSaveza;
    }

    public String getSjediste() {
        return sjediste;
    }

    public void setSjediste(String sjediste) {
        this.sjediste = sjediste;
    }

    @XmlTransient
    public Collection<KarateKamp> getKarateKampCollection() {
        return karateKampCollection;
    }

    public void setKarateKampCollection(Collection<KarateKamp> karateKampCollection) {
        this.karateKampCollection = karateKampCollection;
    }

    @XmlTransient
    public Collection<Takmicenje> getTakmicenjeCollection() {
        return takmicenjeCollection;
    }

    public void setTakmicenjeCollection(Collection<Takmicenje> takmicenjeCollection) {
        this.takmicenjeCollection = takmicenjeCollection;
    }

    @XmlTransient
    public Collection<KarateKlub> getKarateKlubCollection() {
        return karateKlubCollection;
    }

    public void setKarateKlubCollection(Collection<KarateKlub> karateKlubCollection) {
        this.karateKlubCollection = karateKlubCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDSaveza != null ? iDSaveza.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KarateSavez)) {
            return false;
        }
        KarateSavez other = (KarateSavez) object;
        if ((this.iDSaveza == null && other.iDSaveza != null) || (this.iDSaveza != null && !this.iDSaveza.equals(other.iDSaveza))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.KarateSavez[ iDSaveza=" + iDSaveza + " ]";
    }
    
}
