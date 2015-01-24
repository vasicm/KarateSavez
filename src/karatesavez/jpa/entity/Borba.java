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
@Table(name = "borba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Borba.findAll", query = "SELECT b FROM Borba b"),
    @NamedQuery(name = "Borba.findByIDTakmicenja", query = "SELECT b FROM Borba b WHERE b.borbaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "Borba.findByIDKategorije", query = "SELECT b FROM Borba b WHERE b.borbaPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "Borba.findByJMBPlavi", query = "SELECT b FROM Borba b WHERE b.borbaPK.jMBPlavi = :jMBPlavi"),
    @NamedQuery(name = "Borba.findByJMBCrveni", query = "SELECT b FROM Borba b WHERE b.borbaPK.jMBCrveni = :jMBCrveni"),
    @NamedQuery(name = "Borba.findByPoeniPlavi", query = "SELECT b FROM Borba b WHERE b.poeniPlavi = :poeniPlavi"),
    @NamedQuery(name = "Borba.findByPoeniCrveni", query = "SELECT b FROM Borba b WHERE b.poeniCrveni = :poeniCrveni"),
    @NamedQuery(name = "Borba.findByKaznePlavi", query = "SELECT b FROM Borba b WHERE b.kaznePlavi = :kaznePlavi"),
    @NamedQuery(name = "Borba.findByKazneCrveni", query = "SELECT b FROM Borba b WHERE b.kazneCrveni = :kazneCrveni"),
    @NamedQuery(name = "Borba.findByNivoTakmicenja", query = "SELECT b FROM Borba b WHERE b.nivoTakmicenja = :nivoTakmicenja")})
public class Borba implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BorbaPK borbaPK;
    @Column(name = "PoeniPlavi")
    private Integer poeniPlavi;
    @Column(name = "PoeniCrveni")
    private Integer poeniCrveni;
    @Column(name = "KaznePlavi")
    private Integer kaznePlavi;
    @Column(name = "KazneCrveni")
    private Integer kazneCrveni;
    @Column(name = "NivoTakmicenja")
    private Integer nivoTakmicenja;
    @JoinColumns({
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false),
        @JoinColumn(name = "JMBCrveni", referencedColumnName = "JMB", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama;
    @JoinColumns({
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false),
        @JoinColumn(name = "JMBPlavi", referencedColumnName = "JMB", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1;

    public Borba() {
    }

    public Borba(BorbaPK borbaPK) {
        this.borbaPK = borbaPK;
    }

    public Borba(int iDTakmicenja, int iDKategorije, long jMBPlavi, long jMBCrveni) {
        this.borbaPK = new BorbaPK(iDTakmicenja, iDKategorije, jMBPlavi, jMBCrveni);
    }

    public BorbaPK getBorbaPK() {
        return borbaPK;
    }

    public void setBorbaPK(BorbaPK borbaPK) {
        this.borbaPK = borbaPK;
    }

    public Integer getPoeniPlavi() {
        return poeniPlavi;
    }

    public void setPoeniPlavi(Integer poeniPlavi) {
        this.poeniPlavi = poeniPlavi;
    }

    public Integer getPoeniCrveni() {
        return poeniCrveni;
    }

    public void setPoeniCrveni(Integer poeniCrveni) {
        this.poeniCrveni = poeniCrveni;
    }

    public Integer getKaznePlavi() {
        return kaznePlavi;
    }

    public void setKaznePlavi(Integer kaznePlavi) {
        this.kaznePlavi = kaznePlavi;
    }

    public Integer getKazneCrveni() {
        return kazneCrveni;
    }

    public void setKazneCrveni(Integer kazneCrveni) {
        this.kazneCrveni = kazneCrveni;
    }

    public Integer getNivoTakmicenja() {
        return nivoTakmicenja;
    }

    public void setNivoTakmicenja(Integer nivoTakmicenja) {
        this.nivoTakmicenja = nivoTakmicenja;
    }

    public PrijavljujeTakmicenjeUBorbama getPrijavljujeTakmicenjeUBorbama() {
        return prijavljujeTakmicenjeUBorbama;
    }

    public void setPrijavljujeTakmicenjeUBorbama(PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama) {
        this.prijavljujeTakmicenjeUBorbama = prijavljujeTakmicenjeUBorbama;
    }

    public PrijavljujeTakmicenjeUBorbama getPrijavljujeTakmicenjeUBorbama1() {
        return prijavljujeTakmicenjeUBorbama1;
    }

    public void setPrijavljujeTakmicenjeUBorbama1(PrijavljujeTakmicenjeUBorbama prijavljujeTakmicenjeUBorbama1) {
        this.prijavljujeTakmicenjeUBorbama1 = prijavljujeTakmicenjeUBorbama1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (borbaPK != null ? borbaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Borba)) {
            return false;
        }
        Borba other = (Borba) object;
        if ((this.borbaPK == null && other.borbaPK != null) || (this.borbaPK != null && !this.borbaPK.equals(other.borbaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Borba[ borbaPK=" + borbaPK + " ]";
    }
    
}
