package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivision;
import utils.DBConnection;
import utils.DBQuery;
import utils.TimezoneConverter;

import java.sql.*;

public class FirstLevelDivisionDao {
    private static Connection connection = DBConnection.getConnection();
    private static ObservableList<String> firstLevelDivisionList = FXCollections.observableArrayList();

    public FirstLevelDivisionDao() {
    }

    public static ObservableList<String> getFirstLevelDivisionList(String country) {
        String selectStatement = "SELECT * " +
                "FROM countries c " +
                "INNER JOIN first_level_divisions fld " +
                "ON c.Country_ID = fld.COUNTRY_ID " +
                "WHERE Country=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, country);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            firstLevelDivisionList.clear();

            while (resultSet.next()) {
                int id = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
                Timestamp createDate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Create_Date"));
                String createdBy = resultSet.getString("Created_By");
                Timestamp lastUpdate = TimezoneConverter.convertUTCtoLocal(resultSet.getTimestamp("Last_Update"));
                String lastUpdatedBy = resultSet.getString("Last_Updated_By");
                int countryId = resultSet.getInt("COUNTRY_ID");

                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(id, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);

                firstLevelDivisionList.add(firstLevelDivision.getName());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return firstLevelDivisionList;
    }

    public static int getIdFromDivision(String division) {
        String selectStatement = "SELECT Division_ID " +
                "FROM first_level_divisions " +
                "WHERE Division=?";
        try {
            DBQuery.setPreparedStatement(connection, selectStatement);
            PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

            preparedStatement.setString(1, division);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return resultSet.getInt("Division_ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
