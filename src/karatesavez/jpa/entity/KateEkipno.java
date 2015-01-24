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
@Table(name = "kate_ekipno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "KateEkipno.findAll", query = "SELECT k FROM KateEkipno k"),
    @NamedQuery(name = "KateEkipno.findByIDKategorije", query = "SELECT k FROM KateEkipno k WHERE k.iDKategorije = :iDKategorije")})
public class KateEkipno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EkipnaKategorija ekipnaKategorija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kateEkipno")
    private Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollection;

    public KateEkipno() {
    }

    public KateEkipno(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public EkipnaKategorija getEkipnaKategorija() {
        return ekipnaKategorija;
    }

    public void setEkipnaKategorija(EkipnaKategorija ekipnaKategorija) {
        this.ekipnaKategorija = ekipnaKategorija;
    }

    @XmlTransient
    public Collection<PrijavljujeKateEkipno> getPrijavljujeKateEkipnoCollection() {
        return prijavljujeKateEkipnoCollection;
    }

    public void setPrijavljujeKateEkipnoCollection(Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollection) {
        this.prijavljujeKateEkipnoCollection = prijavljujeKateEkipnoCollection;
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
        if (!(object instanceof KateEkipno)) {
            return false;
        }
        KateEkipno other = (KateEkipno) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.KateEkipno[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
