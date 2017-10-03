/*
 * @author Andreas Schreiner
 */

package de.as1259.sql.connectors;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.*;

/**
 * Connector Class for SQLLite
 */
public class SQLITEConnector extends SQLConnector {

    public SQLITEConnector(String connectionName, String name) {
        this(name);
        setConnectionName(connectionName);
    }
    /**
     * Opens and or Creates SQLite DB
     * @param name
     */
    public SQLITEConnector(String name) {
        String sslStatus = "false";
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            connect = DriverManager.getConnection("jdbc:sqlite:" + name);
            ADDRESS = name;
            PORT = 0;
            USER = "";
            DATABASE ="";
            SSL = false;
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    @Override
    public String type() {
        return "sqlite";
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCMD  =".tables;";
        return getRows(sqlCMD);
    }
}

