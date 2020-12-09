package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneConverter;

import java.sql.*;

public class AppointmentDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public AppointmentDao() {
    }

    public static ObservableList<Appointment> getAppointmentList() {
        String selectStatement = "SELECT * FROM appointments";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            appointmentList.clear();

            while (resultSet.next()) {
                Appointment currentAppointment = new Appointment();
                currentAppointment.setId(resultSet.getInt("Appointment_ID"));
                currentAppointment.setTitle(resultSet.getString("Title"));
                currentAppointment.setDescription(resultSet.getString("Description"));
                currentAppointment.setLocation(resultSet.getString("Location"));
                currentAppointment.setContactId(resultSet.getInt("Contact_ID"));
                currentAppointment.setType(resultSet.getString("Type"));
                currentAppointment.setStart(resultSet.getTimestamp("Start"));
                currentAppointment.setEnd(resultSet.getTimestamp("End"));
                currentAppointment.setCustomerId(resultSet.getInt("Customer_ID"));

                appointmentList.add(currentAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public static Boolean addAppointment(Appointment appointment) {
        String insertStatement = "INSERT INTO appointments (" +
                "Title, " +
                "Description, " +
                "Location, " +
                "Contact_ID, " +
                "Type, " +
                "Start, " +
                "End, " +
                "Customer_ID) " +
                "VALUE (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            DBQuery.setPreparedStatement(connection, insertStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setInt(4, appointment.getContactId());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setTimestamp(6, appointment.getStart());
            preparedStatement.setTimestamp(7, appointment.getEnd());
            preparedStatement.setInt(8, appointment.getCustomerId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
