import static org.junit.jupiter.api.Assertions.assertEquals;

import Database.DatabaseConnection;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.ResultSet;

public class TestDB {
    @Test
    public void testConnection(){
        DatabaseConnection dbConn = new DatabaseConnection();
        Connection conn = dbConn.getConnection();

        String usertext = "SamWiniarski";
        String passwordtext = "TestPassword";

        String sqlStr = "SELECT * FROM doctors WHERE id >0;";
        ResultSet rset = s.executeQuery(sqlStr);

        if (usernamedb.equals(usertext) && passwordnamedb.equals(passwordtext)) {
            System.out.println("Log in successful");
            validLogin = true;
            break;
        }

    }
}
