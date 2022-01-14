package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

import Controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


// imports for ECG graph

import Database.myDB;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import static javax.swing.SwingConstants.CENTER;

public class ECGPage implements ActionListener,Launchable{
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1200;
    int HEIGHT = 800;

    //   static GraphicsConfiguration gc;
    //   static JFrame MainFrame = new JFrame(gc);
    private JPanel mainpanel;
    private JPanel HRPanel;
    private JLabel HRTitle; // main title
    private JLabel LiveHRBox;
    private JLabel LiveHRTitle;
    private JLabel ECGBox;
    private JLabel ECGTitle;
    private JLabel TimeSelectBox;
    private JLabel TimeSelectTitle;
    private JLabel PatientName;
    private JLabel PatientHospNo;
    private JLabel HRupdated;
    private JTextField TimeSelect;
    private JLabel PatientInfo1;
    private JLabel PatientInfo2;
    private int HRdataPoint = 0;
    private int ECGdataPoint = 0;
    private int dataPointmod = 0;
    private int[] HRList = {60, 63, 10, 150, 70, 72, 56, 67, 86, 54, 55, 57, 58, 59, 30, 36, 45, 67};
    private int[] ECGList = {20, 21, 24, 25, 24, 27, 30, 24, 21, 23, 50, 55, 57, 58, 59, 30, 36, 45, 67};
    private int graphWidth = 10;
    public JPanel newECGGraph;
    private CardLayout card;
    private JPanel cardPanel;
    public DefaultCategoryDataset globalDataset;

    // colours
    protected final Color RED = new Color(195, 60, 86);
    protected final Color BLUE = new Color(37, 78, 112);
    protected final Color GREY = new Color(159, 159, 159);

    public ECGPage(int patientid) throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        HRPanel = new JPanel();
        HRPanel.setLayout(null);
        HRPanel.setPreferredSize(new Dimension(1000, 800));

        JPanel sidebar = new Sidebar(patientid);

        displayECGComponents();
        displayStandardComponents1(); // add display components to HR panel
        getPatientInfo(patientid);

        mainpanel.add(HRPanel, BorderLayout.CENTER);
        mainpanel.add(sidebar, BorderLayout.LINE_START);
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
        HRPanel.add(PatientName);

        String sexstring = rs.getString("sex");
        JLabel sex = new JLabel("Sex: "+sexstring);
        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.03),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        HRPanel.add(sex);

        String agestring = rs.getString("age");
        JLabel age = new JLabel("Age: "+agestring);
        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.065),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 22));
        age.setForeground(Color.black);
        HRPanel.add(age);

        String bloodstring = rs.getString("blood");
        JLabel blood = new JLabel("Blood: "+bloodstring);
        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.1),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 22));
        blood.setForeground(Color.black);
        HRPanel.add(blood);

    }

    public void displayStandardComponents1() throws SQLException {

      // main title format
        HRTitle = new JLabel("Heart Rate");
        HRTitle.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),400,60);
        HRTitle.setFont(new Font("Roboto",Font.BOLD, 50));
        HRTitle.setForeground(BLUE);
        HRPanel.add(HRTitle);

//        // patient hospital number
//        PatientHospNo = new JLabel("Hospital No.:");
//        PatientHospNo.setBounds((int) (WIDTH * 0.1), (int) (HEIGHT * 0.17), 900, 60);
//        PatientHospNo.setFont(new Font("Roboto", Font.BOLD, 20));
//        PatientHospNo.setForeground(GREY);
//        HRPanel.add(PatientHospNo);

        // updating HR and inner white box
        HRupdated = new JLabel(" ");
        HRupdated.setFont(new Font("Roboto", Font.BOLD, 40));
        HRupdated.setHorizontalTextPosition(CENTER);
        HRupdated.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.29), 410, 150);
        HRupdated.setForeground(Color.black);
        HRupdated.setOpaque(true);
        HRupdated.setBackground(Color.WHITE);
        HRupdated.setVisible(true);
        HRPanel.add(HRupdated);

        /* // Live display title set-up
        LiveHRTitle = new JLabel("Live Heart Rate (bpm):");
        LiveHRTitle.setForeground(Color.WHITE);
        LiveHRTitle.setBounds((int) (WIDTH *0.12),(int) (HEIGHT *0.12),450,280);
        LiveHRTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveHRTitle.setVisible(true);
        HRPanel.add(LiveHRTitle); */

        // Live display box set-up
        LiveHRBox = new JLabel(" ");
        LiveHRBox.setForeground(BLUE);
        LiveHRBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.20), 450, 270);
        LiveHRBox.setBackground(BLUE);
        LiveHRBox.setOpaque(true);
        HRPanel.add(LiveHRBox);

        // time selection and inner white box
        TimeSelect = new JTextField("", 20);
        TimeSelect.setBounds((int) (WIDTH * 0.46), (int) (HEIGHT * 0.29), 410, 150);
        TimeSelect.setForeground(BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        HRPanel.add(TimeSelect);

        // Time Selection title set-up
        TimeSelectTitle = new JLabel("Select to view past _ seconds:");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (WIDTH * 0.46), (int) (HEIGHT * 0.08), 450, 280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        HRPanel.add(TimeSelectTitle);

        // Time Selection box set-up
        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH * 0.44), (int) (HEIGHT * 0.2), 450, 270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        HRPanel.add(TimeSelectBox);

        // timer for HRUpdated
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ECGdataPoint = (ECGdataPoint + 1) % ECGList.length;
                HRdataPoint = (HRdataPoint + 1) % HRList.length;
                HRupdated.setText(String.valueOf(HRList[HRdataPoint]));
                HRupdated.setHorizontalTextPosition(CENTER);
                // card layout to update graph and control layers
                cardPanel = new JPanel();
                cardPanel.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.64), 900, 220);
                newECGGraph = createChartPanel();
                card = new CardLayout();
                cardPanel.setLayout(card);
                newECGGraph.setOpaque(true);
                newECGGraph.setVisible(true);
                newECGGraph.setBounds((int) (WIDTH * 0.135), (int) (HEIGHT * 0.67), 900, 220);
                cardPanel.add(newECGGraph);
                cardPanel.setVisible(true);
                card.next(cardPanel);
                HRPanel.add(cardPanel);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        // ECG box set-up
        ECGBox = new JLabel(" ");
        ECGBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.57), 950, 300);
        ECGBox.setBackground(BLUE);
        ECGBox.setOpaque(true);
        HRPanel.add(ECGBox);

        // patient info set-up
//        PatientInfo1 = new JLabel("<html> Sex: <br> Age: <br> Blood:<html>");
//        PatientInfo1.setForeground(GREY);
//        PatientInfo1.setBounds((int) (WIDTH * 0.44), (int) (HEIGHT * 0.007), 450, 180);
//        PatientInfo1.setFont(new Font("Roboto Slab", Font.BOLD, 25));
//        PatientInfo1.setVisible(true);
//        HRPanel.add(PatientInfo1);
//        PatientInfo2 = new JLabel("<html>Check-in: <br> Department: <br> Bed Number:<html>");
//        PatientInfo2.setForeground(GREY);
//        PatientInfo2.setBounds((int) (WIDTH * 0.59), (int) (HEIGHT * 0.007), 450, 180);
//        PatientInfo2.setFont(new Font("Roboto Slab", Font.BOLD, 25));
//        PatientInfo2.setVisible(true);
//        HRPanel.add(PatientInfo2);

    }

    public void displayECGComponents(){

//        // main title format
//        HRTitle = new JLabel("Heart Rate");
//        HRTitle.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.03),900,800);
//        HRTitle.setFont(new Font("Roboto",Font.BOLD, 60));
//        HRTitle.setForeground(BLUE);
//        HRPanel.add(HRTitle);

        // Live display title set-up
        LiveHRTitle = new JLabel("Live Heart Rate (bpm):");
        LiveHRTitle.setForeground(Color.WHITE);
        LiveHRTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.08),450,280);
        LiveHRTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveHRTitle.setVisible(true);
        HRPanel.add(LiveHRTitle);

        // ECG box set-up
        ECGTitle= new JLabel("Live Electrocardiogram:");
        ECGTitle.setForeground(Color.white);
        ECGTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.43),450,280);
        ECGTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        ECGTitle.setVisible(true);
        HRPanel.add(ECGTitle);


    }

    public void actionPerformed(ActionEvent evt) {
        String inputTimeSelect = TimeSelect.getText();
        System.out.println(inputTimeSelect);
        graphWidth = Integer.valueOf(inputTimeSelect);
    }

    // function for ECG graph
    public JPanel createChartPanel() {
        String chartTitle = "ECG Values for Patient:";
        String categoryAxisLabel = "Time";
        String valueAxisLabel = "mV";

        globalDataset = createDataset(new DefaultCategoryDataset());

        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, globalDataset);

        return new ChartPanel(chart);
    }

    public DefaultCategoryDataset createDataset(DefaultCategoryDataset inputDataset) {
        //DefaultCategoryDataset dataset = new DefaultCategoryDataset();


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