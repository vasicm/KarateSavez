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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kategorija.findAll", query = "SELECT k FROM Kategorija k"),
    @NamedQuery(name = "Kategorija.findByNazivKategorije", query = "SELECT k FROM Kategorija k WHERE k.nazivKategorije = :nazivKategorije"),
    @NamedQuery(name = "Kategorija.findByIDKategorije", query = "SELECT k FROM Kategorija k WHERE k.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "Kategorija.findByUzrast", query = "SELECT k FROM Kategorija k WHERE k.uzrast = :uzrast"),
    @NamedQuery(name = "Kategorija.findByOpisKategorije", query = "SELECT k FROM Kategorija k WHERE k.opisKategorije = :opisKategorije")})
public class Kategorija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NazivKategorije")
    private String nazivKategorije;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @Basic(optional = false)
    @Column(name = "Uzrast")
    private String uzrast;
    @Column(name = "OpisKategorije")
    private String opisKategorije;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kategorija")
    private Collection<TakmicenjeKategorija> takmicenjeKategorijaCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kategorija")
    private EkipnaKategorija ekipnaKategorija;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "kategorija")
    private PojedinacnaKategorija pojedinacnaKategorija;

    public Kategorija() {
    }

    public Kategorija(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Kategorija(Integer iDKategorije, String nazivKategorije, String uzrast) {
        this.iDKategorije = iDKategorije;
        this.nazivKategorije = nazivKategorije;
        this.uzrast = uzrast;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public String getUzrast() {
        return uzrast;
    }

    public void setUzrast(String uzrast) {
        this.uzrast = uzrast;
    }

    public String getOpisKategorije() {
        return opisKategorije;
    }

    public void setOpisKategorije(String opisKategorije) {
        this.opisKategorije = opisKategorije;
    }

    @XmlTransient
    public Collection<TakmicenjeKategorija> getTakmicenjeKategorijaCollection() {
        return takmicenjeKategorijaCollection;
    }

    public void setTakmicenjeKategorijaCollection(Collection<TakmicenjeKategorija> takmicenjeKategorijaCollection) {
        this.takmicenjeKategorijaCollection = takmicenjeKategorijaCollection;
    }

    public EkipnaKategorija getEkipnaKategorija() {
        return ekipnaKategorija;
    }

    public void setEkipnaKategorija(EkipnaKategorija ekipnaKategorija) {
        this.ekipnaKategorija = ekipnaKategorija;
    }

    public PojedinacnaKategorija getPojedinacnaKategorija() {
        return pojedinacnaKategorija;
    }

    public void setPojedinacnaKategorija(PojedinacnaKategorija pojedinacnaKategorija) {
        this.pojedinacnaKategorija = pojedinacnaKategorija;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKategorije != null ? iDKategorije.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kategorija)) {
            return false;
        }
        Kategorija other = (Kategorija) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "mydesignwkc.jpa.entity.Kategorija[ iDKategorije=" + iDKategorije + " ]";
        return nazivKategorije;
    }
    
}
