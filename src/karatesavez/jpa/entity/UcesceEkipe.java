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
@Table(name = "ucesce_ekipe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UcesceEkipe.findAll", query = "SELECT u FROM UcesceEkipe u"),
    @NamedQuery(name = "UcesceEkipe.findByIDEkipe", query = "SELECT u FROM UcesceEkipe u WHERE u.ucesceEkipePK.iDEkipe = :iDEkipe"),
    @NamedQuery(name = "UcesceEkipe.findByIDTakmicenja", query = "SELECT u FROM UcesceEkipe u WHERE u.ucesceEkipePK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "UcesceEkipe.findByDatumPrijave", query = "SELECT u FROM UcesceEkipe u WHERE u.datumPrijave = :datumPrijave")})
public class UcesceEkipe implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UcesceEkipePK ucesceEkipePK;
    @Column(name = "DatumPrijave")
    @Temporal(TemporalType.DATE)
    private Date datumPrijave;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ucesceEkipe")
    private Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollection;
    @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Takmicenje takmicenje;
    @JoinColumn(name = "IDEkipe", referencedColumnName = "IDEkipe", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Ekipa ekipa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ucesceEkipe")
    private Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollection;

    public UcesceEkipe() {
    }

    public UcesceEkipe(UcesceEkipePK ucesceEkipePK) {
        this.ucesceEkipePK = ucesceEkipePK;
    }

    public UcesceEkipe(int iDEkipe, int iDTakmicenja) {
        this.ucesceEkipePK = new UcesceEkipePK(iDEkipe, iDTakmicenja);
    }

    public UcesceEkipePK getUcesceEkipePK() {
        return ucesceEkipePK;
    }

    public void setUcesceEkipePK(UcesceEkipePK ucesceEkipePK) {
        this.ucesceEkipePK = ucesceEkipePK;
    }

    public Date getDatumPrijave() {
        return datumPrijave;
    }

    public void setDatumPrijave(Date datumPrijave) {
        this.datumPrijave = datumPrijave;
    }

    @XmlTransient
    public Collection<PrijavljujeBorbeEkipno> getPrijavljujeBorbeEkipnoCollection() {
        return prijavljujeBorbeEkipnoCollection;
    }

    public void setPrijavljujeBorbeEkipnoCollection(Collection<PrijavljujeBorbeEkipno> prijavljujeBorbeEkipnoCollection) {
        this.prijavljujeBorbeEkipnoCollection = prijavljujeBorbeEkipnoCollection;
    }

    public Takmicenje getTakmicenje() {
        return takmicenje;
    }

    public void setTakmicenje(Takmicenje takmicenje) {
        this.takmicenje = takmicenje;
    }

    public Ekipa getEkipa() {
        return ekipa;
    }

    public void setEkipa(Ekipa ekipa) {
        this.ekipa = ekipa;
    }

    @XmlTransient
    public Collection<PrijavljujeKateEkipno> getPrijavljujeKateEkipnoCollection() {
        return prijavljujeKateEkipnoCollection;
    }

    public void setPrijavljujeKateEkipnoCollection(Collection<PrijavljujeKateEkipno> prijavljujeKateEkipnoCollection) {
        this.prijavljujeKateEkipnoCollection = prijavljujeKateEkipnoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ucesceEkipePK != null ? ucesceEkipePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcesceEkipe)) {
            return false;
        }
        UcesceEkipe other = (UcesceEkipe) object;
        if ((this.ucesceEkipePK == null && other.ucesceEkipePK != null) || (this.ucesceEkipePK != null && !this.ucesceEkipePK.equals(other.ucesceEkipePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.UcesceEkipe[ ucesceEkipePK=" + ucesceEkipePK + " ]";
    }
    
}
