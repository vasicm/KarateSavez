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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "karate_klub")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KarateKlub.findAll", query = "SELECT k FROM KarateKlub k"),
    @NamedQuery(name = "KarateKlub.findByNazivKarateKluba", query = "SELECT k FROM KarateKlub k WHERE k.nazivKarateKluba = :nazivKarateKluba"),
    @NamedQuery(name = "KarateKlub.findByIDKluba", query = "SELECT k FROM KarateKlub k WHERE k.iDKluba = :iDKluba"),
    @NamedQuery(name = "KarateKlub.findByOpis", query = "SELECT k FROM KarateKlub k WHERE k.opis = :opis"),
    @NamedQuery(name = "KarateKlub.findByAdresa", query = "SELECT k FROM KarateKlub k WHERE k.adresa = :adresa"),
    @NamedQuery(name = "KarateKlub.findBySjediste", query = "SELECT k FROM KarateKlub k WHERE k.sjediste = :sjediste")})
public class KarateKlub implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NazivKarateKluba")
    private String nazivKarateKluba;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKluba")
    private Integer iDKluba;
    @Column(name = "Opis")
    private String opis;
    @Column(name = "Adresa")
    private String adresa;
    @Basic(optional = false)
    @Column(name = "Sjediste")
    private String sjediste;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "karateKlub")
    private Collection<Ispit> ispitCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKluba")
    private Collection<Ekipa> ekipaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "karateKlub")
    private Collection<Trening> treningCollection;
    @OneToMany(mappedBy = "iDKluba")
    private Collection<Takmicenje> takmicenjeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKluba")
    private Collection<Clan> clanCollection;
    @JoinColumn(name = "IDSaveza", referencedColumnName = "IDSaveza")
    @ManyToOne(optional = false)
    private KarateSavez iDSaveza;

    public KarateKlub() {
    }

    public KarateKlub(Integer iDKluba) {
        this.iDKluba = iDKluba;
    }

    public KarateKlub(Integer iDKluba, String nazivKarateKluba, String sjediste) {
        this.iDKluba = iDKluba;
        this.nazivKarateKluba = nazivKarateKluba;
        this.sjediste = sjediste;
    }

    public String getNazivKarateKluba() {
        return nazivKarateKluba;
    }

    public void setNazivKarateKluba(String nazivKarateKluba) {
        this.nazivKarateKluba = nazivKarateKluba;
    }

    public Integer getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(Integer iDKluba) {
        this.iDKluba = iDKluba;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getSjediste() {
        return sjediste;
    }

    public void setSjediste(String sjediste) {
        this.sjediste = sjediste;
    }

    @XmlTransient
    public Collection<Ispit> getIspitCollection() {
        return ispitCollection;
    }

    public void setIspitCollection(Collection<Ispit> ispitCollection) {
        this.ispitCollection = ispitCollection;
    }

    @XmlTransient
    public Collection<Ekipa> getEkipaCollection() {
        return ekipaCollection;
    }

    public void setEkipaCollection(Collection<Ekipa> ekipaCollection) {
        this.ekipaCollection = ekipaCollection;
    }

    @XmlTransient
    public Collection<Trening> getTreningCollection() {
        return treningCollection;
    }

    public void setTreningCollection(Collection<Trening> treningCollection) {
        this.treningCollection = treningCollection;
    }

    @XmlTransient
    public Collection<Takmicenje> getTakmicenjeCollection() {
        return takmicenjeCollection;
    }

    public void setTakmicenjeCollection(Collection<Takmicenje> takmicenjeCollection) {
        this.takmicenjeCollection = takmicenjeCollection;
    }

    @XmlTransient
    public Collection<Clan> getClanCollection() {
        return clanCollection;
    }

    public void setClanCollection(Collection<Clan> clanCollection) {
        this.clanCollection = clanCollection;
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
        hash += (iDKluba != null ? iDKluba.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KarateKlub)) {
            return false;
        }
        KarateKlub other = (KarateKlub) object;
        if ((this.iDKluba == null && other.iDKluba != null) || (this.iDKluba != null && !this.iDKluba.equals(other.iDKluba))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "mydesignwkc.jpa.entity.KarateKlub[ iDKluba=" + iDKluba + " ]";
        return nazivKarateKluba;
    }
    
}
