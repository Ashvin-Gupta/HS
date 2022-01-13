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
    private JPanel centerpanel;
    private JPanel backpanel;
    private JPanel info;
    private JLabel patienttitle;
    private JLabel alerttitle;
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color LBLUE = new Color(26,101,158);
    public int WIDTH = 1000;
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

        backpanel = new JPanel();
        backpanel.setLayout(new BoxLayout(backpanel,BoxLayout.Y_AXIS));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,180));

        centerpanel = new JPanel();
        centerpanel.setPreferredSize(new Dimension(1000,500));
        centerpanel.setLayout(new BoxLayout(this.centerpanel, BoxLayout.X_AXIS));

        displayComponents();
        displayPatientTable();
        displayAlertTable();

        backpanel.add(info);
        backpanel.add(centerpanel);

        mainpanel.add(backpanel,BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);
    }

    public void displayPatientTable() {

        // Creating Table to display
        patienttablemodel = new PatientTableModel();
        JTable patienttable = new JTable(patienttablemodel);
        patienttable.setShowHorizontalLines(true);
        patienttable.setRowSelectionAllowed(true);
        patienttable.setForeground(LBLUE);
        patienttable.setFont(new Font("Roboto",Font.PLAIN,16));

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
        centerpanel.add(patientpanel);
    }

    public void displayAlertTable() {

        alerttable = new JTable(new AlertTableModel());
        alerttable.setFont(new Font("Roboto",Font.PLAIN,16));
        alerttable.setForeground(LBLUE);
        JScrollPane pane = new JScrollPane(alerttable);
        alertpanel.add(pane);
        centerpanel.add(alertpanel);

    }

    public void displayComponents() {
        title = new JLabel("Welcome");
        title.setBounds((int) (WIDTH*0.02), (int) (HEIGHT*0.03),480,90);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        info.add(title);

        patienttitle = new JLabel("Patient List");
        patienttitle.setBounds((int)(WIDTH*0.13),(int)(HEIGHT*0.15),300,90);
        patienttitle.setFont(new Font("Roboto",Font.BOLD,32));
        patienttitle.setForeground(LBLUE);
        info.add(patienttitle);

        alerttitle = new JLabel("Alerts");
        alerttitle.setBounds((int)(WIDTH*0.67),(int)(HEIGHT*0.15),300,90);
        alerttitle.setFont(new Font("Roboto",Font.BOLD,32));
        alerttitle.setForeground(LBLUE);
        info.add(alerttitle);



    }

    public JPanel getmainpanel(){
        return mainpanel;
    }

}
