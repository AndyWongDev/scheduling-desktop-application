package dao;

import model.User;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class LoginDao {
    private static Connection connection = DBConnection.getConnection();
    private static User currentUser;

    public LoginDao() {
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        LoginDao.currentUser = currentUser;
    }

    public static Boolean userLogin(String username, String password) {
        String selectStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                int id = resultSet.getInt("User_ID");
                currentUser = new User(id, username, password);
                System.out.println("Username and password found: " + currentUser.getName());
                loginAttemptTracker(true, currentUser.getName());
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loginAttemptTracker(false, username);
        System.out.println("No username and password found.");
        return false;
    }

    /**
     * C. Write code that provides the ability to track user activity by recording all user log-in attempts, dates,
     * and time stamps and whether each attempt was successful in a file named login_activity.txt.
     * Append each new record to the existing file, and save to the root folder of the application.
     *
     * @param status
     * @param username
     */
    private static void loginAttemptTracker(Boolean status, String username) {
        String result = status ? "SUCCESS" : "FAILED";
        Timestamp timestamp = TimezoneUtil.getUTCTime();
        List<String> output = Arrays.asList(result + ": " + username + ": " + timestamp + " UTC");

        try {
            Files.write(Paths.get("login_activity.txt"), output,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
