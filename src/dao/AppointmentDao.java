package dao;

import controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointment;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneUtil;
import utils.Warning;

import java.sql.*;

public class AppointmentDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public AppointmentDao() {
    }

    public static ObservableList<Appointment> getAppointmentList(String selectedFilter) {
        Timestamp selectedDate = MainMenuController.getSelectedDate();
        String sqlStatement = "SELECT *, Start + INTERVAL ? HOUR as Local_Start, End + INTERVAL ? HOUR as Local_End FROM appointments";
        try {
            switch (selectedFilter) {
                case "View Month":
                    sqlStatement += " WHERE MONTH(Start) = ? AND YEAR(Start) = ?";
                    DBQuery.setPreparedStatement(connection, sqlStatement);
                    break;
                case "View Week":
                    sqlStatement += " WHERE WEEK(Start) = WEEK(?) AND YEAR(Start) = ?";
                    break;
            }
            sqlStatement += " ORDER BY Start ASC";

            DBQuery.setPreparedStatement(connection, sqlStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setInt(1, TimezoneUtil.getOffsetToLocalTime());
            preparedStatement.setInt(2, TimezoneUtil.getOffsetToLocalTime());

            switch (selectedFilter) {
                case "View Month":
                    preparedStatement.setInt(3, selectedDate.toLocalDateTime().getMonthValue());
                    preparedStatement.setInt(4, selectedDate.toLocalDateTime().getYear());
                    break;
                case "View Week":
                    preparedStatement.setString(3, selectedDate.toString());
                    preparedStatement.setInt(4, selectedDate.toLocalDateTime().getYear());
                    break;
            }

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

    public static Boolean updateAppointment(Appointment appointment) {
        String selectStatement = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Contact_ID = ?, Type = ?, Start = ?, End = ?, Customer_ID = ? " +
                "WHERE Appointment_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setString(3, appointment.getLocation());
            preparedStatement.setInt(4, appointment.getContactId());
            preparedStatement.setString(5, appointment.getType());
            preparedStatement.setTimestamp(6, appointment.getStart());
            preparedStatement.setTimestamp(7, appointment.getEnd());
            preparedStatement.setInt(8, appointment.getCustomerId());
            preparedStatement.setInt(9, appointment.getId());

            preparedStatement.execute();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteAppointment(int id) {
        String selectStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setAppointmentList(ObservableList<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    private static Boolean hasAppointmentConflicts(Appointment appointment) {
        String selectStatement = "SELECT Local_Start, Local_End " +
                "FROM (SELECT Start + INTERVAL ? HOUR as Local_Start, End + INTERVAL ? HOUR as Local_End " +
                "FROM appointments " +
                "WHERE Customer_ID = ?) LocalTimeTable " +
                "WHERE Local_Start < ? " +
                "AND Local_End > ?";

        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, TimezoneUtil.getOffsetToLocalTime());
            preparedStatement.setInt(2, TimezoneUtil.getOffsetToLocalTime());
            preparedStatement.setInt(3, appointment.getCustomerId());
            preparedStatement.setTimestamp(4, appointment.getEnd());
            preparedStatement.setTimestamp(5, appointment.getStart());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                Warning.generateMessage("Schedule Conflict with appointment(s)", Alert.AlertType.ERROR);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Boolean isValidAppointment(Appointment appointment) {
        return (!hasAppointmentConflicts(appointment) && TimezoneUtil.isOfficeHours(appointment));
    }
}