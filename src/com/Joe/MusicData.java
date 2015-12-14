package com.Joe;

import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class MusicData extends AbstractTableModel {

    private int rowCount = 0;
    private int colCount = 0;
    ResultSet resultSet;
    Statement statement;
    Connection conn;

    public MusicData(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    private void setup(){
        countRows();
    }
    /*private void setup() {

        countRows();

        try {
            colCount = resultSet.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }*/

    /*private void countRows() {
        rowCount = 0;
        try {
            //Move cursor to the start...
            resultSet.beforeFirst();
            // next() method moves the cursor forward one row and returns true if there is another row ahead
            while (resultSet.next()) {
                rowCount++;

            }
            resultSet.beforeFirst();

        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
        }

    }

    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }
    @Override
    public int getColumnCount() {
        try {
            colCount = resultSet.getMetaData().getColumnCount();
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error created by get column count");
        }
        return colCount;
    }*/
    private void countRows(){
        rowCount=0;
        try{
            resultSet.beforeFirst();
            while (resultSet.next()){
                rowCount++;
            }
            resultSet.beforeFirst();
        }catch(SQLException se){
            System.out.println("An Error Occurred Counting Rows. "+se);
        }
    }


    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        try{
            colCount=resultSet.getMetaData().getColumnCount();
        }catch(SQLException se){
            System.out.println(se);
            System.out.println("Error created by get column count");
        }
        return colCount;
    }


    @Override
    public Object getValueAt(int row, int col) {
        try {
            //  System.out.println("get value at, row = " +row);
            resultSet.absolute(row + 1);
            Object o = resultSet.getObject(col + 1);
            return o.toString();
        } catch (SQLException se) {
            System.out.println(se);
            //se.printStackTrace();
            return se.toString();

        }
    }


    //Delete row, return true if successful, false otherwise
    public boolean deleteRow(int row) {
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        } catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }

    public void updateResultSet(ResultSet newRS){
        resultSet = newRS;
        setup();
    }

    public boolean insertRecordRow(String consignerName, String title, String artist, int sellingPrice) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();
            resultSet.updateString(Main.CONSIGNER_NAME, consignerName);
            resultSet.updateString(Main.TITLE_COLUMN, title);
            resultSet.updateString(Main.RECORD_ARTIST, artist);
            resultSet.updateInt(Main.SELLING_PRICE, sellingPrice);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }
    }

    public boolean insertCosignerRow(String consignerName, String consignerEmail, String address) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();
            resultSet.updateString(CreateTables.CONSIGNER_NAME, consignerName);
            resultSet.updateString(CreateTables.CONSIGNER_EMAIL, consignerEmail);
            resultSet.updateString(CreateTables.CONSIGNER_ADDRESS, address);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }
    /**public void search(String selField, String searchString, int tabIndex) {
         The ideas behind this method are entirely thanks to the genius of Anna
         Coding is copied from Malcolm's project

        if(tabIndex==0){
            if (selField.equals("Default")) {
                try {
                    this.resultSet = statement.executeQuery("SELECT * FROM " + Main.MUSICRECORD_TABLE_NAME + " WHERE Main.CONSIGNER_NAME = *; ");
                }catch(SQLException se){
                    System.out.println("Error resetting to default results");
                    System.out.println(se);
                }
            } else {

                String sqlToRun = "SELECT * FROM music_records WHERE " +
                        selField + " LIKE ?";
                PreparedStatement ps = null;
                try {
                    ps = conn.prepareStatement(sqlToRun);
                    ps.setString(1, "%" + searchString + "%");
                    this.resultSet = ps.executeQuery();
                } catch (SQLException sqle) {
                    System.out.println("Unable to fetch search results.");
                }
            }
            this.fireTableDataChanged();
        }//Search the consigners table
        else if(tabIndex==1){
            if(selField.equals("Default")){
                try{
                    this.resultSet = statement.executeQuery("SELECT * FROM consigner_info");
                    this.fireTableDataChanged();
                }catch(SQLException se){
                    System.out.println("An error ocurred while trying reset to default search.");
                    System.out.println(se);
                }
            }else {
                String sqlToRun="SELECT * FROM consigner_info WHERE "+selField+" LIKE ?";
                PreparedStatement ps = null;
                try{
                    ps=conn.prepareStatement(sqlToRun);
                    ps.setString(1,"%"+searchString+"%");
                    this.resultSet=ps.executeQuery();
                }catch(SQLException se){
                    System.out.println("Unable to fetch search results");
                    System.out.println(se);
                }
            }
            this.fireTableDataChanged();
        }
        else if(tabIndex==2){
            //this is the sold records updater
            if(selField=="Default"){
                try{
                    this.resultSet= statement.executeQuery("SELECT * FROM music_records");
                    this.fireTableDataChanged();
                }catch (SQLException se){
                    System.out.println(se);

                }
            }
        }
        else if(tabIndex==3){
            if(selField=="Default"){
                try{

                    this.resultSet=statement.executeQuery("SELECT * FROM consignerSales");
                    this.fireTableDataChanged();

                }catch (SQLException se){
                    System.out.println(se);

                }
            }
        }
        else if(tabIndex==4){
            if(selField=="Default"){
                try {
                    this.resultSet = statement.executeQuery("Select * FROM sales_table;");
                    this.fireTableDataChanged();
                }catch(SQLException se){
                    System.out.println(se);
                }
            }
        }
    }
    public void searchPrice(String selField, double searchPrice) {
       The ideas behind this method are entirely thanks to the genius of Anna
        String sqlToRun = "SELECT * FROM record_catalog WHERE " +
                selField + " <= ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sqlToRun);
            ps.setDouble(1, searchPrice);
            this.resultSet = ps.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("Unable to fetch search results.");
            System.out.println(sqle);
        }

        this.fireTableDataChanged();
    }*/
}

