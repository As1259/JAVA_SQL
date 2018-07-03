/*
 * @author Andreas Schreiner
 */
package de.Fearvel.sql.connector;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
/**
 * Connector Class for SQLLite
 */
public class SQLITEConnector extends SQLConnector {

    /**
     * Opens and or Creates SQLite DB
     * @param fileLocation Pfad zur SQLite Datei
     */
    public SQLITEConnector(String fileLocation)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException,
            NoSuchMethodException, InvocationTargetException {
        Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
        connection = DriverManager.getConnection("jdbc:sqlite:" + fileLocation);
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCommandString  =".tables;";
        return getRows(sqlCommandString);
    }
}