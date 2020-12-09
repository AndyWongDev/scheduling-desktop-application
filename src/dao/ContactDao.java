package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> contactList = FXCollections.observableArrayList();

    public ContactDao() {
    }

    public static ObservableList<String> getContactList() {
        String selectStatement = "SELECT * FROM contacts";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                contactList.add(resultSet.getString("Contact_Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactList;
    }

    public static int getContactIdFromName(String name) {
        String selectStatement = "SELECT Contact_ID " +
                "FROM contacts " +
                "WHERE Contact_Name = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, name);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("Contact_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
