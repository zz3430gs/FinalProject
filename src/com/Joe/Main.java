package com.Joe;

import java.sql.*;

public class Main {
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "music_shop";
    private static final String USER = "root";
    private static final String PASS = "038292Jl";

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    public final static String MUSICRECORD_TABLE_NAME = "music_records";
    public final static String PK_COLUMN = "record_id";
    public final static String CONSIGNER_NAME = "consigner_name";
    public final static String TITLE_COLUMN = "record_title";
    public final static String RECORD_ARTIST = "record_artist";
    public final static String SELLING_PRICE = "selling_price";

    private static MusicData musicDataModel;

    public static void main(String[] args) {

        CreateTables ct = new CreateTables();

        if (!setup()) {
            System.exit(-1);
        }

        if (!loadAllMusics()) {
            System.exit(-1);
        }

        //If no errors, then start GUI
        MusicRecordGUI tableGUI = new MusicRecordGUI(musicDataModel);

    }

    //Create or recreate a ResultSet containing the whole database, and give it to musicDataModel
    public static boolean loadAllMusics() {

        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + MUSICRECORD_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            if (musicDataModel == null) {
                //If there's no current MusicDataModel then create one
                musicDataModel = new MusicData(rs);
            }
                return true;
        } catch (Exception e) {
            System.out.println("Error loading or reloading musics");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setup(){
        try {

            //Load driver class
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("No database drivers found. Quitting");
                return false;
            }

            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
            //Coding from Clara's MOVIERATING project
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //Create table if the table does not exist
            if (!musicTableExists()) {

                //Create a table in the database with 4 columns: Consigner name, record title, record artist and selling price
                String createTableSQL = "CREATE TABLE " + MUSICRECORD_TABLE_NAME + " (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT," + CONSIGNER_NAME + " varchar(50), " + TITLE_COLUMN + " varchar(50), " + RECORD_ARTIST + " varchar(50), " + SELLING_PRICE + " int, PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);

                System.out.println("Created music_records table");

                String addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "("+ CONSIGNER_NAME +", " + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")";
                System.out.println(addDataSQl);
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('James','Dark Side of the Moon','Pink Floyd',20)";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Mark','Back in Black','AC/DC',20.50)";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Jimmy','Back in Black','AC/DC',20.50);";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Alex','Born to Run','Bruce Springsteen',27.99);";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES ('Lious','Goodbye Yellow Brick Road','Elton John',30);";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Michelle','Tunnel of Love','Bruce Springsteen',21.99);";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Jason','The Man Who Sold the World','David Bowe',15);";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Charlie','Saturday Night Fever','Various Artists', 10.99)";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Anna','Too Dark Park','Skinny Puppy',15)";
                statement.executeUpdate(addDataSQl);
                addDataSQl = "INSERT INTO" + MUSICRECORD_TABLE_NAME + "(" + TITLE_COLUMN + ", " + RECORD_ARTIST + ", " + SELLING_PRICE + ")" + " VALUES('Jamie','Slippery When Wet','Bon Jovi',19.99)";
                statement.executeUpdate(addDataSQl);

            }

            return true;

        } catch (SQLException se) {
            System.out.println(se);
            se.printStackTrace();
            return false;
        }

    }

    private static boolean musicTableExists() throws SQLException {

    String checkTablePresentQuery = "SHOW TABLES LIKE '" + MUSICRECORD_TABLE_NAME + "'";   //Can query the database schema
        ResultSet tablesRS = statement.executeQuery(checkTablePresentQuery);
        if (tablesRS.next()) {    //If ResultSet has a next row, it has at least one row... that must be our table
            return true;
        }
        return false;

    }

    //Close the ResultSet, statement and connection, in that order.
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