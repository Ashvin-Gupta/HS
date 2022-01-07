package Views;

import Data.PatientTableModel;
import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WelcomePage implements Launchable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JTable patienttable;
    private JPanel mainpanel;
    private JPanel centerpanel;
    public int WIDTH = 1200;
    public int HEIGHT = 800;

    public WelcomePage() throws SQLException {
//        Create main panel to add Sidebar panel and center panel to
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
//        Create centerpanel
//        Main components of screen will go on this panel
        centerpanel = new JPanel();
        centerpanel.setLayout(null);
        centerpanel.setPreferredSize(new Dimension(1000,800));
//        Create a new sidebar panel
        JPanel sidebar = new Sidebar();

        displayComponents();
        displayPatientTable();

//        Add both panels to main panel
        mainpanel.add(centerpanel,BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);


    }

    public void displayComponents() {
        title = new JLabel("Welcome");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.1),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.blue);
        centerpanel.add(title);


    }

    public void displayPatientTable() {


        patienttable = new JTable(new PatientTableModel());
        System.out.println("Table created");
        patienttable.setPreferredScrollableViewportSize(new Dimension(800,800));
//        JScrollPane scrollPane = new JScrollPane(patienttable);
        patienttable.setFillsViewportHeight(true);
//        add(patienttable);

        JScrollPane sp = new JScrollPane(patienttable);
        patienttable.setVisible(true);
        patienttable.setFillsViewportHeight(true);
        sp.setVisible(true);
//        add(patienttable);
        centerpanel.add(sp);




    }

    public JPanel getmainpanel(){
        return mainpanel;
    }

}
