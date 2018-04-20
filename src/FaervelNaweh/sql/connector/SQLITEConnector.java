package FaervelNaweh.sql.connector;/*
 * @author Andreas Schreiner
 */

import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Connector Class for SQLLite
 */
public class SQLITEConnector extends SQLConnector {

    /**
     * Opens and or Creates SQLite DB
     * @param name
     */
    public SQLITEConnector(String name) {
        String sslStatus = "false";
        try {
            Class.forName("org.sqlite.JDBC").newInstance();
            connect = DriverManager.getConnection("jdbc:sqlite:" + name);
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

