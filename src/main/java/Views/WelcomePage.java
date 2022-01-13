package Views;

import Controller.UIController;
import Data.AlertTableModel;
import Data.PatientTableModel;

import Database.myDB;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class WelcomePage implements Launchable {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JPanel mainpanel;
    private JPanel patientpanel;
    private JPanel alertpanel;
    private JTable patienttable;
    private JTable alerttable;
    private PatientTableModel patienttablemodel;
    protected final Color BLUE  = new Color(37,78,112);
    public int WIDTH = 1200;
    public int HEIGHT = 800;

    public WelcomePage() throws SQLException {
//        Create main panel to add Sidebar panel and center panel to
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        mainpanel.add(sidebar);

        patientpanel = new JPanel(new GridBagLayout());
        patientpanel.setPreferredSize(new Dimension(400,800));

        alertpanel = new JPanel(new GridBagLayout());
        alertpanel.setPreferredSize(new Dimension(400,800));

//        displayComponents();
        displayPatientTable();
        displayAlertTable();

        mainpanel.setLayout(new BoxLayout(this.mainpanel, BoxLayout.X_AXIS));
    }

    public void displayPatientTable() {

        // Creating Table to display
        patienttablemodel = new PatientTableModel();
        JTable patienttable = new JTable(patienttablemodel);
        patienttable.setShowHorizontalLines(true);
        patienttable.setRowSelectionAllowed(true);

        // Adding table to a container
        JScrollPane pane = new JScrollPane(patienttable);
        patientpanel.add(pane);

        patienttable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (patienttable.getSelectedRow() > -1) {
//                    System.out.println("ID of selected patient" + patienttable.getValueAt(patienttable.getSelectedRow(),0).toString());
                    int patientid = (int) patienttable.getValueAt(patienttable.getSelectedRow(),0);
                    try {
                        UIController.launchPatientDashboard(patientid);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        //Adding table to main panel
        mainpanel.add(patientpanel);
    }

    public void displayAlertTable() {

        alerttable = new JTable(new AlertTableModel());
        JScrollPane pane = new JScrollPane(alerttable);
        alertpanel.add(pane);
        mainpanel.add(alertpanel);

    }

    public void displayComponents() {
        title = new JLabel("Welcome");
        title.setBounds(12,0,180,90);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        mainpanel.add(title);
    }

    public JPanel getmainpanel(){
        return mainpanel;
    }

}
