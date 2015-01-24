/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "takmicenje_kategorija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TakmicenjeKategorija.findAll", query = "SELECT t FROM TakmicenjeKategorija t"),
    @NamedQuery(name = "TakmicenjeKategorija.findByIDTakmicenja", query = "SELECT t FROM TakmicenjeKategorija t WHERE t.takmicenjeKategorijaPK.iDTakmicenja = :iDTakmicenja"),
    @NamedQuery(name = "TakmicenjeKategorija.findByIDKategorije", query = "SELECT t FROM TakmicenjeKategorija t WHERE t.takmicenjeKategorijaPK.iDKategorije = :iDKategorije"),
    @NamedQuery(name = "TakmicenjeKategorija.findByVrijemePocetka", query = "SELECT t FROM TakmicenjeKategorija t WHERE t.vrijemePocetka = :vrijemePocetka"),
    @NamedQuery(name = "TakmicenjeKategorija.findByBrojBorilista", query = "SELECT t FROM TakmicenjeKategorija t WHERE t.brojBorilista = :brojBorilista")})
public class TakmicenjeKategorija implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TakmicenjeKategorijaPK takmicenjeKategorijaPK;
    @Column(name = "VrijemePocetka")
    @Temporal(TemporalType.DATE)
    private Date vrijemePocetka;
    @Column(name = "BrojBorilista")
    private Integer brojBorilista;
    @JoinColumn(name = "IDKategorije", referencedColumnName = "IDKategorije", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Kategorija kategorija;
    @JoinColumn(name = "IDTakmicenja", referencedColumnName = "IDTakmicenja", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Takmicenje takmicenje;

    public TakmicenjeKategorija() {
    }

    public TakmicenjeKategorija(TakmicenjeKategorijaPK takmicenjeKategorijaPK) {
        this.takmicenjeKategorijaPK = takmicenjeKategorijaPK;
    }

    public TakmicenjeKategorija(int iDTakmicenja, int iDKategorije) {
        this.takmicenjeKategorijaPK = new TakmicenjeKategorijaPK(iDTakmicenja, iDKategorije);
    }

    public TakmicenjeKategorijaPK getTakmicenjeKategorijaPK() {
        return takmicenjeKategorijaPK;
    }

    public void setTakmicenjeKategorijaPK(TakmicenjeKategorijaPK takmicenjeKategorijaPK) {
        this.takmicenjeKategorijaPK = takmicenjeKategorijaPK;
    }

    public Date getVrijemePocetka() {
        return vrijemePocetka;
    }

    public void setVrijemePocetka(Date vrijemePocetka) {
        this.vrijemePocetka = vrijemePocetka;
    }

    public Integer getBrojBorilista() {
        return brojBorilista;
    }

    public void setBrojBorilista(Integer brojBorilista) {
        this.brojBorilista = brojBorilista;
    }

    public Kategorija getKategorija() {
        return kategorija;
    }

    public void setKategorija(Kategorija kategorija) {
        this.kategorija = kategorija;
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
        hash += (takmicenjeKategorijaPK != null ? takmicenjeKategorijaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TakmicenjeKategorija)) {
            return false;
        }
        TakmicenjeKategorija other = (TakmicenjeKategorija) object;
        if ((this.takmicenjeKategorijaPK == null && other.takmicenjeKategorijaPK != null) || (this.takmicenjeKategorijaPK != null && !this.takmicenjeKategorijaPK.equals(other.takmicenjeKategorijaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.TakmicenjeKategorija[ takmicenjeKategorijaPK=" + takmicenjeKategorijaPK + " ]";
    }
    
}
