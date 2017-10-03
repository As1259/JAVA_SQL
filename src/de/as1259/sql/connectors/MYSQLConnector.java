/*
 * @author Andreas Schreiner
 */

package de.as1259.sql.connectors;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Optional;

/**
 * Connector Class for  MYSQL/MariaDB
 */
public class MYSQLConnector extends SQLConnector{


    public MYSQLConnector(String connectionName, String address, int port, String database, String user,
                          String password,boolean verifyCertificate, boolean ssl) {
        this(address, port, database, user, password,verifyCertificate, ssl);
        setConnectionName(connectionName);
    }
    /**
     *  Establishes a SQL Connection
     * @param address
     * @param port
     * @param database
     * @param user
     * @param password
     * @param verifyCertificate
     * @param ssl
     */
    public MYSQLConnector(String address, int port, String database,String user,
                     String password, boolean verifyCertificate, boolean ssl){
        String vc = "false";
        String sslStatus = "false";
        if (verifyCertificate){
            vc = "true";
        }
        if (ssl){
            sslStatus = "true";
        }
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:mysql://"+ address + ":"+ port + "/"+ database +
                    "?verifyServerCertificate="+ vc + "&useSSL=" + sslStatus, user , password);
        } catch (Exception e) {
            System.err.println(e);
        }
        ADDRESS = address;
        PORT = port;
        USER = user;
        DATABASE =database;
        SSL = ssl;
    }
    @Override
    public String type() {
        return "mysql";
    }

    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCMD  ="SELECT TABLE_NAME \n" +
                "FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_TYPE = 'BASE TABLE';";
        return getRows(sqlCMD);
    }
}
