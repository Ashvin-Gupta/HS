package Database;

import java.sql.*;

public class myDB {
    String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    Connection conn = null;

    //connects java program with the SQL database
    public myDB() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "Swamiji812!");
    }

    //closes the java-database connection
    public void closeConnect() throws SQLException {
        conn.close();
    }

    public void createPatientsTable() throws SQLException {
        String sqlStr = "create table patients ( id SERIAL PRIMARY KEY, name varchar(20) NOT NULL, surname varchar(20) NOT NULL, sex varchar(20), age int, blood varchar (4), sbp int, dbp int, rr float, hr int, temp float, ecg text );";
        String sqlStr1 = "insert into patients  values (1, 'Antonio', 'Serra', 'Male', 21, 'B+', 120, 80, 25, 62, 37.2);";
        String sqlStr2 = "insert into patients  values (2, 'Tom', 'Lee', 'Male', 34, 'A', 111, 77, 25, 60, 36.1);";
        String sqlStr3 = "insert into patients  values (3, 'Lana', 'Simpson', 'Female', 40, '0', 130, 87, 20, 55, 36.6);";
        Statement s = conn.createStatement();
        s.execute(sqlStr);
        s.execute(sqlStr1);
        s.execute(sqlStr2);
        s.execute(sqlStr3);
        s.close();
    }

    public void createDoctorTable() throws SQLException {
        String sqlStr = "create table doctors ( id SERIAL PRIMARY KEY, username varchar(20) NOT NULL, password varchar(20) NOT NULL);";
//        String sqlStr1 = "insert into doctors  values (1, 'rick12', '123');";
//        String sqlStr2 = "insert into doctors  values (2, 'am4nda', '1234');";
//        String sqlStr3 = "insert into doctors  values (3, 'docforrest', '12345');";
        Statement s = conn.createStatement();
        s.execute(sqlStr);
//        s.execute(sqlStr1);
//        s.execute(sqlStr2);
//        s.execute(sqlStr3);
        s.close();
    }

    public Connection getConnection() {
        return conn;
    }



 
}

