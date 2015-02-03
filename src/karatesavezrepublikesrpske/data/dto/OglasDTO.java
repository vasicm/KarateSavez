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
public class OglasDTO implements Serializable {

    private int id;
    private String tipOglasa;
    private String tekstOglasa;
    private KarateSavezDTO idSaveza;

    public OglasDTO() {
    }

    public OglasDTO(int id, String tipOglasa, String tekstOglasa, KarateSavezDTO idSaveza) {
        this.id = id;
        this.tipOglasa = tipOglasa;
        this.tekstOglasa = tekstOglasa;
        this.idSaveza = idSaveza;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipOglasa(String tipOglasa) {
        this.tipOglasa = tipOglasa;
    }

    public void setTekstOglasa(String tekstOglasa) {
        this.tekstOglasa = tekstOglasa;
    }

    public void setIdSaveza(KarateSavezDTO idSaveza) {
        this.idSaveza = idSaveza;
    }

    public int getId() {
        return id;
    }

    public String getTipOglasa() {
        return tipOglasa;
    }

    public String getTekstOglasa() {
        return tekstOglasa;
    }

    public KarateSavezDTO getIdSaveza() {
        return idSaveza;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
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
        OglasDTO other = (OglasDTO) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id+"_"+tipOglasa;
    }

}
