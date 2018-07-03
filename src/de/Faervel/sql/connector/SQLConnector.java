/*
 * @author Andreas Schreiner
 */
package de.Faervel.sql.connector;
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
    Connection connect = null;
    public abstract ObservableList<ObservableList> getTableNames() throws SQLException;
    /**
     * Executes SQL Command if connection is open
     *
     * @param sqlCommandString SQL String
     */
    public void shell(String sqlCommandString) throws SQLException {
        Statement st = connect.createStatement();
        st.execute(sqlCommandString);
    }
    /**
     * Executes SQL Command if connection is open
     * Retrieves Query Information
     *
     * @param sqlCommandString SQL String
     * @return a ResultSet Containing the Result of a Query
     */
    public ResultSet shellRS(String sqlCommandString) throws SQLException {
        Statement st = connect.createStatement();
        return st.executeQuery(sqlCommandString);
    }
    /**
     * Closes and finalizes the Connection
     *
     * @throws SQLException Wirft exception wenn null
     */
    public void close() throws SQLException {
            connect.close();
    }
    @Getter
    public boolean isOpen() throws SQLException {
            if (connect == null) {
                return false;
            } else return !connect.isClosed();
    }

    public ObservableList<ObservableList> getRows(String sqlCommandString) throws SQLException {
        ResultSet rs = shellRS(sqlCommandString);
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
        ResultSet rs = shellRS(sqlCommandString);
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