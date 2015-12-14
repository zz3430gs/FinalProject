package com.Joe;


import java.sql.*;

public class CreateTables {

    private static MusicData musicDataModel;
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "music_shop";
    private static final String USER = "root";
    private static final String PASS = "038292Jl";
    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    public final static String CONSIGNER_TABLE_NAME = "consigner_info";
    public final static String PK_Consigner = "consigner_id";
    public final static String CONSIGNER_NAME = "consigner_name";
    public final static String CONSIGNER_EMAIL = "consigner_email";
    public final static String CONSIGNER_ADDRESS = "consigner_address";

    public final static String SALE_TABLE_NAME = "sales_table";
    public final static String PK_SALE = "sales_id";
    public final static String C_NAME = "consigner_name";
    public final static String SALESPR = "our60%";
    public final static String C_SALESPR = "c_40%";
    public final static String RECORD_TITLE = "record_title";
    public final static String RECORD_ARTIST = "record_artist";


    public CreateTables() {
        createTable();
    }

    public void createTable() {


        System.out.println("Checking if table exists");
        try {
            if (!ConsignerTableExists()) {
                //Create a table in the database with 4 columns: Consigner name, record title, record artist and selling price
                String createTableSQL = "CREATE TABLE " + CONSIGNER_TABLE_NAME + " (" + PK_Consigner + " int NOT NULL AUTO_INCREMENT," + CONSIGNER_NAME + " varchar(50), " + CONSIGNER_EMAIL + " varchar(50), " + CONSIGNER_ADDRESS + " varchar(50),  PRIMARY KEY(" + PK_Consigner + "))";
                Main.statement.executeUpdate(createTableSQL);
                insert_into_consigner_info();
                System.out.println("Created consigners_info table");
            }
            if (!SalesTableExists()){
                String createTableSQL = "CREATE TABLE " + SALE_TABLE_NAME + " (" + PK_SALE + "int NOT NULL AUTO_INCREMENT," + C_NAME + " varchar(50)," + SALESPR + "double," + C_SALESPR + "double," + RECORD_ARTIST + "varchar(50)," + RECORD_TITLE+ "varchar(50)";
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error creating Table");
        }
    }



    public boolean ConsignerTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+ CONSIGNER_TABLE_NAME + "'";
        System.out.println(CheckTableExists);
        ResultSet rs = statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }

    }
    public boolean SalesTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+ SALE_TABLE_NAME + "'";
        System.out.println(CheckTableExists);
        ResultSet rs = statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }
}