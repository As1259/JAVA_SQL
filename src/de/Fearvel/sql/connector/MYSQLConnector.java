/*
 * @author Andreas Schreiner
 */
package de.Fearvel.sql.connector;
import javafx.collections.ObservableList;

import java.lang.reflect.InvocationTargetException;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Connector Class for  MYSQL/MariaDB
 */
public class MYSQLConnector extends SQLConnector{
    /**
     *  Establishes a SQL Connection
     * @param address Adresse des SQL Servers
     * @param port Port des SQL Servers
     * @param database Namen der zu verwendenden Datenbank
     * @param user Benutzernamen
     * @param password Passwort
     * @param verifyCertificate Auf Zertifikat Prüfen
     * @param ssl SSL geschützte Verbindung
     */
    public MYSQLConnector(String address, int port, String database,String user, String password,
            boolean verifyCertificate, boolean ssl)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException,
            NoSuchMethodException, InvocationTargetException {
        String vc = verifyCertificate ? "true" : "false";
        String sslStatus = ssl ? "true" : "false";
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+ address
                            + ":"+ port + "/"+ database
                            + "?verifyServerCertificate="+ vc
                            + "&useSSL=" + sslStatus,
                            user , password);
    }
    @Override
    public ObservableList<ObservableList> getTableNames() throws SQLException {
        String sqlCommandString  ="SELECT TABLE_NAME \n" +
                "FROM INFORMATION_SCHEMA.TABLES\n" +
                "WHERE TABLE_TYPE = 'BASE TABLE';";
        return getRows(sqlCommandString);
    }
}