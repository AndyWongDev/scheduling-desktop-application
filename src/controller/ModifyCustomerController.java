package controller;

import dao.CountryDao;
import dao.CustomerDao;
import dao.FirstLevelDivisionDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Customer;
import utils.Warning;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @Override()
    public void initialize(URL url, ResourceBundle rb) {
        countryDropdown.setItems(CountryDao.getCountryList());

        Customer selectedCustomer = MainMenuController.getSelectedCustomer();
        idText.setText(String.valueOf(selectedCustomer.getId()));
        nameText.setText(selectedCustomer.getName());
        addressText.setText(selectedCustomer.getAddress());
        postalCodeText.setText(selectedCustomer.getPostalCode());
        phoneText.setText(selectedCustomer.getPhone());
        countryDropdown.setValue(CountryDao.getCountryFromFirstLevelDivisionId(selectedCustomer.getDivisionId()));
        firstLevelDivisionsDropdown.setValue(FirstLevelDivisionDao.getDivisionFromId(selectedCustomer.getDivisionId()));
    }

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneText;

    @FXML
    private ChoiceBox<String> countryDropdown;

    @FXML
    private ChoiceBox<String> firstLevelDivisionsDropdown;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button deleteButton;

    /**
     * Controller to return to MainMenu view
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancelButton(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Controller to delete Customers without associated Appointments
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionDeleteButton(ActionEvent event) throws IOException {
        int id = Integer.parseInt(idText.getText());

        try {
            CustomerDao.deleteCustomer(id);
            Warning.generateMessage("Customer Deleted", Alert.AlertType.CONFIRMATION);
        } catch (SQLException e) {
            Warning.generateMessage("Customers assigned to Appointments cannot be deleted.", Alert.AlertType.WARNING);
        }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Controller to save Customer modifications
     *
     * @param event
     */
    @FXML
    void onActionSaveButton(ActionEvent event) {
        try {
            int id = Integer.parseInt(idText.getText());
            String name = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            int divisionId = FirstLevelDivisionDao.getIdFromDivision(firstLevelDivisionsDropdown.getValue());

            Customer customer = new Customer(id, name, address, postalCode, phone, divisionId);
            Boolean result = CustomerDao.updateCustomer(customer);

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

    /**
     * Controller to dynamically populate First Level Division Dropdown options based on Country Dropdown value
     *
     * @param event
     */
    @FXML
    void onActionCountryDropdown(ActionEvent event) {
        ObservableList<String> firstLevelDivisionList = FirstLevelDivisionDao.getFirstLevelDivisionList(countryDropdown.getValue());
        firstLevelDivisionsDropdown.setItems(firstLevelDivisionList);
    }
}
