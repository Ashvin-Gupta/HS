package Controller;

import Views.ECGPage;
import Views.LoginPage;
import Views.RespRatePage;
import Views.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIController {

    static GraphicsConfiguration gc;
    static JFrame frame = new JFrame(gc);

    public int WIDTH = 1200;
    public int HEIGHT = 800;

    public UIController() throws SQLException {
//        frame.getContentPane().add(new LoginPage());
        frame.getContentPane().add(new RespRatePage().getmainpanel());   // TO BE ADAPTED TO THE DIFF PAGES
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

    public static void launchWelcomePage() throws SQLException {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new WelcomePage().getmainpanel());
        frame.setVisible(true);
        frame.setSize(new Dimension(1200,800));
    }



}
