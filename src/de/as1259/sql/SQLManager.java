/*
 * @author Andreas Schreiner
 * @version 1.0
 */

package de.as1259.sql;

import de.as1259.sql.connectors.SQLConnector;

public class SQLManager {
    public static de.as1259.sql.connectors.SQLConnector[] sqlConn = new SQLConnector[0];

    public static de.as1259.sql.connectors.SQLConnector getConnection(int pos) {
        if (pos <= sqlConn.length && sqlConn[pos] != null) {
            if (sqlConn[pos].isOpen()) {
                return sqlConn[pos];
            }
        }
        return null;
    }

    public static de.as1259.sql.connectors.SQLConnector[] getConnectionByName(String Name) {
        de.as1259.sql.connectors.SQLConnector[] sqlConnN;

        int counter = 0;
        for (int i = 0; i < sqlConn.length; i++) {
            if (sqlConn[i].getConnectionName().compareTo(Name) == 0) {
                counter++;
            }
        }
        sqlConnN  = new SQLConnector[counter];
        for (int i = 0; i < sqlConn.length; i++) {
            if (sqlConn[i].getConnectionName().compareTo(Name) == 0) {
                if (counter >0) {
                    sqlConnN[(sqlConnN.length - 1) - (counter - 1)] = sqlConn[0];
                    counter--;
                }
            }
        }
        return sqlConnN;
    }

    public static int addConnection(de.as1259.sql.connectors.SQLConnector con) {
        if (con != null) {
            if (con.isOpen()) {
                enlargeArray();
                sqlConn[sqlConn.length - 1] = con;
                return sqlConn.length - 1;
            }
        }
        return -1;
    }

    public static void Close(int pos) {
        deletePos(pos);
    }

    public static int Count() {
        return sqlConn.length;
    }

    public static int CountOpen() {
        int open = 0;
        for (int i = 0; i < sqlConn.length; i++) {
            if (sqlConn[i] != null) {
                if (sqlConn[i].isOpen()) {
                    open++;
                }
            }
        }
        return open;
    }

    private static void enlargeArray() {
        de.as1259.sql.connectors.SQLConnector[] sqlConnN = new SQLConnector[sqlConn.length + 1];
        for (int i = 0; i < sqlConn.length; i++) {
            sqlConnN[i] = sqlConn[i];
        }
        sqlConn = sqlConnN;
    }

    private static void deletePos(int p) {
        de.as1259.sql.connectors.SQLConnector[] sqlConnN = new SQLConnector[sqlConn.length - 1];
        for (int i = 0; i < sqlConnN.length; i++) {
            if (i < p) {
                sqlConnN[i] = sqlConn[i];
            } else if (i >= p) {
                sqlConnN[i] = sqlConn[i + 1];
            }
        }
        sqlConn = sqlConnN;
    }

}
