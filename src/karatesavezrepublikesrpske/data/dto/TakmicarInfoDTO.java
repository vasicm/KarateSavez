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
public class TakmicarInfoDTO implements Serializable{

    private long jmb;
    private String ime;
    private String prezime;
    private String pojas;
    private Date datumRodjenja;
    private KarateKlubDTO idKluba;
    private double kilaza;

    public TakmicarInfoDTO() {
    }

    public TakmicarInfoDTO(long jmb, String ime, String prezime, String pojas, Date datumRodjenja, KarateKlubDTO idKluba, double kilaza) {
        this.jmb = jmb;
        this.ime = ime;
        this.prezime = prezime;
        this.pojas = pojas;
        this.datumRodjenja = datumRodjenja;
        this.idKluba = idKluba;
        this.kilaza = kilaza;
    }

    public long getJmb() {
        return jmb;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getPojas() {
        return pojas;
    }

    public Date getDatumRodjenja() {
        return datumRodjenja;
    }

    public KarateKlubDTO getIdKluba() {
        return idKluba;
    }

    public double getKilaza() {
        return kilaza;
    }

    public void setJmb(long jmb) {
        this.jmb = jmb;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public void setPojas(String pojas) {
        this.pojas = pojas;
    }

    public void setDatumRodjenja(Date datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public void setIdKluba(KarateKlubDTO idKluba) {
        this.idKluba = idKluba;
    }

    public void setKilaza(double kilaza) {
        this.kilaza = kilaza;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(jmb);
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
        TakmicarInfoDTO other = (TakmicarInfoDTO) obj;
        if (jmb != other.jmb) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return jmb + "_" + ime + " " + prezime;
    }
}
