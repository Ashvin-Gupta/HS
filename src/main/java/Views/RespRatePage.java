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

public class RespRatePage implements ActionListener, Launchable{
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1200;
    int HEIGHT = 800;

    private JPanel mainpanel;
    private JPanel RRPanel;
    private JLabel RRTitle; // main title
    private JLabel LiveRRTitle;
    private JLabel LiveRRBox;
    private JLabel O2SatTitle;
    private JLabel O2SatBox;
    private JLabel GraphBox;
    private JLabel GraphTitle;
    private JLabel TimeSelectBox;
    private JLabel TimeSelectTitle;
    private JLabel PatientName;
    private JLabel RRupdated;
    private JLabel O2Updated;
    private JTextField TimeSelect;
    private int HRdataPoint = 0;
    private int ECGdataPoint = 0;
    private int O2dataPoint = 0;
    private int [] HRList = {60, 63, 10, 150, 70, 72, 56, 67, 86, 54, 55, 57, 58, 59, 30, 36, 45, 67};
    private int [] ECGList = {20, 21, 24, 25, 24, 27, 30, 24, 21, 23, 50, 55, 57, 58, 59, 30, 36, 45, 67};
    private int graphWidth = 10;
    public JPanel newRRGraph;
    private CardLayout card;
    private JPanel cardPanel;
    public DefaultCategoryDataset globalDataset;

    // colours
    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);


    public RespRatePage(int patientid) throws SQLException{
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        RRPanel = new JPanel();
        RRPanel.setLayout(null);
        RRPanel.setPreferredSize(new Dimension(1000, 800));

        JPanel sidebar = new Sidebar(patientid);

        displayRespRateComponents();
        displayStandardComponents2();
        getPatientInfo(patientid);

        mainpanel.add(RRPanel, BorderLayout.CENTER);
        mainpanel.add(sidebar, BorderLayout.LINE_START);
    }

    private void getPatientInfo(int patientid) throws SQLException {

        // patient information layout

        String getpatientname = "select name,surname,sex,age,blood from patients where id="+patientid;
        PreparedStatement prpStm = conn.prepareStatement(getpatientname);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        rs.next();

        String patientname = rs.getString("name") + " " + rs.getString("surname");
        PatientName = new JLabel(patientname);
        PatientName.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.11), 400, 60);
        PatientName.setFont(new Font("Roboto", Font.BOLD, 40));
        PatientName.setForeground(RED);
        RRPanel.add(PatientName);

        String sexstring = rs.getString("sex");
        JLabel sex = new JLabel("Sex: "+sexstring);
        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.03),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        RRPanel.add(sex);

        String agestring = rs.getString("age");
        JLabel age = new JLabel("Age: "+agestring);
        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.065),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 22));
        age.setForeground(Color.black);
        RRPanel.add(age);

        String bloodstring = rs.getString("blood");
        JLabel blood = new JLabel("Blood: "+bloodstring);
        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.1),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 22));
        blood.setForeground(Color.black);
        RRPanel.add(blood);

    }

    private void displayStandardComponents2() {

        // updating RR and inner white box
        RRupdated = new JLabel(" ");
        RRupdated.setFont(new Font("Roboto", Font.BOLD, 40));
        RRupdated.setHorizontalTextPosition(CENTER);
        RRupdated.setBounds((int) (WIDTH * 0.034), (int) (HEIGHT * 0.31), 260, 150);
        RRupdated.setForeground(Color.black);
        RRupdated.setOpaque(true);
        RRupdated.setBackground(Color.WHITE);
        RRupdated.setVisible(true);
        RRPanel.add(RRupdated);

        // Live display box set-up
        LiveRRBox = new JLabel(" ");
        LiveRRBox.setForeground(BLUE);
        LiveRRBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.2), 300, 270);
        LiveRRBox.setBackground(BLUE);
        LiveRRBox.setOpaque(true);
        RRPanel.add(LiveRRBox);

        // time selection and inner white box
        TimeSelect = new JTextField("", 20);
        TimeSelect.setBounds((int) (WIDTH * 0.567), (int) (HEIGHT * 0.31), 260, 150);
        TimeSelect.setForeground(BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        RRPanel.add(TimeSelect);

        // Time Selection title set-up
        TimeSelectTitle = new JLabel("<html> Select to view last ___ <br> mins:<html>");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (WIDTH * 0.57), (int) (HEIGHT * 0.09), 450, 280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        RRPanel.add(TimeSelectTitle);

        // Time Selection box set-up
        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH * 0.55), (int) (HEIGHT * 0.2), 300, 270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        RRPanel.add(TimeSelectBox);

        // timer for RRUpdated
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ECGdataPoint = (ECGdataPoint + 1) % ECGList.length;
                HRdataPoint = (HRdataPoint + 1) % HRList.length;
                O2dataPoint = (ECGdataPoint + 1) % ECGList.length;
                RRupdated.setText(String.valueOf(HRList[HRdataPoint]));
                O2Updated.setText(String.valueOf(ECGList[O2dataPoint]));

                RRupdated.setHorizontalTextPosition(CENTER);

                // card layout to update graph and control layers
                cardPanel = new JPanel();
                cardPanel.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.64), 900, 220);
                newRRGraph = createChartPanel();
                card = new CardLayout();
                cardPanel.setLayout(card);
                newRRGraph.setOpaque(true);
                newRRGraph.setVisible(true);
                newRRGraph.setBounds((int) (WIDTH * 0.135), (int) (HEIGHT * 0.67), 900, 220);
                cardPanel.add(newRRGraph);
                cardPanel.setVisible(true);
                card.next(cardPanel);
                RRPanel.add(cardPanel);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        // Live graph box set-up
        GraphBox = new JLabel(" ");
        GraphBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.57), 950, 300);
        GraphBox.setBackground(BLUE);
        GraphBox.setOpaque(true);
        RRPanel.add(GraphBox);

    }

    private void displayRespRateComponents() {

        // main title format
        RRTitle = new JLabel("Respiratory Rate");
        RRTitle.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),900,80);
        RRTitle.setFont(new Font("Roboto",Font.BOLD, 50));
        RRTitle.setForeground(BLUE);
        RRPanel.add(RRTitle);

        // Live display title set-up
        LiveRRTitle = new JLabel("<html> Live Respiratory Rate: <br> (breaths/minute) <html>");
        LiveRRTitle.setForeground(Color.WHITE);
        LiveRRTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.09),450,280);
        LiveRRTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveRRTitle.setVisible(true);
        RRPanel.add(LiveRRTitle);

        // Graph title set-up
        GraphTitle= new JLabel("Breathing rate over the last ___ minutes:");
        GraphTitle.setForeground(Color.white);
        GraphTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.43),450,280);
        GraphTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        GraphTitle.setVisible(true);
        RRPanel.add(GraphTitle);

        // 02 saturation title:
        O2SatTitle = new JLabel("<html> Oxygen Saturation:<br> (SpO2%) <html>");
        O2SatTitle.setForeground(Color.WHITE);
        O2SatTitle.setBounds((int) (WIDTH *0.3),(int) (HEIGHT *0.09),450,280);
        O2SatTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        O2SatTitle.setVisible(true);
        RRPanel.add(O2SatTitle);

        // 02 saturation box:
        O2SatBox = new JLabel(" ");
        O2SatBox.setBounds((int) (WIDTH * 0.285), (int) (HEIGHT * 0.2), 300, 270);
        O2SatBox.setBackground(BLUE);
        O2SatBox.setOpaque(true);
        RRPanel.add(O2SatBox);

        // O2 live and inner white box:
        O2Updated = new JLabel(" ");
        O2Updated.setFont(new Font("Roboto", Font.BOLD, 40));
        O2Updated.setHorizontalTextPosition(CENTER);
        O2Updated.setBounds((int) (WIDTH * 0.3), (int) (HEIGHT * 0.31), 260, 150);
        O2Updated.setForeground(Color.black);
        O2Updated.setOpaque(true);
        O2Updated.setBackground(Color.WHITE);
        O2Updated.setVisible(true);
        RRPanel.add(O2Updated);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    // function for graph
    public JPanel createChartPanel() {
        String chartTitle = "Breathing Rate Values for Patient:";
        String categoryAxisLabel = "Time";
        String valueAxisLabel = "Breaths per minute";

        globalDataset = createDataset(new DefaultCategoryDataset());

        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, globalDataset);

        return new ChartPanel(chart);
    }

    // creating the dataset for the graph
    public DefaultCategoryDataset createDataset(DefaultCategoryDataset inputDataset) {

        for (int i=ECGdataPoint-graphWidth;i<ECGdataPoint;i=i+1)
        {
            inputDataset.setValue(getHR(i), "patient data", String.valueOf(i));
        }

        return inputDataset;
    }

    public int getHR(int i){
        int new_i = i%ECGList.length;
        if (i<0){
            return ECGList[ECGList.length-1 + new_i];
        }
        else {
            return ECGList[new_i];
        }
    }

    public JPanel getmainpanel() {
        return mainpanel;
    }

}
