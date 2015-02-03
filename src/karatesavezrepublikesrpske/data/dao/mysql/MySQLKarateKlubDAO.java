/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import karatesavezrepublikesrpske.data.dao.KarateKlubDAO;
import karatesavezrepublikesrpske.data.dto.KarateKlubDTO;
import karatesavezrepublikesrpske.data.dto.KarateSavezDTO;

/**
 *
 * @author Marko
 */
public class MySQLKarateKlubDAO implements KarateKlubDAO {

    @Override
    public List<KarateKlubDTO> klubovi() {
        List<KarateKlubDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select kk.*, ks.NazivSaveza "
                + "from karate_klub kk, karate_savez ks "
                + "where kk.IDSaveza = ks.IDSaveza "
                + "and ks.IDSaveza = 1";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while(rs.next()){
                retVal.add(
                        new KarateKlubDTO(
                                rs.getInt(1), 
                                new KarateSavezDTO(1, rs.getString(7), null, null), 
                                rs.getString(3), 
                                rs.getString(4), 
                                rs.getString(5), 
                                rs.getString(6)
                        )
                );
            }
            
         {
                
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            DBUtilities.getInstance().showSQLException(ex);
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps, rs);
        }
        return retVal;
    }

}
