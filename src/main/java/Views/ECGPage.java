package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.SQLException;

import Controller.UIController;
import Database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

// imports for ECG graph
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import static javax.swing.SwingConstants.CENTER;

public class ECGPage implements ActionListener{
    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1000;
    int HEIGHT = 800;

    // static GraphicsConfiguration gc;
    // static JFrame MainFrame = new JFrame(gc);
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
    private int [] HRList = {60, 63, 10, 150, 70, 72, 56, 67, 86, 54, 55, 57, 58, 59, 30, 36, 45, 67};
    private int [] ECGList = {20, 21, 24, 25, 24, 27, 30, 24, 21, 23, 50, 55, 57, 58, 59, 30, 36, 45, 67};
    private int graphWidth = 10;
    public JPanel ECGGraph;
    public JPanel newECGGraph;
    public DefaultCategoryDataset globalDataset;


    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public ECGPage() throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        HRPanel = new JPanel();
        HRPanel.setLayout(null);
        HRPanel.setPreferredSize(new Dimension(1000,800));

        JPanel sidebar = new Sidebar();

        displayComponents();    // add display components to HR panel

        mainpanel.add(HRPanel,BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);
    }

    public void displayComponents() {

        // main title format
        HRTitle = new JLabel("Heart Rate");
        HRTitle.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.04),900,60);
        HRTitle.setFont(new Font("Roboto",Font.BOLD, 60));
        HRTitle.setForeground(BLUE);
        HRPanel.add(HRTitle);

        // patient name format
        PatientName = new JLabel("Ana Lopez");
        PatientName.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.11),900,60);
        PatientName.setFont(new Font("Roboto",Font.BOLD, 40));
        PatientName.setForeground(RED);
        HRPanel.add(PatientName);

        // patient hospital number
        PatientHospNo = new JLabel("Hospital No.: 1234567");
        PatientHospNo.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.17),900,60);
        PatientHospNo.setFont(new Font("Roboto",Font.BOLD, 20));
        PatientHospNo.setForeground(GREY);
        HRPanel.add(PatientHospNo);

        // updating HR and inner white box
        HRupdated = new JLabel(" ");
        HRupdated.setFont(new Font("Roboto",Font.BOLD, 40));
        HRupdated.setHorizontalTextPosition(CENTER);
        HRupdated.setBounds((int) (WIDTH *0.12),(int) (HEIGHT *0.33),410,150);
        HRupdated.setForeground(Color.black);
        HRupdated.setOpaque(true);
        HRupdated.setBackground(Color.WHITE);
        HRupdated.setVisible(true);
        HRPanel.add(HRupdated);

        // Live HR display box set-up
        LiveHRTitle = new JLabel("Live Heart Rate (bpm):");
        LiveHRTitle.setForeground(Color.WHITE);
        LiveHRTitle.setBounds((int) (WIDTH *0.12),(int) (HEIGHT *0.12),450,280);
        LiveHRTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveHRTitle.setVisible(true);
        HRPanel.add(LiveHRTitle);
        LiveHRBox = new JLabel(" ");
        LiveHRBox.setForeground(BLUE);
        LiveHRBox.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.24),450,270);
        LiveHRBox.setBackground(BLUE);
        LiveHRBox.setOpaque(true);
        HRPanel.add(LiveHRBox);

        // time selection and inner white box
        TimeSelect = new JTextField("",20);
        TimeSelect.setBounds((int) (WIDTH *0.64),(int) (HEIGHT *0.33),410,150);
        TimeSelect.setForeground(BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        HRPanel.add(TimeSelect);

        // Time Selection box set-up
        TimeSelectTitle= new JLabel("Select to view past _ seconds:");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (WIDTH *0.65),(int) (HEIGHT *0.12),450,280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        HRPanel.add(TimeSelectTitle);
        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH *0.62),(int) (HEIGHT *0.24),450,270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        HRPanel.add(TimeSelectBox);

        // timer for HRUpdated
        Timer timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ECGdataPoint = (ECGdataPoint+1)%ECGList.length;
                HRdataPoint = (HRdataPoint+1)%HRList.length;
                HRupdated.setText(String.valueOf(HRList[HRdataPoint]));
                HRupdated.setHorizontalTextPosition(CENTER);
                // removes and then creates new chart every time
                // HRPanel.remove(ECGGraph);
                newECGGraph = createChartPanel();
                newECGGraph.setOpaque(true);
                newECGGraph.setVisible(true);
                newECGGraph.setBounds((int) (WIDTH *0.6*ECGdataPoint/5.0),(int) (HEIGHT *0.67),900,230);
                HRPanel.add(newECGGraph);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();


        // ECG box set-up
        ECGTitle= new JLabel("Live Electrocardiogram:");
        ECGTitle.setForeground(Color.black);
        ECGTitle.setBounds((int) (WIDTH *0.15),(int) (HEIGHT *0.47),450,280);
        ECGTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        ECGTitle.setVisible(true);
        HRPanel.add(ECGTitle);
       /* ECGBox = new JLabel(" ");
        ECGBox.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.6),970,300);
        ECGBox.setBackground(BLUE);
        ECGBox.setOpaque(true);
        HRPanel.add(ECGBox); */


        // patient info set-up
        PatientInfo1 = new JLabel("<html> Sex: <br> Age: <br> Blood:<html>");
        PatientInfo1.setForeground(GREY);
        PatientInfo1.setBounds((int) (WIDTH *0.55),(int) (HEIGHT *0.01),450,180);
        PatientInfo1.setFont(new Font("Roboto", Font.BOLD, 20));
        PatientInfo1.setVisible(true);
        HRPanel.add(PatientInfo1);
        PatientInfo2 = new JLabel("<html>Check-in: <br> Department: <br> Bed Number:<html>");
        PatientInfo2.setForeground(GREY);
        PatientInfo2.setBounds((int) (WIDTH *0.75),(int) (HEIGHT *0.01),450,180);
        PatientInfo2.setFont(new Font("Roboto", Font.BOLD, 20));
        PatientInfo2.setVisible(true);
        HRPanel.add(PatientInfo2);



        // ECG graph set up
        /*ECGGraph = createChartPanel();
        ECGGraph.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.6),950,300);
        HRPanel.add(ECGGraph);*/

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