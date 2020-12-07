package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.DBConnection;
import utils.DBQuery;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private ResourceBundle language;
    private Connection connection = DBConnection.getConnection();
    private Locale locale = Locale.getDefault();

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

    @FXML
    void onActionLoginButton(ActionEvent event) throws SQLException {
        String selectStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";
        DBQuery.setPreparedStatement(connection, selectStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        String username = usernameText.getText();
        String password = passwordText.getText();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getResultSet();

        if (resultSet.next()) {
            System.out.println("Username and password found: " + resultSet.getString("User_Name"));
        } else {
            System.out.println("No username and password found.");
            messageText.setText(language.getString("messageText"));
        }
    }

    @FXML
    void onActionResetButton(ActionEvent event) {
        usernameText.clear();
        passwordText.clear();
    }

}
