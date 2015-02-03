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
import karatesavezrepublikesrpske.data.dao.TakmicarDAO;
import karatesavezrepublikesrpske.data.dto.KarateKlubDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;

/**
 *
 * @author Marko
 */
public class MySQLTakmicarDAO implements TakmicarDAO {

    @Override
    public List<TakmicarInfoDTO> tamicari() {
        List<TakmicarInfoDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select TI.*, KK.NazivKarateKluba "
                + "from takmicar_info TI, karate_klub KK "
                + "where  TI.IDKluba = KK.IDKluba ";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                retVal.add(
                        new TakmicarInfoDTO(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getDate(5),
                                new KarateKlubDTO(rs.getInt(6), null, rs.getString(8), null, null, null),
                                rs.getDouble(7)
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
    public List<TakmicarInfoDTO> tamicari(String nazivKluba, String nazivTakmicenja, String nazivKategorije) {
        List<TakmicarInfoDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = null;
        if (nazivKategorije == null) {
            query = "select TI.*, KK.IDKluba "
                    + " from takmicar_info TI, karate_klub KK, ucesce_pojedinca UP, takmicenje TAK "
                    + " where TI.IDKluba = KK.IDKluba ";
            if (nazivKluba != null) {
                query += " and KK.NazivKarateKluba = ? ";
                //    + "	and UP.IDTakmicenja = TAK.IDTakmicenja ";
            }
            if (nazivTakmicenja != null) {
                query += " and TAK.NazivTakmicenja = ?"
                        + "	and UP.IDTakmicenja = TAK.IDTakmicenja "
                        + "	and UP.JMB = TI.JMB ";
            }

        } else if (nazivKluba != null && nazivTakmicenja != null) {
            query = "( "
                    + "select Ti.*, KK.IDKluba"
                    + "from takmicar_info TI, prijavljuje_takmicenje_u_borbama PUB, kategorija KAT, takmicenje TAK, karate_klub KK "
                    + "where PUB.IDTakmicenja = TAK.IDTakmicenja "
                    + "	and PUB.IDKategorije = KAT.IDKategorije "
                    + "	and PUB.JMB = TI.JMB "
                    + "	and TI.IDKluba = KK.IDKluba "
                    + "	and KK.NazivKarateKluba like ? "
                    + "	and TAK.NazivTakmicenja like ? "
                    + "	and KAT.NazivKategorije like ? "
                    + ") "
                    + "union "
                    + "( "
                    + "select TI.*, KK.IDKluba "
                    + "from takmicar_info TI, prijavljuje_takmicenje_u_katama PUK, kategorija KAT, takmicenje TAK, karate_klub KK "
                    + "where PUK.IDTakmicenja = TAK.IDTakmicenja "
                    + "	and PUK.IDKategorije = KAT.IDKategorije "
                    + "	and PUK.JMB = TI.JMB "
                    + "	and TI.IDKluba = KK.IDKluba "
                    + "	and KK.NazivKarateKluba like ? "
                    + "	and TAK.NazivTakmicenja like ? "
                    + "	and KAT.NazivKategorije like ? "
                    + ")";
        } else {
            return retVal;
        }

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            if (nazivKategorije == null) {
                if (nazivKluba != null) {
                    ps.setString(1, nazivKluba);
                    if (nazivTakmicenja != null) {
                        ps.setString(2, nazivTakmicenja);
                    }
                } else if (nazivTakmicenja != null) {
                    ps.setString(1, nazivTakmicenja);
                }

            } else {
                ps.setString(1, nazivKluba);
                ps.setString(2, nazivTakmicenja);
                ps.setString(3, nazivKategorije);
                ps.setString(4, nazivKluba);
                ps.setString(5, nazivTakmicenja);
                ps.setString(6, nazivKategorije);
            }
            rs = ps.executeQuery();

            while (rs.next()) {
                retVal.add(
                        new TakmicarInfoDTO(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getDate(5),
                                new KarateKlubDTO(rs.getInt(6), null, nazivKluba, null, null, null),
                                rs.getDouble(7)
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
    public List<TakmicarInfoDTO> tamicari(int idKluba, int idTakmicenja, int idKategorije) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TakmicarInfoDTO> tamicari(int idTakmicenja, int idKategorije) {
        List<TakmicarInfoDTO> retVal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select ti.* "
                + " from prijavljuje_takmicenje_u_borbama as ptub , takmicar_info as ti "
                + " where ptub.JMB = ti.JMB  "
                + " and ptub.IDTakmicenja=?"
                + " and ptub.IDKategorije=? ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, idTakmicenja);
            ps.setInt(2, idKategorije);
            rs = ps.executeQuery();
            while (rs.next()) {
                retVal.add(
                        new TakmicarInfoDTO(
                                rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getDate(5),
                                new KarateKlubDTO(rs.getInt(6), null, null, null, null, null),
                                rs.getDouble(7)
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
    public TakmicarInfoDTO takmicar(int id) {
        TakmicarInfoDTO retVal = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = "select TI.*, KK.NazivKarateKluba "
                + "from takmicar_info TI, karate_klub KK "
                + "where  TI.IDKluba = KK.IDKluba "
                + "	and TI.JMB = ?";

        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            if (rs.next()) {
                retVal = new TakmicarInfoDTO(
                        id,
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getDate(5),
                        new KarateKlubDTO(rs.getInt(6), null, rs.getString(8), null, null, null),
                        rs.getDouble(7)
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
    public boolean dodajTakmicara(TakmicarInfoDTO takmicar) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query1 = "INSERT INTO clan VALUES "
                + "(?, ?, ?, ?, ?, ?) ";
        String query2 = "INSERT INTO takmicar  (JMB, Kilaza) VALUES "
                + "(?, ?) ";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query1);
            ps.setLong(1, takmicar.getJmb());
            ps.setDate(2, new Date(takmicar.getDatumRodjenja().getTime()));
            ps.setString(3, takmicar.getPojas());
            ps.setInt(4, takmicar.getIdKluba().getIdKluba());
            ps.setString(5, takmicar.getIme());
            ps.setString(6, takmicar.getPrezime());
            retVal = ps.executeUpdate() == 1;

            ps = conn.prepareStatement(query2);
            ps.setLong(1, takmicar.getJmb());
            ps.setDouble(2, takmicar.getKilaza());
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
    public boolean obrisiTakmicara(int id) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query1 = "DELETE FROM takmicar WHERE  ID=?";
        String query2 = "DELETE FROM clan WHERE  ID=?";
        try {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query1);
            ps.setInt(1, id);

            retVal = ps.executeUpdate() == 1;
            ps = conn.prepareStatement(query2);
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
    public boolean azurirajTakmicara(TakmicarInfoDTO takmicar) {
        boolean retVal = false;
        Connection conn = null;
        PreparedStatement ps = null;

        String query = "UPDATE clan SET "
                + " Pojas=?, "
                + " IDKluba=?, "
                + " Ime=?, "
                + " Prezime=? "
                + "WHERE  JMB=? ";
        try
        {
            conn = ConnectionPool.getInstance().checkOut();
            ps = conn.prepareStatement(query);
            ps.setString(1, takmicar.getPojas());
            ps.setInt(2, takmicar.getIdKluba().getIdKluba());
            ps.setString(3, takmicar.getIme());
            ps.setString(4, takmicar.getPrezime());
            ps.setLong(5, takmicar.getJmb());

            retVal = ps.executeUpdate() == 1;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            DBUtilities.getInstance().showSQLException(e);
        }
        finally
        {
            ConnectionPool.getInstance().checkIn(conn);
            DBUtilities.getInstance().close(ps);
        }
        return retVal;
    }

}
