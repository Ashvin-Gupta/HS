package Views;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

import Database.myDB;
import Views.ECGPage;

import Controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

// imports for graphs
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import static javax.swing.SwingConstants.CENTER;

public class BloodPresPage implements Launchable {
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1200;
    int HEIGHT = 800;

    private JPanel mainpanel;
    private JPanel BPPanel;
    private JLabel BPTitle;
    private JLabel LiveSBPTitle;
    private JLabel SystolicBox;
    private JLabel LiveDBPTitle;
    private JLabel DiastolicBox;
    private JLabel TimeSelectTitle;
    private JLabel TimeSelectBox;
    private JLabel TimeInnerSelectBox;
    private JLabel PatientName;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);


    public BloodPresPage(int patientid) throws SQLException{
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        BPPanel = new JPanel();
        BPPanel.setLayout(null);
        BPPanel.setPreferredSize(new Dimension(1000, 800));

        JPanel sidebar = new Sidebar(patientid);

        displayBloodPresComponents();
        getPatientInfo(patientid);

        mainpanel.add(BPPanel, BorderLayout.CENTER);
        mainpanel.add(sidebar, BorderLayout.LINE_START);
    }

    private void displayBloodPresComponents() {
        BPTitle = new JLabel("Blood Pressure");
        BPTitle.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),900,80);
        BPTitle.setFont(new Font("Roboto",Font.BOLD, 60));
        BPTitle.setForeground(BLUE);
        BPPanel.add(BPTitle);

        // Systolic pressure text and box

        LiveSBPTitle = new JLabel("<html> Live Systolic Blood Pressure: <br> (mmHg) <html>");
        LiveSBPTitle.setForeground(Color.WHITE);
        LiveSBPTitle.setBounds((int) (50),(int) (HEIGHT *0.12),225,280);
        LiveSBPTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveSBPTitle.setVisible(true);
        BPPanel.add(LiveSBPTitle);

        SystolicBox = new JLabel(" ");
        SystolicBox.setBounds((int) (25), (int) (HEIGHT * 0.24), 250, 270);
        SystolicBox.setBackground(BLUE);
        SystolicBox.setOpaque(true);
        BPPanel.add(SystolicBox);


        // Diastolic pressure text and book

        LiveDBPTitle = new JLabel("<html> Live Diastolic Blood Presure: <br> (mmHg) <html>");
        LiveDBPTitle.setForeground(Color.WHITE);
        LiveDBPTitle.setBounds((int) (350),(int) (HEIGHT *0.12),225,280);
        LiveDBPTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveDBPTitle.setVisible(true);
        BPPanel.add(LiveDBPTitle);

        DiastolicBox = new JLabel(" ");
        DiastolicBox.setBounds((int) (325), (int) (HEIGHT * 0.24), 250, 270);
        DiastolicBox.setBackground(BLUE);
        DiastolicBox.setOpaque(true);
        BPPanel.add(DiastolicBox);

        // Time selection box and text

        TimeSelectTitle = new JLabel("<html> Select to view last ___ <br> mins:<html>");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (650), (int) (HEIGHT * 0.12), 225, 280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        BPPanel.add(TimeSelectTitle);

        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (625), (int) (HEIGHT * 0.24), 250, 270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        BPPanel.add(TimeSelectBox);

    }

    private void getPatientInfo(int patientid) throws SQLException {
        // patient name format
        String getpatientname = "select name,surname,sex,age,blood from patients where id="+patientid;
        PreparedStatement prpStm = conn.prepareStatement(getpatientname);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        rs.next();

        String patientname = rs.getString("name") + rs.getString("surname");
        PatientName = new JLabel(patientname);
        PatientName.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.11), 400, 60);
        PatientName.setFont(new Font("Roboto", Font.BOLD, 40));
        PatientName.setForeground(RED);
        BPPanel.add(PatientName);

        String sexstring = rs.getString("sex");
        JLabel sex = new JLabel("Sex: "+sexstring);
        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.03),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        BPPanel.add(sex);

        String agestring = rs.getString("age");
        JLabel age = new JLabel("Age: "+agestring);
        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.065),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 22));
        age.setForeground(Color.black);
        BPPanel.add(age);

        String bloodstring = rs.getString("blood");
        JLabel blood = new JLabel("Blood: "+bloodstring);
        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.1),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 22));
        blood.setForeground(Color.black);
        BPPanel.add(blood);

    }

    public JPanel getmainpanel() {
        return mainpanel;
    }

}
