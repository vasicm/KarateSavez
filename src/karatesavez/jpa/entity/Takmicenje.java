/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "takmicenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Takmicenje.findAll", query = "SELECT t FROM Takmicenje t"),
    @NamedQuery(name = "Takmicenje.findByIDTakmicenja", query = "SELECT t FROM Takmicenje t WHERE t.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "Takmicenje.findByNazivTakmicenja", query = "SELECT t FROM Takmicenje t WHERE t.nazivTakmicenja = :nazivTakmicenja"),
    @NamedQuery(name = "Takmicenje.findByDatumPocetka", query = "SELECT t FROM Takmicenje t WHERE t.datumPocetka = :datumPocetka"),
    @NamedQuery(name = "Takmicenje.findByAdresa", query = "SELECT t FROM Takmicenje t WHERE t.adresa = :adresa")})
public class Takmicenje implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private Integer iDTakmicenja;
    @Column(name = "NazivTakmicenja")
    private String nazivTakmicenja;
    @Basic(optional = false)
    @Column(name = "DatumPocetka")
    @Temporal(TemporalType.DATE)
    private Date datumPocetka;
    @Basic(optional = false)
    @Column(name = "Adresa")
    private String adresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "takmicenje")
    private Collection<TakmicenjeKategorija> takmicenjeKategorijaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "takmicenje")
    private Collection<UcesceEkipe> ucesceEkipeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "takmicenje")
    private Collection<UcescePojedinca> ucescePojedincaCollection;
    @JoinColumn(name = "IDSaveza", referencedColumnName = "IDSaveza")
    @ManyToOne
    private KarateSavez iDSaveza;
    @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba")
    @ManyToOne
    private KarateKlub iDKluba;

    public Takmicenje() {
    }

    public Takmicenje(Integer iDTakmicenja) {
        this.iDTakmicenja = iDTakmicenja;
    }

    public Takmicenje(Integer iDTakmicenja, Date datumPocetka, String adresa) {
        this.iDTakmicenja = iDTakmicenja;
        this.datumPocetka = datumPocetka;
        this.adresa = adresa;
    }

    public Integer getIDTakmicenja() {
        return iDTakmicenja;
    }

    public void setIDTakmicenja(Integer iDTakmicenja) {
        this.iDTakmicenja = iDTakmicenja;
    }

    public String getNazivTakmicenja() {
        return nazivTakmicenja;
    }

    public void setNazivTakmicenja(String nazivTakmicenja) {
        this.nazivTakmicenja = nazivTakmicenja;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @XmlTransient
    public Collection<TakmicenjeKategorija> getTakmicenjeKategorijaCollection() {
        return takmicenjeKategorijaCollection;
    }

    public void setTakmicenjeKategorijaCollection(Collection<TakmicenjeKategorija> takmicenjeKategorijaCollection) {
        this.takmicenjeKategorijaCollection = takmicenjeKategorijaCollection;
    }

    @XmlTransient
    public Collection<UcesceEkipe> getUcesceEkipeCollection() {
        return ucesceEkipeCollection;
    }

    public void setUcesceEkipeCollection(Collection<UcesceEkipe> ucesceEkipeCollection) {
        this.ucesceEkipeCollection = ucesceEkipeCollection;
    }

    @XmlTransient
    public Collection<UcescePojedinca> getUcescePojedincaCollection() {
        return ucescePojedincaCollection;
    }

    public void setUcescePojedincaCollection(Collection<UcescePojedinca> ucescePojedincaCollection) {
        this.ucescePojedincaCollection = ucescePojedincaCollection;
    }

    public KarateSavez getIDSaveza() {
        return iDSaveza;
    }

    public void setIDSaveza(KarateSavez iDSaveza) {
        this.iDSaveza = iDSaveza;
    }

    public KarateKlub getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(KarateKlub iDKluba) {
        this.iDKluba = iDKluba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTakmicenja != null ? iDTakmicenja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Takmicenje)) {
            return false;
        }
        Takmicenje other = (Takmicenje) object;
        if ((this.iDTakmicenja == null && other.iDTakmicenja != null) || (this.iDTakmicenja != null && !this.iDTakmicenja.equals(other.iDTakmicenja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "mydesignwkc.jpa.entity.Takmicenje[ iDTakmicenja=" + iDTakmicenja + " ]";
        return nazivTakmicenja;
    }
    
}
