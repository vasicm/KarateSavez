/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.util;

import karatesavezrepublikesrpske.data.dao.DAOFactory;
import karatesavezrepublikesrpske.data.dao.mysql.MySQLDAOFactory;

/**
 *
 * @author Marko
 */
public class KSRSUtilities {

    private static DAOFactory daoFactory;

    public static DAOFactory getDAOFactory() {
        if (daoFactory == null) {
            daoFactory = new MySQLDAOFactory();
        }
        return daoFactory;
    }
    
    public static boolean tryParseInt(String s)
    {
        boolean retVal = false;
        try
        {
            Integer.valueOf(s);
            retVal = true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("String "+ s + " nije moguce pretvoriti u int");
//            e.printStackTrace();
        }
        return retVal;
    }
    
    public static boolean tryParseLong(String s)
    {
        boolean retVal = false;
        try
        {
            Long.valueOf(s);
            retVal = true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("String "+ s + " nije moguce pretvoriti u long");
//            e.printStackTrace();
        }
        return retVal;
    }
    
    public static boolean tryParseDouble(String s)
    {
        boolean retVal = false;
        try
        {
            Double.valueOf(s);
            retVal = true;
        }
        catch (NumberFormatException e)
        {
            System.out.println("String "+ s + " nije moguce pretvoriti u double");
//            e.printStackTrace();
        }
        return retVal;
    }

}
