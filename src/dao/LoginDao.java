package dao;

import model.User;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("No username and password found.");
        return false;
    }
}
