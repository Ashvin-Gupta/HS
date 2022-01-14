package Controller;

import Views.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIController {

    static GraphicsConfiguration gc;
    static JFrame frame = new JFrame(gc);

    public int WIDTH = 1200;
    public int HEIGHT = 800;

    public UIController() throws SQLException {

        frame.getContentPane().add(new LoginPage());
        frame.setSize(new Dimension(WIDTH,HEIGHT));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void launchLoginPage() throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new LoginPage());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchAccountPage() throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new CreateAccount());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));

    }

    public static void launchWelcomePage() throws SQLException {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new WelcomePage().getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchPatientDashboard(int patientID) throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new PatientDashboard(patientID).getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchAlertsPage(int patientID) throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new AlertsPage(patientID).getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchECGPage(int patientid) throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new ECGPage(patientid).getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchRespPage(int patientid) throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new RespRatePage(patientid).getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchBloodPresPage(int patientid) throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new BloodPresPage(patientid).getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }



}