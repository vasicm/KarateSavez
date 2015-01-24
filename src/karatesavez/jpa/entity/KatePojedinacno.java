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
@Table(name = "kate_pojedinacno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KatePojedinacno.findAll", query = "SELECT k FROM KatePojedinacno k"),
    @NamedQuery(name = "KatePojedinacno.findByNivo", query = "SELECT k FROM KatePojedinacno k WHERE k.nivo = :nivo"),
    @NamedQuery(name = "KatePojedinacno.findByIDKategorije", query = "SELECT k FROM KatePojedinacno k WHERE k.iDKategorije = :iDKategorije")})
public class KatePojedinacno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "Nivo")
    private String nivo;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "katePojedinacno")
    private Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollection;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PojedinacnaKategorija pojedinacnaKategorija;

    public KatePojedinacno() {
    }

    public KatePojedinacno(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public String getNivo() {
        return nivo;
    }

    public void setNivo(String nivo) {
        this.nivo = nivo;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    @XmlTransient
    public Collection<PrijavljujeTakmicenjeUKatama> getPrijavljujeTakmicenjeUKatamaCollection() {
        return prijavljujeTakmicenjeUKatamaCollection;
    }

    public void setPrijavljujeTakmicenjeUKatamaCollection(Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollection) {
        this.prijavljujeTakmicenjeUKatamaCollection = prijavljujeTakmicenjeUKatamaCollection;
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
        if (!(object instanceof KatePojedinacno)) {
            return false;
        }
        KatePojedinacno other = (KatePojedinacno) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.KatePojedinacno[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
