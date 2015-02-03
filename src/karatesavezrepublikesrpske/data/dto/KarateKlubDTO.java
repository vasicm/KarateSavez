/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dto;

/**
 *
 * @author Marko
 */
@SuppressWarnings("serial")
public class KarateKlubDTO {
    private int idKluba;
    private KarateSavezDTO idSaveza;
    private String nazivKarateKluba;
    private String opis;
    private String adresa;
    private String sjediste;

    public KarateKlubDTO() {
    }

    public KarateKlubDTO(int idKluba, KarateSavezDTO idSaveza, String nazivKarateKluba, String opis, String adresa, String sjediste) {
        this.idKluba = idKluba;
        this.idSaveza = idSaveza;
        this.nazivKarateKluba = nazivKarateKluba;
        this.opis = opis;
        this.adresa = adresa;
        this.sjediste = sjediste;
    }

    public void setIdKluba(int idKluba) {
        this.idKluba = idKluba;
    }

    public void setIdSaveza(KarateSavezDTO idSaveza) {
        this.idSaveza = idSaveza;
    }

    public void setNazivKarateKluba(String nazivKarateKluba) {
        this.nazivKarateKluba = nazivKarateKluba;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setSjediste(String sjediste) {
        this.sjediste = sjediste;
    }

    public int getIdKluba() {
        return idKluba;
    }

    public KarateSavezDTO getIdSaveza() {
        return idSaveza;
    }

    public String getNazivKarateKluba() {
        return nazivKarateKluba;
    }

    public String getOpis() {
        return opis;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getSjediste() {
        return sjediste;
    }
     @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + idKluba;
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
        KarateKlubDTO other = (KarateKlubDTO) obj;
        if (idKluba != other.idKluba) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nazivKarateKluba;
    }
}
