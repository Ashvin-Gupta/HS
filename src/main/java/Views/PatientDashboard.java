package Views;

import Controller.UIController;
import Database.myDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDashboard implements Launchable{

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    private JLabel title, name, sex, age, blood,sBP, dBP, alert, RR, HR, temp;
    private String sbpVal,dbpVal, RRVal, HRVal, tempVal;
    public int WIDTH = 1200;
    public int HEIGHT = 800;
    private JPanel mainpanel;
    private JPanel centerpanel;
    private JPanel info;
    private JPanel backpanel;
    private JPanel bottompanel;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public PatientDashboard() throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();

        centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(2,3,50,50));
        centerpanel.setPreferredSize(new Dimension(1000,500));
        centerpanel.setBorder(new EmptyBorder(50,50,50,50));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,310));

        backpanel = new JPanel();
        backpanel.setLayout(new BoxLayout(backpanel,BoxLayout.Y_AXIS));

        bottompanel = new JPanel();
        bottompanel.setPreferredSize(new Dimension(1000,380));

        backpanel.add(info);
        backpanel.add(centerpanel);
        backpanel.add(bottompanel);


        displayComponents();

        mainpanel.add(backpanel, BorderLayout.CENTER);
        mainpanel.add(sidebar,BorderLayout.LINE_START);
    }

    public void displayComponents() throws SQLException {

        String sqlStr = "select * from patients where id = 1;";
        PreparedStatement prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();
        while (rs.next()) {
            ///
            //System.out.println("ID: " + rs.getString("id"));
            name = new JLabel(rs.getString("name") +" " + rs.getString("surname"));
            sex = new JLabel("Sex: " + rs.getString("sex"));
            age = new JLabel("Age: "+ rs.getString("age"));
            blood = new JLabel("Blood: " + rs.getString("blood"));
            sbpVal = new String(rs.getString("sbp") + " mmHg");
            dbpVal = new String(rs.getString("dbp") + " mmHg");
            RRVal = new String(rs.getString("rr") + " rpm");
            HRVal = new String(rs.getString("hr") + " bpm");
            tempVal = new String(rs.getString("temp") + " °C");
        }

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

//        Centerpanel
//        Systolic Blood Pressure
        sBP = new JLabel("<html>Systolic BP<br/><br/>" + sbpVal+"</html>");
        sBP.setFont(new Font("Roboto",Font.BOLD, 28));
        sBP.setAlignmentX(JLabel.CENTER);
        sBP.setForeground(BLUE);
        sBP.setBackground(Color.white);
        sBP.setOpaque(true);
        sBP.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(sBP);

//        Diastolic Blood Pressure
        dBP = new JLabel("<html>Diastolic BP<br/><br/>"+ dbpVal+"</html>");
        dBP.setFont(new Font("Roboto",Font.BOLD, 28));
        dBP.setForeground(BLUE);
        dBP.setBackground(Color.white);
        dBP.setOpaque(true);
        dBP.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(dBP);

//        Alerts
        alert = new JLabel("Alerts");
        alert.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.2),900,60);
        alert.setFont(new Font("Roboto",Font.BOLD, 24));
        alert.setForeground(RED);
        centerpanel.add(alert);

//        Respiration Rate
        RR = new JLabel("<html>Respiration rate<br/><br/>"+RRVal+"</html>");
        RR.setFont(new Font("Roboto",Font.BOLD, 28));
        RR.setForeground(BLUE);
        RR.setBackground(Color.white);
        RR.setOpaque(true);
        RR.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(RR);

//        Heart Rate
        HR = new JLabel("<html>Heart Rate<br/><br/>"+HRVal+"</html>");
        HR.setFont(new Font("Roboto",Font.BOLD, 28));
        HR.setForeground(BLUE);
        HR.setBackground(Color.white);
        HR.setOpaque(true);
        HR.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(HR);

//        Temperature
        temp = new JLabel("<html>Temperature<br/><br/>"+tempVal+"</html>");
        temp.setFont(new Font("Roboto",Font.BOLD, 28));
        temp.setForeground(BLUE);
        temp.setBackground(Color.white);
        temp.setOpaque(true);
        temp.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(temp);
    }

    public JPanel getmainpanel(){
        return mainpanel;
    }
}