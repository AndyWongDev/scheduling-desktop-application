package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportsDao {
    private static Connection connection = DBConnection.getConnection();

    public static ObservableList<Appointment> getAppointmentReport() {
        ObservableList<Appointment> appointmentReport = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Type, Month(Start) as Start_Month, Count(*) as Total " +
                "FROM appointments " +
                "GROUP BY Start_Month, Type";

        try {
            DBQuery.setPreparedStatement(connection, sqlStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            appointmentReport.clear();

            while (resultSet.next()) {
                Appointment currentAppointment = new Appointment();
                currentAppointment.setType(resultSet.getString("Type"));
                // Temp use of customerId property
                currentAppointment.setCustomerId(resultSet.getInt("Start_Month"));
                // Temp use of contactId property
                currentAppointment.setContactId(resultSet.getInt("Total"));
                appointmentReport.add(currentAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentReport;
    }

    public static ObservableList<Country> getAppointmentsByCountryReport() {
        ObservableList<Country> appointmentByCountryReport = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Country, Count(Appointment_ID) as Total " +
                "FROM appointments a " +
                "INNER JOIN customers cu " +
                "ON a.Customer_ID = cu.Customer_ID " +
                "INNER JOIN first_level_divisions fld " +
                "ON fld.Division_ID = cu.Division_ID " +
                "INNER JOIN countries co " +
                "ON co.Country_ID = fld.Country_ID " +
                "GROUP BY Country";

        try {
            DBQuery.setPreparedStatement(connection, sqlStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            appointmentByCountryReport.clear();

            while (resultSet.next()) {
                Country currentCountry = new Country();
                currentCountry.setCountry(resultSet.getString("Country"));
                currentCountry.setId(resultSet.getInt("Total"));
                appointmentByCountryReport.add(currentCountry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentByCountryReport;
    }

    public static ObservableList<Appointment> getContactScheduleReport(int contactId) {
        ObservableList<Appointment> contactScheduleReport = FXCollections.observableArrayList();
        String sqlStatement = "SELECT * " +
                "FROM appointments " +
                "WHERE Contact_ID = ? " +
                "ORDER BY Start ASC";

        try {
            DBQuery.setPreparedStatement(connection, sqlStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setInt(1, contactId);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            contactScheduleReport.clear();

            while (resultSet.next()) {
                Appointment currentAppointment = new Appointment();
                currentAppointment.setId(resultSet.getInt("Appointment_ID"));
                currentAppointment.setTitle(resultSet.getString("Title"));
                currentAppointment.setType(resultSet.getString("Type"));
                currentAppointment.setDescription(resultSet.getString("Description"));
                currentAppointment.setStart(resultSet.getTimestamp("Start"));
                currentAppointment.setEnd(resultSet.getTimestamp("End"));
                currentAppointment.setCustomerId(resultSet.getInt("Customer_ID"));

                contactScheduleReport.add(currentAppointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactScheduleReport;
    }
}
