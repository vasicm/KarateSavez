/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Marko
 */
@SuppressWarnings("serial")
public class TakmicenjeDTO implements Serializable {
    private int idTakmicenja;
    private String nazivTakmicenja;
    private Date datumPocetka;
    private KarateKlubDTO idKluba;
    private KarateSavezDTO idSaveza;
    private String adresa;

    public TakmicenjeDTO() {
    }

    public TakmicenjeDTO(int idTakmicenja, String nazivTakmicenja, Date datumPocetka, KarateKlubDTO idKluba, KarateSavezDTO idSaveza, String adresa) {
        this.idTakmicenja = idTakmicenja;
        this.nazivTakmicenja = nazivTakmicenja;
        this.datumPocetka = datumPocetka;
        this.idKluba = idKluba;
        this.idSaveza = idSaveza;
        this.adresa = adresa;
    }

    public String getNazivTakmicenja() {
        return nazivTakmicenja;
    }

    public void setNazivTakmicenja(String nazivTakmicenja) {
        this.nazivTakmicenja = nazivTakmicenja;
    }
    
    public int getIdTakmicenja() {
        return idTakmicenja;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public KarateKlubDTO getIdKluba() {
        return idKluba;
    }

    public KarateSavezDTO getIdSaveza() {
        return idSaveza;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setIdTakmicenja(int idTakmicenja) {
        this.idTakmicenja = idTakmicenja;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public void setIdKluba(KarateKlubDTO idKluba) {
        this.idKluba = idKluba;
    }

    public void setIdSaveza(KarateSavezDTO idSaveza) {
        this.idSaveza = idSaveza;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idTakmicenja;
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
        TakmicenjeDTO other = (TakmicenjeDTO) obj;
        if (idTakmicenja != other.idTakmicenja) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivTakmicenja;
    }
    
}
