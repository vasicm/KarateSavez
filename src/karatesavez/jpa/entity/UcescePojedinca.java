/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "ucesce_pojedinca")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UcescePojedinca.findAll", query = "SELECT u FROM UcescePojedinca u"),
    @NamedQuery(name = "UcescePojedinca.findByIDTakmicenja", query = "SELECT u FROM UcescePojedinca u WHERE u.ucescePojedincaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "UcescePojedinca.findByJmb", query = "SELECT u FROM UcescePojedinca u WHERE u.ucescePojedincaPK.jmb = :jmb"),
    @NamedQuery(name = "UcescePojedinca.findByDatumPrijave", query = "SELECT u FROM UcescePojedinca u WHERE u.datumPrijave = :datumPrijave")})
public class UcescePojedinca implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UcescePojedincaPK ucescePojedincaPK;
    @Column(name = "DatumPrijave")
    @Temporal(TemporalType.DATE)
    private Date datumPrijave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ucescePojedinca")
    private Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ucescePojedinca")
    private Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollection;
    @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Takmicar takmicar;
    @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Takmicenje takmicenje;

    public UcescePojedinca() {
    }

    public UcescePojedinca(UcescePojedincaPK ucescePojedincaPK) {
        this.ucescePojedincaPK = ucescePojedincaPK;
    }

    public UcescePojedinca(int iDTakmicenja, long jmb) {
        this.ucescePojedincaPK = new UcescePojedincaPK(iDTakmicenja, jmb);
    }

    public UcescePojedincaPK getUcescePojedincaPK() {
        return ucescePojedincaPK;
    }

    public void setUcescePojedincaPK(UcescePojedincaPK ucescePojedincaPK) {
        this.ucescePojedincaPK = ucescePojedincaPK;
    }

    public Date getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(Date datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    @XmlTransient
    public Collection<PrijavljujeTakmicenjeUBorbama> getPrijavljujeTakmicenjeUBorbamaCollection() {
        return prijavljujeTakmicenjeUBorbamaCollection;
    }

    public void setPrijavljujeTakmicenjeUBorbamaCollection(Collection<PrijavljujeTakmicenjeUBorbama> prijavljujeTakmicenjeUBorbamaCollection) {
        this.prijavljujeTakmicenjeUBorbamaCollection = prijavljujeTakmicenjeUBorbamaCollection;
    }

    @XmlTransient
    public Collection<PrijavljujeTakmicenjeUKatama> getPrijavljujeTakmicenjeUKatamaCollection() {
        return prijavljujeTakmicenjeUKatamaCollection;
    }

    public void setPrijavljujeTakmicenjeUKatamaCollection(Collection<PrijavljujeTakmicenjeUKatama> prijavljujeTakmicenjeUKatamaCollection) {
        this.prijavljujeTakmicenjeUKatamaCollection = prijavljujeTakmicenjeUKatamaCollection;
    }

    public Takmicar getTakmicar() {
        return takmicar;
    }

    public void setTakmicar(Takmicar takmicar) {
        this.takmicar = takmicar;
    }

    public Takmicenje getTakmicenje() {
        return takmicenje;
    }

    public void setTakmicenje(Takmicenje takmicenje) {
        this.takmicenje = takmicenje;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ucescePojedincaPK != null ? ucescePojedincaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcescePojedinca)) {
            return false;
        }
        UcescePojedinca other = (UcescePojedinca) object;
        if ((this.ucescePojedincaPK == null && other.ucescePojedincaPK != null) || (this.ucescePojedincaPK != null && !this.ucescePojedincaPK.equals(other.ucescePojedincaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.UcescePojedinca[ ucescePojedincaPK=" + ucescePojedincaPK + " ]";
    }
    
}
