/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao.mysql;

import karatesavezrepublikesrpske.data.dao.BorbaDAO;
import karatesavezrepublikesrpske.data.dao.DAOFactory;
import karatesavezrepublikesrpske.data.dao.KarateKlubDAO;
import karatesavezrepublikesrpske.data.dao.KategorijaBorbeDAO;
import karatesavezrepublikesrpske.data.dao.OglasDAO;
import karatesavezrepublikesrpske.data.dao.TakmicarAnalizaDAO;
import karatesavezrepublikesrpske.data.dao.TakmicarDAO;
import karatesavezrepublikesrpske.data.dao.TakmicenjeDAO;

/**
 *
 * @author Marko
 */
public class MySQLDAOFactory extends DAOFactory {

    @Override
    public OglasDAO getOglasDAO() {
        return new MySQLOglasDAO();
    }

    @Override
    public TakmicarDAO getTakmicarDAO() {
        return new MySQLTakmicarDAO();
    }

    @Override
    public TakmicenjeDAO getTakmicenjeDAO() {
        return new MySQLTakmicenjeDAO();
    }

    @Override
    public BorbaDAO getBorbaDAO() {
        return new MySQLBorbaDAO();
    }

    @Override
    public KarateKlubDAO getKarateKlubDAO() {
        return new MySQLKarateKlubDAO();
    }

    @Override
    public KategorijaBorbeDAO getKategorijaBorbeDAO() {
        return new MySQLKategorijaBorbeDAO();
    }

    @Override
    public TakmicarAnalizaDAO getTakmicarAnalizaDAO() {
        return new MySQLTakmicarAnalizaDAO();
    }

}
