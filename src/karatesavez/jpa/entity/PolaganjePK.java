/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Marko
 */
@Embeddable
public class PolaganjePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Pojas")
    private String pojas;
    @Basic(optional = false)
    @Column(name = "JMB")
    private long jmb;
    @Basic(optional = false)
    @Column(name = "IDKluba")
    private int iDKluba;
    @Basic(optional = false)
    @Column(name = "DatumIVrijeme")
    @Temporal(TemporalType.DATE)
    private Date datumIVrijeme;

    public PolaganjePK() {
    }

    public PolaganjePK(String pojas, long jmb, int iDKluba, Date datumIVrijeme) {
        this.pojas = pojas;
        this.jmb = jmb;
        this.iDKluba = iDKluba;
        this.datumIVrijeme = datumIVrijeme;
    }

    public String getPojas() {
        return pojas;
    }

    public void setPojas(String pojas) {
        this.pojas = pojas;
    }

    public long getJmb() {
        return jmb;
    }

    public void setJmb(long jmb) {
        this.jmb = jmb;
    }

    public int getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(int iDKluba) {
        this.iDKluba = iDKluba;
    }

    public Date getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(Date datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pojas != null ? pojas.hashCode() : 0);
        hash += (int) jmb;
        hash += (int) iDKluba;
        hash += (datumIVrijeme != null ? datumIVrijeme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolaganjePK)) {
            return false;
        }
        PolaganjePK other = (PolaganjePK) object;
        if ((this.pojas == null && other.pojas != null) || (this.pojas != null && !this.pojas.equals(other.pojas))) {
            return false;
        }
        if (this.jmb != other.jmb) {
            return false;
        }
        if (this.iDKluba != other.iDKluba) {
            return false;
        }
        if ((this.datumIVrijeme == null && other.datumIVrijeme != null) || (this.datumIVrijeme != null && !this.datumIVrijeme.equals(other.datumIVrijeme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PolaganjePK[ pojas=" + pojas + ", jmb=" + jmb + ", iDKluba=" + iDKluba + ", datumIVrijeme=" + datumIVrijeme + " ]";
    }
    
}
