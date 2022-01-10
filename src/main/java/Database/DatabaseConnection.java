package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    Connection conn= DriverManager.getConnection(dbUrl, "aidanpadraig", "aidanpadraig");

    public DatabaseConnection() throws SQLException {
    }

    public Connection getConnection() {
        return conn;
    }
}
