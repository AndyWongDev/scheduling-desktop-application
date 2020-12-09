package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneConverter;

import java.sql.*;

public class CustomerDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();


    CustomerDao() {
    }

    public static ObservableList<Customer> getCustomerList() {
        String selectStatement = "SELECT * FROM customers";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            customerList.clear();

            while (resultSet.next()) {
                int id = resultSet.getInt("Customer_ID");
                String name = resultSet.getString("Customer_Name");
                String address = resultSet.getString("Address");
                String postalCode = resultSet.getString("Postal_Code");
                String phone = resultSet.getString("Phone");
                Timestamp createDate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Create_Date"));
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Last_Update"));
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int divisionId = resultSet.getInt("Division_ID");

                Customer currentCustomer = new Customer(id, name, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(currentCustomer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
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
