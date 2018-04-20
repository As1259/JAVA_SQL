package FaervelNaweh.sql.connector;/*
 * @author Andreas Schreiner
 */


import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Connector Class for Microsoft SQL
 */
public class MSSQLConnector extends SQLConnector {

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
