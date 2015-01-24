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
public class TreningPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @Column(name = "IDKluba")
    private int iDKluba;

    public TreningPK() {
    }

    public TreningPK(Date datum, int iDKluba) {
        this.datum = datum;
        this.iDKluba = iDKluba;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getIDKluba() {
        return iDKluba;
    }

    public void setIDKluba(int iDKluba) {
        this.iDKluba = iDKluba;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datum != null ? datum.hashCode() : 0);
        hash += (int) iDKluba;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TreningPK)) {
            return false;
        }
        TreningPK other = (TreningPK) object;
        if ((this.datum == null && other.datum != null) || (this.datum != null && !this.datum.equals(other.datum))) {
            return false;
        }
        if (this.iDKluba != other.iDKluba) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.TreningPK[ datum=" + datum + ", iDKluba=" + iDKluba + " ]";
    }
    
}
