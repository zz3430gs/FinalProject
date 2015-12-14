package com.Joe;


import java.sql.*;

public class CreateTables {

    static Statement statement = null;
    static Statement statement2 = null;
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
    public final static String SALES_PRICE = "sales_price";
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
                String createcosignerTableSQL = "CREATE TABLE IF NOT EXISTS " + CONSIGNER_TABLE_NAME + " (" + PK_Consigner + " int NOT NULL AUTO_INCREMENT," + CONSIGNER_NAME + " varchar(50), " + CONSIGNER_EMAIL + " varchar(50), " + CONSIGNER_ADDRESS + " varchar(50),  PRIMARY KEY(" + PK_Consigner + "))";
                //System.out.println(createcosignerTableSQL);
                statement.executeUpdate(createcosignerTableSQL);
                insert_into_consigner_info();
                System.out.println("Created consigners_info table");
            }
            if (!SalesTableExists()){
                String createsalesTableSQL = "CREATE TABLE IF NOT EXISTS " + SALE_TABLE_NAME + " (" + PK_SALE + "int NOT NULL AUTO_INCREMENT," + C_NAME + " varchar(50),"+SALES_PRICE + "double" + SALESPR + "double," + C_SALESPR + "double," + RECORD_ARTIST + "varchar(50)," + RECORD_TITLE+ "varchar(50)";
                statement2.executeUpdate(createsalesTableSQL);
                insert_record_data_into_sales();
                System.out.println("Created sales table");
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error creating Table");
        }
    }
    public boolean insert_into_consigner_info(){
        try {

            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Mark','mark@yahoo.com','4029 Broad Ave N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jimmy','jboi@gmail.com','3029 Hudson Ave N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('James','james@gmail.com','252 Knock Ave N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Alex','Al@hotmail.com','6th Street S');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Lious','Lu@yahoo.com','7th Street N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Michelle','Mich@gmail.com','3rd Street W');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jason','Jas@yahoo.com','3000 Oliver Ave N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Charlie','Charles@hotmail.com','254 WestGate N');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Anna','Anne@gmail.com','44th Street S');");
            statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jamie','Jamie@yahoo.com','767 Dowe Ave S');");
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
            statement2.executeUpdate("insert into sales_table(consigner_name, sales_price, our60%, c_40%, record_artist, record_title) VALUES ('Terry',30,18,12,'Mariah Carey','Without You')");
            return true;

        }
        catch (SQLException Se){
            System.out.println(Se);
            System.out.println("Error inserting data into table");
        }

        return true;
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
        ResultSet rs = statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }
}