//import static org.junit.Assertions.assertEquals;

import Database.DatabaseConnection;
import org.junit.jupiter.api.Test;

import java.sql.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

public class TestDB {

    @InjectMocks private DatabaseConnection dbConn = new DatabaseConnection();
    @Mock private Connection mockConnection = dbConn.getConnection();
    @Mock private Statement mockStatement;

    public TestDB() throws SQLException {
        // ummm what do i put here lol
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMockDBConnection() throws Exception {
        Statement m = mockConnection.createStatement();
        String sqlStr = "SELECT * FROM doctors;";
        Mockito.when(m).thenReturn(mockStatement);
        Mockito.when(m.executeUpdate(Mockito.any())).thenReturn(1);
        ResultSet valueSet = m.executeQuery(sqlStr);
        Assertions.assertNotNull(valueSet,"it was null ... bruh ");
        Mockito.verify(mockConnection.createStatement(), Mockito.times(1));
    }
}



