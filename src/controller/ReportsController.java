package controller;

import dao.AppointmentDao;
import dao.ContactDao;
import dao.ReportsDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Country;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {
    private static String selectedContactName;

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Add properties for Type/Month Report
         * (using Appointment class and properties as a temporary DTO)
         */
        customerAppointmentTableView.setItems(ReportsDao.getAppointmentReport());
        cusTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startMonthCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        totalCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        /**
         * Add properties for Country/Appointment Report
         * (using Country class and properties as a temporary DTO)
         */
        countryAppointmentTableView.setItems(ReportsDao.getAppointmentsByCountryReport());
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        appTotalCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        /**
         * Pre-populate the Contact Dropdown
         * Default Table View items with no filters
         */
        contactDropdown.setItems(ContactDao.getContactList());
        contactScheduleTableView.setItems(AppointmentDao.getAppointmentList("View All"));
        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        conTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }

    @FXML
    private TableView<Appointment> customerAppointmentTableView;

    @FXML
    private TableColumn<Appointment, String> cusTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> startMonthCol;

    @FXML
    private TableColumn<Appointment, Integer> totalCol;

    @FXML
    private TableView<Country> countryAppointmentTableView;

    @FXML
    private TableColumn<Country, String> countryCol;

    @FXML
    private TableColumn<Country, Integer> appTotalCol;

    @FXML
    private TableView<Appointment> contactScheduleTableView;

    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> conTypeCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, Timestamp> startCol;

    @FXML
    private TableColumn<Appointment, Timestamp> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private ChoiceBox<String> contactDropdown;

    @FXML
    private Button backButton;

    /**
     * Controller to return to MainMenu view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionBackButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Controller to dynamically populate Appointments for selected Contacts options
     *
     * @param event
     */
    @FXML
    void onActionContactDropDown(ActionEvent event) {
        selectedContactName = contactDropdown.getValue();
        int selectedContactId = ContactDao.getContactIdFromName(selectedContactName);

        /**
         * Add properties for Contact Schedule Report
         * (using Appointment class and properties)
         */
        contactScheduleTableView.setItems(ReportsDao.getContactScheduleReport(selectedContactId));
        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        conTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    }
}
