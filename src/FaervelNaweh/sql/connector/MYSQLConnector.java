package FaervelNaweh.sql.connector;/*
 * @author Andreas Schreiner
 */

import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connector Class for  MYSQL/MariaDB
 */
public class MYSQLConnector extends SQLConnector{


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
