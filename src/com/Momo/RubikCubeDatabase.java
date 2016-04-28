package com.Momo;
import java.sql.*;

public class RubikCubeDatabase {


    //Definition of the localhost server
    private static String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    //The database name to be created in Mysql
    private static final String DB_NAME = "CubesDatabase";
    //User name and password for the user on the database
    private static final String USER = "Momo";
    private static final String PASS = "password";

    //
    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;


    public final static String RUBIC = "rubikTableName";   //The table name
    public final static String PK_COLUMN = "Number";//Wanted this to be a primary key but have problem getting it to work
    //A primary key is needed to allow updates to the database on modifications to ResultSet
    public final static String RUBIKTITLENAME = "Cube_Solver"; //Variable of the Cube solver which define the cube solver
    public final static String TIME_IN_SECONDS = "time_In_seconds";  //The variable that holds the time in seconds in the database;
    private static RubikCubeDataModel dataModel;  //The model varible of the data model




    public static void main(String[] args) {

        //A setup that creates the database

        if (!setUP()) {
            System.exit(-1);

        }
        if (!loadRubikCubeData()) {
            System.exit(-1);
        }


        //Initailization of the data form which lunch the gui
        DataForm tableData = new DataForm(dataModel);

    }
       //A method that either create or recreate the resultset
    public static boolean loadRubikCubeData() {
        try {
            if (rs != null) {
                rs.close();
            }
            String loadData = "SELECT* FROM  RUBIKTABLENAME";
            rs = statement.executeQuery(loadData);
            if (dataModel == null) {
                dataModel = new RubikCubeDataModel(rs);

            } else {
                dataModel.updateResultsSet(rs);
            }
            return true;
            //Catching of any error and displaying that particular error
        } catch (Exception e) {
            System.out.println("Error loading or reloading data");
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }

          //A method that setup the database to be lunch and connected
    public static boolean setUP() {
        try {
            try {
                String Driver = "com.mysql.jdbc.Driver";
                Class.forName(Driver);
            } catch (ClassNotFoundException nfe) {
                System.out.println("No Database driver were found");
                return false;
            }
            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME, USER, PASS);
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
               //If result exit run this and create a table
            if (!rubikTableexist()) {
                PreparedStatement psInsert = null;
                   // Creation of the table
                String createTable = "CREATE TABLE  RUBIC (PK_COLUMN int AUTO_INCREMENT, RUBIKTITLENAME VARCHAR(50), TIME_IN_SECONDS DOUBLE )";
                statement.executeUpdate(createTable);
                //Use of the prepared statement
                String addDataSql = "INSERT INTO RUBIC VALUES  (?, ?)";
                psInsert = conn.prepareStatement(addDataSql);
                //Setting of prepared statement varioable
                psInsert.setString(1, "Fakhri Raihaan");
                psInsert.setDouble(2, 27.23);

                psInsert.setString(1,"Cubestormer II robot" );
                psInsert.setDouble(2, 5.27);
                psInsert.executeUpdate();
                psInsert.close();


            }
            return true;

          //Catch and any and display the error in the command line
        } catch (SQLException se) {

            System.out.println(se);
            se.printStackTrace();
             return false;
        }

    }
          //A method that checks if result exist
  private static boolean rubikTableexist() throws SQLException{
     String checkQuery = "SHOW TABLES LIKE '"+ RUBIC +"'";
      ResultSet tableRs = statement.executeQuery(checkQuery);
      if(tableRs.next()){
          return true;
      }
      return false;
  }
    //A method that shut down th database successfully by closing all of the various variable use.
    public static void shutDown(){
        try {
            if (rs != null) {
                rs.close();

            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        try{
            if(statement !=null){
                statement.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}




