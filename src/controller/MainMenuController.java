package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import model.Appointment;

import java.net.URL;
import java.time.*;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    ZonedDateTime zonedDateTime = ZonedDateTime.now();
    LocalDateTime localDateTime = zonedDateTime.now().toLocalDateTime();
    LocalDateTime utcDateTime = zonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        datePicker.setValue(localDateTime.toLocalDate());
    }

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button modifyCustomerButton;

    @FXML
    private Button addAppointmentButton;

    @FXML
    private Button modifyAppointmentButton;

    @FXML
    private Button reportsButton;

    @FXML
    private RadioButton viewWeekButton;

    @FXML
    private ToggleGroup viewToggle;

    @FXML
    private RadioButton viewMonthButton;

    @FXML
    private RadioButton viewAllButton;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<Appointment> calendarTableView;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    void onActionAddAppointmentButton(ActionEvent event) {

    }

    @FXML
    void onActionAddCustomerButton(ActionEvent event) {

    }

    @FXML
    void onActionModifyAppointmentButton(ActionEvent event) {

    }

    @FXML
    void onActionModifyCustomerButton(ActionEvent event) {

    }

    @FXML
    void onActionReportsButton(ActionEvent event) {

    }

}
