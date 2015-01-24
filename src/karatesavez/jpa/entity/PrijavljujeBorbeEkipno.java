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
@Table(name = "prijavljuje_borbe_ekipno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrijavljujeBorbeEkipno.findAll", query = "SELECT p FROM PrijavljujeBorbeEkipno p"),
    @NamedQuery(name = "PrijavljujeBorbeEkipno.findByIDEkipe", query = "SELECT p FROM PrijavljujeBorbeEkipno p WHERE p.prijavljujeBorbeEkipnoPK.iDEkipe = :iDEkipe"),
    @NamedQuery(name = "PrijavljujeBorbeEkipno.findByIDTakmicenja", query = "SELECT p FROM PrijavljujeBorbeEkipno p WHERE p.prijavljujeBorbeEkipnoPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "PrijavljujeBorbeEkipno.findByIDKategorije", query = "SELECT p FROM PrijavljujeBorbeEkipno p WHERE p.prijavljujeBorbeEkipnoPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "PrijavljujeBorbeEkipno.findByPlasman", query = "SELECT p FROM PrijavljujeBorbeEkipno p WHERE p.plasman = :plasman")})
public class PrijavljujeBorbeEkipno implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrijavljujeBorbeEkipnoPK prijavljujeBorbeEkipnoPK;
    @Column(name = "Plasman")
    private Integer plasman;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private BorbeEkipno borbeEkipno;
    @JoinColumns({
        @JoinColumn(name = "IDEkipe", referencedColumnName = "IDEkipe", insertable = false, updatable = false),
        @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private UcesceEkipe ucesceEkipe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prijavljujeBorbeEkipno")
    private Collection<EkipnaBorba> ekipnaBorbaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prijavljujeBorbeEkipno1")
    private Collection<EkipnaBorba> ekipnaBorbaCollection1;

    public PrijavljujeBorbeEkipno() {
    }

    public PrijavljujeBorbeEkipno(PrijavljujeBorbeEkipnoPK prijavljujeBorbeEkipnoPK) {
        this.prijavljujeBorbeEkipnoPK = prijavljujeBorbeEkipnoPK;
    }

    public PrijavljujeBorbeEkipno(int iDEkipe, int iDTakmicenja, int iDKategorije) {
        this.prijavljujeBorbeEkipnoPK = new PrijavljujeBorbeEkipnoPK(iDEkipe, iDTakmicenja, iDKategorije);
    }

    public PrijavljujeBorbeEkipnoPK getPrijavljujeBorbeEkipnoPK() {
        return prijavljujeBorbeEkipnoPK;
    }

    public void setPrijavljujeBorbeEkipnoPK(PrijavljujeBorbeEkipnoPK prijavljujeBorbeEkipnoPK) {
        this.prijavljujeBorbeEkipnoPK = prijavljujeBorbeEkipnoPK;
    }

    public Integer getPlasman() {
        return plasman;
    }

    public void setPlasman(Integer plasman) {
        this.plasman = plasman;
    }

    public BorbeEkipno getBorbeEkipno() {
        return borbeEkipno;
    }

    public void setBorbeEkipno(BorbeEkipno borbeEkipno) {
        this.borbeEkipno = borbeEkipno;
    }

    public UcesceEkipe getUcesceEkipe() {
        return ucesceEkipe;
    }

    public void setUcesceEkipe(UcesceEkipe ucesceEkipe) {
        this.ucesceEkipe = ucesceEkipe;
    }

    @XmlTransient
    public Collection<EkipnaBorba> getEkipnaBorbaCollection() {
        return ekipnaBorbaCollection;
    }

    public void setEkipnaBorbaCollection(Collection<EkipnaBorba> ekipnaBorbaCollection) {
        this.ekipnaBorbaCollection = ekipnaBorbaCollection;
    }

    @XmlTransient
    public Collection<EkipnaBorba> getEkipnaBorbaCollection1() {
        return ekipnaBorbaCollection1;
    }

    public void setEkipnaBorbaCollection1(Collection<EkipnaBorba> ekipnaBorbaCollection1) {
        this.ekipnaBorbaCollection1 = ekipnaBorbaCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prijavljujeBorbeEkipnoPK != null ? prijavljujeBorbeEkipnoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeBorbeEkipno)) {
            return false;
        }
        PrijavljujeBorbeEkipno other = (PrijavljujeBorbeEkipno) object;
        if ((this.prijavljujeBorbeEkipnoPK == null && other.prijavljujeBorbeEkipnoPK != null) || (this.prijavljujeBorbeEkipnoPK != null && !this.prijavljujeBorbeEkipnoPK.equals(other.prijavljujeBorbeEkipnoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PrijavljujeBorbeEkipno[ prijavljujeBorbeEkipnoPK=" + prijavljujeBorbeEkipnoPK + " ]";
    }
    
}
