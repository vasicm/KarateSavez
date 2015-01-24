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
public class PrijavljujeKateEkipnoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDEkipe")
    private int iDEkipe;
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private int iDKategorije;

    public PrijavljujeKateEkipnoPK() {
    }

    public PrijavljujeKateEkipnoPK(int iDEkipe, int iDTakmicenja, int iDKategorije) {
        this.iDEkipe = iDEkipe;
        this.iDTakmicenja = iDTakmicenja;
        this.iDKategorije = iDKategorije;
    }

    public int getIDEkipe() {
        return iDEkipe;
    }

    public void setIDEkipe(int iDEkipe) {
        this.iDEkipe = iDEkipe;
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
        hash += (int) iDEkipe;
        hash += (int) iDTakmicenja;
        hash += (int) iDKategorije;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeKateEkipnoPK)) {
            return false;
        }
        PrijavljujeKateEkipnoPK other = (PrijavljujeKateEkipnoPK) object;
        if (this.iDEkipe != other.iDEkipe) {
            return false;
        }
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
        return "mydesignwkc.jpa.entity.PrijavljujeKateEkipnoPK[ iDEkipe=" + iDEkipe + ", iDTakmicenja=" + iDTakmicenja + ", iDKategorije=" + iDKategorije + " ]";
    }
    
}
