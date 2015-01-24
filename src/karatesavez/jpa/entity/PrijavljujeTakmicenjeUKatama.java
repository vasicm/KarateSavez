/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "prijavljuje_takmicenje_u_katama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findAll", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findByIDTakmicenja", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p WHERE p.prijavljujeTakmicenjeUKatamaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findByJmb", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p WHERE p.prijavljujeTakmicenjeUKatamaPK.jmb = :jmb"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findByIDKategorije", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p WHERE p.prijavljujeTakmicenjeUKatamaPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findByPlasman", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p WHERE p.plasman = :plasman"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUKatama.findByBrojNastupa", query = "SELECT p FROM PrijavljujeTakmicenjeUKatama p WHERE p.brojNastupa = :brojNastupa")})
public class PrijavljujeTakmicenjeUKatama implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrijavljujeTakmicenjeUKatamaPK prijavljujeTakmicenjeUKatamaPK;
    @Column(name = "Plasman")
    private Integer plasman;
    @Column(name = "BrojNastupa")
    private Integer brojNastupa;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private KatePojedinacno katePojedinacno;
    @JoinColumns({
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private UcescePojedinca ucescePojedinca;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prijavljujeTakmicenjeUKatama")
    private IzvodjenjeKate izvodjenjeKate;

    public PrijavljujeTakmicenjeUKatama() {
    }

    public PrijavljujeTakmicenjeUKatama(PrijavljujeTakmicenjeUKatamaPK prijavljujeTakmicenjeUKatamaPK) {
        this.prijavljujeTakmicenjeUKatamaPK = prijavljujeTakmicenjeUKatamaPK;
    }

    public PrijavljujeTakmicenjeUKatama(int iDTakmicenja, long jmb, int iDKategorije) {
        this.prijavljujeTakmicenjeUKatamaPK = new PrijavljujeTakmicenjeUKatamaPK(iDTakmicenja, jmb, iDKategorije);
    }

    public PrijavljujeTakmicenjeUKatamaPK getPrijavljujeTakmicenjeUKatamaPK() {
        return prijavljujeTakmicenjeUKatamaPK;
    }

    public void setPrijavljujeTakmicenjeUKatamaPK(PrijavljujeTakmicenjeUKatamaPK prijavljujeTakmicenjeUKatamaPK) {
        this.prijavljujeTakmicenjeUKatamaPK = prijavljujeTakmicenjeUKatamaPK;
    }

    public Integer getPlasman() {
        return plasman;
    }

    public void setPlasman(Integer plasman) {
        this.plasman = plasman;
    }

    public Integer getBrojNastupa() {
        return brojNastupa;
    }

    public void setBrojNastupa(Integer brojNastupa) {
        this.brojNastupa = brojNastupa;
    }

    public KatePojedinacno getKatePojedinacno() {
        return katePojedinacno;
    }

    public void setKatePojedinacno(KatePojedinacno katePojedinacno) {
        this.katePojedinacno = katePojedinacno;
    }

    public UcescePojedinca getUcescePojedinca() {
        return ucescePojedinca;
    }

    public void setUcescePojedinca(UcescePojedinca ucescePojedinca) {
        this.ucescePojedinca = ucescePojedinca;
    }

    public IzvodjenjeKate getIzvodjenjeKate() {
        return izvodjenjeKate;
    }

    public void setIzvodjenjeKate(IzvodjenjeKate izvodjenjeKate) {
        this.izvodjenjeKate = izvodjenjeKate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prijavljujeTakmicenjeUKatamaPK != null ? prijavljujeTakmicenjeUKatamaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeTakmicenjeUKatama)) {
            return false;
        }
        PrijavljujeTakmicenjeUKatama other = (PrijavljujeTakmicenjeUKatama) object;
        if ((this.prijavljujeTakmicenjeUKatamaPK == null && other.prijavljujeTakmicenjeUKatamaPK != null) || (this.prijavljujeTakmicenjeUKatamaPK != null && !this.prijavljujeTakmicenjeUKatamaPK.equals(other.prijavljujeTakmicenjeUKatamaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PrijavljujeTakmicenjeUKatama[ prijavljujeTakmicenjeUKatamaPK=" + prijavljujeTakmicenjeUKatamaPK + " ]";
    }
    
}
