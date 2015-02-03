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
import karatesavezrepublikesrpske.data.dao.OglasDAO;
import karatesavezrepublikesrpske.data.dto.KarateSavezDTO;
import karatesavezrepublikesrpske.data.dto.OglasDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 *
 * @author Marko
 */
public class MySQLOglasDAO implements OglasDAO {

    @Override
    public List<OglasDTO> oglasi() {
        List<OglasDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * from oglas o, karate_savez ks where o.IDSaveza = ks.IDSaveza";

        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                retVal.add(
                        new OglasDTO(
                                rs.getInt(1),
                                rs.getString(3),
                                rs.getString(4),
                                new KarateSavezDTO(
                                        rs.getInt(5),
                                        rs.getString(6),
                                        rs.getString(7),
                                        rs.getString(8)
                                ))
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
    public OglasDTO oglas(int id) {
        OglasDTO retVal = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "SELECT * from oglas o, karate_savez ks where o.IDSaveza = ks.IDSaveza and o.ID=?";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                retVal = new OglasDTO(
                        rs.getInt(1),
                        rs.getString(3),
                        rs.getString(4),
                        new KarateSavezDTO(
                                rs.getInt(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8)
                        ));
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
    public boolean dodajOglas(OglasDTO oglas) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO OGLAS VALUES "
                + "(null, ?, ?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, oglas.getIdSaveza().getIdSaveza());
            ps.setString(2, oglas.getTipOglasa());
            ps.setString(3, oglas.getTekstOglasa());

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
    public boolean obrisiOglas(int id) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "DELETE FROM oglas WHERE  ID=?";
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
    public boolean azurirajOglas(OglasDTO oglas) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE oglas SET "
                + "IDSaveza=?, "
                + "TipOglasa=?, "
                + "TekstOglasa=? "
                + "WHERE  ID=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, oglas.getIdSaveza().getIdSaveza());
            ps.setString(2, oglas.getTipOglasa());
            ps.setString(3, oglas.getTekstOglasa());
            ps.setInt(4, oglas.getId());

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
