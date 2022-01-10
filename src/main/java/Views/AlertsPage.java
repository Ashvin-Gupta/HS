package Views;

import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AlertsPage implements Launchable {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title;
    private JPanel mainpanel;
    private JPanel centerpanel;
    private JTable alerttable;

    public AlertsPage() throws SQLException {

        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();
        mainpanel.add(sidebar);

        centerpanel = new JPanel(new GridBagLayout());
        centerpanel.setPreferredSize(new Dimension(1000,800));

        displayAlertTable();

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
        mainpanel.add(centerpanel);
    }

    @Override
    public JPanel getmainpanel() {
        return null;
    }
}
