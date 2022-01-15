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
import javax.swing.text.DefaultTextUI;

// imports for graphs
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import static javax.swing.SwingConstants.CENTER;

public class BloodPresPage implements ActionListener, Launchable {
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1200;
    int HEIGHT = 800;

    private JPanel mainpanel;
    private JPanel BPPanel;
    private JLabel BPTitle;
    private JLabel LiveSBPTitle;
    private JLabel SystolicBox;
    private JLabel BPSUpdated;
    private JLabel LiveDBPTitle;
    private JLabel DiastolicBox;
    private JLabel BPDUpdated;
    private JLabel TimeSelectTitle;
    private JLabel TimeSelectBox;

    private JLabel SystolicGraphBox;
    private JLabel SysGraphBoxTitle;
    private JLabel DiastolicGraphBox;
    private JLabel DiasGraphBoxTitle;
    private int BPSdataPoint = 0;
    private int BPDdataPoint = 0;
//    private int [] BPSList= {120, 121, 120, 123, 124, 122, 120, 128, 129, 118};
//    private int [] BPDList= {90, 92, 93, 94, 92, 90, 91, 94};
    private float[] SFloats;
    private String SString;
    private float[] DFloats;
    private String DString;

    public JPanel newBPSGraph;
    public JPanel newBPDGraph;
    private CardLayout cardBPS;
    private CardLayout cardBPD;
    private JPanel cardPanelBPS;
    private JPanel cardPanelBPD;
    public DefaultCategoryDataset BPSDataset;
    public DefaultCategoryDataset BPDDataset;
    private int graphWidth = 10;
    private JTextField TimeSelect;
    private JLabel PatientName, PatientInfo1, PatientInfo2;


    private JLabel TimeInnerSelectBox;


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

        displayBloodPresComponents(patientid);

        getPatientInfo(patientid);

        mainpanel.add(BPPanel, BorderLayout.CENTER);
        mainpanel.add(sidebar, BorderLayout.LINE_START);
    }

    private void displayBloodPresComponents(int patientid) throws SQLException {
        String stringpatientID = String.valueOf(patientid);
        String sqlStr = "select * from patients where id = " + stringpatientID;
        PreparedStatement prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        while (rs.next()){
            SString = rs.getString("sbp");
            DString = rs.getString("dbp");
        }

        SFloats = StringToInt(SString);
        DFloats = StringToInt(DString);

        // main title

        BPTitle = new JLabel("Blood Pressure");
        BPTitle.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.03), 900, 80);
        BPTitle.setFont(new Font("Roboto", Font.BOLD, 60));
        BPTitle.setForeground(BLUE);
        BPPanel.add(BPTitle);

        // Systolic pressure display text and box

        LiveSBPTitle = new JLabel("<html> Live Systolic Blood <br> Pressure: (mmHg) <html>");
        // Systolic pressure text and box

        LiveSBPTitle.setForeground(Color.WHITE);
        LiveSBPTitle.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.09), 450, 280);
        LiveSBPTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveSBPTitle.setVisible(true);
        BPPanel.add(LiveSBPTitle);

        SystolicBox = new JLabel(" ");
        SystolicBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.2), 300, 270);
        SystolicBox.setBackground(BLUE);
        SystolicBox.setOpaque(true);
        BPPanel.add(SystolicBox);

        BPSUpdated = new JLabel(" ");
        BPSUpdated.setFont(new Font("Roboto", Font.BOLD, 40));
        BPSUpdated.setHorizontalTextPosition(CENTER);
        BPSUpdated.setBounds((int) (WIDTH * 0.034), (int) (HEIGHT * 0.31), 260, 150);
        BPSUpdated.setForeground(Color.black);
        BPSUpdated.setOpaque(true);
        BPSUpdated.setBackground(Color.WHITE);
        BPSUpdated.setVisible(true);
        BPPanel.add( BPSUpdated);

        // Diastolic pressure display text and book

        LiveDBPTitle = new JLabel("<html> Live Diastolic Blood <br> Pressure: (mmHg) <html>");
        LiveDBPTitle.setForeground(Color.WHITE);
        LiveDBPTitle.setBounds((int) (WIDTH * 0.3), (int) (HEIGHT * 0.09), 450, 280);
        LiveDBPTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveDBPTitle.setVisible(true);
        BPPanel.add(LiveDBPTitle);

        BPDUpdated = new JLabel(" ");
        BPDUpdated.setFont(new Font("Roboto", Font.BOLD, 40));
        BPDUpdated.setHorizontalTextPosition(CENTER);
        BPDUpdated.setBounds((int) (WIDTH * 0.3), (int) (HEIGHT * 0.31), 260, 150);
        BPDUpdated.setForeground(Color.black);
        BPDUpdated.setOpaque(true);
        BPDUpdated.setBackground(Color.WHITE);
        BPDUpdated.setVisible(true);
        BPPanel.add(BPDUpdated);

        DiastolicBox = new JLabel(" ");
        DiastolicBox.setBounds((int) (WIDTH * 0.285), (int) (HEIGHT * 0.2), 300, 270);
        DiastolicBox.setBackground(BLUE);
        DiastolicBox.setOpaque(true);
        BPPanel.add(DiastolicBox);


        // Time selection box and text

        TimeSelectTitle = new JLabel("<html> Select to view last ___ <br> seconds:<html>");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (WIDTH * 0.57), (int) (HEIGHT * 0.09), 450, 280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        BPPanel.add(TimeSelectTitle);

        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH * 0.55), (int) (HEIGHT * 0.2), 300, 270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        BPPanel.add(TimeSelectBox);

        // Systolic pressure graph box and title

        SysGraphBoxTitle = new JLabel("Live systolic pressure over time:");
        SysGraphBoxTitle.setForeground(Color.WHITE);
        SysGraphBoxTitle.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.43), 450, 280);
        SysGraphBoxTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        SysGraphBoxTitle.setVisible(true);
        BPPanel.add(SysGraphBoxTitle);

        SystolicGraphBox = new JLabel();
        SystolicGraphBox.setForeground(Color.WHITE);
        SystolicGraphBox.setFont(new Font("Roboto", Font.BOLD, 20));
        SystolicGraphBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.57), 450, 300);
        SystolicGraphBox.setBackground(BLUE);
        SystolicGraphBox.setOpaque(true);
        BPPanel.add(SystolicGraphBox);

        // Diastolic pressure graph box and title

        DiasGraphBoxTitle = new JLabel("Live Diastolic pressure over time:");
        DiasGraphBoxTitle.setForeground(Color.WHITE);
        DiasGraphBoxTitle.setBounds((int) (WIDTH * 0.45), (int) (HEIGHT * 0.43), 450, 280);
        DiasGraphBoxTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        DiasGraphBoxTitle.setVisible(true);
        BPPanel.add(DiasGraphBoxTitle);

        DiastolicGraphBox = new JLabel();
        DiastolicGraphBox.setForeground(Color.WHITE);
        DiastolicGraphBox.setFont(new Font("Roboto", Font.BOLD, 20));
        DiastolicGraphBox.setBounds((int) (WIDTH * 0.425), (int) (HEIGHT * 0.57), 450, 300);
        DiastolicGraphBox.setBackground(BLUE);
        DiastolicGraphBox.setOpaque(true);
        BPPanel.add(DiastolicGraphBox);

        // time selection window set up
        TimeSelect = new JTextField("", 20);
        TimeSelect.setBounds((int) (WIDTH * 0.567), (int) (HEIGHT * 0.31), 260, 150);
        TimeSelect.setForeground(BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        BPPanel.add(TimeSelect);

        // graphs set up using timer

        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

                // live number displays:
                BPSdataPoint = (BPSdataPoint + 1) % SFloats.length;
                BPSUpdated.setText(String.valueOf(SFloats[BPSdataPoint]));
                BPSUpdated.setHorizontalTextPosition(CENTER);

                BPDdataPoint = (BPDdataPoint + 1) % DFloats.length;
                BPDUpdated.setText(String.valueOf(DFloats[BPDdataPoint]));
                BPDUpdated.setHorizontalTextPosition(CENTER);

                // systolic graph
                cardPanelBPS = new JPanel();
                cardPanelBPS.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.64), 400, 220);
                newBPSGraph = createChartPanel1();
                cardBPS = new CardLayout();
                cardPanelBPS.setLayout(cardBPS);
                newBPSGraph.setOpaque(true);
                newBPSGraph.setVisible(true);
                newBPSGraph.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.64), 400, 220);
                cardPanelBPS.add(newBPSGraph);
                cardPanelBPS.setVisible(true);
                cardBPS.next(cardPanelBPS);
                BPPanel.add(cardPanelBPS);

               // diastolic graph
                cardPanelBPD = new JPanel();
                cardPanelBPD.setBounds((int) (WIDTH * 0.445), (int) (HEIGHT * 0.64), 400, 220);
                newBPDGraph = createChartPanel2();
                cardBPD = new CardLayout();
                cardPanelBPD.setLayout(cardBPD);
                newBPDGraph.setOpaque(true);
                newBPDGraph.setVisible(true);
                newBPDGraph.setBounds((int) (WIDTH * 0.445), (int) (HEIGHT * 0.64), 400, 220);
                cardPanelBPD.add(newBPDGraph);
                cardPanelBPD.setVisible(true);
                cardBPD.next(cardPanelBPD);
                BPPanel.add(cardPanelBPD);

            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

    }




    // retrieving value from time selection window
    public void actionPerformed(ActionEvent evt) {
        String inputTimeSelect = TimeSelect.getText();
        System.out.println(inputTimeSelect);
        graphWidth = Integer.valueOf(inputTimeSelect);
    }


    // creates panel for graph 1

    public JPanel createChartPanel1() {
        String chartTitle = "Systolic pressure";
        String categoryAxisLabel = "Time";
        String valueAxisLabel = "mmHg";

        BPSDataset = createDataset1(new DefaultCategoryDataset());
        //BPDDataset = createDataset2(new DefaultCategoryDataset());

        JFreeChart chart1 = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, BPSDataset);


        return new ChartPanel(chart1);
    }

    // creates panel for graph 2

    public JPanel createChartPanel2() {
        String chartTitle = "Diastolic Pressure";
        String categoryAxisLabel = "Time";
        String valueAxisLabel = "mmHg";

        BPDDataset = createDataset2(new DefaultCategoryDataset());

        JFreeChart chart2 = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, BPDDataset);

        return new ChartPanel(chart2);
    }

    // creates dataset for graph 1

    public DefaultCategoryDataset createDataset1(DefaultCategoryDataset inputDataset1) {
        //DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i=BPSdataPoint-graphWidth;i<BPSdataPoint;i=i+1)
        {
            inputDataset1.setValue(getBPS(i), "patient data", String.valueOf(i));
            //inputDataset.setValue(getBPD(i), "patient data", String.valueOf(i));
        }

        return inputDataset1;
    }

    // creates dataset for graph 2
    public DefaultCategoryDataset createDataset2(DefaultCategoryDataset inputDataset2) {
        //DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i=BPSdataPoint-graphWidth;i<BPSdataPoint;i=i+1)
        {
            inputDataset2.setValue(getBPD(i), "patient data", String.valueOf(i));
        }

        return inputDataset2;
    }


    public int getBPS(int i){
        int new_i = i%SFloats.length;
        if (i<0){
            return (int) SFloats[SFloats.length-1 + new_i];
        }
        else {
            return (int) SFloats[new_i];
        }
    }

   public int getBPD(int i){
        int new_i = i%DFloats.length;
        if (i<0){
            return (int) DFloats[DFloats.length-1 + new_i];
        }
        else {
            return (int) DFloats[new_i];
        }
    }

    private void getPatientInfo(int patientid) throws SQLException {
        // patient name format
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

    private static float[] StringToInt(String number){
        String[] string = number.split(",");
        float[] arr = new float[string.length];
        for (int i = 0; i < string.length; i++) {
            arr[i] = Float.valueOf(string[i]);
        }
        return arr;
    }

}
