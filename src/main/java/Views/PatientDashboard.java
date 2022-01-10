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

public class PatientDashboard extends JPanel {

    DatabaseConnection dbConn = new DatabaseConnection();
    Connection conn = dbConn.getConnection();

    private JLabel title, name, sex, age, blood,sBP, dBP, alert, sbpVal, dbpVal, RR, HR, temp, RRVal, HRVal, tempVal;
    public int WIDTH = 1200;
    public int HEIGHT = 800;

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public PatientDashboard() throws SQLException {
        setLayout(null);
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBorder(BorderFactory.createLineBorder(GREY));
        displayComponents();
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

        title = new JLabel("PATIENT DASHBOARD");
        sBP = new JLabel("SYSTOLIC BLOOD PRESSURE");
        dBP = new JLabel("DIASTOLIC BLOOD PRESSURE");
        alert = new JLabel("ALERTS");
        RR = new JLabel("RESPIRATION RATE");
        HR = new JLabel("HEART RATE");
        temp = new JLabel("TEMPERATURE");

        title.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.01),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 30));
        title.setForeground(Color.blue);
        add(title);
        name.setBounds((int) (WIDTH *0.1),(int) (HEIGHT *0.05),900,60);
        name.setFont(new Font("Times New Roman",Font.BOLD, 28));
        name.setForeground(Color.red);
        add(name);
        sex.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.01),900,60);
        sex.setFont(new Font("Times New Roman",Font.BOLD, 20));
        sex.setForeground(Color.black);
        add(sex);
        age.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.04),900,60);
        age.setFont(new Font("Times New Roman",Font.BOLD, 20));
        age.setForeground(Color.black);
        add(age);
        blood.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.07),900,60);
        blood.setFont(new Font("Times New Roman",Font.BOLD, 20));
        blood.setForeground(Color.black);
        add(blood);
        sBP.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.2),900,60);
        sBP.setFont(new Font("Times New Roman",Font.BOLD, 24));
        sBP.setForeground(Color.black);
        add(sBP);
        dBP.setBounds((int) (WIDTH *0.35),(int) (HEIGHT *0.2),900,60);
        dBP.setFont(new Font("Times New Roman",Font.BOLD, 24));
        dBP.setForeground(Color.black);
        add(dBP);
        alert.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.2),900,60);
        alert.setFont(new Font("Times New Roman",Font.BOLD, 24));
        alert.setForeground(Color.red);
        add(alert);
        sbpVal.setBounds((int) (WIDTH *0.08),(int) (HEIGHT *0.25),900,60);
        sbpVal.setFont(new Font("Times New Roman",Font.BOLD, 35));
        sbpVal.setForeground(Color.black);
        add(sbpVal);
        dbpVal.setBounds((int) (WIDTH *0.43),(int) (HEIGHT *0.25),900,60);
        dbpVal.setFont(new Font("Times New Roman",Font.BOLD, 35));
        dbpVal.setForeground(Color.black);
        add(dbpVal);
        RR.setBounds((int) (WIDTH *0.02),(int) (HEIGHT *0.35),900,60);
        RR.setFont(new Font("Times New Roman",Font.BOLD, 24));
        RR.setForeground(Color.black);
        add(RR);
        RRVal.setBounds((int) (WIDTH *0.08),(int) (HEIGHT *0.4),900,60);
        RRVal.setFont(new Font("Times New Roman",Font.BOLD, 35));
        RRVal.setForeground(Color.black);
        add(RRVal);
        HR.setBounds((int) (WIDTH *0.35),(int) (HEIGHT *0.35),900,60);
        HR.setFont(new Font("Times New Roman",Font.BOLD, 24));
        HR.setForeground(Color.black);
        add(HR);
        HRVal.setBounds((int) (WIDTH *0.43),(int) (HEIGHT *0.4),900,60);
        HRVal.setFont(new Font("Times New Roman",Font.BOLD, 35));
        HRVal.setForeground(Color.black);
        add(HRVal);
        temp.setBounds((int) (WIDTH *0.7),(int) (HEIGHT *0.35),900,60);
        temp.setFont(new Font("Times New Roman",Font.BOLD, 24));
        temp.setForeground(Color.black);
        add(temp);
        tempVal.setBounds((int) (WIDTH *0.75),(int) (HEIGHT *0.4),900,60);
        tempVal.setFont(new Font("Times New Roman",Font.BOLD, 35));
        tempVal.setForeground(Color.black);
        add(tempVal);

    }
}
