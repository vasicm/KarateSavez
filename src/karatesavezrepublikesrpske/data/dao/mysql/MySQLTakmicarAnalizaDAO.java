/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import karatesavezrepublikesrpske.data.dao.TakmicarAnalizaDAO;
import karatesavezrepublikesrpske.data.dto.TakmicarAnalizaDTO;

/**
 *
 * @author Marko
 */
public class MySQLTakmicarAnalizaDAO implements TakmicarAnalizaDAO {

    @Override
    public TakmicarAnalizaDTO analizaTakmicara(long idTAkmicara) {
        TakmicarAnalizaDTO retVal = new TakmicarAnalizaDTO();
        Connection conn = null;
        CallableStatement cs = null;
        String query = "{call analizaTakmicara(?, ?, ?, ?) }";
        
        try {
            conn = ConnectionPool.getInstance().checkOut();
            cs = conn.prepareCall(query);
            cs.setLong(1, idTAkmicara);
            cs.registerOutParameter(2, Types.DOUBLE);
            cs.registerOutParameter(3, Types.DOUBLE);
            cs.registerOutParameter(4, Types.INTEGER);

            cs.execute();
            
            retVal.setProsjekPostignutihPoena(cs.getDouble(2));
            retVal.setProsjekPrimljenihPoena(cs.getDouble(3));
            retVal.setBrojMeceva(cs.getInt(4));
            
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtilities.getInstance().showSQLException(e);
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(cs);
        }
        return retVal;
    }

}
