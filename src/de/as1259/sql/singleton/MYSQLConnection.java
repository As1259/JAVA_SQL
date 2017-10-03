/*
 * @author Andreas Schreiner
 */

package de.as1259.sql.singleton;
import de.as1259.sql.connectors.MYSQLConnector;

import java.sql.ResultSet;

/**
 * Singleton Class for the MYSQL/MariaDB Conncetion
 */
@Deprecated
public class  MYSQLConnection {
    /*
     *The Connection
     */
    private  static MYSQLConnector sqlConn;

    /**
     * Opens the SQL Connection
     * @param address
     * @param port
     * @param database
     * @param user
     * @param password
     * @param verifyCertificate
     * @param ssl
     */
    public static void open(String address, int port, String database,String user,
                            String password, boolean verifyCertificate, boolean ssl){
        sqlConn =  new  MYSQLConnector(address,port,database,user,password,verifyCertificate,ssl);
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
