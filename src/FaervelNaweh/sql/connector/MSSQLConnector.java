/*
 * @author Andreas Schreiner
 */
package FaervelNaweh.sql.connector;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
/**
 * Connector Class for Microsoft SQL
 */
public class MSSQLConnector extends SQLConnector {
    /**
     * Establishes a SQL Connection
     * @param address Adresse des SQL Servers
     * @param port Port des SQL Servers
     * @param database Namen der zu verwendenden Datenbank
     * @param user Benutzernamen
     * @param password Passwort
     * @param ssl SSL geschützte Verbindung
     */
    public MSSQLConnector(String address, int port, String database, String user, String password, boolean ssl)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException,
            NoSuchMethodException, InvocationTargetException {
        String sslStatus = ssl ? "true" : "false";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").getDeclaredConstructor().newInstance();
        connect = DriverManager.getConnection(
                    "jdbc:sqlserver://" + address
                            + ":" + port
                            + ";databaseName=" + database
                            + ";user=" + user
                            + ";password=" + password
                            + ";&integratedSecurity=" + sslStatus);
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCommandString  ="SELECT TABLE_NAME\n" +
                "FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_TYPE = 'BASE TABLE';";
        return getRows(sqlCommandString);
    }
}