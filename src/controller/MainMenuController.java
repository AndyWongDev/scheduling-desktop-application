package controller;

import dao.AppointmentDao;
import dao.CustomerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import utils.TimezoneUtil;
import utils.Warning;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    private static Appointment selectedAppointment;
    private static Customer selectedCustomer;
    private static Timestamp selectedDate = TimezoneUtil.getUTCTime();

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Set default value of DatePicker
         */
        datePicker.setValue(selectedDate.toLocalDateTime().toLocalDate());

        /**
         * Add properties from Appointment model
         */
        calendarTableView.setItems(AppointmentDao.getAppointmentList("View All"));
        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        /**
         * Add properties from Customer model
         */
        customerTableView.setItems(CustomerDao.getCustomerList());
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameIdCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("formattedAddress"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        createDateCol.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        lastUpdatedByCol.setCellValueFactory(new PropertyValueFactory<>("lastUpdatedBy"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
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

    /**
     * Appointment Table View
     */

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
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> startCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> endCol;

    @FXML
    private TableColumn<Appointment, Integer> appCustomerIdCol;

    /**
     * Customer Table View
     */

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> nameIdCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Timestamp> createDateCol;

    @FXML
    private TableColumn<Customer, String> createdByCol;

    @FXML
    private TableColumn<Customer, Timestamp> lastUpdateCol;

    @FXML
    private TableColumn<Customer, String> lastUpdatedByCol;

    @FXML
    private TableColumn<Customer, Integer> divisionIdCol;

    @FXML
    void onActionAddAppointmentButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCustomerButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyAppointmentButton(ActionEvent event) throws IOException {
        selectedAppointment = calendarTableView.getSelectionModel().getSelectedItem();
        if (calendarTableView.getSelectionModel().isEmpty()) {
            Warning.generateMessage("No Appointment was selected in the Tableview!", Alert.AlertType.WARNING);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionModifyCustomerButton(ActionEvent event) throws IOException {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (customerTableView.getSelectionModel().isEmpty()) {
            Warning.generateMessage("No Customer was selected in the Tableview!", Alert.AlertType.WARNING);
        } else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionReportsButton(ActionEvent event) {

    }

    @FXML
    void onActionDatePicker(ActionEvent event) {
        selectedDate = Timestamp.valueOf(datePicker.getValue().atStartOfDay());
        System.out.println(selectedDate);
        System.out.println(TimezoneUtil.timestampWithOffset(selectedDate, TimezoneUtil.getOffsetToLocalTime()));
        String selectedFilter = ((RadioButton)viewToggle.getSelectedToggle()).getText();
        calendarTableView.setItems(AppointmentDao.getAppointmentList(selectedFilter));
    }

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public static Timestamp getSelectedDate() {
        return selectedDate;
    }

}
