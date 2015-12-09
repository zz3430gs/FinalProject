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

    private static MusicDataModel musicDataModel;

    public static void main(String[] args) {

        if (!setup()) {
            System.exit(-1);
        }

        if (!loadAllMusics()) {
            System.exit(-1);
        }

        //If no errors, then start GUI
        MusicRecordGUI tableGUI = new MusicRecordGUI(musicDataModel);

    }

    //Create or recreate a ResultSet containing the whole database, and give it to movieDataModel
    public static boolean loadAllMusics() {

        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + MUSICRECORD_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            if (musicDataModel == null) {
                //If there's no current MusicDataModel then create one
                musicDataModel = new MusicDataModel(rs);
            }
                return true;
        } catch (Exception e) {
            System.out.println("Error loading or reloading movies");
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
                String createTableSQL = "CREATE TABLE " + MUSICRECORD_TABLE_NAME + " (" + PK_COLUMN + " int NOT NULL AUTO_INCREMENT,"+CONSIGNER_NAME +" varchar(50), " + TITLE_COLUMN + " varchar(50), " + RECORD_ARTIST + " String, " + SELLING_PRICE + " int, PRIMARY KEY(" + PK_COLUMN + "))";
                System.out.println(createTableSQL);
                statement.executeUpdate(createTableSQL);

                System.out.println("Created music_records table");
                // Add some test data - change to some movies you like, if desired
                //Example SQL: INSERT INTO movie_reviews ( title, year_released, rating ) VALUES ( 'Back to the future', 1985, 5)
                //Here we have to specify which columns the data will go into, because we want to omit the ID column and have MySQL fill it in for us.
                //But, since we are only adding 3 pieces of data for 4 columns, we have to specify which columns each data item is for.
                /*String addDataSQL = "INSERT INTO " + MOVIE_TABLE_NAME + "(" + TITLE_COLUMN + ", " + YEAR_COLUMN + ", " + RATING_COLUMN + ")" + " VALUES ('Back to the future', 1985, 5)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + MOVIE_TABLE_NAME +  "(" + TITLE_COLUMN + ", " + YEAR_COLUMN + ", " + RATING_COLUMN + ")" + " VALUES('Back to the Future II', 1989, 4)";
                statement.executeUpdate(addDataSQL);
                addDataSQL = "INSERT INTO " + MOVIE_TABLE_NAME +  "(" + TITLE_COLUMN + ", " + YEAR_COLUMN + ", " + RATING_COLUMN + ")" + " VALUES ('Back to the Future III', 1990, 3)";
                statement.executeUpdate(addDataSQL);*/
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
        } catch (SQLException se){
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        }
        catch (SQLException se) {
            se.printStackTrace();
        }
    }
}