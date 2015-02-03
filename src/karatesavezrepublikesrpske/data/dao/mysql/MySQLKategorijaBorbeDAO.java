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
import java.util.logging.Level;
import java.util.logging.Logger;
import karatesavezrepublikesrpske.data.dao.KategorijaBorbeDAO;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;

/**
 *
 * @author Marko
 */
public class MySQLKategorijaBorbeDAO implements KategorijaBorbeDAO {

    @Override
    public List<BorbePojdeinacnoInfoDTO> sveKategorije() {
        List<BorbePojdeinacnoInfoDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM borbe_pojedinacno_kategorija_info";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new BorbePojdeinacnoInfoDTO(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getInt(5),
                                rs.getDouble(6))
                );
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

    @Override
    public List<BorbePojdeinacnoInfoDTO> sveKategorijeNaTakmicenju(int idTakmicenja) {
        List<BorbePojdeinacnoInfoDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select bpki.* "
                + "from borbe_pojedinacno_kategorija_info bpki, takmicenje_kategorija tk "
                + "where bpki.IDKategorije = tk.IDKategorije "
                + "and tk.IDTakmicenja = ? ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                retVal.add(
                        new BorbePojdeinacnoInfoDTO(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getInt(5),
                                rs.getDouble(6))
                );
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

    @Override
    public boolean prijaviTakmicara(long idTakmicara, int idKategorije, int idTakmicenja) {
         boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO prijavljuje_takmicenje_u_borbama  (IDTakmicenja, IDKategorije, JMB ) "
                + "VALUES (?, ?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            ps.setInt(2, idKategorije);
            ps.setLong(3, idTakmicara);

            retVal = ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            DBUtilities.getInstance().showSQLException(e);
        } finally {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return retVal;
    }

    @Override
    public boolean odjaviTakmicara(long idTakmicara, int idKategorije, int idTakmicenja) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
