package Views;

import Database.myDB;

import javax.swing.*;
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

    public AlertsPage() throws SQLException {

        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();

        centerpanel = new JPanel(new GridBagLayout());
        centerpanel.setPreferredSize(new Dimension(800,800));

        displayAlertTable();

        mainpanel.add(centerpanel,BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);

    }

    private void displayAlertTable() {
        String[][] rec = {
                { "10:02:01", "Lees", "Low HR" },
                { "10:17:24", "Gupta", "ECG Irr" },
        };
        String[] header = { "Time", "Last Name","Alert Type" };

        alerttable = new JTable(rec, header);
        JScrollPane pane = new JScrollPane(alerttable);
        centerpanel.add(pane);
    }

    @Override
    public JPanel getmainpanel() {
        return mainpanel;
    }
}
