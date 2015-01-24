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
public class IspitPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "DatumIVrijeme")
    @Temporal(TemporalType.DATE)
    private Date datumIVrijeme;
    @Basic(optional = false)
    @Column(name = "IDKluba")
    private int iDKluba;

    public IspitPK() {
    }

    public IspitPK(Date datumIVrijeme, int iDKluba) {
        this.datumIVrijeme = datumIVrijeme;
        this.iDKluba = iDKluba;
    }

    public Date getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(Date datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
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
        hash += (datumIVrijeme != null ? datumIVrijeme.hashCode() : 0);
        hash += (int) iDKluba;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IspitPK)) {
            return false;
        }
        IspitPK other = (IspitPK) object;
        if ((this.datumIVrijeme == null && other.datumIVrijeme != null) || (this.datumIVrijeme != null && !this.datumIVrijeme.equals(other.datumIVrijeme))) {
            return false;
        }
        if (this.iDKluba != other.iDKluba) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.IspitPK[ datumIVrijeme=" + datumIVrijeme + ", iDKluba=" + iDKluba + " ]";
    }
    
}
