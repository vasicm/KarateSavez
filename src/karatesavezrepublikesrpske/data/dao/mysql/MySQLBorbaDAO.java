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
import karatesavezrepublikesrpske.data.dao.BorbaDAO;
import karatesavezrepublikesrpske.data.dto.BorbaDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;

/**
 *
 * @author Marko
 */
public class MySQLBorbaDAO implements BorbaDAO {

    @Override
    public List<BorbaDTO> borbe(int idTakmicenja) {
        List<BorbaDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select b.* , c.Ime , c.Prezime, c2.Ime, c2.Prezime "
                + " from borba b, clan c, clan c2 "
                + " where b.JMBPlavi = c.JMB and b.JMBCrveni=c2.JMB "
                + " and b.IDTakmicenja = ?";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new BorbaDTO(
                                rs.getInt(1),
                                rs.getInt(2),
                                new TakmicarInfoDTO(rs.getLong(3), rs.getString(10), rs.getString(11), null, null, null, 0),
                                new TakmicarInfoDTO(rs.getLong(4), rs.getString(12), rs.getString(13), null, null, null, 0),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(9)
                        )
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
    public List<BorbaDTO> borbe(int idTakmicenja, int idKategorije) {

        List<BorbaDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select b.* , c.Ime , c.Prezime, c2.Ime, c2.Prezime "
                + " from borba b, clan c, clan c2 "
                + " where b.JMBPlavi = c.JMB and b.JMBCrveni=c2.JMB "
                + " and b.IDTakmicenja = ? "
                + " and b.IDKategorije = ? ";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            ps.setInt(2, idKategorije);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new BorbaDTO(
                                rs.getInt(1),
                                rs.getInt(2),
                                new TakmicarInfoDTO(rs.getLong(3), rs.getString(10), rs.getString(11), null, null, null, 0),
                                new TakmicarInfoDTO(rs.getLong(4), rs.getString(12), rs.getString(13), null, null, null, 0),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(9)
                        )
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
    public List<BorbaDTO> borbe(int idTakmicenja, int idKategorije, long jmb) {
        List<BorbaDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       String query = "select b.* , c.Ime , c.Prezime, c2.Ime, c2.Prezime "
                + " from borba b, clan c, clan c2 "
                + " where b.JMBPlavi = c.JMB and b.JMBCrveni=c2.JMB "
                + " and b.IDTakmicenja = ? "
                + " and b.IDKategorije = ? "
                + " and (b.JMBPlavi = ? or b.JMBCrveni = ?) ";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            ps.setInt(2, idKategorije);
            ps.setLong(3, jmb);
            ps.setLong(4, jmb);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new BorbaDTO(
                                rs.getInt(1),
                                rs.getInt(2),
                                new TakmicarInfoDTO(rs.getLong(3), rs.getString(10), rs.getString(11), null, null, null, 0),
                                new TakmicarInfoDTO(rs.getLong(4), rs.getString(12), rs.getString(13), null, null, null, 0),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(9)
                        )
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
    public List<BorbaDTO> borbe(long jmb) {
        List<BorbaDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       String query = "select b.* , c.Ime , c.Prezime, c2.Ime, c2.Prezime "
                + " from borba b, clan c, clan c2 "
                + " where b.JMBPlavi = c.JMB and b.JMBCrveni=c2.JMB "
                + " and (b.JMBPlavi = ? or b.JMBCrveni = ?) ";
        try {

            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setLong(1, jmb);
            ps.setLong(2, jmb);
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new BorbaDTO(
                                rs.getInt(1),
                                rs.getInt(2),
                                new TakmicarInfoDTO(rs.getLong(3), rs.getString(10), rs.getString(11), null, null, null, 0),
                                new TakmicarInfoDTO(rs.getLong(4), rs.getString(12), rs.getString(13), null, null, null, 0),
                                rs.getInt(5),
                                rs.getInt(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getInt(9)
                        )
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
    public boolean dodajBorbu(BorbaDTO borba) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "INSERT INTO borba  "
                + "(IDTakmicenja, IDKategorije, JMBPlavi, JMBCrveni, PoeniPlavi, PoeniCrveni, KaznePlavi, KazneCrveni, NivoTakmicenja) "
                + "VALUES (?, ?, ?, ?, ?, ? ,?, ?, ?) ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, borba.getIdTakmicenja());
            ps.setInt(2, borba.getIdKategorije());
            ps.setLong(3, borba.getJmbPlavi().getJmb());
            ps.setLong(4, borba.getJmbCrveni().getJmb());
            ps.setInt(5, borba.getPoeniPlavi());
            ps.setInt(6, borba.getPoeniCrveni());
            ps.setInt(7, borba.getKaznePlavi());
            ps.setInt(8, borba.getKazneCrveni());
            ps.setInt(9, borba.getNivoTakmicenja());

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
