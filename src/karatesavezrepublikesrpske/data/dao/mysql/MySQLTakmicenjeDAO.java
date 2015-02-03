/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.data.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import karatesavezrepublikesrpske.data.dao.TakmicenjeDAO;
import karatesavezrepublikesrpske.data.dto.TakmicenjeDTO;

/**
 *
 * @author Marko
 */
public class MySQLTakmicenjeDAO implements TakmicenjeDAO {

    @Override
    public List<TakmicenjeDTO> takmicenja() {
        List<TakmicenjeDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * FROM TAKMICENJE";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new TakmicenjeDTO(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getDate(3),
                                null,
                                null,
                                rs.getString(6))
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
    public boolean dodajTakmicenje(TakmicenjeDTO takmicenje) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO takmicenje  (IDTakmicenja, NazivTakmicenja, DatumPocetka, IDSaveza, Adresa) "
                + "VALUES (?, ?, ?, ?, ?) ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, takmicenje.getIdTakmicenja());
            ps.setString(2, takmicenje.getNazivTakmicenja());
            ps.setDate(3, (Date) takmicenje.getDatumPocetka());
            ps.setInt(4, 1);
            ps.setString(5, takmicenje.getAdresa());

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
    public boolean azurirajTakmicenje(TakmicenjeDTO takmicenje) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE takmicenje SET "
                + "IDTakmicenja=?, "
                + "NazivTakmicenja=?, "
                + "DatumPocetka=?, "
                + "IDSaveza=?, "
                + "Adresa=?, "
                + "WHERE  ID=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            
            ps.setInt(1, takmicenje.getIdTakmicenja());
            ps.setString(2, takmicenje.getNazivTakmicenja());
            ps.setDate(3, (Date) takmicenje.getDatumPocetka());
            ps.setInt(4,1);
            ps.setString(5, takmicenje.getAdresa());
            ps.setInt(6, takmicenje.getIdTakmicenja());
            
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
    public boolean obrisiTakmicenje(int id) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "DELETE FROM takmicenje WHERE  IDTakmicenja=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);

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
    public boolean prijaviTakmicaraNaTakmicenje(int idTakmicara, int idTakmicenja) {
         boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;
        String query = "INSERT INTO ucesce_pojedinca  (JMB, IDTakmicenja) "
                + "VALUES (?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicara);
            ps.setInt(2, idTakmicenja);

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

}
