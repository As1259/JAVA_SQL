/*
 * @author Andreas Schreiner
 */


package de.as1259.sql.connectors;

import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.*;

/**
 * Connector Class for Microsoft SQL
 */
public class MSSQLConnector extends SQLConnector {


    public MSSQLConnector(String connectionName, String address, int port, String database, String user,
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
    public MSSQLConnector(String address, int port, String database, String user,
                          String password, boolean ssl) {
        String sslStatus = "false";

        if (ssl) {
            sslStatus = "true";
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
            connect = DriverManager.getConnection("jdbc:sqlserver://" + address + ":" +
                    port + ";databaseName=" + database + ";user=" + user +
                    ";password=" + password + ";&integratedSecurity=" + sslStatus);
            ADDRESS = address;
            PORT = port;
            USER = user;
            DATABASE = database;
            SSL = ssl;
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @Override
    public String type() {
        return "microsoft SQL";
    }

    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCMD  ="SELECT TABLE_NAME\n" +
                "FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_TYPE = 'BASE TABLE';";

        return getRows(sqlCMD);
    }
}
