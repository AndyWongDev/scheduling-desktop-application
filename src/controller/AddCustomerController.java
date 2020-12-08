package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class AddCustomerController {

    @FXML
    private TextField nameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField postalCodeText;

    @FXML
    private TextField phoneText;

    @FXML
    private ChoiceBox<?> countryDropdown;

    @FXML
    private ChoiceBox<?> firstNameDivisionsDropdown;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    void onActionCancelButton(ActionEvent event) {

    }

    @FXML
    void onActionSaveButton(ActionEvent event) {

    }

}

