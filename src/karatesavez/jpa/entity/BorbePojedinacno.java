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
@Table(name = "borbe_pojedinacno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BorbePojedinacno.findAll", query = "SELECT b FROM BorbePojedinacno b"),
    @NamedQuery(name = "BorbePojedinacno.findByKilaza", query = "SELECT b FROM BorbePojedinacno b WHERE b.kilaza = :kilaza"),
    @NamedQuery(name = "BorbePojedinacno.findByIDKategorije", query = "SELECT b FROM BorbePojedinacno b WHERE b.iDKategorije = :iDKategorije")})
public class BorbePojedinacno implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Kilaza")
    private Double kilaza;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "borbePojedinacno")
    private Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollection;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PojedinacnaKategorija pojedinacnaKategorija;

    public BorbePojedinacno() {
    }

    public BorbePojedinacno(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Double getKilaza() {
        return kilaza;
    }

    public void setKilaza(Double kilaza) {
        this.kilaza = kilaza;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    @XmlTransient
    public Collection<PrijavljujeTakmicenjeUBorbama> getPrijavljujeTakmicenjeUBorbamaCollection() {
        return prijavljujeTakmicenjeUBorbamaCollection;
    }

    public void setPrijavljujeTakmicenjeUBorbamaCollection(Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollection) {
        this.prijavljujeTakmicenjeUBorbamaCollection = prijavljujeTakmicenjeUBorbamaCollection;
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
        if (!(object instanceof BorbePojedinacno)) {
            return false;
        }
        BorbePojedinacno other = (BorbePojedinacno) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.BorbePojedinacno[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
