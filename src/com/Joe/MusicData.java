package com.Joe;

import javax.swing.*;
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
    public boolean insert_Record_To_Table(String artistName,String recordTitle,double price, String consignerName){
        PreparedStatement ps = null;
        try{

            //insert into record_catalog (Artist_Name,Album_Title,Price,Shelved_Date,Sold_Or_Not,consigners_C_ID) Values('Dosh','Tommy',20.75,'2015-03-15',false,5);

            String preparedInsertString="INSERT INTO music_records (record_artist,record_title,selling_price,consigner_name) VALUES(?,?,?,?)";
            resultSet.moveToInsertRow();
            ps=Main.conn.prepareStatement(preparedInsertString);
            ps.setString(1,artistName);
            ps.setString(2,recordTitle);
            ps.setDouble(3,price);
            ps.setString(4,consignerName);
            ps.executeUpdate();
            this.fireTableDataChanged();
            ps.close();
            return true;
        }catch(SQLException se){
            System.out.println(se);
            System.out.println("An error occurred adding a row.");
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
    public boolean insert_Consigner_To_List(String consigner_Name,String address,String E_Mail,String banking_Number){
        PreparedStatement ps = null;
        try{
            String prep_Insert_String="INSERT INTO consigner_info (Consigner_Name,Address,E_Mail,Bank_Account_Number)VALUES(?,?,?)";
            resultSet.moveToInsertRow() ;
            ps=Main.conn.prepareStatement(prep_Insert_String);
            ps.setString(1,consigner_Name);
            ps.setString(2,address);
            ps.setString(3,E_Mail);
            ps.executeUpdate();
            this.fireTableDataChanged();
            ps.close();
            JOptionPane.showMessageDialog(null,"You successfully added "+consigner_Name+", Address: "+address+", E-mail: "+E_Mail+", Routing Number: "+banking_Number+".");
            System.out.println(String.format("You added"));
            return true;
        }catch(SQLException se){
            System.out.println("Insertion of Consigner Information Failed");
            System.out.println(se);
            return false;
        }
    }
}

