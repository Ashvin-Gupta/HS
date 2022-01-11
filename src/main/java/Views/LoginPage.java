package Views;

import Controller.UIController;
import Database.myDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class LoginPage extends JPanel {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);

    private JLabel title;
    private JLabel user;
    private TextField userfield;
    private JLabel pass;
    private JPasswordField passfield;
    private JButton logBut;
    private JButton account;
    public boolean validLogin = false;

    public LoginPage() throws SQLException {
        displayComponents();
        validateLogin();
        createAccount();
    }


    public void displayComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets =  new Insets(5,0,20,0);

        title = new JLabel("HealthSentinel");
        title.setFont(new Font("Roboto",Font.BOLD, 72));
        title.setForeground(BLUE);
        add(title,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        user = new JLabel("Username");
        user.setForeground(RED);
        user.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(user,gbc);

        userfield = new TextField();
        userfield.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(userfield,gbc);

        pass = new JLabel("Password");
        pass.setForeground(RED);
        pass.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(pass,gbc);

        passfield = new JPasswordField();
        passfield.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(passfield,gbc);

    }

    public void validateLogin() {
        logBut = new JButton("Login");
        logBut.setFont(new Font("Roboto",Font.PLAIN, 40));
        logBut.setForeground(BLUE);
        logBut.setMargin(new Insets(6,6,6,6));
        logBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usertext = userfield.getText();
                String passwordtext = String.valueOf(passfield.getPassword());

                try {
                    System.out.println("Entered");
                    Statement s = conn.createStatement();
                    System.out.println(">>>>ABC");
                    String sqlStr = "SELECT * FROM doctors WHERE id >0;";
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

                if (validLogin) {
                    System.out.println("2nd Login successful");
                    try {
                        UIController.launchWelcomePage();
                    } catch (SQLException ex) {}
                }
            }
        });
        add(logBut);
    }


    private void createAccount() {
        account = new JButton("Create Account");
        account.setFont(new Font("Roboto",Font.PLAIN, 40));
        account.setForeground(BLUE);
        account.setMargin(new Insets(6,6,6,6));
        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIController.launchAccountPage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(account);
    }
}
