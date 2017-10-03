/*
 * @author Andreas Schreiner
 */


package de.as1259.sql.connectors;

import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Connector Class for Oracle SQL
 */
public class OSQLConnector extends SQLConnector{


    public OSQLConnector(String connectionName, String address, int port, String SID, String user,
                          String password) {
        this(address, port, SID, user, password);
        setConnectionName(connectionName);
    }
    /**
     * Establishes a SQL Connection
     * @param address
     * @param port
     * @param user
     * @param password
     */
    public OSQLConnector(String address, int port, String SID,String user,
                     String password){
        String sslStatus = "false";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
            connect = DriverManager.getConnection("jdbc:oracle:thin:@"+ address + ":"+
                    port + ":" + SID, user, password);
        } catch (Exception e) {
            System.err.println(e);
        }
        ADDRESS = address;
        PORT = port;
        USER = user;
        DATABASE =SID;
        SSL = false;
    }
    @Override
    public String type() {
        return "oracle thin SQL";
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCMD  ="SELECT table_name FROM dba_tables;";
        return getRows(sqlCMD);
    }
}
