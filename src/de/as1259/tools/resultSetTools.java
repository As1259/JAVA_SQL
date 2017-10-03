/*
 * @author Andreas Schreiner
 * @version 1.0
 */

package de.as1259.tools;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
@Deprecated
public class resultSetTools {
    public static TableColumn[] getColumns(ResultSet rs) throws SQLException {
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
    public static ObservableList<ObservableList> getRows(ResultSet rs) throws SQLException {
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
}
