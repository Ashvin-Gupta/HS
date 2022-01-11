package Views;

import Controller.UIController;
import Database.DatabaseConnection;

import javax.swing.*;
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

    private JLabel title, name, sex, age, blood,sBP, dBP, alert, sbpVal, dbpVal, RR, HR, temp, RRVal, HRVal, tempVal;
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
        centerpanel.setLayout(null);
        centerpanel.setPreferredSize(new Dimension(1000,HEIGHT));

        info = new JPanel();
        info.setLayout(null);
        info.setPreferredSize(new Dimension(1000,200));

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
            sbpVal = new JLabel(rs.getString("sbp") + " mmHg");
            dbpVal = new JLabel(rs.getString("dbp") + " mmHg");
            RRVal = new JLabel(rs.getString("rr") + " rpm");
            HRVal = new JLabel(rs.getString("hr") + " bpm");
            tempVal = new JLabel(rs.getString("temp") + " °C");
        }


        sBP = new JLabel("SYSTOLIC BLOOD PRESSURE");
        dBP = new JLabel("DIASTOLIC BLOOD PRESSURE");
        alert = new JLabel("ALERTS");
        RR = new JLabel("RESPIRATION RATE");
        HR = new JLabel("HEART RATE");
        temp = new JLabel("TEMPERATURE");

        title = new JLabel("Patient Dashboard");
        title.setBounds((int) (WIDTH *0.05),(int) (HEIGHT *0.01),400,60);
        title.setFont(new Font("Roboto",Font.BOLD, 60));
        title.setForeground(BLUE);
        info.add(title);

        name.setBounds((int) (WIDTH *0.05),(int) (HEIGHT *0.11),400,60);
        name.setFont(new Font("Roboto",Font.BOLD, 40));
        name.setForeground(Color.red);
        info.add(name);

        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.01),900,60);
        sex.setFont(new Font("Roboto",Font.BOLD, 22));
        sex.setForeground(Color.black);
        info.add(sex);

        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.04),900,60);
        age.setFont(new Font("Roboto",Font.BOLD, 20));
        age.setForeground(Color.black);
        info.add(age);

        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.07),900,60);
        blood.setFont(new Font("Roboto",Font.BOLD, 20));
        blood.setForeground(Color.black);
        info.add(blood);

        sBP.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.2),900,60);
        sBP.setFont(new Font("Roboto",Font.BOLD, 24));
        sBP.setForeground(Color.black);
        centerpanel.add(sBP);

        dBP.setBounds((int) (WIDTH *0.35),(int) (HEIGHT *0.2),900,60);
        dBP.setFont(new Font("Roboto",Font.BOLD, 24));
        dBP.setForeground(Color.black);
        centerpanel.add(dBP);
        alert.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.2),900,60);
        alert.setFont(new Font("Roboto",Font.BOLD, 24));
        alert.setForeground(Color.red);
        centerpanel.add(alert);
        sbpVal.setBounds((int) (WIDTH *0.08),(int) (HEIGHT *0.25),900,60);
        sbpVal.setFont(new Font("Roboto",Font.BOLD, 35));
        sbpVal.setForeground(Color.black);
        centerpanel.add(sbpVal);
        dbpVal.setBounds((int) (WIDTH *0.43),(int) (HEIGHT *0.25),900,60);
        dbpVal.setFont(new Font("Roboto",Font.BOLD, 35));
        dbpVal.setForeground(Color.black);
        centerpanel.add(dbpVal);
        RR.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.35),900,60);
        RR.setFont(new Font("Roboto",Font.BOLD, 24));
        RR.setForeground(Color.black);
        centerpanel.add(RR);
        RRVal.setBounds((int) (WIDTH *0.08),(int) (HEIGHT *0.4),900,60);
        RRVal.setFont(new Font("Roboto",Font.BOLD, 35));
        RRVal.setForeground(Color.black);
        centerpanel.add(RRVal);
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
