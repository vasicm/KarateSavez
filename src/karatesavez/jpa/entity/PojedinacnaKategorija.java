/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "pojedinacna_kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PojedinacnaKategorija.findAll", query = "SELECT p FROM PojedinacnaKategorija p"),
    @NamedQuery(name = "PojedinacnaKategorija.findByIDKategorije", query = "SELECT p FROM PojedinacnaKategorija p WHERE p.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "PojedinacnaKategorija.findByBrojPrijavljenihTakmicara", query = "SELECT p FROM PojedinacnaKategorija p WHERE p.brojPrijavljenihTakmicara = :brojPrijavljenihTakmicara")})
public class PojedinacnaKategorija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @Column(name = "BrojPrijavljenihTakmicara")
    private Integer brojPrijavljenihTakmicara;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pojedinacnaKategorija")
    private BorbePojedinacno borbePojedinacno;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Kategorija kategorija;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pojedinacnaKategorija")
    private KatePojedinacno katePojedinacno;

    public PojedinacnaKategorija() {
    }

    public PojedinacnaKategorija(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Integer getBrojPrijavljenihTakmicara() {
        return brojPrijavljenihTakmicara;
    }

    public void setBrojPrijavljenihTakmicara(Integer brojPrijavljenihTakmicara) {
        this.brojPrijavljenihTakmicara = brojPrijavljenihTakmicara;
    }

    public BorbePojedinacno getBorbePojedinacno() {
        return borbePojedinacno;
    }

    public void setBorbePojedinacno(BorbePojedinacno borbePojedinacno) {
        this.borbePojedinacno = borbePojedinacno;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
    }

    public KatePojedinacno getKatePojedinacno() {
        return katePojedinacno;
    }

    public void setKatePojedinacno(KatePojedinacno katePojedinacno) {
        this.katePojedinacno = katePojedinacno;
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
        if (!(object instanceof PojedinacnaKategorija)) {
            return false;
        }
        PojedinacnaKategorija other = (PojedinacnaKategorija) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PojedinacnaKategorija[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
