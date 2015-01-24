/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "trening")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Trening.findAll", query = "SELECT t FROM Trening t"),
    @NamedQuery(name = "Trening.findByDatum", query = "SELECT t FROM Trening t WHERE t.treningPK.datum = :datum"),
    @NamedQuery(name = "Trening.findByBrojPrisutnih", query = "SELECT t FROM Trening t WHERE t.brojPrisutnih = :brojPrisutnih"),
    @NamedQuery(name = "Trening.findByAdresa", query = "SELECT t FROM Trening t WHERE t.adresa = :adresa"),
    @NamedQuery(name = "Trening.findByIDKluba", query = "SELECT t FROM Trening t WHERE t.treningPK.iDKluba = :iDKluba")})
public class Trening implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TreningPK treningPK;
    @Column(name = "BrojPrisutnih")
    private Integer brojPrisutnih;
    @Basic(optional = false)
    @Column(name = "Adresa")
    private String adresa;
    @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private KarateKlub karateKlub;

    public Trening() {
    }

    public Trening(TreningPK treningPK) {
        this.treningPK = treningPK;
    }

    public Trening(TreningPK treningPK, String adresa) {
        this.treningPK = treningPK;
        this.adresa = adresa;
    }

    public Trening(Date datum, int iDKluba) {
        this.treningPK = new TreningPK(datum, iDKluba);
    }

    public TreningPK getTreningPK() {
        return treningPK;
    }

    public void setTreningPK(TreningPK treningPK) {
        this.treningPK = treningPK;
    }

    public Integer getBrojPrisutnih() {
        return brojPrisutnih;
    }

    public void setBrojPrisutnih(Integer brojPrisutnih) {
        this.brojPrisutnih = brojPrisutnih;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public KarateKlub getKarateKlub() {
        return karateKlub;
    }

    public void setKarateKlub(KarateKlub karateKlub) {
        this.karateKlub = karateKlub;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (treningPK != null ? treningPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Trening)) {
            return false;
        }
        Trening other = (Trening) object;
        if ((this.treningPK == null && other.treningPK != null) || (this.treningPK != null && !this.treningPK.equals(other.treningPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Trening[ treningPK=" + treningPK + " ]";
    }
    
}
