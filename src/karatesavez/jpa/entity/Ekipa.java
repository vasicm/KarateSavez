/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "ekipa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ekipa.findAll", query = "SELECT e FROM Ekipa e"),
    @NamedQuery(name = "Ekipa.findByIDEkipe", query = "SELECT e FROM Ekipa e WHERE e.iDEkipe = :iDEkipe"),
    @NamedQuery(name = "Ekipa.findByNazivEkipe", query = "SELECT e FROM Ekipa e WHERE e.nazivEkipe = :nazivEkipe")})
public class Ekipa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDEkipe")
    private Integer iDEkipe;
    @Basic(optional = false)
    @Column(name = "NazivEkipe")
    private String nazivEkipe;
    @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba")
    @ManyToOne(optional = false)
    private KarateKlub iDKluba;
    @OneToMany(mappedBy = "iDEkipe")
    private Collection<Takmicar> takmicarCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ekipa")
    private Collection<UcesceEkipe> ucesceEkipeCollection;

    public Ekipa() {
    }

    public Ekipa(Integer iDEkipe) {
        this.iDEkipe = iDEkipe;
    }

    public Ekipa(Integer iDEkipe, String nazivEkipe) {
        this.iDEkipe = iDEkipe;
        this.nazivEkipe = nazivEkipe;
    }

    public Integer getIDEkipe() {
        return iDEkipe;
    }

    public void setIDEkipe(Integer iDEkipe) {
        this.iDEkipe = iDEkipe;
    }

    public String getNazivEkipe() {
        return nazivEkipe;
    }

    public void setNazivEkipe(String nazivEkipe) {
        this.nazivEkipe = nazivEkipe;
    }

    public KarateKlub getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(KarateKlub iDKluba) {
        this.iDKluba = iDKluba;
    }

    @XmlTransient
    public Collection<Takmicar> getTakmicarCollection() {
        return takmicarCollection;
    }

    public void setTakmicarCollection(Collection<Takmicar> takmicarCollection) {
        this.takmicarCollection = takmicarCollection;
    }

    @XmlTransient
    public Collection<UcesceEkipe> getUcesceEkipeCollection() {
        return ucesceEkipeCollection;
    }

    public void setUcesceEkipeCollection(Collection<UcesceEkipe> ucesceEkipeCollection) {
        this.ucesceEkipeCollection = ucesceEkipeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEkipe != null ? iDEkipe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ekipa)) {
            return false;
        }
        Ekipa other = (Ekipa) object;
        if ((this.iDEkipe == null && other.iDEkipe != null) || (this.iDEkipe != null && !this.iDEkipe.equals(other.iDEkipe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Ekipa[ iDEkipe=" + iDEkipe + " ]";
    }
    
}
