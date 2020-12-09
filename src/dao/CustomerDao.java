package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> countryList = FXCollections.observableArrayList();

    CustomerDao() {
    }

    public static Boolean addCustomer(Customer customer) {
        String insertStatement = "INSERT INTO customers (" +
                "Customer_Name, " +
                "Address, " +
                "Postal_Code, " +
                "Phone, " +
                "Create_Date, " +
                "Created_By, " +
                "Last_Update, " +
                "Last_Updated_By, " +
                "Division_ID) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            DBQuery.setPreparedStatement(connection, insertStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, TimezoneConverter.getUTCTime());
            preparedStatement.setString(6, customer.getCreatedBy());
            preparedStatement.setTimestamp(7, customer.getLastUpdate());
            preparedStatement.setString(8, customer.getLastUpdatedBy());
            preparedStatement.setInt(9, customer.getDivisionId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
