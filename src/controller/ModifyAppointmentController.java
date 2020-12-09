package controller;

import dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import utils.TimezoneConverter;
import utils.Warning;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {

    @Override()
    public void initialize(URL url, ResourceBundle rb) {
        contactDropdown.setItems(ContactDao.getContactList());
        customerDropdown.setItems(CustomerDao.getCustomerNameList());

        Appointment selectedAppointment = MainMenuController.getSelectedAppointment();

        LocalDateTime startLDT = selectedAppointment.getStart().toLocalDateTime();
        LocalDate startDate = startLDT.toLocalDate();
        String startTime = startLDT.toLocalTime().toString();

        LocalDateTime endLDT = selectedAppointment.getStart().toLocalDateTime();
        LocalDate endDate = endLDT.toLocalDate();
        String endTime = endLDT.toLocalTime().toString();

        idText.setText(String.valueOf(selectedAppointment.getId()));
        titleText.setText(selectedAppointment.getTitle());
        descriptionText.setText(selectedAppointment.getDescription());
        locationText.setText(selectedAppointment.getLocation());
        contactDropdown.setValue(ContactDao.getContactNameFromId(selectedAppointment.getContactId()));
        typeText.setText(selectedAppointment.getType());
        startDatePicker.setValue(startDate);
        startText.setText(startTime);
        endDatePicker.setValue(endDate);
        endText.setText(endTime);
        customerDropdown.setValue(CustomerDao.getCustomerNameFromId(selectedAppointment.getCustomerId()));
    }

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private ChoiceBox<String> contactDropdown;

    @FXML
    private TextField typeText;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startText;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endText;

    @FXML
    private ChoiceBox<String> customerDropdown;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    @FXML
    void onActionCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idText.getText());
        AppointmentDao.deleteAppointment(id);
        Warning.generateMessage("Appointment Deleted", Alert.AlertType.CONFIRMATION);
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idText.getText());
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = locationText.getText();
            int contactId = ContactDao.getContactIdFromName(contactDropdown.getValue());
            String type = typeText.getText();
            LocalDate startDate = startDatePicker.getValue();
            String startTime = startText.getText();
            LocalDate endDate = endDatePicker.getValue();
            String endTime = endText.getText();
            int customerId = CustomerDao.getCustomerIdFromName(customerDropdown.getValue());

            Timestamp startTimestamp = TimezoneConverter.timestampFormatter(startDate, startTime);
            Timestamp endTimestamp = TimezoneConverter.timestampFormatter(endDate, endTime);

            Appointment appointment = new Appointment();
            appointment.setId(id);
            appointment.setTitle(title);
            appointment.setDescription(description);
            appointment.setLocation(location);
            appointment.setContactId(contactId);
            appointment.setType(type);
            appointment.setStart(startTimestamp);
            appointment.setEnd(endTimestamp);
            appointment.setCustomerId(customerId);
            Boolean result = AppointmentDao.updateAppointment(appointment);

            if (result) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                Warning.generateMessage("Invalid Inputs, try again", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
