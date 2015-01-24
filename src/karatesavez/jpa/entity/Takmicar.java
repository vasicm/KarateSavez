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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "takmicar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Takmicar.findAll", query = "SELECT t FROM Takmicar t"),
    @NamedQuery(name = "Takmicar.findByKilaza", query = "SELECT t FROM Takmicar t WHERE t.kilaza = :kilaza"),
    @NamedQuery(name = "Takmicar.findByJmb", query = "SELECT t FROM Takmicar t WHERE t.jmb = :jmb")})
public class Takmicar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Kilaza")
    private double kilaza;
    @Id
    @Basic(optional = false)
    @Column(name = "JMB")
    private Long jmb;
    @JoinColumn(name = "IDEkipe", referencedColumnName = "IDEkipe")
    @ManyToOne
    private Ekipa iDEkipe;
    @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Clan clan;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "takmicar")
    private Collection<UcescePojedinca> ucescePojedincaCollection;

    public Takmicar() {
    }

    public Takmicar(Long jmb) {
        this.jmb = jmb;
    }

    public Takmicar(Long jmb, double kilaza) {
        this.jmb = jmb;
        this.kilaza = kilaza;
    }

    public double getKilaza() {
        return kilaza;
    }

    public void setKilaza(double kilaza) {
        this.kilaza = kilaza;
    }

    public Long getJmb() {
        return jmb;
    }

    public void setJmb(Long jmb) {
        this.jmb = jmb;
    }

    public Ekipa getIDEkipe() {
        return iDEkipe;
    }

    public void setIDEkipe(Ekipa iDEkipe) {
        this.iDEkipe = iDEkipe;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    @XmlTransient
    public Collection<UcescePojedinca> getUcescePojedincaCollection() {
        return ucescePojedincaCollection;
    }

    public void setUcescePojedincaCollection(Collection<UcescePojedinca> ucescePojedincaCollection) {
        this.ucescePojedincaCollection = ucescePojedincaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jmb != null ? jmb.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Takmicar)) {
            return false;
        }
        Takmicar other = (Takmicar) object;
        if ((this.jmb == null && other.jmb != null) || (this.jmb != null && !this.jmb.equals(other.jmb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Takmicar[ jmb=" + jmb + " ]";
    }
    
}
