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
public class BorbaDTO implements Serializable {

    private int idTakmicenja;
    private int idKategorije;
    private TakmicarInfoDTO jmbPlavi;
    private TakmicarInfoDTO jmbCrveni;
    private int poeniPlavi;
    private int poeniCrveni;
    private int kaznePlavi;
    private int kazneCrveni;
    private int nivoTakmicenja;

    public BorbaDTO() {
    }

    public BorbaDTO(int idTakmicenja, int idKategorije, TakmicarInfoDTO jmbPlavi, TakmicarInfoDTO jmbCrveni, int poeniPlavi, int poeniCrveni, int kaznePlavi, int kazneCrveni, int nivoTakmicenja) {
        this.idTakmicenja = idTakmicenja;
        this.idKategorije = idKategorije;
        this.jmbPlavi = jmbPlavi;
        this.jmbCrveni = jmbCrveni;
        this.poeniPlavi = poeniPlavi;
        this.poeniCrveni = poeniCrveni;
        this.kaznePlavi = kaznePlavi;
        this.kazneCrveni = kazneCrveni;
        this.nivoTakmicenja = nivoTakmicenja;
    }

    public int getIdTakmicenja() {
        return idTakmicenja;
    }

    public int getIdKategorije() {
        return idKategorije;
    }

    public TakmicarInfoDTO getJmbPlavi() {
        return jmbPlavi;
    }

    public TakmicarInfoDTO getJmbCrveni() {
        return jmbCrveni;
    }

    public int getPoeniPlavi() {
        return poeniPlavi;
    }

    public int getPoeniCrveni() {
        return poeniCrveni;
    }

    public int getKaznePlavi() {
        return kaznePlavi;
    }

    public int getKazneCrveni() {
        return kazneCrveni;
    }

    public int getNivoTakmicenja() {
        return nivoTakmicenja;
    }

    public void setIdTakmicenja(int idTakmicenja) {
        this.idTakmicenja = idTakmicenja;
    }

    public void setIdKategorije(int idKategorije) {
        this.idKategorije = idKategorije;
    }

    public void setJmbPlavi(TakmicarInfoDTO jmbPlavi) {
        this.jmbPlavi = jmbPlavi;
    }

    public void setJmbCrveni(TakmicarInfoDTO jmbCrveni) {
        this.jmbCrveni = jmbCrveni;
    }

    public void setPoeniPlavi(int poeniPlavi) {
        this.poeniPlavi = poeniPlavi;
    }

    public void setPoeniCrveni(int poeniCrveni) {
        this.poeniCrveni = poeniCrveni;
    }

    public void setKaznePlavi(int kaznePlavi) {
        this.kaznePlavi = kaznePlavi;
    }

    public void setKazneCrveni(int kazneCrveni) {
        this.kazneCrveni = kazneCrveni;
    }

    public void setNivoTakmicenja(int nivoTakmicenja) {
        this.nivoTakmicenja = nivoTakmicenja;
    }

}
