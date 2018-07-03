/*
 * @author Andreas Schreiner
 */
package de.Fearvel.sql.connector;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.annotations.Getter;
import java.sql.*;
/**
 * Abstract Connector Class
 */
abstract class SQLConnector {
    /**
     * The SQL Connection
     */
    protected Connection connection = null;
    public abstract ObservableList<ObservableList> getTableNames() throws SQLException;
    /**
     * Executes SQL NonQuery if connection is open
     *
     * @param sqlCommandString SQL String
     */
    public void NonQuery(String sqlCommandString) throws SQLException {
        Statement st = connection.createStatement();
        st.execute(sqlCommandString);
    }

    /**
     * Executes SQL NonQuery if connection is open
     *
     * @param ps prepared statement
     */
    public void NonQuery(PreparedStatement ps) throws SQLException {
        ps.execute();
    }
    /**
     * Executes SQL Query if connection is open
     * Retrieves Query Information
     *
     * @param sqlCommandString SQL String
     * @return a ResultSet Containing the Result of a Query
     */
    public ResultSet Query(String sqlCommandString) throws SQLException {
        Statement st = connection.createStatement();
        return st.executeQuery(sqlCommandString);
    }
    /**
     * Executes SQL Query if connection is open
     * Retrieves Query Information
     *
     * @param ps prepared statement
     * @return a ResultSet Containing the Result of a Query
     */
    public ResultSet Query(PreparedStatement ps) throws SQLException {
        return ps.executeQuery();
    }

    /**
     * Allows prepared statements
     *
     * @param query sql string for creation of the prepared statement
     * @return A prepared statement based on a sql String
     */
    public PreparedStatement GetPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }
    /**
     * Closes and finalizes the Connection
     *
     * @throws SQLException Wirft exception wenn null
     */
    public void close() throws SQLException {
            connection.close();
    }
    @Getter
    public boolean isOpen() throws SQLException {
            if (connection == null) {
                return false;
            } else return !connection.isClosed();
    }

    public ObservableList<ObservableList> getRows(String sqlCommandString) throws SQLException {
        ResultSet rs = Query(sqlCommandString);
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        while(rs.next()){
            //Iterate Row
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                //Iterate Column
                if (rs.getString(i) !=  null) {
                    row.add(rs.getString(i));
                } else{
                    row.add("");
                }
            }
            data.add(row);
        }
        return data;
    }

    public TableColumn[] getColumns(String sqlCommandString) throws SQLException {
        ResultSet rs = Query(sqlCommandString);
        TableColumn tc[] =  new TableColumn[rs.getMetaData().getColumnCount()];
        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;
            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(
                    (Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                            param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            tc[i] = col;
        }
        return tc;
    }
}