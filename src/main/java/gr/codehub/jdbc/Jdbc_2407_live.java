package gr.codehub.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc_2407_live {

    private static void driverLoad() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
    }

    private static Connection connectToMySql(String databaseName) throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3306";
        String userName = "root";
        String password = "root";
        String database = databaseName;

        Connection connection = null;
        System.out.println("Connecting to database " + database);
        connection = DriverManager.getConnection(connectionUrl + "/" + database, userName, password);
        System.out.println("Connected!");
        return connection;
    }

    
    private static void testMetadata(Connection connection) throws SQLException {
        DatabaseMetaData md = connection.getMetaData();
        System.out.println("Database name = " + md.getDatabaseProductName());
        System.out.println("Database version = " + md.getDatabaseProductVersion());
    }

    
    private static void testAllTableColumns(Connection connection, String table) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = """
                     SELECT *
                     FROM INFORMATION_SCHEMA.COLUMNS
                     WHERE TABLE_NAME =
                     """ ;
        sql += "'" + table + "'";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()) {
            String s = rs.getString("column_name").toUpperCase();
            String t = rs.getString("data_type");
            System.out.println(s +" [" + t + "]");
        }
        rs.close();
    }

    private static void testAllActorRows(Connection connection, String nameStart) throws SQLException {
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM actor WHERE first_name LIKE '" + nameStart +"%'  ";
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()){
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            System.out.printf("%10s %s\n", firstName, lastName);
        }
        rs.close();
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("Application started");
        driverLoad();
        Connection connection = connectToMySql("sakila");
        testMetadata(connection);
        testAllTableColumns(connection, "language");
        testAllActorRows(connection, "ja");
        connection.close();
        System.out.println("Application finished");
    }

}
