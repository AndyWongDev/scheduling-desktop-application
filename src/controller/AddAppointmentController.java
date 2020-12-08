package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddAppointmentController {

    @FXML
    private TextField idText;

    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private TextField locationText;

    @FXML
    private ChoiceBox<?> contactDropdown;

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
    private ChoiceBox<?> customerDropdown;

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
