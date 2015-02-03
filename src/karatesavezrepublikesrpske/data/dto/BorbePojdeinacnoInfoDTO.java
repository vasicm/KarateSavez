/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dto;

import java.io.Serializable;

/**
 *
 * @author Marko
 */
@SuppressWarnings("serial")
public class BorbePojdeinacnoInfoDTO implements Serializable{
    private int idKategorije;
    private String nazivKategorije;
    private String uzrast;
    private String opis;
    private int brojPrijavljenih;
    private double kilaza;

    public BorbePojdeinacnoInfoDTO() {
    }

    public BorbePojdeinacnoInfoDTO(int idKategorije, String nazivKategorije, String uzrast, String opis, int brojPrijavljenih, double kilaza) {
        this.idKategorije = idKategorije;
        this.nazivKategorije = nazivKategorije;
        this.uzrast = uzrast;
        this.opis = opis;
        this.brojPrijavljenih = brojPrijavljenih;
        this.kilaza = kilaza;
    }

    public int getIdKategorije() {
        return idKategorije;
    }

    public String getNazivKategorije() {
        return nazivKategorije;
    }

    public String getUzrast() {
        return uzrast;
    }

    public String getOpis() {
        return opis;
    }

    public int getBrojPrijavljenih() {
        return brojPrijavljenih;
    }

    public double getKilaza() {
        return kilaza;
    }

    public void setIdKategorije(int idKategorije) {
        this.idKategorije = idKategorije;
    }

    public void setNazivKategorije(String nazivKategorije) {
        this.nazivKategorije = nazivKategorije;
    }

    public void setUzrast(String uzrast) {
        this.uzrast = uzrast;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setBrojPrijavljenih(int brojPrijavljenih) {
        this.brojPrijavljenih = brojPrijavljenih;
    }

    public void setKilaza(double kilaza) {
        this.kilaza = kilaza;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idKategorije;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BorbePojdeinacnoInfoDTO other = (BorbePojdeinacnoInfoDTO) obj;
        if (idKategorije != other.idKategorije) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivKategorije;
    }
    
}
