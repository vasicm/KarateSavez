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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "ispit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ispit.findAll", query = "SELECT i FROM Ispit i"),
    @NamedQuery(name = "Ispit.findByAdresa", query = "SELECT i FROM Ispit i WHERE i.adresa = :adresa"),
    @NamedQuery(name = "Ispit.findByDatumIVrijeme", query = "SELECT i FROM Ispit i WHERE i.ispitPK.datumIVrijeme = :datumIVrijeme"),
    @NamedQuery(name = "Ispit.findByIDKluba", query = "SELECT i FROM Ispit i WHERE i.ispitPK.iDKluba = :iDKluba"),
    @NamedQuery(name = "Ispit.findByBrojPrijavljenihTakmicara", query = "SELECT i FROM Ispit i WHERE i.brojPrijavljenihTakmicara = :brojPrijavljenihTakmicara"),
    @NamedQuery(name = "Ispit.findByProjPolezenih", query = "SELECT i FROM Ispit i WHERE i.projPolezenih = :projPolezenih")})
public class Ispit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected IspitPK ispitPK;
    @Column(name = "Adresa")
    private String adresa;
    @Column(name = "BrojPrijavljenihTakmicara")
    private Integer brojPrijavljenihTakmicara;
    @Column(name = "ProjPolezenih")
    private Integer projPolezenih;
    @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private KarateKlub karateKlub;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ispit")
    private Collection<Polaganje> polaganjeCollection;

    public Ispit() {
    }

    public Ispit(IspitPK ispitPK) {
        this.ispitPK = ispitPK;
    }

    public Ispit(Date datumIVrijeme, int iDKluba) {
        this.ispitPK = new IspitPK(datumIVrijeme, iDKluba);
    }

    public IspitPK getIspitPK() {
        return ispitPK;
    }

    public void setIspitPK(IspitPK ispitPK) {
        this.ispitPK = ispitPK;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getBrojPrijavljenihTakmicara() {
        return brojPrijavljenihTakmicara;
    }

    public void setBrojPrijavljenihTakmicara(Integer brojPrijavljenihTakmicara) {
        this.brojPrijavljenihTakmicara = brojPrijavljenihTakmicara;
    }

    public Integer getProjPolezenih() {
        return projPolezenih;
    }

    public void setProjPolezenih(Integer projPolezenih) {
        this.projPolezenih = projPolezenih;
    }

    public KarateKlub getKarateKlub() {
        return karateKlub;
    }

    public void setKarateKlub(KarateKlub karateKlub) {
        this.karateKlub = karateKlub;
    }

    @XmlTransient
    public Collection<Polaganje> getPolaganjeCollection() {
        return polaganjeCollection;
    }

    public void setPolaganjeCollection(Collection<Polaganje> polaganjeCollection) {
        this.polaganjeCollection = polaganjeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ispitPK != null ? ispitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ispit)) {
            return false;
        }
        Ispit other = (Ispit) object;
        if ((this.ispitPK == null && other.ispitPK != null) || (this.ispitPK != null && !this.ispitPK.equals(other.ispitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Ispit[ ispitPK=" + ispitPK + " ]";
    }
    
}
