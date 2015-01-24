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
public class UcescePojedincaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;
    @Basic(optional = false)
    @Column(name = "JMB")
    private long jmb;

    public UcescePojedincaPK() {
    }

    public UcescePojedincaPK(int iDTakmicenja, long jmb) {
        this.iDTakmicenja = iDTakmicenja;
        this.jmb = jmb;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDTakmicenja;
        hash += (int) jmb;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcescePojedincaPK)) {
            return false;
        }
        UcescePojedincaPK other = (UcescePojedincaPK) object;
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        if (this.jmb != other.jmb) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.UcescePojedincaPK[ iDTakmicenja=" + iDTakmicenja + ", jmb=" + jmb + " ]";
    }
    
}
