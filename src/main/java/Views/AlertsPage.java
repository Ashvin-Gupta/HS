package Views;

import Controller.UIController;
import Data.AlertTableModel;
import Database.myDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AlertsPage implements Launchable {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JPanel mainpanel;
    private JPanel centerpanel;
    private JTable alerttable;
    private JPanel backpanel;
    private JPanel info;
    private int WIDTH = 1000;
    private int HEIGHT = 800;

    protected final Color BLUE  = new Color(37,78,112);
    protected final Color LBLUE = new Color(26,101,158);

    public AlertsPage(int patientID) throws SQLException {

        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar(patientID);

        centerpanel = new JPanel(new GridBagLayout());
        centerpanel.setPreferredSize(new Dimension(800,800));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,200));

        displayAlertTable(patientID);
        displaytitle();

        backpanel = new JPanel();
        backpanel.setLayout(new BoxLayout(backpanel,BoxLayout.Y_AXIS));

        backpanel.add(info);
        backpanel.add(centerpanel);

        mainpanel.add(backpanel,BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);

    }

    private void displaytitle() {
        title = new JLabel("Alert History");
        title.setBounds((int) (WIDTH*0.02), (int) (HEIGHT*0.03),480,90);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        info.add(title);
    }

    private void displayAlertTable(int patientID) throws SQLException {

        AlertTableModel alertTableModel = new AlertTableModel(patientID);
        alerttable = new JTable(alertTableModel);
        JScrollPane pane = new JScrollPane(alerttable);
        alerttable.setForeground(LBLUE);
        alerttable.setFont(new Font("Roboto",Font.PLAIN,16));
        alerttable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (alerttable.getSelectedRow() > -1) {
//                    System.out.println("ID of selected patient" + patienttable.getValueAt(patienttable.getSelectedRow(),0).toString());
                    int patientid = (int) alerttable.getValueAt(alerttable.getSelectedRow(),0);
                    try {
                        UIController.launchAlertsPage(patientid);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        centerpanel.add(pane);
    }

    @Override
    public JPanel getmainpanel() {
        return mainpanel;
    }
}
