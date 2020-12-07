package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBQuery {

    private static PreparedStatement preparedStatement;

    // Create Statement Object
    public static void setPreparedStatement(Connection connection, String sqlStatement) throws SQLException {
        preparedStatement = connection.prepareStatement(sqlStatement);
    }

    // Return Statement Object
    public static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
