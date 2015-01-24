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
public class PrijavljujeTakmicenjeUKatamaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "JMB")
    private long jmb;
    @Basic(optional = false)
    @Column(name = "IDKategorije")
    private int iDKategorije;

    public PrijavljujeTakmicenjeUKatamaPK() {
    }

    public PrijavljujeTakmicenjeUKatamaPK(int iDTakmicenja, long jmb, int iDKategorije) {
        this.iDTakmicenja = iDTakmicenja;
        this.jmb = jmb;
        this.iDKategorije = iDKategorije;
    }

    public int getIDTakmicenja() {
        return iDTakmicenja;
    }

    public void setIDTakmicenja(int iDTakmicenja) {
        this.iDTakmicenja = iDTakmicenja;
    }

    public long getJmb() {
        return jmb;
    }

    public void setJmb(long jmb) {
        this.jmb = jmb;
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
        hash += (int) jmb;
        hash += (int) iDKategorije;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrijavljujeTakmicenjeUKatamaPK)) {
            return false;
        }
        PrijavljujeTakmicenjeUKatamaPK other = (PrijavljujeTakmicenjeUKatamaPK) object;
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        if (this.jmb != other.jmb) {
            return false;
        }
        if (this.iDKategorije != other.iDKategorije) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.PrijavljujeTakmicenjeUKatamaPK[ iDTakmicenja=" + iDTakmicenja + ", jmb=" + jmb + ", iDKategorije=" + iDKategorije + " ]";
    }
    
}
