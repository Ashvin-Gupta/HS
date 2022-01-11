import Controller.UIController;
import Database.myDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        myDB db = new myDB();
//        db.createPatientsTable();
//        db.createDoctorTable();

        new UIController();

    }
}
