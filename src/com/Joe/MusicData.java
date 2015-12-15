package com.Joe;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.*;

public class MusicData extends AbstractTableModel {

    private int rowCount = 0;
    private int colCount = 0;
    ResultSet resultSet;
    //Statement statement;
    //Connection conn;

    public MusicData(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }
    private void setup(){

        countRows();
    }

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

//Help from malcolms project
    public boolean sellRecord(String record_ID){
        PreparedStatement ps = null;
        int r_id=Integer.parseInt(record_ID);
        String sell_Record_SQL="UPDATE music_records WHERE record_id = ? ;";
        try{
            ps=ConnectDB.conn.prepareStatement(sell_Record_SQL);
            ps.setInt(1,r_id);
            ps.executeUpdate();
            this.fireTableDataChanged();
            return true;
        }catch(SQLException se){
            System.out.println("An error occurred when selling the record.");
            System.out.println(se);
            return false;
        }
    }
    public boolean insert_Record_To_Table(String artistName,String recordTitle,double price, String consignerName){
        PreparedStatement ps = null;
        try{
            String preparedInsertString="INSERT INTO music_records (record_artist,record_title,selling_price,consigner_name) VALUES(?,?,?,?)";
            resultSet.moveToInsertRow();
            ps=ConnectDB.conn.prepareStatement(preparedInsertString);
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

    public boolean insert_Consigner_To_List(String consigner_Name,String address,String E_Mail){
        PreparedStatement ps = null;
        try{
            String prep_Insert_String="INSERT INTO consigner_info (Consigner_Name,Address,E_Mail,)VALUES(?,?,?)";
            resultSet.moveToInsertRow() ;
            ps=ConnectDB.conn.prepareStatement(prep_Insert_String);
            ps.setString(1,consigner_Name);
            ps.setString(2,address);
            ps.setString(3,E_Mail);
            ps.executeUpdate();
            this.fireTableDataChanged();
            ps.close();
            JOptionPane.showMessageDialog(null,"Added "+consigner_Name+", Address: "+address+", E-mail: "+E_Mail+"");
            System.out.println(String.format("You added"));
            return true;
        }catch(SQLException se){
            System.out.println("Error inserting Consigner information");
            System.out.println(se);
            return false;
        }
    }
    public boolean insert_into_Sales(String consignerName, double price, String recordArtist, String recordTitle ){
        PreparedStatement pS = null;
        double saleX60 = price * .6;
        double saleX40 = price * .4;
        try {
            String prep_insert_string = "INSERT INTO sales_table(consignerName, price, my60percemt, c_40percent, recordArtist, recordTitle) VALUES (?,?,?,?,?,?)";
            resultSet.moveToInsertRow();
            pS=ConnectDB.conn.prepareStatement(prep_insert_string);
            pS.setString(1, consignerName);
            pS.setDouble(2, price);
            pS.setDouble(3, saleX60);
            pS.setDouble(4, saleX40);
            pS.setString(5, recordArtist);
            pS.setString(6, recordTitle);
            pS.executeUpdate();
            this.fireTableDataChanged();
            pS.close();
            JOptionPane.showMessageDialog(null,"Added "+ consignerName + ", price: "+ price + " my60%: "+ saleX60 + " consigner40%:" + saleX40+ " record artist: "+ recordArtist + " record titel: "+ recordTitle);
            return true;

        }
        catch (SQLException se){
            System.out.println("Insertion of sales information failed");
            return false;
        }
    }
}

