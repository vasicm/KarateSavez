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
@Table(name = "ekipno_izvodjenje_kate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findAll", query = "SELECT e FROM EkipnoIzvodjenjeKate e"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByOcjena1", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ocjena1 = :ocjena1"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByOcjena2", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ocjena2 = :ocjena2"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByOcjena3", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ocjena3 = :ocjena3"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByNazivKate", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.nazivKate = :nazivKate"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByIDEkipe", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ekipnoIzvodjenjeKatePK.iDEkipe = :iDEkipe"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByIDTakmicenja", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ekipnoIzvodjenjeKatePK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "EkipnoIzvodjenjeKate.findByIDKategorije", query = "SELECT e FROM EkipnoIzvodjenjeKate e WHERE e.ekipnoIzvodjenjeKatePK.iDKategorije = :iDKategorije")})
public class EkipnoIzvodjenjeKate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EkipnoIzvodjenjeKatePK ekipnoIzvodjenjeKatePK;
    @Column(name = "Ocjena1")
    private Integer ocjena1;
    @Column(name = "Ocjena2")
    private Integer ocjena2;
    @Column(name = "Ocjena3")
    private Integer ocjena3;
    @Column(name = "NazivKate")
    private String nazivKate;
    @JoinColumns({
        @JoinColumn(name = "IDEkipe", referencedColumnName = "IDEkipe", insertable = false, updatable = false),
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false),
        @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private PrijavljujeKateEkipno prijavljujeKateEkipno;

    public EkipnoIzvodjenjeKate() {
    }

    public EkipnoIzvodjenjeKate(EkipnoIzvodjenjeKatePK ekipnoIzvodjenjeKatePK) {
        this.ekipnoIzvodjenjeKatePK = ekipnoIzvodjenjeKatePK;
    }

    public EkipnoIzvodjenjeKate(int iDEkipe, int iDTakmicenja, int iDKategorije) {
        this.ekipnoIzvodjenjeKatePK = new EkipnoIzvodjenjeKatePK(iDEkipe, iDTakmicenja, iDKategorije);
    }

    public EkipnoIzvodjenjeKatePK getEkipnoIzvodjenjeKatePK() {
        return ekipnoIzvodjenjeKatePK;
    }

    public void setEkipnoIzvodjenjeKatePK(EkipnoIzvodjenjeKatePK ekipnoIzvodjenjeKatePK) {
        this.ekipnoIzvodjenjeKatePK = ekipnoIzvodjenjeKatePK;
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

    public PrijavljujeKateEkipno getPrijavljujeKateEkipno() {
        return prijavljujeKateEkipno;
    }

    public void setPrijavljujeKateEkipno(PrijavljujeKateEkipno prijavljujeKateEkipno) {
        this.prijavljujeKateEkipno = prijavljujeKateEkipno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ekipnoIzvodjenjeKatePK != null ? ekipnoIzvodjenjeKatePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EkipnoIzvodjenjeKate)) {
            return false;
        }
        EkipnoIzvodjenjeKate other = (EkipnoIzvodjenjeKate) object;
        if ((this.ekipnoIzvodjenjeKatePK == null && other.ekipnoIzvodjenjeKatePK != null) || (this.ekipnoIzvodjenjeKatePK != null && !this.ekipnoIzvodjenjeKatePK.equals(other.ekipnoIzvodjenjeKatePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.EkipnoIzvodjenjeKate[ ekipnoIzvodjenjeKatePK=" + ekipnoIzvodjenjeKatePK + " ]";
    }
    
}
