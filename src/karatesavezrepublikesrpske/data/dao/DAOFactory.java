/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao;

/**
 *
 * @author Marko
 */
public abstract class DAOFactory {

    public abstract OglasDAO getOglasDAO();

    public abstract TakmicarDAO getTakmicarDAO();

    public abstract TakmicenjeDAO getTakmicenjeDAO();

    public abstract BorbaDAO getBorbaDAO();

    public abstract KarateKlubDAO getKarateKlubDAO();

    public abstract KategorijaBorbeDAO getKategorijaBorbeDAO();
    
    public abstract TakmicarAnalizaDAO getTakmicarAnalizaDAO();
}
