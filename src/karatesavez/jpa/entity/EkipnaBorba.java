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
@Table(name = "ekipna_borba")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EkipnaBorba.findAll", query = "SELECT e FROM EkipnaBorba e"),
    @NamedQuery(name = "EkipnaBorba.findByBrojBorbe", query = "SELECT e FROM EkipnaBorba e WHERE e.brojBorbe = :brojBorbe"),
    @NamedQuery(name = "EkipnaBorba.findByPoeniPlavi", query = "SELECT e FROM EkipnaBorba e WHERE e.poeniPlavi = :poeniPlavi"),
    @NamedQuery(name = "EkipnaBorba.findByPoeniCrveni", query = "SELECT e FROM EkipnaBorba e WHERE e.poeniCrveni = :poeniCrveni"),
    @NamedQuery(name = "EkipnaBorba.findByNazivEkipePlavi", query = "SELECT e FROM EkipnaBorba e WHERE e.nazivEkipePlavi = :nazivEkipePlavi"),
    @NamedQuery(name = "EkipnaBorba.findByNazivEkipeCrveni", query = "SELECT e FROM EkipnaBorba e WHERE e.nazivEkipeCrveni = :nazivEkipeCrveni"),
    @NamedQuery(name = "EkipnaBorba.findByIDEkipePlavi", query = "SELECT e FROM EkipnaBorba e WHERE e.ekipnaBorbaPK.iDEkipePlavi = :iDEkipePlavi"),
    @NamedQuery(name = "EkipnaBorba.findByIDTakmicenja", query = "SELECT e FROM EkipnaBorba e WHERE e.ekipnaBorbaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "EkipnaBorba.findByIDKategorije", query = "SELECT e FROM EkipnaBorba e WHERE e.ekipnaBorbaPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "EkipnaBorba.findByIDEkipeCrveni", query = "SELECT e FROM EkipnaBorba e WHERE e.ekipnaBorbaPK.iDEkipeCrveni = :iDEkipeCrveni")})
public class EkipnaBorba implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EkipnaBorbaPK ekipnaBorbaPK;
    @Column(name = "BrojBorbe")
    private Integer brojBorbe;
    @Column(name = "PoeniPlavi")
    private Integer poeniPlavi;
    @Column(name = "PoeniCrveni")
    private Integer poeniCrveni;
    @Column(name = "NazivEkipePlavi")
    private String nazivEkipePlavi;
    @Column(name = "NazivEkipeCrveni")
    private String nazivEkipeCrveni;
    @JoinColumns({
        @JoinColumn(name = "IDEkipeCrveni", referencedColumnName = "IDEkipe", insertable = false, updatable = false),
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PrijavljujeBorbeEkipno prijavljujeBorbeEkipno;
    @JoinColumns({
        @JoinColumn(name = "IDEkipePlavi", referencedColumnName = "IDEkipe", insertable = false, updatable = false),
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1;

    public EkipnaBorba() {
    }

    public EkipnaBorba(EkipnaBorbaPK ekipnaBorbaPK) {
        this.ekipnaBorbaPK = ekipnaBorbaPK;
    }

    public EkipnaBorba(int iDEkipePlavi, int iDTakmicenja, int iDKategorije, int iDEkipeCrveni) {
        this.ekipnaBorbaPK = new EkipnaBorbaPK(iDEkipePlavi, iDTakmicenja, iDKategorije, iDEkipeCrveni);
    }

    public EkipnaBorbaPK getEkipnaBorbaPK() {
        return ekipnaBorbaPK;
    }

    public void setEkipnaBorbaPK(EkipnaBorbaPK ekipnaBorbaPK) {
        this.ekipnaBorbaPK = ekipnaBorbaPK;
    }

    public Integer getBrojBorbe() {
        return brojBorbe;
    }

    public void setBrojBorbe(Integer brojBorbe) {
        this.brojBorbe = brojBorbe;
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

    public String getNazivEkipePlavi() {
        return nazivEkipePlavi;
    }

    public void setNazivEkipePlavi(String nazivEkipePlavi) {
        this.nazivEkipePlavi = nazivEkipePlavi;
    }

    public String getNazivEkipeCrveni() {
        return nazivEkipeCrveni;
    }

    public void setNazivEkipeCrveni(String nazivEkipeCrveni) {
        this.nazivEkipeCrveni = nazivEkipeCrveni;
    }

    public PrijavljujeBorbeEkipno getPrijavljujeBorbeEkipno() {
        return prijavljujeBorbeEkipno;
    }

    public void setPrijavljujeBorbeEkipno(PrijavljujeBorbeEkipno prijavljujeBorbeEkipno) {
        this.prijavljujeBorbeEkipno = prijavljujeBorbeEkipno;
    }

    public PrijavljujeBorbeEkipno getPrijavljujeBorbeEkipno1() {
        return prijavljujeBorbeEkipno1;
    }

    public void setPrijavljujeBorbeEkipno1(PrijavljujeBorbeEkipno prijavljujeBorbeEkipno1) {
        this.prijavljujeBorbeEkipno1 = prijavljujeBorbeEkipno1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ekipnaBorbaPK != null ? ekipnaBorbaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EkipnaBorba)) {
            return false;
        }
        EkipnaBorba other = (EkipnaBorba) object;
        if ((this.ekipnaBorbaPK == null && other.ekipnaBorbaPK != null) || (this.ekipnaBorbaPK != null && !this.ekipnaBorbaPK.equals(other.ekipnaBorbaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.EkipnaBorba[ ekipnaBorbaPK=" + ekipnaBorbaPK + " ]";
    }
    
}
