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
                Timestamp localStart = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Start"));
                Timestamp localEnd = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("End"));

                Appointment currentAppointment = new Appointment();
                currentAppointment.setId(resultSet.getInt("Appointment_ID"));
                currentAppointment.setTitle(resultSet.getString("Title"));
                currentAppointment.setDescription(resultSet.getString("Description"));
                currentAppointment.setLocation(resultSet.getString("Location"));
                currentAppointment.setContactId(resultSet.getInt("Contact_ID"));
                currentAppointment.setType(resultSet.getString("Type"));
                currentAppointment.setStart(localStart);
                currentAppointment.setEnd(localEnd);
                currentAppointment.setCustomerId(resultSet.getInt("Customer_ID"));

                appointmentList.add(currentAppointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }

    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }
}
