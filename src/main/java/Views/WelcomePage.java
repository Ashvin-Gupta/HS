package Views;

import Data.PatientTableModel;
import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class WelcomePage implements Launchable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JPanel mainpanel;
    private JPanel patientpanel;
    private JPanel alertpanel;
    private JTable patienttable;
    private JTable alerttable;
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
        String[][] rec = {
                { "1", "Aidan", "Lees" },
                { "2", "Sam", "Wini" },
        };
        String[] header = { "ID", "First Name", "Last Name" };

        JTable patienttable = new JTable(rec, header);
        JScrollPane pane = new JScrollPane(patienttable);
        patientpanel.add(pane);
        mainpanel.add(patientpanel);
    }

    public void displayAlertTable() {
        String[][] rec = {
                { "10:02:01", "Lees", "Low HR" },
                { "10:17:24", "Gupta", "ECG Irr" },
        };
        String[] header = { "Time", "Last Name","Alert Type" };

        alerttable = new JTable(rec, header);
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

    public void patientSelect() {
        int row = patienttable.getSelectedRow();
        int col = patienttable.getSelectedColumn();
        System.out.println("row: " + row + "col: " + col);
    }

    public JPanel getmainpanel(){
        return mainpanel;
    }

}
