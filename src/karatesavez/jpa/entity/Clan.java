/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "clan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Clan.findAll", query = "SELECT c FROM Clan c"),
    @NamedQuery(name = "Clan.findByJmb", query = "SELECT c FROM Clan c WHERE c.jmb = :jmb"),
    @NamedQuery(name = "Clan.findByDatumRodjenja", query = "SELECT c FROM Clan c WHERE c.datumRodjenja = :datumRodjenja"),
    @NamedQuery(name = "Clan.findIdKluba", query = "SELECT c FROM Clan c WHERE c.iDKluba.iDKluba = :IDKluba"),
    @NamedQuery(name = "Clan.findByIme", query = "SELECT c FROM Clan c WHERE c.ime = :ime"),
    @NamedQuery(name = "Clan.findByPrezime", query = "SELECT c FROM Clan c WHERE c.prezime = :prezime")})
public class Clan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "JMB")
    private Long jmb;
    @Basic(optional = false)
    @Column(name = "DatumRodjenja")
    @Temporal(TemporalType.DATE)
    private Date datumRodjenja;
    @Basic(optional = false)
    @Column(name = "Pojas")
    private String pojas;
    @Basic(optional = false)
    @Column(name = "Ime")
    private String ime;
    @Basic(optional = false)
    @Column(name = "Prezime")
    private String prezime;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clan")
    private Collection<Polaganje> polaganjeCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clan")
    private Sudija sudija;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clan")
    private Takmicar takmicar;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clan")
    private Trener trener;
    @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba")
    @ManyToOne(optional = false)
    private KarateKlub iDKluba;

    public Clan() {
    }

    public Clan(Long jmb) {
        this.jmb = jmb;
    }

    public Clan(Long jmb, Date datumRodjenja, String pojas, String ime, String prezime) {
        this.jmb = jmb;
        this.datumRodjenja = datumRodjenja;
        this.pojas = pojas;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Long getJmb() {
        return jmb;
    }

    public void setJmb(Long jmb) {
        this.jmb = jmb;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getPojas() {
        return pojas;
    }

    public void setPojas(String pojas) {
        this.pojas = pojas;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @XmlTransient
    public Collection<Polaganje> getPolaganjeCollection() {
        return polaganjeCollection;
    }

    public void setPolaganjeCollection(Collection<Polaganje> polaganjeCollection) {
        this.polaganjeCollection = polaganjeCollection;
    }

    public Sudija getSudija() {
        return sudija;
    }

    public void setSudija(Sudija sudija) {
        this.sudija = sudija;
    }

    public Takmicar getTakmicar() {
        return takmicar;
    }

    public void setTakmicar(Takmicar takmicar) {
        this.takmicar = takmicar;
    }

    public Trener getTrener() {
        return trener;
    }

    public void setTrener(Trener trener) {
        this.trener = trener;
    }

    public KarateKlub getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(KarateKlub iDKluba) {
        this.iDKluba = iDKluba;
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
        if (!(object instanceof Clan)) {
            return false;
        }
        Clan other = (Clan) object;
        if ((this.jmb == null && other.jmb != null) || (this.jmb != null && !this.jmb.equals(other.jmb))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Clan[ jmb=" + jmb + " ]";
    }
    
}
