/*
 * @author Andreas Schreiner
 */


package de.as1259.sql.singleton;

import de.as1259.sql.connectors.PSQLConnector;

import java.sql.ResultSet;

/**
 * Singleton Class for the PostgreSQL Conncetion
 */
@Deprecated
public class  PSQLConnection {
    /*
     *The Connection
     */
    private  static PSQLConnector sqlConn;

    /**
     * Opens the SQL Connection
     * @param address
     * @param port
     * @param database
     * @param user
     * @param password
     * @param ssl
     */
    public static void open(String address, int port, String database,String user,
                            String password, boolean ssl){
        sqlConn =  new PSQLConnector(address,port,database,user,password,ssl);
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
