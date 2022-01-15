import Controller.UIController;
import Database.myDB;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
        myDB db = new myDB();
//        db.createPatientsTable();
//        db.createDoctorTable();
//        db.createAlertTable();

        new UIController();
    }
}
