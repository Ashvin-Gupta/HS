package Views;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;


// imports for graph

import Database.myDB;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import static javax.swing.SwingConstants.CENTER;

public class BodyTempPage implements ActionListener,Launchable {
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    int WIDTH = 1200;
    int HEIGHT = 800;

    //   static GraphicsConfiguration gc;
    //   static JFrame MainFrame = new JFrame(gc);
    private JPanel mainpanel;
    private JPanel BTPanel;
    private JLabel BTTitle; // main title
    private JLabel LiveBTBox;
    private JLabel LiveBTTitle;
    private JLabel graphBox;
    private JLabel TimeSelectBox;
    private JLabel TimeSelectTitle;
    private JLabel PatientName;
    private JLabel BTupdated;
    private JTextField TimeSelect;
    private int BTdataPoint = 0;
    private float[] TList;
    private String TString;
    private int graphWidth = 10;
    public JPanel newTempGraph;
    private CardLayout card;
    private JPanel cardPanel;
    public DefaultCategoryDataset globalDataset;

    // colours
    protected final Color RED = new Color(195, 60, 86);
    protected final Color BLUE = new Color(37, 78, 112);
    protected final Color GREY = new Color(159, 159, 159);

    public BodyTempPage(int patientid) throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        BTPanel = new JPanel();
        BTPanel.setLayout(null);
        BTPanel.setPreferredSize(new Dimension(1000, 800));

        JPanel sidebar = new Sidebar(patientid);

        displayECGComponents();
        displayStandardComponents1(patientid);    // add display components to HR panel
        getPatientInfo(patientid);

        mainpanel.add(BTPanel, BorderLayout.CENTER);
        mainpanel.add(sidebar, BorderLayout.LINE_START);
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
        BTPanel.add(PatientName);

        String sexstring = rs.getString("sex");
        JLabel sex = new JLabel("Sex: "+sexstring);
        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.03),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        BTPanel.add(sex);

        String agestring = rs.getString("age");
        JLabel age = new JLabel("Age: "+agestring);
        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.065),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 22));
        age.setForeground(Color.black);
        BTPanel.add(age);

        String bloodstring = rs.getString("blood");
        JLabel blood = new JLabel("Blood: "+bloodstring);
        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.1),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 22));
        blood.setForeground(Color.black);
        BTPanel.add(blood);

    }

    public void displayStandardComponents1(int patientid) throws SQLException {

        String stringpatientID = String.valueOf(patientid);
        String sqlStr = "select * from patients where id = " + stringpatientID;
        PreparedStatement prpStm = null;
        prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        while (rs.next()){
            TString = rs.getString("temp");
        }
        TList = StringToInt(TString);


        // main title format
        BTTitle = new JLabel("Body Temperature");
        BTTitle.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),500,60);
        BTTitle.setFont(new Font("Roboto",Font.BOLD, 50));
        BTTitle.setForeground(BLUE);
        BTPanel.add(BTTitle);

        // updating HR and inner white box
        BTupdated = new JLabel(" ");
        BTupdated.setFont(new Font("Roboto", Font.BOLD, 40));
        BTupdated.setHorizontalTextPosition(CENTER);
        BTupdated.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.29), 410, 150);
        BTupdated.setForeground(Color.black);
        BTupdated.setOpaque(true);
        BTupdated.setBackground(Color.WHITE);
        BTupdated.setVisible(true);
        BTPanel.add(BTupdated);


        // Live display box set-up
        LiveBTBox = new JLabel(" ");
        LiveBTBox.setForeground(BLUE);
        LiveBTBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.20), 450, 270);
        LiveBTBox.setBackground(BLUE);
        LiveBTBox.setOpaque(true);
        BTPanel.add(LiveBTBox);

        // time selection and inner white box
        TimeSelect = new JTextField("", 20);
        TimeSelect.setBounds((int) (WIDTH * 0.46), (int) (HEIGHT * 0.29), 410, 150);
        TimeSelect.setForeground(BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        BTPanel.add(TimeSelect);

        // Time Selection title set-up
        TimeSelectTitle = new JLabel("Select to view past _ seconds:");
        TimeSelectTitle.setForeground(Color.WHITE);
        TimeSelectTitle.setBounds((int) (WIDTH * 0.46), (int) (HEIGHT * 0.08), 450, 280);
        TimeSelectTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        TimeSelectTitle.setVisible(true);
        BTPanel.add(TimeSelectTitle);

        // Time Selection box set-up
        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH * 0.44), (int) (HEIGHT * 0.2), 450, 270);
        TimeSelectBox.setBackground(BLUE);
        TimeSelectBox.setOpaque(true);
        BTPanel.add(TimeSelectBox);

        // timer for HRUpdated
        Timer timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                BTdataPoint = (BTdataPoint + 1) % TList.length;
//                ECGdataPoint = (ECGdataPoint + 1) % ECGList.length;
                BTupdated.setText(String.valueOf(TList[BTdataPoint]));
                BTupdated.setHorizontalTextPosition(CENTER);
                // card layout to update graph and control layers
                cardPanel = new JPanel();
                cardPanel.setBounds((int) (WIDTH * 0.04), (int) (HEIGHT * 0.64), 900, 220);
                card = new CardLayout();
                cardPanel.setLayout(card);
                newTempGraph = createChartPanel();
                newTempGraph.setOpaque(true);
                newTempGraph.setVisible(true);
                newTempGraph.setBounds((int) (WIDTH * 0.135), (int) (HEIGHT * 0.67), 900, 220);
                cardPanel.add(newTempGraph);
                cardPanel.setVisible(true);
                card.next(cardPanel);
                BTPanel.add(cardPanel);
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

        // graph box set-up
        graphBox = new JLabel(" ");
        graphBox.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.57), 950, 300);
        graphBox.setBackground(BLUE);
        graphBox.setOpaque(true);
        BTPanel.add(graphBox);

    }

    public void displayECGComponents(){

        // Live display title set-up
        LiveBTTitle = new JLabel("Live Body Temperature (Celsius):");
        LiveBTTitle.setForeground(Color.WHITE);
        LiveBTTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.08),450,280);
        LiveBTTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        LiveBTTitle.setVisible(true);
        BTPanel.add(LiveBTTitle);

        // graph box title
        BTTitle= new JLabel("Live body temperature over the last ___ seconds:");
        BTTitle.setForeground(Color.white);
        BTTitle.setBounds((int) (WIDTH *0.04),(int) (HEIGHT *0.43),550,280);
        BTTitle.setFont(new Font("Roboto", Font.BOLD, 20));
        BTTitle.setVisible(true);
        BTPanel.add(BTTitle);


    }

    public void actionPerformed(ActionEvent evt) {
        String inputTimeSelect = TimeSelect.getText();
        JLabel timeSelectHint = new JLabel("Please enter a value less than 20.");
        if (Integer.valueOf(inputTimeSelect)> 20)
        {
            // ignore new input
            graphWidth = 0;

            // display hint below
            timeSelectHint.setForeground(Color.BLACK);
            timeSelectHint.setBackground(Color.WHITE);
            timeSelectHint.setBounds((int)(WIDTH *0.02),(int)(HEIGHT *0.57),950,300);
            timeSelectHint.setFont(new Font("Roboto",Font.BOLD, 30));
            timeSelectHint.setVisible(true);
            timeSelectHint.setOpaque(true);
            cardPanel.add(timeSelectHint);
            card.next(cardPanel);

            System.out.println("Please enter a value less than 20 seconds.");
        }
        else {
            cardPanel.add(timeSelectHint);
            card.next(cardPanel);
            System.out.println(inputTimeSelect);
            graphWidth = Integer.valueOf(inputTimeSelect);
        }
    }

    // function for ECG graph
    public JPanel createChartPanel() {
        String chartTitle = "Temperature Values for Patient:";
        String categoryAxisLabel = "Time";
        String valueAxisLabel = "mV";

        globalDataset = createDataset(new DefaultCategoryDataset());

        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, globalDataset);

        return new ChartPanel(chart);
    }

    public DefaultCategoryDataset createDataset(DefaultCategoryDataset inputDataset) {
        for (int i=BTdataPoint-graphWidth;i<BTdataPoint;i=i+1)
        {
            inputDataset.setValue(getHR(i), "Patient", String.valueOf(i));
        }
        return inputDataset;
    }

    public float getHR(int i){
        int new_i = i%TList.length;
        if (i<0){
            return TList[TList.length-1 + new_i];
        }
        else {
            return TList[new_i];
        }
    }

    private static float[] StringToInt(String number){
        String[] string = number.split(",");
        float[] arr = new float[string.length];
        for (int i = 0; i < string.length; i++) {
            arr[i] = Float.valueOf(string[i]);
        }
        return arr;
    }

    public JPanel getmainpanel() {
        return mainpanel;
    }


}
