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
public class BorbaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private int iDKategorije;
    @Basic(optional = false)
    @Column(name = "JMBPlavi")
    private long jMBPlavi;
    @Basic(optional = false)
    @Column(name = "JMBCrveni")
    private long jMBCrveni;

    public BorbaPK() {
    }

    public BorbaPK(int iDTakmicenja, int iDKategorije, long jMBPlavi, long jMBCrveni) {
        this.iDTakmicenja = iDTakmicenja;
        this.iDKategorije = iDKategorije;
        this.jMBPlavi = jMBPlavi;
        this.jMBCrveni = jMBCrveni;
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

    public long getJMBPlavi() {
        return jMBPlavi;
    }

    public void setJMBPlavi(long jMBPlavi) {
        this.jMBPlavi = jMBPlavi;
    }

    public long getJMBCrveni() {
        return jMBCrveni;
    }

    public void setJMBCrveni(long jMBCrveni) {
        this.jMBCrveni = jMBCrveni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDTakmicenja;
        hash += (int) iDKategorije;
        hash += (int) jMBPlavi;
        hash += (int) jMBCrveni;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BorbaPK)) {
            return false;
        }
        BorbaPK other = (BorbaPK) object;
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        if (this.iDKategorije != other.iDKategorije) {
            return false;
        }
        if (this.jMBPlavi != other.jMBPlavi) {
            return false;
        }
        if (this.jMBCrveni != other.jMBCrveni) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.BorbaPK[ iDTakmicenja=" + iDTakmicenja + ", iDKategorije=" + iDKategorije + ", jMBPlavi=" + jMBPlavi + ", jMBCrveni=" + jMBCrveni + " ]";
    }
    
}
