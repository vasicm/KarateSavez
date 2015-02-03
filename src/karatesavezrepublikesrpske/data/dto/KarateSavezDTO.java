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
public class KarateSavezDTO implements Serializable{
    private int idSaveza;
    private String nazivSaveza;
    private String sjediste;
    private String opis;

    public KarateSavezDTO() {
    }

    public KarateSavezDTO(int idSaveza, String nazivSaveza, String sjediste, String opis) {
        this.idSaveza = idSaveza;
        this.nazivSaveza = nazivSaveza;
        this.sjediste = sjediste;
        this.opis = opis;
    }

    public void setIdSaveza(int idSaveza) {
        this.idSaveza = idSaveza;
    }

    public void setNazivSaveza(String nazivSaveza) {
        this.nazivSaveza = nazivSaveza;
    }

    public void setSjediste(String sjediste) {
        this.sjediste = sjediste;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getIdSaveza() {
        return idSaveza;
    }

    public String getNazivSaveza() {
        return nazivSaveza;
    }

    public String getSjediste() {
        return sjediste;
    }

    public String getOpis() {
        return opis;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + idSaveza;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KarateSavezDTO other = (KarateSavezDTO) obj;
        if (idSaveza != other.idSaveza)
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return nazivSaveza;
    }
}
