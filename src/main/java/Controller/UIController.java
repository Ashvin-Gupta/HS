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

    public static void laucnhLoginPage() throws SQLException{
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

    public static void launchPatientDashboard() throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new PatientDashboard().getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }

    public static void launchAlertsPage() throws SQLException{
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new AlertsPage().getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));

    }



}
