package com.Joe;


import java.sql.*;

public class CreateTables {

    //static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;
    static ResultSet saleRS = null;

    public final static String CONSIGNER_TABLE_NAME = "consigner_info";
    public final static String PK_Consigner = "consigner_id";
    public final static String CONSIGNER_NAME = "consigner_name";
    public final static String CONSIGNER_EMAIL = "consigner_email";
    public final static String CONSIGNER_ADDRESS = "consigner_address";

    public final static String SALE_TABLE_NAME = "sales_table";
    public final static String PK_SALE = "sales_id";
    public final static String C_NAME = "consigner_name";
    public final static String SALES_PRICE = "sales_price";
    public final static String SALESPR = "our60%";
    public final static String C_SALESPR = "c_40%";
    public final static String RECORD_TITLE = "record_title";
    public final static String RECORD_ARTIST = "record_artist";


    public CreateTables() {
        createTable();
       // loadAllRecordData();
        loadAllConsignerInfo();
        loadAllSalesInfo();
    }

    public void createTable() {

        System.out.println("Checking if table exists");
        try {
            if (!ConsignerTableExists()) {
                //Create a table in the database with 4 columns: Consigner name, record title, record artist and selling price
                String createcosignerTableSQL = "CREATE TABLE " + CONSIGNER_TABLE_NAME + " (" + PK_Consigner + " int NOT NULL AUTO_INCREMENT," + CONSIGNER_NAME + " varchar(50), " + CONSIGNER_EMAIL + " varchar(50), " + CONSIGNER_ADDRESS + " varchar(50),  PRIMARY KEY(" + PK_Consigner + "))";
                System.out.println(createcosignerTableSQL);
                Main.statement.executeUpdate(createcosignerTableSQL);
                //insert_into_consigner_info();
                System.out.println("Created consigners_info table");
                insert_into_consigner_info();
            }
            if (!SalesTableExists()){
            String createsalesTableSQL = "CREATE TABLE IF NOT EXISTS sales_tables (sales_id int NOT NULL AUTO_INCREMENT, consigner_name varchar(50),sales_price double, our60% double,c_40% double,record_artist varchar(50),record_title varchar(50), PRIMARY KEY (sales_id))";
                System.out.println(createsalesTableSQL);
                Main.statement.executeUpdate(createsalesTableSQL);
                //insert_record_data_into_sales();
                System.out.println("Created sales table");
                insert_record_data_into_sales();
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error creating Table");
        }
    }
    public boolean insert_into_consigner_info(){
        try {

            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Mark','mark@yahoo.com','4029 Broad Ave N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jimmy','jboi@gmail.com','3029 Hudson Ave N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('James','james@gmail.com','252 Knock Ave N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Alex','Al@hotmail.com','6th Street S');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Lious','Lu@yahoo.com','7th Street N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Michelle','Mich@gmail.com','3rd Street W');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jason','Jas@yahoo.com','3000 Oliver Ave N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Charlie','Charles@hotmail.com','254 WestGate N');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Anna','Anne@gmail.com','44th Street S');");
            Main.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jamie','Jamie@yahoo.com','767 Dowe Ave S');");
        return true;
        }
        catch (SQLException se){
            System.out.println(se);
            System.out.println("Error inserting data into table");
            return false;
        }
    }
    public boolean insert_record_data_into_sales(){
        //Test data for table not belong to consigner
        try {
            Main.statement.executeUpdate("insert into sales_table(consigner_name, sales_price, our60%, c_40%, record_artist, record_title) VALUES ('Terry',30,18,12,'Mariah Carey','Without You')");
            return true;

        }
        catch (SQLException Se){
            System.out.println(Se);
            System.out.println("Error inserting data into table");
        }

        return true;
    }

    public static boolean loadAllConsignerInfo(){
        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + CONSIGNER_TABLE_NAME;
            rs = Main.statement.executeQuery(getAllData);
            return true;
        } catch (Exception e) {
            System.out.println("Error loading Consigner data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    public static boolean loadAllSalesInfo(){
        try {
            if (rs != null){
                rs.close();
            }
            String getAllData = "SELECT * FROM " + SALE_TABLE_NAME;
            rs = Main.statement.executeQuery(getAllData);
            return true;
        }
        catch (SQLException se){
            System.out.println(se);
            System.out.println("Error loading sales data");
            return false;
        }
    }


    public boolean ConsignerTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+ CONSIGNER_TABLE_NAME + "'";
        ResultSet consignerRs = Main.statement.executeQuery(CheckTableExists);
        if (consignerRs.next()){
            return true;
        }else {
            return false;
        }

    }
    public boolean SalesTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+ SALE_TABLE_NAME + "'";
        System.out.println(CheckTableExists);
        ResultSet rs = Main.statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }
}