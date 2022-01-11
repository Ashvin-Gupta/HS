package Views;

import Controller.UIController;
import Database.DatabaseConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDashboard {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title, name, sex, age, blood,sBP, dBP, alert, RR, HR, temp, HRVal, tempVal;
    private String sbpVal,dbpVal, RRVal;
    public int WIDTH = 1200;
    public int HEIGHT = 800;
    private JPanel mainpanel;
    private JPanel centerpanel;
    private JPanel info;
    private JPanel backpanel;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public PatientDashboard() throws SQLException {
        mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());

        Sidebar sidebar = new Sidebar();

        centerpanel = new JPanel();
        centerpanel.setLayout(new GridLayout(3,3,50,50));
        centerpanel.setPreferredSize(new Dimension(1000,700));
        centerpanel.setBorder(new EmptyBorder(50,50,50,50));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,310));

        backpanel = new JPanel();
        backpanel.setLayout(new BoxLayout(backpanel,BoxLayout.Y_AXIS));
        backpanel.add(info);
        backpanel.add(centerpanel);


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
            HRVal = new JLabel(rs.getString("hr") + " bpm");
            tempVal = new JLabel(rs.getString("temp") + " °C");
        }






        HR = new JLabel("HEART RATE");
        temp = new JLabel("TEMPERATURE");

//        Info Panel at the top
        title = new JLabel("Dashboard");
        title.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.03),400,60);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        info.add(title);

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
        sBP = new JLabel("<html>Systolic BP<br/><br/>" + sbpVal+"</html>");
        sBP.setFont(new Font("Roboto",Font.BOLD, 28));
        sBP.setAlignmentX(JLabel.CENTER);
        sBP.setForeground(BLUE);
        sBP.setBackground(Color.white);
        sBP.setOpaque(true);
        sBP.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(sBP);

        dBP = new JLabel("<html>Diastolic BP<br/><br/>"+ dbpVal+"</html>");
        dBP.setFont(new Font("Roboto",Font.BOLD, 28));
        dBP.setForeground(BLUE);
        dBP.setBackground(Color.white);
        dBP.setOpaque(true);
        dBP.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(dBP);

        alert = new JLabel("Alerts");
        alert.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.2),900,60);
        alert.setFont(new Font("Roboto",Font.BOLD, 24));
        alert.setForeground(RED);
        centerpanel.add(alert);


        RR = new JLabel("<html>Respiration rate<br/><br/>"+RRVal+"</html>");
        RR.setFont(new Font("Roboto",Font.BOLD, 28));
        RR.setForeground(BLUE);
        RR.setBackground(Color.white);
        RR.setOpaque(true);
        RR.setBorder(new EmptyBorder(10,10,10,10));
        centerpanel.add(RR);


        HR.setBounds((int) (WIDTH *0.35),(int) (HEIGHT *0.35),900,60);
        HR.setFont(new Font("Roboto",Font.BOLD, 24));
        HR.setForeground(Color.black);
        centerpanel.add(HR);

        HRVal.setBounds((int) (WIDTH *0.43),(int) (HEIGHT *0.4),900,60);
        HRVal.setFont(new Font("Roboto",Font.BOLD, 35));
        HRVal.setForeground(Color.black);
        centerpanel.add(HRVal);

        temp.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.35),900,60);
        temp.setFont(new Font("Roboto",Font.BOLD, 24));
        temp.setForeground(Color.black);
        centerpanel.add(temp);

        tempVal.setBounds((int) (WIDTH *0.75),(int) (HEIGHT *0.4),900,60);
        tempVal.setFont(new Font("Roboto",Font.BOLD, 35));
        tempVal.setForeground(Color.black);
        centerpanel.add(tempVal);

    }

    public JPanel getmainpanel(){
        return mainpanel;
    }
}
