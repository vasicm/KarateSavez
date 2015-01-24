/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "izvodjenje_kate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IzvodjenjeKate.findAll", query = "SELECT i FROM IzvodjenjeKate i"),
    @NamedQuery(name = "IzvodjenjeKate.findByOcjena1", query = "SELECT i FROM IzvodjenjeKate i WHERE i.ocjena1 = :ocjena1"),
    @NamedQuery(name = "IzvodjenjeKate.findByOcjena2", query = "SELECT i FROM IzvodjenjeKate i WHERE i.ocjena2 = :ocjena2"),
    @NamedQuery(name = "IzvodjenjeKate.findByIDTakmicenja", query = "SELECT i FROM IzvodjenjeKate i WHERE i.izvodjenjeKatePK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "IzvodjenjeKate.findByJmb", query = "SELECT i FROM IzvodjenjeKate i WHERE i.izvodjenjeKatePK.jmb = :jmb"),
    @NamedQuery(name = "IzvodjenjeKate.findByIDKategorije", query = "SELECT i FROM IzvodjenjeKate i WHERE i.izvodjenjeKatePK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "IzvodjenjeKate.findByOcjena3", query = "SELECT i FROM IzvodjenjeKate i WHERE i.ocjena3 = :ocjena3"),
    @NamedQuery(name = "IzvodjenjeKate.findByNazivKate", query = "SELECT i FROM IzvodjenjeKate i WHERE i.nazivKate = :nazivKate")})
public class IzvodjenjeKate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IzvodjenjeKatePK izvodjenjeKatePK;
    @Column(name = "Ocjena1")
    private Integer ocjena1;
    @Column(name = "Ocjena2")
    private Integer ocjena2;
    @Column(name = "Ocjena3")
    private Integer ocjena3;
    @Column(name = "NazivKate")
    private String nazivKate;
    @JoinColumns({
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama;

    public IzvodjenjeKate() {
    }

    public IzvodjenjeKate(IzvodjenjeKatePK izvodjenjeKatePK) {
        this.izvodjenjeKatePK = izvodjenjeKatePK;
    }

    public IzvodjenjeKate(int iDTakmicenja, long jmb, int iDKategorije) {
        this.izvodjenjeKatePK = new IzvodjenjeKatePK(iDTakmicenja, jmb, iDKategorije);
    }

    public IzvodjenjeKatePK getIzvodjenjeKatePK() {
        return izvodjenjeKatePK;
    }

    public void setIzvodjenjeKatePK(IzvodjenjeKatePK izvodjenjeKatePK) {
        this.izvodjenjeKatePK = izvodjenjeKatePK;
    }

    public Integer getOcjena1() {
        return ocjena1;
    }

    public void setOcjena1(Integer ocjena1) {
        this.ocjena1 = ocjena1;
    }

    public Integer getOcjena2() {
        return ocjena2;
    }

    public void setOcjena2(Integer ocjena2) {
        this.ocjena2 = ocjena2;
    }

    public Integer getOcjena3() {
        return ocjena3;
    }

    public void setOcjena3(Integer ocjena3) {
        this.ocjena3 = ocjena3;
    }

    public String getNazivKate() {
        return nazivKate;
    }

    public void setNazivKate(String nazivKate) {
        this.nazivKate = nazivKate;
    }

    public PrijavljujeTakmicenjeUKatama getPrijavljujeTakmicenjeUKatama() {
        return prijavljujeTakmicenjeUKatama;
    }

    public void setPrijavljujeTakmicenjeUKatama(PrijavljujeTakmicenjeUKatama prijavljujeTakmicenjeUKatama) {
        this.prijavljujeTakmicenjeUKatama = prijavljujeTakmicenjeUKatama;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (izvodjenjeKatePK != null ? izvodjenjeKatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IzvodjenjeKate)) {
            return false;
        }
        IzvodjenjeKate other = (IzvodjenjeKate) object;
        if ((this.izvodjenjeKatePK == null && other.izvodjenjeKatePK != null) || (this.izvodjenjeKatePK != null && !this.izvodjenjeKatePK.equals(other.izvodjenjeKatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.IzvodjenjeKate[ izvodjenjeKatePK=" + izvodjenjeKatePK + " ]";
    }
    
}
