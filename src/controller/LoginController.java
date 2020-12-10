package controller;

import dao.AppointmentDao;
import dao.LoginDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private ResourceBundle language;
    private Connection connection = DBConnection.getConnection();
    private Locale locale = Locale.getDefault();

    Stage stage;
    Parent scene;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        language = ResourceBundle.getBundle("i18n/Login", locale);
        titleLabel.setText(language.getString("titleLabel"));
        usernameLabel.setText(language.getString("usernameLabel"));
        passwordLabel.setText(language.getString("passwordLabel"));
        loginButton.setText(language.getString("loginButton"));
        resetButton.setText(language.getString("resetButton"));
        languageText.setText(locale.getDisplayLanguage());
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameText;

    @FXML
    private Label passwordLabel;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button resetButton;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageText;

    @FXML
    private Label languageText;

    /**
     * Validates username and password against database and attempts login
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLoginButton(ActionEvent event) throws IOException {
        String username = usernameText.getText();
        String password = passwordText.getText();

        Boolean loginAttempt = LoginDao.userLogin(username, password);

        if (loginAttempt) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            /**
             * Upcoming Appointment Checks
             */
            AppointmentDao.getUpcomingAppointment();
        } else {
            messageText.setText(language.getString("messageText"));
        }
    }

    /**
     * Controller to allow user to clear username and password textbox
     *
     * @param event
     */
    @FXML
    void onActionResetButton(ActionEvent event) {
        usernameText.clear();
        passwordText.clear();
    }

}
