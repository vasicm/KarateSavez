/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "prijavljuje_takmicenje_u_borbama")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findAll", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByIDTakmicenja", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.prijavljujeTakmicenjeUBorbamaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByIDKategorije", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.prijavljujeTakmicenjeUBorbamaPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByJmb", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.prijavljujeTakmicenjeUBorbamaPK.jmb = :jmb"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByPlasman", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.plasman = :plasman"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByBrojPobjeda", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.brojPobjeda = :brojPobjeda"),
    @NamedQuery(name = "PrijavljujeTakmicenjeUBorbama.findByBrojPoraza", query = "SELECT p FROM PrijavljujeTakmicenjeUBorbama p WHERE p.brojPoraza = :brojPoraza")})
public class PrijavljujeTakmicenjeUBorbama implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrijavljujeTakmicenjeUBorbamaPK prijavljujeTakmicenjeUBorbamaPK;
    @Column(name = "Plasman")
    private Integer plasman;
    @Column(name = "BrojPobjeda")
    private Integer brojPobjeda;
    @Column(name = "BrojPoraza")
    private Integer brojPoraza;
    @JoinColumns({
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private UcescePojedinca ucescePojedinca;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BorbePojedinacno borbePojedinacno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prijavljujeTakmicenjeUBorbama")
    private Collection<Borba> borbaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prijavljujeTakmicenjeUBorbama1")
    private Collection<Borba> borbaCollection1;

    public PrijavljujeTakmicenjeUBorbama() {
    }

    public PrijavljujeTakmicenjeUBorbama(PrijavljujeTakmicenjeUBorbamaPK prijavljujeTakmicenjeUBorbamaPK) {
        this.prijavljujeTakmicenjeUBorbamaPK = prijavljujeTakmicenjeUBorbamaPK;
    }

    public PrijavljujeTakmicenjeUBorbama(int iDTakmicenja, int iDKategorije, long jmb) {
        this.prijavljujeTakmicenjeUBorbamaPK = new PrijavljujeTakmicenjeUBorbamaPK(iDTakmicenja, iDKategorije, jmb);
    }

    public PrijavljujeTakmicenjeUBorbamaPK getPrijavljujeTakmicenjeUBorbamaPK() {
        return prijavljujeTakmicenjeUBorbamaPK;
    }

    public void setPrijavljujeTakmicenjeUBorbamaPK(PrijavljujeTakmicenjeUBorbamaPK prijavljujeTakmicenjeUBorbamaPK) {
        this.prijavljujeTakmicenjeUBorbamaPK = prijavljujeTakmicenjeUBorbamaPK;
    }

    public Integer getPlasman() {
        return plasman;
    }

    public void setPlasman(Integer plasman) {
        this.plasman = plasman;
    }

    public Integer getBrojPobjeda() {
        return brojPobjeda;
    }

    public void setBrojPobjeda(Integer brojPobjeda) {
        this.brojPobjeda = brojPobjeda;
    }

    public Integer getBrojPoraza() {
        return brojPoraza;
    }

    public void setBrojPoraza(Integer brojPoraza) {
        this.brojPoraza = brojPoraza;
    }

    public UcescePojedinca getUcescePojedinca() {
        return ucescePojedinca;
    }

    public void setUcescePojedinca(UcescePojedinca ucescePojedinca) {
        this.ucescePojedinca = ucescePojedinca;
    }

    public BorbePojedinacno getBorbePojedinacno() {
        return borbePojedinacno;
    }

    public void setBorbePojedinacno(BorbePojedinacno borbePojedinacno) {
        this.borbePojedinacno = borbePojedinacno;
    }

    @XmlTransient
    public Collection<Borba> getBorbaCollection() {
        return borbaCollection;
    }

    public void setBorbaCollection(Collection<Borba> borbaCollection) {
        this.borbaCollection = borbaCollection;
    }

    @XmlTransient
    public Collection<Borba> getBorbaCollection1() {
        return borbaCollection1;
    }

    public void setBorbaCollection1(Collection<Borba> borbaCollection1) {
        this.borbaCollection1 = borbaCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prijavljujeTakmicenjeUBorbamaPK != null ? prijavljujeTakmicenjeUBorbamaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeTakmicenjeUBorbama)) {
            return false;
        }
        PrijavljujeTakmicenjeUBorbama other = (PrijavljujeTakmicenjeUBorbama) object;
        if ((this.prijavljujeTakmicenjeUBorbamaPK == null && other.prijavljujeTakmicenjeUBorbamaPK != null) || (this.prijavljujeTakmicenjeUBorbamaPK != null && !this.prijavljujeTakmicenjeUBorbamaPK.equals(other.prijavljujeTakmicenjeUBorbamaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PrijavljujeTakmicenjeUBorbama[ prijavljujeTakmicenjeUBorbamaPK=" + prijavljujeTakmicenjeUBorbamaPK + " ]";
    }
    
}
