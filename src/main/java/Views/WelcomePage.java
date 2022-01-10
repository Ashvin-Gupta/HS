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
    private JPanel centerpanel;
    private JPanel tablepanel;
    private JTable table;
    public int WIDTH = 1200;
    public int HEIGHT = 800;

    public WelcomePage() throws SQLException {
//        Create main panel to add Sidebar panel and center panel to
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        mainpanel.add(sidebar);

        centerpanel = new JPanel();

        String[][] rec = {
                { "1", "Aidan", "Lees" },
                { "2", "Sam", "Wini" },
        };
        String[] header = { "ID", "First Name", "Last Name" };
        JTable table = new JTable(rec, header);
        centerpanel.add(table);
        mainpanel.add(centerpanel);
    }

    public void displayComponents() {
        title = new JLabel("Welcome");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.1),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.blue);
        mainpanel.add(title);
    }

    public JPanel getmainpanel(){
        return mainpanel;
    }

}
