package Views;

import Data.PatientTableModel;
import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WelcomePage extends JPanel {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JTable patienttable;

    public WelcomePage() throws SQLException {
        setLayout(null);
        displayComponents();
        displayPatientTable();
    }

    public void displayComponents() {
        title = new JLabel("Welcome");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.1),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.blue);
        add(title);
    }

    public void displayPatientTable() {

        patienttable = new JTable(new PatientTableModel());
        System.out.println("Table created");
//        patienttable.setPreferredScrollableViewportSize(new Dimension(800,800));
//        JScrollPane scrollPane = new JScrollPane(patienttable);
//        patienttable.setFillsViewportHeight(true);
//        add(patienttable);

        JScrollPane sp = new JScrollPane(patienttable);
        patienttable.setVisible(true);
        sp.setVisible(true);
        add(sp);
    }
}
