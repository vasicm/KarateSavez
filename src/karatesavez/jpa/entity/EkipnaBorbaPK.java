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
public class EkipnaBorbaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDEkipePlavi")
    private int iDEkipePlavi;
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private int iDKategorije;
    @Basic(optional = false)
    @Column(name = "IDEkipeCrveni")
    private int iDEkipeCrveni;

    public EkipnaBorbaPK() {
    }

    public EkipnaBorbaPK(int iDEkipePlavi, int iDTakmicenja, int iDKategorije, int iDEkipeCrveni) {
        this.iDEkipePlavi = iDEkipePlavi;
        this.iDTakmicenja = iDTakmicenja;
        this.iDKategorije = iDKategorije;
        this.iDEkipeCrveni = iDEkipeCrveni;
    }

    public int getIDEkipePlavi() {
        return iDEkipePlavi;
    }

    public void setIDEkipePlavi(int iDEkipePlavi) {
        this.iDEkipePlavi = iDEkipePlavi;
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

    public int getIDEkipeCrveni() {
        return iDEkipeCrveni;
    }

    public void setIDEkipeCrveni(int iDEkipeCrveni) {
        this.iDEkipeCrveni = iDEkipeCrveni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDEkipePlavi;
        hash += (int) iDTakmicenja;
        hash += (int) iDKategorije;
        hash += (int) iDEkipeCrveni;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EkipnaBorbaPK)) {
            return false;
        }
        EkipnaBorbaPK other = (EkipnaBorbaPK) object;
        if (this.iDEkipePlavi != other.iDEkipePlavi) {
            return false;
        }
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        if (this.iDKategorije != other.iDKategorije) {
            return false;
        }
        if (this.iDEkipeCrveni != other.iDEkipeCrveni) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.EkipnaBorbaPK[ iDEkipePlavi=" + iDEkipePlavi + ", iDTakmicenja=" + iDTakmicenja + ", iDKategorije=" + iDKategorije + ", iDEkipeCrveni=" + iDEkipeCrveni + " ]";
    }
    
}
