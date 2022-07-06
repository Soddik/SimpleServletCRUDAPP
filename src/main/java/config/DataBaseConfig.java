package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConfig {
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String dbUser = "postgres";
        String password = "1234";

        if (connection != null) {
            return connection;
        } else {
            return DriverManager.getConnection(url, dbUser, password);
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //todo implement logger
            }
        }
    }
}
