package FaervelNaweh.sql.connector;/*
 * @author Andreas Schreiner
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import java.sql.*;

/**
 * Abstract Connector Class
 */
public abstract class SQLConnector {


    public abstract String type();
    public abstract ObservableList<ObservableList> getTableNames() throws SQLException;

    /**
     * The SQL Connection
     */
    protected Connection connect = null;

    /**
     * Executes SQL Command if connection is open
     *
     * @param sqlcmd
     */
    public void shell(String sqlcmd) {
        try {
            Statement st = connect.createStatement();
            st.execute(sqlcmd);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Executes SQL Command if connection is open
     * Retrieves Query Information
     *
     * @param sqlcmd
     * @return a ResultSet Containing the Result of a Query
     */
    public ResultSet shellRS(String sqlcmd) {
        try {
            Statement st = connect.createStatement();
            return st.executeQuery(sqlcmd);
        } catch (SQLException e) {
            return null;
        }

    }

    /**
     * Closes and finalizes the Connection
     *
     * @throws Exception
     */
    public void close() throws Exception {
        try {
            connect.close();
            this.finalize();
        } catch (Throwable e) {
        }
    }
    @Getter
    public boolean isOpen() {
        try {
            if (connect == null) {
                return false;
            } else if (connect.isClosed()) {
                return false;
            }
        } catch (SQLException e) {
        }
        return true;
    }
    public ObservableList<ObservableList> getRows(String SQLCmd) throws SQLException {
        ResultSet rs = shellRS(SQLCmd);
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

    public TableColumn[] getColumns(String SQLCmd) throws SQLException {
        ResultSet rs = shellRS(SQLCmd);
        TableColumn tc[] =  new TableColumn[rs.getMetaData().getColumnCount()];
        for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //We are using non property style for making dynamic table
            final int j = i;

            TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    return new SimpleStringProperty(param.getValue().get(j).toString());
                }
            });

            tc[i] = col;
        }
        return tc;
    }


}

