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

    public static String getCountryFromId(int id) {
        String selectStatement = "SELECT Country " +
                "FROM countries " +
                "WHERE Country_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public static String getCountryFromFirstLevelDivisionId(int divisionId) {
        String selectStatement = "SELECT Country " +
                "FROM first_level_divisions fld " +
                "INNER JOIN countries c " +
                "ON fld.COUNTRY_ID = c.Country_ID " +
                "WHERE fld.Division_ID = ?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setInt(1, divisionId);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getString("Country");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }
}
