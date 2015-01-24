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
@Table(name = "borbe_ekipno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BorbeEkipno.findAll", query = "SELECT b FROM BorbeEkipno b"),
    @NamedQuery(name = "BorbeEkipno.findByIDKategorije", query = "SELECT b FROM BorbeEkipno b WHERE b.iDKategorije = :iDKategorije")})
public class BorbeEkipno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EkipnaKategorija ekipnaKategorija;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borbeEkipno")
    private Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollection;

    public BorbeEkipno() {
    }

    public BorbeEkipno(Integer iDKategorije) {
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
    public Collection<PrijavljujeBorbeEkipno> getPrijavljujeBorbeEkipnoCollection() {
        return prijavljujeBorbeEkipnoCollection;
    }

    public void setPrijavljujeBorbeEkipnoCollection(Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollection) {
        this.prijavljujeBorbeEkipnoCollection = prijavljujeBorbeEkipnoCollection;
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
        if (!(object instanceof BorbeEkipno)) {
            return false;
        }
        BorbeEkipno other = (BorbeEkipno) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.BorbeEkipno[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
