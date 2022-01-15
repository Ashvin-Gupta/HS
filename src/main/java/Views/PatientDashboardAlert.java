package Views;

import Controller.UIController;
import Database.myDB;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PatientDashboardAlert {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    private JLabel title, name, sex, age, blood,sBP, dBP, alert;
    private JButton alerts;
    private String sbpVal,dbpVal, RRVal, HRVal, tempVal;
    private float[] tempInt;
    private float[] sysInt;
    private float[] diaInt;
    private float[] HRInt;
    private float[] RespInt;
    private JButton HR, RR, temp;
    public int WIDTH = 1200;
    public int HEIGHT = 800;
    private JPanel mainpanel;
    private JPanel centerpanel;
    private JPanel info;
    private JPanel backpanel;
    private JPanel bottompanel;
    private int tempDatapoint = 0;
    private int diastolicDatapoint = 0;
    private int systolicDatapoint = 0;
    private int HRDatapoint = 0;
    private int RespDatapoint = 0;
    private float Temp = 0;
    private float Diastolic = 0;
    private float Systolic = 0;
    private float HeartRate = 0;
    private float RespRate = 0;

    private int[] ECGList = {20, 21, 24, 25, 24, 27, 30, 24, 21, 23, 50, 55, 57, 58, 59, 30, 36, 45, 67};
    private int graphWidth = 10;
    public JPanel newECGGraph, cardPanel;
    private CardLayout card;
    public DefaultCategoryDataset globalDataset;
    private int ECGdataPoint = 0;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);
    protected final Color LRED = new Color(213, 91, 126);

    public PatientDashboardAlert(int patientid, String vitalsign, String msg) throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar(patientid);

        centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(2,3,50,50));
        centerpanel.setPreferredSize(new Dimension(1000,500));
        centerpanel.setBorder(new EmptyBorder(0,50,50,50));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,310));

        backpanel = new JPanel();
        backpanel.setLayout(new BoxLayout(backpanel,BoxLayout.Y_AXIS));

        bottompanel = new JPanel();
        bottompanel.setPreferredSize(new Dimension(1000,380));
        bottompanel.setLayout(null);
        bottompanel.setBorder(new EmptyBorder(0,0,10,0));

        backpanel.add(info);
        backpanel.add(centerpanel);
        backpanel.add(bottompanel);

        displayComponents(patientid);
        newAlert(HR,msg,patientid);

        mainpanel.add(backpanel, BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);
    }

    public void displayComponents(int patientid) throws SQLException {
        String stringpatientID = String.valueOf(patientid);
        String sqlStr = "select * from patients where id = " + stringpatientID;
        PreparedStatement prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        System.out.println("RS ENTERING...");
        while (rs.next()) {
            ///
            //System.out.println("ID: " + rs.getString("id"));
            name = new JLabel(rs.getString("name") +" " + rs.getString("surname"));
            sex = new JLabel("Sex: " + rs.getString("sex"));
            age = new JLabel("Age: "+ rs.getString("age"));
            blood = new JLabel("Blood: " + rs.getString("blood"));
            sbpVal = rs.getString("sbp");
            dbpVal = rs.getString("dbp");
            RRVal = rs.getString("rr");
            HRVal = rs.getString("hr");
            tempVal = rs.getString("temp");
        }

        tempInt = StringToInt(tempVal);
        diaInt = StringToInt(dbpVal);
        sysInt = StringToInt(sbpVal);
        RespInt = StringToInt(RRVal);
        HRInt = StringToInt(HRVal);
        System.out.println("ALL STRINGS ADDED TO FLOAT ARRAY");

//        Info Panel at the top
//        Dashboard title
        title = new JLabel("Dashboard");
        title.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),400,60);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        info.add(title);

//        Patient Name
        name.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.1),400,60);
        name.setFont(new Font("Roboto",Font.BOLD, 40));
        name.setForeground(RED);
        info.add(name);

        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.03),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        info.add(sex);

        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.065),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 22));
        age.setForeground(Color.black);
        info.add(age);

        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.1),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 22));
        blood.setForeground(Color.black);
        info.add(blood);

//      Center Panel
        JButton sBP = new JButton("<html>Systolic BP<br/><br/>"+sbpVal+"</html>");
        sBP.setFont(new Font("Roboto",Font.BOLD, 28));
        sBP.setAlignmentX(JButton.CENTER);
        sBP.setForeground(BLUE);
        sBP.setBackground(Color.white);
        sBP.setOpaque(true);
        sBP.setBorder(new EmptyBorder(10,10,10,10));
        sBP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchBloodPresPage(patientid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        centerpanel.add(sBP);

//        Diastolic Blood Pressure
        JButton dBP = new JButton("<html>Diastolic BP<br/><br/>"+dbpVal+"</html>");
        dBP.setFont(new Font("Roboto",Font.BOLD, 28));
        dBP.setAlignmentX(JButton.CENTER);
        dBP.setForeground(BLUE);
        dBP.setBackground(Color.white);
        dBP.setOpaque(true);
        dBP.setBorder(new EmptyBorder(10,10,10,10));
        dBP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchBloodPresPage(patientid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        centerpanel.add(dBP);


//        Alerts
        alerts = new JButton("Alerts");
        alerts.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.2),900,60);
        alerts.setFont(new Font("Roboto",Font.BOLD, 24));
        alerts.setForeground(RED);
        alerts.setBorder(new EmptyBorder(10,10,10,10));
        System.out.println("Patient ID is" + patientid);
        alerts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchAlertsPage(patientid);
                } catch (SQLException ex) {
                    System.out.println("Alerts could not be found...");
                }
            }
        });
        centerpanel.add(alerts);

//        Respiration Rate
        RR = new JButton("<html>Respiration rate<br/><br/>"+RRVal+"</html>");
        RR.setFont(new Font("Roboto",Font.BOLD, 28));
        RR.setForeground(BLUE);
        RR.setBackground(Color.white);
        RR.setOpaque(true);
        RR.setBorder(new EmptyBorder(10,10,10,10));
        RR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchRespPage(patientid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        centerpanel.add(RR);

//        Heart Rate
        HR = new JButton("<html>Heart Rate<br/><br/>"+HRVal+ "</html>");
        HR.setFont(new Font("Roboto",Font.BOLD, 28));
        HR.setForeground(BLUE);
        HR.setBackground(Color.white);
        HR.setOpaque(true);
        HR.setBorder(new EmptyBorder(10,10,10,10));
        HR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchECGPage(patientid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        centerpanel.add(HR);

//        Temperature
        temp = new JButton(" ");
        temp.setFont(new Font("Roboto",Font.BOLD, 28));
        temp.setForeground(BLUE);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        temp.setBorder(new EmptyBorder(10,10,10,10));
        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchBodyTempPage(patientid);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        centerpanel.add(temp);

    }

    public Component getmainpanel() {
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

    public void newAlert(JButton vitalsign, String msg, int patientid) throws SQLException {
        vitalsign.setBackground(RED);
        alerts.setText("<html> Alerts <br>NEW ALERT <html>");
        alerts.setAlignmentX(JButton.CENTER);

        String quote = "'";

        System.out.println("BEFORE SQL SELECT QUERY");
        String sqlStr = "select surname from patients where id="+patientid+";";
        PreparedStatement prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        rs.next();
        String surname = rs.getString("surname");

        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        time.format(formatter);
        String timestring = time.toString();

        System.out.println("JUST BEFORE SQL QUERY INSERT");
        String sqlStr1 = "insert into alerts (patient_id, surname, time, alerttype) values ("+quote+patientid+quote+","+quote+surname+quote+","+quote+timestring+quote+","+quote+msg+quote+");";
        Statement s = conn.createStatement();
        s.execute(sqlStr1);
        s.close();

    }
}
