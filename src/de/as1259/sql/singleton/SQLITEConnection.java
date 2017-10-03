/*
 * @author Andreas Schreiner
 */

package de.as1259.sql.singleton;

import de.as1259.sql.connectors.SQLITEConnector;

import java.sql.ResultSet;

/**
 * Singleton Class for the SQLite Conncetion
 */
@Deprecated
public class SQLITEConnection {
    /*
     *The Connection
     */
    private  static SQLITEConnector sqlConn;
    /**
     * Opens and or Creates SQLite DB
     * @param name
     */
    public static void open(String name){
        sqlConn =  new SQLITEConnector(name);
    }
    /**
     * Closes and finalizes The SQl Connection
     * @throws Exception
     */
    public static void close() throws Exception {
        sqlConn.close();
    }

    /**
     * Executes SQL Command
     * @param sqlCMD
     */
    public static void shell(String sqlCMD){
        sqlConn.shell(sqlCMD);
    }
    /**
     * Executes SQL Command if connection is open
     * Retrieves Query Information
     * @param sqlCMD
     * @return
     */
    public static ResultSet shellRS(String sqlCMD){
        return sqlConn.shellRS(sqlCMD);
    }

    public static boolean isOpen(){
        if (sqlConn == null) {
            return false;
        } else if (sqlConn.isOpen()){
            return true;
        }
        return false;
    }
}
