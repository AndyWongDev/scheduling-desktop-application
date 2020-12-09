package controller;

import dao.AppointmentDao;
import dao.ContactDao;
import dao.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import utils.TimezoneUtil;
import utils.Warning;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {
    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contactDropdown.setItems(ContactDao.getContactList());
        customerDropdown.setItems(CustomerDao.getCustomerNameList());
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        startText.setText("00:00");
        endText.setText("01:00");
    }

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
    void onActionCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveButton(ActionEvent event) {
        try {
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

            Timestamp startTimestamp = TimezoneUtil.timestampFormatter(startDate, startTime);
            Timestamp endTimestamp = TimezoneUtil.timestampFormatter(endDate, endTime);

            Appointment appointment = new Appointment();
            appointment.setTitle(title);
            appointment.setDescription(description);
            appointment.setLocation(location);
            appointment.setContactId(contactId);
            appointment.setType(type);
            appointment.setStart(startTimestamp);
            appointment.setEnd(endTimestamp);
            appointment.setCustomerId(customerId);

            Boolean result = AppointmentDao.addAppointment(appointment);

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
