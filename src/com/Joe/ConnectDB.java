package com.Joe;

import java.sql.*;

public class ConnectDB {

    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static String JDBC_Driver = "com.mysql.jdbc.Driver";
    private static final String DB_NAME = "music_shop";
    private static final String USER = "root";
    private static final String PASS = "038292Jl";

    static Statement statement = null;
    static Statement statementforSales = null;
    static Statement statementforRecords= null;
    static Statement statementforConsigners= null;
    static Connection conn = null;
    static ResultSet rs = null;
    static ResultSet rsForRecordTab = null;
    static ResultSet rs2 = null;

    static MusicData Consigner_info_Display = null;
    static MusicData musicRecord = null;
    static MusicData sales_record = null;

    public ConnectDB() {
        try {
            try {
                Class.forName(JDBC_Driver);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("No database drivers found! Exiting the program.");
                System.out.println(cnfe);
            }
            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statementforConsigners= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statementforRecords= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            statementforSales= conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            CreateTables createtable = new CreateTables();
            loadAllRecordData();
            loadAllConsignerInfo();
            loadAllSalesInfo();

            createRecordModel();
            createConsignerModel();
            CreateSaleModel();

            Consigner_Info consigner_info = new Consigner_Info(Consigner_info_Display);
            MusicRecordGUI musicRecordGUI = new MusicRecordGUI(musicRecord);
            SalesRecords salesRecords = new SalesRecords(sales_record);

           // MainGui mainGui = new MainGui(musicRecord, Consigner_info_Display, sales_record);
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("From ConnectDB");

        }
    }

    public static void shutdownResources() throws SQLException {
        statement.close();
        statementforRecords.close();
        statementforSales.close();
        statementforConsigners.close();
        conn.close();
        rs.close();
        rsForRecordTab.close();
        rs2.close();

    }

    public static boolean loadAllConsignerInfo() {
        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + CreateTables.CONSIGNER_TABLE_NAME + ";";
            rs = ConnectDB.statementforConsigners.executeQuery(getAllData);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading Consigner data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loadAllSalesInfo() {
        try {
            if (rs != null) {
                rs.close();
            }
            String getAllData = "SELECT * FROM " + CreateTables.SALE_TABLE_NAME + ";";
            rs = ConnectDB.statementforSales.executeQuery(getAllData);
            return true;
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error loading sales data");
            return false;
        }
    }

    public static boolean loadAllRecordData() {
        try {
            if (rs != null) {
                rs.close();
            }
            String getAllData = "SELECT * FROM " + CreateTables.MUSICRECORD_TABLE_NAME + ";";
            rs = ConnectDB.statementforRecords.executeQuery(getAllData);
            return true;
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error loading record data");
            return false;
        }
    }

    public static void createRecordModel() {
        try {
            if (rsForRecordTab != null) {
                rsForRecordTab.close();
            }
            if (musicRecord == null) {
                System.out.println("The data model was null, making new record catalog model...");
                rsForRecordTab = ConnectDB.statementforRecords.executeQuery("SELECT * FROM music_records");
                musicRecord = new MusicData(rsForRecordTab);

            } else {
                System.out.println("Found the data model!!!");
                musicRecord.updateResultSet(rsForRecordTab);
            }
        } catch (SQLException se) {
            System.out.println("Error with Creating recordmodel");
            System.out.println(se);
        }
    }
    public static void createConsignerModel() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (Consigner_info_Display == null) {
                System.out.println("The data model was null, making new consigner model...");
                rs = ConnectDB.statementforConsigners.executeQuery("SELECT * FROM consigner_info");
                Consigner_info_Display = new MusicData(rs);

            } else {
                System.out.println("Found the data model");
                Consigner_info_Display.updateResultSet(rs);
            }
        } catch (SQLException se) {
            System.out.println("Error with Create RecordCatalog Method has ocurred.");
            System.out.println(se);
        }
    }
    public static void CreateSaleModel() {
        try {
            if (rs2 != null) {
                rs2.close();
            }
            if (sales_record == null) {
                System.out.println("The data model was null, making new sales model...");
                rs2 = ConnectDB.statementforSales.executeQuery("SELECT * FROM sales_table");
                sales_record = new MusicData(rs2);

            } else {
                System.out.println("Found the data model");
                sales_record.updateResultSet(rs2);
            }
        } catch (SQLException se) {
            System.out.println("Error with sales model.");
            System.out.println(se);
        }
    }

    public static void shutdown(){
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
            else{

            }
        } catch (SQLException se){
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
            else{
                return;
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
