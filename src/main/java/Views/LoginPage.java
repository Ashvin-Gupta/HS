package Views;

import Controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage extends JPanel {

    String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    Connection conn= DriverManager.getConnection(dbUrl, "aidanpadraig", "aidanpadraig");

    private JButton logBut;
    private JLabel user;
    private JLabel pass;
    private TextField userfield;
    private JPasswordField passfield;
    private JLabel title;
    public int WIDTH = 1200;
    public int HEIGHT = 800;
    public boolean validLogin = false;

    public LoginPage() throws SQLException {
        setLayout(null);
        displayComponents();
        validateLogin();
    }

    public void displayComponents() {
        title = new JLabel("Health Sentinel");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.1),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.blue);
        add(title);

        user = new JLabel("Username");
        user.setBounds((int) (WIDTH *0.32), (int) (HEIGHT *0.4),150,40);
        user.setForeground(Color.black);
        user.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(user);

        userfield = new TextField(20);
        userfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.4),300,40);
        userfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(userfield);

        pass = new JLabel("Password");
        pass.setBounds((int) (WIDTH *0.32),(int) (HEIGHT *0.5),150,40);
        pass.setForeground(Color.black);
        pass.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(pass);

        passfield = new JPasswordField(20);
        passfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.5),300,40);
        passfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(passfield);


    }

    public void validateLogin() {
        logBut = new JButton("Login");
        logBut.setBounds((int) (WIDTH *0.465),(int) (HEIGHT *0.6),150,60);
        logBut.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        logBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usertext = userfield.getText();
                String passwordtext = String.valueOf(passfield.getPassword());
                System.out.println(passwordtext);

                try {
                    System.out.println("Entered");
                    Statement s = conn.createStatement();
                    System.out.println(">>>>ABC");
                    String sqlStr = "SELECT * FROM doctors WHERE id =1;";
                    System.out.println(">>>>Y");
                    ResultSet rset = s.executeQuery(sqlStr);

                    while (rset.next()) {
                        System.out.println(">>>>X");
                        String usernamedb = rset.getString("username");
                        String passwordnamedb = rset.getString("password");

                        if (usernamedb.equals(usertext) && passwordnamedb.equals(passwordtext)) {
                            System.out.println("Log in successful");
                            validLogin = true;
                            break;
                        }
                    }
                    rset.close();
                    s.close();
                    conn.close();
                }
                catch (Exception ep){}

                if (validLogin == true) {
                    System.out.println("2nd Login successful");
                    try {
                        UIController.launchWelcomePage();
                    } catch (SQLException ex) {}
                }
            }
        });
        add(logBut);
    }
}
