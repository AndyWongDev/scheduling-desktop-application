import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBConnection;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        DBConnection.initialize();
        launch(args);
        DBConnection.terminate();

        /**
         * PreparedStatements
         */
//        String insertStatement = "INSERT INTO countries(Country, Create_Date, Created_By, Last_Updated_By) VALUES(?, ?, ?, ?)";
//
//        DBQuery.setPreparedStatement(connection, insertStatement);
//
//        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
//
//        String countryName = "Japan";
//        String countryDate = "2020-12-06 23:00:00";
//        String createdBy = "admin";
//        String lastUpdatedBy = "admin";
//
//        preparedStatement.setString(1, countryName);
//        preparedStatement.setString(2, countryDate);
//        preparedStatement.setString(3, createdBy);
//        preparedStatement.setString(4, lastUpdatedBy);
//
//        preparedStatement.execute();
//        if (preparedStatement.getUpdateCount() > 0) {
//            System.out.println("Rows updated: " + preparedStatement.getUpdateCount());
//        } else {
//            System.out.println("No rows updated.");
//        }

//        DBQuery.setStatement(connection);
//        Statement statement = DBQuery.getStatement();

/**
 * Making an INSERT statement
 */
//        String insertStatement = "INSERT INTO countries(Country, Create_Date, Last_Updated_By)" +
//                "VALUES('Japan', '2020-12-06 23:00:00', 'admin')";
//
//        // Execute SQL Statement
//        statement.execute(insertStatement); // returns False on INSERT
//
//        if (statement.getUpdateCount() > 0) {
//            System.out.println("Rows updated: " + statement.getUpdateCount());
//        } else {
//            System.out.println("No rows updated.");
//        }

/**
 * Making an SELECT statement
 */
//        String selectStatement = "SELECT * FROM countries";
//        statement.execute(selectStatement);
//        ResultSet resultSet = statement.getResultSet();
//
//        while (resultSet.next()) {
//            int countryId = resultSet.getInt("Country_ID");
//            String countryName = resultSet.getString("Country");
//            LocalDate createDate = resultSet.getDate("Create_Date").toLocalDate();
//            LocalTime createTime = resultSet.getTime("Create_Date").toLocalTime();
//
//            System.out.println(countryId + " | "
//                    + countryName + " | "
//                    + createDate + " | "
//                    + createTime);
//        }
    }
}
