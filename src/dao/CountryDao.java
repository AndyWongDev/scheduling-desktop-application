package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneConverter;

import java.sql.*;

public class CountryDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> countryList = FXCollections.observableArrayList();

    public CountryDao() {
    }

    public static ObservableList<String> getCountryList() {
        String selectStatement = "SELECT * FROM countries";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            countryList.clear();

            while (resultSet.next()) {
                Timestamp localCreateDate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Create_Date"));
                Timestamp localLastUpdate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Last_Update"));

                Country currentCountry = new Country();
                currentCountry.setId(resultSet.getInt("Country_ID"));
                currentCountry.setCountry(resultSet.getString("Country"));
                currentCountry.setCreateDate(localCreateDate);
                currentCountry.setCreatedBy(resultSet.getString("Created_By"));
                currentCountry.setCreateDate(localLastUpdate);
                currentCountry.setCreatedBy(resultSet.getString("Last_Updated_By"));

                countryList.add(currentCountry.getCountry());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }
}
