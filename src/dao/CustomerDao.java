package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneUtil;

import java.sql.*;

public class CustomerDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<Customer> customerList = FXCollections.observableArrayList();

    CustomerDao() {
    }

    /**
     * GET Request for All Customers from customers table
     *
     * @return ObservableList<Customer> customerList
     */
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
                Timestamp createDate = resultSet.getTimestamp("Create_Date");
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = resultSet.getTimestamp("Last_Update");
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

    /**
     * B. Write at least two different lambda expressions to improve your code.
     * Maps ObservableList<Customer> customerList to an ObservableList<String> to populate dropdown
     *
     * Allows for linear search for customerNames without needing to query the database for redundant information
     *
     * @return customerNameList
     */
    public static ObservableList<String> getCustomerNameList() {
        ObservableList<String> customerNameList = FXCollections.observableArrayList();
        customerList.forEach(customer -> customerNameList.add(customer.getName()));
        return customerNameList;
    }

    /**
     * POST Request to insert valid Customers into the customer table
     *
     * @param customer
     * @return
     */
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
            preparedStatement.setTimestamp(5, TimezoneUtil.getUTCTime());
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

    /**
     * UPDATE request to for existing Customers in the customers table
     *
     * @param customer
     * @return
     */
    public static Boolean updateCustomer(Customer customer) {
        String selectStatement = "UPDATE customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getPostalCode());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setTimestamp(5, TimezoneUtil.getUTCTime());
            preparedStatement.setString(6, LoginDao.getCurrentUser().getName());
            preparedStatement.setInt(7, customer.getDivisionId());
            preparedStatement.setInt(8, customer.getId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * DELETE request for Customers in the customers table
     *
     * @param id
     * @throws SQLException
     */
    public static void deleteCustomer(int id) throws SQLException {
        String selectStatement = "DELETE FROM customers WHERE Customer_ID = ?";
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
    }

    /**
     * Row level fetch request for customerId from customeName
     *
     * @param name
     * @return
     */
    public static int getCustomerIdFromName(String name) {
        String selectStatement = "SELECT Customer_ID " +
                "FROM customers " +
                "WHERE Customer_Name = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, name);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("Customer_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Row level fetch request for customerName from customerId
     *
     * @param id
     * @return
     */
    public static String getCustomerNameFromId(int id) {
        String selectStatement = "SELECT Customer_Name " +
                "FROM customers " +
                "WHERE Customer_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Customer_Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}