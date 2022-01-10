package Views;

import Controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Sidebar extends JPanel {
    private JLabel title;
    private JLabel doctorName;
    private JButton patients;
    private JButton dashboard;
    private JButton alerts;
    private JButton logout;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public Sidebar(){
        setLayout(null);
        setPreferredSize(new Dimension(200,800));
        setBorder(BorderFactory.createLineBorder(GREY));
        displayComponents();

    }

    private void displayComponents() {
        title = new JLabel("HealthSentinel");
        title.setBounds(12,10,180,90);
        title.setFont(new Font("Roboto",Font.BOLD, 23));
        title.setForeground(BLUE);
        add(title);

        patients = new JButton("Patients");
        patients.setBounds(0,250,200,50);
        patients.setFont(new Font("Roboto",Font.PLAIN, 20));
        patients.setBackground(RED);
        patients.setForeground(Color.white);
        patients.setOpaque(true);
        patients.setBorderPainted(false);
        patients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Patient pressed");
                try {
                    UIController.launchWelcomePage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(patients);

        dashboard = new JButton("Dashboard");
        dashboard.setBounds(0,300,200,50);
        dashboard.setFont(new Font("Roboto",Font.PLAIN, 20));
        dashboard.setBackground(RED);
        dashboard.setForeground(Color.white);
        dashboard.setOpaque(true);
        dashboard.setBorderPainted(false);
        dashboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Navigate to Patient Dashboard

            }
        });
        add(dashboard);

        alerts = new JButton("Alerts");
        alerts.setBounds(0,350,200,50);
        alerts.setFont(new Font("Roboto",Font.PLAIN, 20));
        alerts.setBackground(RED);
        alerts.setForeground(Color.white);
        alerts.setOpaque(true);
        alerts.setBorderPainted(false);
        alerts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                Navigate to Alerts Page
                try {
                    System.out.println("Alerts page launched");
                    UIController.launchAlertsPage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });
        add(alerts);

        logout = new JButton("Log Out");
        logout.setBounds(0,700,199,50);
        logout.setFont(new Font("Roboto",Font.PLAIN, 20));
        logout.setForeground(BLUE);
        logout.setOpaque(true);
        logout.setBorderPainted(false);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.laucnhLoginPage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(logout);

    }

}
