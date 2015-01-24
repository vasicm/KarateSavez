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
public class UcesceEkipePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "IDEkipe")
    private int iDEkipe;
    @Basic(optional = false)
    @Column(name = "IDTakmicenja")
    private int iDTakmicenja;

    public UcesceEkipePK() {
    }

    public UcesceEkipePK(int iDEkipe, int iDTakmicenja) {
        this.iDEkipe = iDEkipe;
        this.iDTakmicenja = iDTakmicenja;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iDEkipe;
        hash += (int) iDTakmicenja;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UcesceEkipePK)) {
            return false;
        }
        UcesceEkipePK other = (UcesceEkipePK) object;
        if (this.iDEkipe != other.iDEkipe) {
            return false;
        }
        if (this.iDTakmicenja != other.iDTakmicenja) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mydesignwkc.jpa.entity.UcesceEkipePK[ iDEkipe=" + iDEkipe + ", iDTakmicenja=" + iDTakmicenja + " ]";
    }
    
}
