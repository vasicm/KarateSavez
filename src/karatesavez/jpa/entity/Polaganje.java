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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Marko
 */
@Entity
@Table(name = "polaganje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Polaganje.findAll", query = "SELECT p FROM Polaganje p"),
    @NamedQuery(name = "Polaganje.findByPojas", query = "SELECT p FROM Polaganje p WHERE p.polaganjePK.pojas = :pojas"),
    @NamedQuery(name = "Polaganje.findByPolozio", query = "SELECT p FROM Polaganje p WHERE p.polozio = :polozio"),
    @NamedQuery(name = "Polaganje.findByJmb", query = "SELECT p FROM Polaganje p WHERE p.polaganjePK.jmb = :jmb"),
    @NamedQuery(name = "Polaganje.findByIDKluba", query = "SELECT p FROM Polaganje p WHERE p.polaganjePK.iDKluba = :iDKluba"),
    @NamedQuery(name = "Polaganje.findByDatumIVrijeme", query = "SELECT p FROM Polaganje p WHERE p.polaganjePK.datumIVrijeme = :datumIVrijeme")})
public class Polaganje implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PolaganjePK polaganjePK;
    @Column(name = "Polozio")
    private Integer polozio;
    @JoinColumns({
        @JoinColumn(name = "IDKluba", referencedColumnName = "IDKluba", insertable = false, updatable = false),
        @JoinColumn(name = "DatumIVrijeme", referencedColumnName = "DatumIVrijeme", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Ispit ispit;
    @JoinColumn(name = "JMB", referencedColumnName = "JMB", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Clan clan;

    public Polaganje() {
    }

    public Polaganje(PolaganjePK polaganjePK) {
        this.polaganjePK = polaganjePK;
    }

    public Polaganje(String pojas, long jmb, int iDKluba, Date datumIVrijeme) {
        this.polaganjePK = new PolaganjePK(pojas, jmb, iDKluba, datumIVrijeme);
    }

    public PolaganjePK getPolaganjePK() {
        return polaganjePK;
    }

    public void setPolaganjePK(PolaganjePK polaganjePK) {
        this.polaganjePK = polaganjePK;
    }

    public Integer getPolozio() {
        return polozio;
    }

    public void setPolozio(Integer polozio) {
        this.polozio = polozio;
    }

    public Ispit getIspit() {
        return ispit;
    }

    public void setIspit(Ispit ispit) {
        this.ispit = ispit;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (polaganjePK != null ? polaganjePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Polaganje)) {
            return false;
        }
        Polaganje other = (Polaganje) object;
        if ((this.polaganjePK == null && other.polaganjePK != null) || (this.polaganjePK != null && !this.polaganjePK.equals(other.polaganjePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.Polaganje[ polaganjePK=" + polaganjePK + " ]";
    }
    
}
