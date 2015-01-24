/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Marko
 */
@Embeddable
public class TakmicenjeKategorijaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private int iDKategorije;

    public TakmicenjeKategorijaPK() {
    }

    public TakmicenjeKategorijaPK(int iDTakmicenja, int iDKategorije) {
        this.iDTakmicenja = iDTakmicenja;
        this.iDKategorije = iDKategorije;
    }

    public int getIDTakmicenja() {
        return iDTakmicenja;
    }

    public void setIDTakmicenja(int iDTakmicenja) {
        this.iDTakmicenja = iDTakmicenja;
    }

    public int getIDKategorije() {
        return iDKategorije;
    }

    public void setIDKategorije(int iDKategorije) {
        this.iDKategorije = iDKategorije;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDTakmicenja;
        hash += (int) iDKategorije;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TakmicenjeKategorijaPK)) {
            return false;
        }
        TakmicenjeKategorijaPK other = (TakmicenjeKategorijaPK) object;
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        if (this.iDKategorije != other.iDKategorije) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.TakmicenjeKategorijaPK[ iDTakmicenja=" + iDTakmicenja + ", iDKategorije=" + iDKategorije + " ]";
    }
    
}
