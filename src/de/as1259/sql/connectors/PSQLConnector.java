/*
 * @author Andreas Schreiner
 */

package de.as1259.sql.connectors;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.*;

/**
 * Connector Class for Postgresql
 */
public class PSQLConnector extends SQLConnector {

    public PSQLConnector(String connectionName, String address, int port, String database, String user,
                         String password, boolean ssl) {
        this(address, port, database, user, password, ssl);
        setConnectionName(connectionName);
    }

    /**
     * Establishes a SQL Connection
     *
     * @param address
     * @param port
     * @param database
     * @param user
     * @param password
     * @param ssl
     */
    public PSQLConnector(String address, int port, String database, String user,
                         String password, boolean ssl) {
        String sslStatus = "false";
        if (ssl) {
            sslStatus = "true";
        }
        try {
            Class.forName("org.postgresql.Driver").newInstance();
            connect = DriverManager.getConnection("jdbc:postgresql://" + address + ":" + port + "/" + database +
                    "?user=" + user + "&password=" + password + "&ssl=" + sslStatus);
        } catch (Exception e) {
            System.err.println(e);
        }
        ADDRESS = address;
        PORT = port;
        USER = user;
        DATABASE = database;
        SSL = ssl;
    }

    @Override
    public String type() {
        return "postgresql";
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCMD  ="\\d";
        return getRows(sqlCMD);
    }
}
