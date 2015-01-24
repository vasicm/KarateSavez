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
@Table(name = "ekipna_kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EkipnaKategorija.findAll", query = "SELECT e FROM EkipnaKategorija e"),
    @NamedQuery(name = "EkipnaKategorija.findByIDKategorije", query = "SELECT e FROM EkipnaKategorija e WHERE e.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "EkipnaKategorija.findByBrojPrijavljenihEkipa", query = "SELECT e FROM EkipnaKategorija e WHERE e.brojPrijavljenihEkipa = :brojPrijavljenihEkipa")})
public class EkipnaKategorija implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private Integer iDKategorije;
    @Column(name = "BrojPrijavljenihEkipa")
    private Integer brojPrijavljenihEkipa;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ekipnaKategorija")
    private BorbeEkipno borbeEkipno;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "ekipnaKategorija")
    private KateEkipno kateEkipno;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Kategorija kategorija;

    public EkipnaKategorija() {
    }

    public EkipnaKategorija(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Integer getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(Integer iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    public Integer getBrojPrijavljenihEkipa() {
        return brojPrijavljenihEkipa;
    }

    public void setBrojPrijavljenihEkipa(Integer brojPrijavljenihEkipa) {
        this.brojPrijavljenihEkipa = brojPrijavljenihEkipa;
    }

    public BorbeEkipno getBorbeEkipno() {
        return borbeEkipno;
    }

    public void setBorbeEkipno(BorbeEkipno borbeEkipno) {
        this.borbeEkipno = borbeEkipno;
    }

    public KateEkipno getKateEkipno() {
        return kateEkipno;
    }

    public void setKateEkipno(KateEkipno kateEkipno) {
        this.kateEkipno = kateEkipno;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
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
        if (!(object instanceof EkipnaKategorija)) {
            return false;
        }
        EkipnaKategorija other = (EkipnaKategorija) object;
        if ((this.iDKategorije == null && other.iDKategorije != null) || (this.iDKategorije != null && !this.iDKategorije.equals(other.iDKategorije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.EkipnaKategorija[ iDKategorije=" + iDKategorije + " ]";
    }
    
}
