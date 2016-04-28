package com.Momo;

import com.Momo.RubikCubeDatabase;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Momo Johnson on 4/27/2016.
 */
//Implemention of the AbstractTableModel in order to have the table
public class RubikCubeDataModel extends AbstractTableModel{
    private int rowCount =0;
    private int colCunt =0;
    ResultSet resultSet;

    //A constructor that takes the Resultset as an argument

    public RubikCubeDataModel(ResultSet rs){
        this.resultSet = rs;
       setUP();


    }
    //Setting of the column
    private  void setUP(){
        countRows();
        try {
            colCunt = resultSet.getMetaData().getColumnCount();
        }catch (SQLException ce){
            System.out.println("Error occur in column count "+ce);
        }
    }
//A method that update the Resultset in the database
    public void updateResultsSet(ResultSet newRS){
        resultSet = newRS;
        setUP();
    }



    private void countRows(){
        rowCount =0;
        try {
            resultSet.beforeFirst();
            while (resultSet.next()) {
                rowCount++;
            }
            resultSet.beforeFirst();
        }catch (SQLException ce){
            System.out.println("Error counting rows "+ ce);
        }
    }

    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }

    @Override
    public int getColumnCount() {

        return colCunt;
    }

    @Override
    // A mthod that get the value of row and column in making the talbe
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            resultSet.absolute(rowIndex+1);
            Object o = resultSet.getObject(columnIndex +1);
            return  o.toString();
        }catch (SQLException ce){
            System.out.println("Error "+ ce);
            return ce.toString();
        }

    }
    //A method that delete a row in the database
    public boolean deleteRow(int row){
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            fireTableDataChanged();
            return true;
        }catch (SQLException se){
            System.out.println("Error row wasn't deleted "+ se);
            return false;

        }
    }
//A method that insert a row in the database
    public boolean insertRows(String cube_Solver, double time_In_seconds ){
        try{
            resultSet.moveToCurrentRow();
            resultSet.updateString(RubikCubeDatabase.RUBIKTITLENAME, cube_Solver);
            resultSet.updateDouble(RubikCubeDatabase.TIME_IN_SECONDS, time_In_seconds);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            return true;
        }catch (SQLException ce){
            System.out.println(ce);
            return false;

        }
    }

    @Override
    //A method that get the name of the coumn
    public String getColumnName(int col){
        try{
            return resultSet.getMetaData().getColumnName(col+1);

        }catch (SQLException se){
            se.printStackTrace();
            return "?";
        }
    }




}
