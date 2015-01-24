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
@Table(name = "prijavljuje_kate_ekipno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrijavljujeKateEkipno.findAll", query = "SELECT p FROM PrijavljujeKateEkipno p"),
    @NamedQuery(name = "PrijavljujeKateEkipno.findByIDEkipe", query = "SELECT p FROM PrijavljujeKateEkipno p WHERE p.prijavljujeKateEkipnoPK.iDEkipe = :iDEkipe"),
    @NamedQuery(name = "PrijavljujeKateEkipno.findByIDTakmicenja", query = "SELECT p FROM PrijavljujeKateEkipno p WHERE p.prijavljujeKateEkipnoPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "PrijavljujeKateEkipno.findByIDKategorije", query = "SELECT p FROM PrijavljujeKateEkipno p WHERE p.prijavljujeKateEkipnoPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "PrijavljujeKateEkipno.findByPlasman", query = "SELECT p FROM PrijavljujeKateEkipno p WHERE p.plasman = :plasman")})
public class PrijavljujeKateEkipno implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrijavljujeKateEkipnoPK prijavljujeKateEkipnoPK;
    @Column(name = "Plasman")
    private Integer plasman;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private KateEkipno kateEkipno;
    @JoinColumns({
        @JoinColumn(name = "IDEkipe", referencedColumnName = "IDEkipe", insertable = false, updatable = false),
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private UcesceEkipe ucesceEkipe;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "prijavljujeKateEkipno")
    private EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate;

    public PrijavljujeKateEkipno() {
    }

    public PrijavljujeKateEkipno(PrijavljujeKateEkipnoPK prijavljujeKateEkipnoPK) {
        this.prijavljujeKateEkipnoPK = prijavljujeKateEkipnoPK;
    }

    public PrijavljujeKateEkipno(int iDEkipe, int iDTakmicenja, int iDKategorije) {
        this.prijavljujeKateEkipnoPK = new PrijavljujeKateEkipnoPK(iDEkipe, iDTakmicenja, iDKategorije);
    }

    public PrijavljujeKateEkipnoPK getPrijavljujeKateEkipnoPK() {
        return prijavljujeKateEkipnoPK;
    }

    public void setPrijavljujeKateEkipnoPK(PrijavljujeKateEkipnoPK prijavljujeKateEkipnoPK) {
        this.prijavljujeKateEkipnoPK = prijavljujeKateEkipnoPK;
    }

    public Integer getPlasman() {
        return plasman;
    }

    public void setPlasman(Integer plasman) {
        this.plasman = plasman;
    }

    public KateEkipno getKateEkipno() {
        return kateEkipno;
    }

    public void setKateEkipno(KateEkipno kateEkipno) {
        this.kateEkipno = kateEkipno;
    }

    public UcesceEkipe getUcesceEkipe() {
        return ucesceEkipe;
    }

    public void setUcesceEkipe(UcesceEkipe ucesceEkipe) {
        this.ucesceEkipe = ucesceEkipe;
    }

    public EkipnoIzvodjenjeKate getEkipnoIzvodjenjeKate() {
        return ekipnoIzvodjenjeKate;
    }

    public void setEkipnoIzvodjenjeKate(EkipnoIzvodjenjeKate ekipnoIzvodjenjeKate) {
        this.ekipnoIzvodjenjeKate = ekipnoIzvodjenjeKate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prijavljujeKateEkipnoPK != null ? prijavljujeKateEkipnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeKateEkipno)) {
            return false;
        }
        PrijavljujeKateEkipno other = (PrijavljujeKateEkipno) object;
        if ((this.prijavljujeKateEkipnoPK == null && other.prijavljujeKateEkipnoPK != null) || (this.prijavljujeKateEkipnoPK != null && !this.prijavljujeKateEkipnoPK.equals(other.prijavljujeKateEkipnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PrijavljujeKateEkipno[ prijavljujeKateEkipnoPK=" + prijavljujeKateEkipnoPK + " ]";
    }
    
}
