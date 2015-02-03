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
public class TakmicarAnalizaDTO {
    private TakmicarInfoDTO takmicar;
    private double prosjekPrimljenihPoena;
    private double prosjekPostignutihPoena;
    private int brojMeceva;

    public TakmicarAnalizaDTO() {
    }

    public TakmicarAnalizaDTO(TakmicarInfoDTO takmicar, double prosjekPrimljenihPoena, double prosjekPostignutihPoena, int brojMeceva) {
        this.takmicar = takmicar;
        this.prosjekPrimljenihPoena = prosjekPrimljenihPoena;
        this.prosjekPostignutihPoena = prosjekPostignutihPoena;
        this.brojMeceva = brojMeceva;
    }

    public TakmicarInfoDTO getTakmicar() {
        return takmicar;
    }

    public double getProsjekPrimljenihPoena() {
        return prosjekPrimljenihPoena;
    }

    public double getProsjekPostignutihPoena() {
        return prosjekPostignutihPoena;
    }

    public int getBrojMeceva() {
        return brojMeceva;
    }

    public void setTakmicar(TakmicarInfoDTO takmicar) {
        this.takmicar = takmicar;
    }

    public void setProsjekPrimljenihPoena(double prosjekPrimljenihPoena) {
        this.prosjekPrimljenihPoena = prosjekPrimljenihPoena;
    }

    public void setProsjekPostignutihPoena(double prosjekPostignutihPoena) {
        this.prosjekPostignutihPoena = prosjekPostignutihPoena;
    }

    public void setBrojMeceva(int brojMeceva) {
        this.brojMeceva = brojMeceva;
    }
    
    
}
