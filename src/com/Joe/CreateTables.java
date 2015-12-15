package com.Joe;


import java.sql.*;

public class CreateTables {

    private static final String DB_NAME = "music_shop";
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
    public final static String C_NAME = "consigners_name";
    public final static String SALES_PRICE = "sales_price";
    public final static String SALESPR = "our60percent";
    public final static String C_SALESPR = "c_40percent";
    public final static String RECORD_TITLE = "record_Title";
    public final static String RECORD_ARTIST = "record_Artist";

    public final static String MUSICRECORD_TABLE_NAME = "music_records";
    public final static String PK_COLUMN = "record_id";
    public final static String CONSIGNER_NAMEs = "consigner_names";
    public final static String TITLE_COLUMN = "records_title";
    public final static String RECORDs_ARTIST = "records_artist";
    public final static String SELLING_PRICE = "selling_price";


    public CreateTables() {
        createTable();
        /*loadAllRecordData();
        loadAllConsignerInfo();
        loadAllSalesInfo();*/
    }

    public void createTable() {

        System.out.println("Checking if table exists");
        try {
            if (!ConsignerTableExists()) {
                //Create a table in the database with 4 columns: Consigner name, record title, record artist and selling price
                String createcosignerTableSQL = "CREATE TABLE " + CONSIGNER_TABLE_NAME + " (" + PK_Consigner + " int NOT NULL AUTO_INCREMENT," + CONSIGNER_NAME + " varchar(50), " + CONSIGNER_EMAIL + " varchar(50), " + CONSIGNER_ADDRESS + " varchar(50),  PRIMARY KEY(" + PK_Consigner + "))";
                //System.out.println(createcosignerTableSQL);
                ConnectDB.statement.executeUpdate(createcosignerTableSQL);
                System.out.println("Created consigners_info table");
                insert_into_consigner_info();
            }
            if (!SalesTableExists()){
                String createsalesTableSQL = "CREATE TABLE IF NOT EXISTS sales_table (sales_id INT NOT NULL AUTO_INCREMENT, consigner_name varchar(50),sales_price DOUBLE, our60percent DOUBLE,c_40percent DOUBLE,record_artist varchar(50),record_title varchar(50), PRIMARY KEY (sales_id))";
                //System.out.println(createsalesTableSQL);
                ConnectDB.statement.executeUpdate(createsalesTableSQL);
                System.out.println("Created sales table");
                insert_record_data_into_sales();
            }
            //Create table if it doesn't exists
            if (!RecordTableExists()){
                String createTableSQL = "CREATE TABLE "+ MUSICRECORD_TABLE_NAME+ " ("+ PK_COLUMN + " INT NOT NULL AUTO_INCREMENT,"+ CONSIGNER_NAMEs + "varchar(50), " + TITLE_COLUMN + " varchar(50),"+ RECORDs_ARTIST + " varchar(50),"+ SELLING_PRICE +" double ,PRIMARY KEY("+ PK_COLUMN+"))";
                ConnectDB.statement.executeUpdate(createTableSQL);
                System.out.println("Created music_records table");
                insert_into_record_table();
            }
        } catch (SQLException se) {
            System.out.println(se);
            System.out.println("Error creating Table");
        }
    }
    public boolean insert_into_consigner_info(){
        try {

            //Test data for consigners
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Mark','mark@yahoo.com','4029 Broad Ave N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jimmy','jboi@gmail.com','3029 Hudson Ave N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('James','james@gmail.com','252 Knock Ave N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Alex','Al@hotmail.com','6th Street S');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Lious','Lu@yahoo.com','7th Street N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Michelle','Mich@gmail.com','3rd Street W');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jason','Jas@yahoo.com','3000 Oliver Ave N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Charlie','Charles@hotmail.com','254 WestGate N');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Anna','Anne@gmail.com','44th Street S');");
            ConnectDB.statement.executeUpdate("insert into consigner_info(consigner_name, consigner_email, consigner_address) VALUES ('Jamie','Jamie@yahoo.com','767 Dowe Ave S');");
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
            ConnectDB.statement.executeUpdate("insert into sales_table(consigners_name, sales_price, our60percent, c_40percent, record_Artist, record_Title) VALUES ('Terry',30,18,12,'Mariah Carey','Without You')");
            return true;

        }
        catch (SQLException Se){
            System.out.println(Se);
            System.out.println("Error inserting data into table");
        }

        return true;
    }
    public boolean insert_into_record_table(){
        //Can't get the test data to show on the GUI and database
        try{
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('James','Back in Black','AC/DC',20);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Mark','Back in Black','AC/DC',20.50);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Jimmy','Back in Black','AC/DC',20.50);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Alex','Born to Run','Bruce Springsteen',27.99);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Lious','Goodbye Yellow Brick Road','Elton John',30);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Michelle','Tunnel of Love','Bruce Springsteen',21.99);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Jason','The Man Who Sold the World','David Bowe',15);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Charlie','Saturday Night Fever','Various Artists', 10.99);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Anna','Too Dark Park','Skinny Puppy',15);");
            ConnectDB.statement.executeUpdate("insert into music_records(consigner_names, records_title, records_artist, selling_price) VALUES ('Jamie','Slippery When Wet','Bon Jovi',19.99);");

            return true;
        }
        catch (SQLException se){
            System.out.println(se);
            System.out.println("Error adding data to table");
        }
        return false;
    }

    /*public static boolean loadAllConsignerInfo(){
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
    }*/
    public static boolean loadAllSalesInfo(){
        try {
            if (rs != null){
                rs.close();
            }
            String getAllData = "SELECT * FROM " + SALE_TABLE_NAME;
            rs = ConnectDB.statement.executeQuery(getAllData);
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
        ResultSet consignerRs = ConnectDB.statement.executeQuery(CheckTableExists);
        if (consignerRs.next()){
            return true;
        }else {
            return false;
        }

    }
    public boolean SalesTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+ SALE_TABLE_NAME + "'";
        System.out.println(CheckTableExists);
        ResultSet rs = ConnectDB.statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }
    public boolean RecordTableExists() throws SQLException{
        String CheckTableExists = "SHOW TABLES LIKE '"+MUSICRECORD_TABLE_NAME+"'";
        System.out.println(CheckTableExists);
        ResultSet rs = ConnectDB.statement.executeQuery(CheckTableExists);
        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }
}