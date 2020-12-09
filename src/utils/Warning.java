package utils;

import javafx.scene.control.Alert;

public class Warning {
    public static void generateMessage(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
    }
}
