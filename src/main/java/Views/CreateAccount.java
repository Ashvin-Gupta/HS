package Views;

import Controller.UIController;
import Database.myDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateAccount extends JPanel {
    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();

    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);

    private JLabel title;
    private JLabel user;
    private TextField userfield;
    private JLabel pass;
    private TextField passfield;
    private JButton account;

    public CreateAccount() throws SQLException {
        displayComponents();
        makeLogin();

    }

    private void displayComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets =  new Insets(5,0,20,0);

        title = new JLabel("Create Account");
        title.setFont(new Font("Roboto",Font.BOLD, 72));
        title.setForeground(BLUE);
        add(title,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        user = new JLabel("Enter Username");
        user.setForeground(RED);
        user.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(user,gbc);

        userfield = new TextField();
        userfield.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(userfield,gbc);

        pass = new JLabel("Enter Password");
        pass.setForeground(RED);
        pass.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(pass,gbc);

        passfield = new TextField();
        passfield.setFont(new Font("Roboto",Font.PLAIN, 32));
        add(passfield,gbc);
    }

    private void makeLogin() {
        account = new JButton("Create Account");
        account.setFont(new Font("Roboto",Font.PLAIN, 40));
        account.setForeground(BLUE);
        account.setMargin(new Insets(6,6,6,6));
        String quotes = "'";
        account.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usertext = userfield.getText();
                String passwordtext = passfield.getText();
                try{
                    Statement s = conn.createStatement();
//                    ENTER USER INPUT INTO DATABASE
                    String sqlStr = "INSERT INTO doctors (username,password) values (" +quotes + usertext +quotes + ","+quotes+ passwordtext + quotes+ ");";
                    System.out.println(sqlStr);
                    s.executeUpdate(sqlStr);

                    s.close();
                    conn.close();

                }
                catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                try {
                    UIController.launchLoginPage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(account);
    }

}
