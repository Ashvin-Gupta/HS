package Database;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;

public class myDB {
    String dbUrl = "jdbc:postgresql://localhost:5431/postgres";
    Connection conn = null;

    //connects java program with the SQL database
    public myDB() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
        }
        conn = DriverManager.getConnection(dbUrl, "postgres", "postgres");
    }

    //closes the java-database connection
    public void closeConnect() throws SQLException {
        conn.close();
    }

    public void createPatientsTable() throws SQLException {
        String quotes = "'";
        String DPString1 = readIn("DiastolicPressure_1.txt");
        String DPString2 = readIn("DiastolicPressure_2.txt");
        String DPString3 = readIn("DiastolicPressure_3.txt");
        String SPString1 = readIn("SystolicPressure_1.txt");
        String SPString2 = readIn("SystolicPressure_2.txt");
        String SPString3 = readIn("SystolicPressure_3.txt");
        String TString1 = readIn("Temperature_1.txt");
        String TString2 = readIn("Temperature_2.txt");
        String TString3 = readIn("Temperature_3.txt");
        String RRString1 = readIn("RespRate_1.txt");
        String RRString2 = readIn("RespRate_2.txt");
        String RRString3 = readIn("RespRate_3.txt");

        String sqlStr = "create table patients ( id SERIAL PRIMARY KEY, name varchar(20) NOT NULL, surname varchar(20) NOT NULL, sex varchar(20), age int, blood varchar (4), sbp varchar(8000), dbp varchar(8000), rr varchar(8000), hr int, temp varchar(8000), ecg varchar(8000));";
        String sqlStr1 = "insert into patients  values (1, 'Antonio', 'Serra', 'Male', 21, 'B+',"+quotes+SPString1+quotes+","+quotes+DPString1+quotes+","+quotes+RRString1+quotes+",62,"+quotes+TString1+quotes+");";
        String sqlStr2 = "insert into patients  values (2, 'Tom', 'Lee', 'Male', 34, 'A',"+quotes+SPString2+quotes+","+quotes+DPString2+quotes+","+quotes+RRString2+quotes+",60,"+quotes+TString2+quotes+");";
        String sqlStr3 = "insert into patients  values (3, 'Lana', 'Simpson', 'Female', 40, '0',"+quotes+SPString3+quotes+","+quotes+DPString3+quotes+","+quotes+RRString3+quotes+",55,"+quotes+TString3+quotes+");";
        Statement s = conn.createStatement();
        s.execute(sqlStr);
        System.out.println("Patient table made");
        s.execute(sqlStr1);
        System.out.println("Execute 1");
        s.execute(sqlStr2);
        System.out.println("Execute 2");
        s.execute(sqlStr3);
        System.out.println("Execute 3");
        s.close();
    }

    public String readIn(String path) {
        Path currdir = Paths.get(path);

        String data = "";
        try {
            File filein = currdir.toFile();
            Scanner myReader = new Scanner(filein);
            if (myReader.hasNextLine())
                data += myReader.nextLine();
            while (myReader.hasNextLine()) {
                data = data+","+myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return data;
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

    public void createAlertTable() throws SQLException {
        String sqlStr = "create table alerts (alert_id SERIAL PRIMARY KEY, patient_id int, surname varchar(20), time varchar(20), alerttype varchar(20));";
        String sqlStr1 = "insert into alerts  values (1, 1, 'Serra', '10:24:36', 'Low HR');";
        String sqlStr2 = "insert into alerts  values (2, 1, 'Serra', '11:31:04', 'ECG Irr');";
        String sqlStr3 = "insert into alerts  values (3, 2, 'Lee', '14:46:12', 'Low Resp Rate');";

        Statement s = conn.createStatement();
        s.execute(sqlStr);
        s.execute(sqlStr1);
        s.execute(sqlStr2);
        s.execute(sqlStr3);

        s.close();
    }

    public Connection getConnection() {
        return conn;
    }




 
}

