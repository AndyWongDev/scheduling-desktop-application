package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = "mysql";
    private static final String ipAddress = "wgudb.ucertify.com";
    private static final String databaseName = "WJ07jvN";
    private static final String username = "U07jvN";
    private static final String password = "53689048420";
    private static final String mysqlJbdcDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;

    private static String getUrl() {
        return String.format("%s:%s://%s/%s", protocol, vendorName, ipAddress, databaseName);
    }

    public static Connection initialize() {
        try {
            Class.forName(mysqlJbdcDriver);
            connection = DriverManager.getConnection(getUrl(), username, password);
            System.out.println("Database connection successful for " + databaseName);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return connection;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void terminate() {
        try {
            connection.close();
            System.out.println("Connection closed for " + databaseName);
        }
        catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
