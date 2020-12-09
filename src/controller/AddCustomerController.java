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
import model.FirstLevelDivision;
import utils.Warning;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        countryDropdown.setItems(CountryDao.getCountryList());
    }

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
    private ChoiceBox<String> firstNameDivisionsDropdown;

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
    void onActionCountryDropdown(ActionEvent event) {
        ObservableList<String> firstLevelDivisionList = FirstLevelDivisionDao.getFirstLevelDivisionList(countryDropdown.getValue());
        firstNameDivisionsDropdown.setItems(firstLevelDivisionList);
    }

    @FXML
    void onActionSaveButton(ActionEvent event) {
        try {
            String name = nameText.getText();
            String address = addressText.getText();
            String postalCode = postalCodeText.getText();
            String phone = phoneText.getText();
            int divisionId = FirstLevelDivisionDao.getIdFromDivision(firstNameDivisionsDropdown.getValue());

            Customer customer = new Customer(name, address, postalCode, phone, divisionId);
            Boolean result = CustomerDao.addCustomer(customer);

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

