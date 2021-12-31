import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginPage {
    static GraphicsConfiguration gc;
    JFrame frame  = new JFrame(gc);
    String dbUrl = "jdbc:postgresql://localhost:5432/postgres";
    Connection conn= DriverManager.getConnection(dbUrl, "aidanpadraig", "aidanpadraig");
    private JButton logBut;
    private JLabel user;
    private JLabel pass;
    private TextField userfield;
    private JPasswordField passfield;
    private JButton createAccount;
    private JLabel title;
    private double WIDTH = 1200;
    private double HEIGHT = 800;
    private CardLayout cl = new CardLayout();
    JPanel mainpanel = new JPanel();
    JPanel login = new JPanel();

    public LoginPage() throws SQLException {
//        Create frame to add all panels to
        frame.setSize(1200,800);
        frame.setResizable(false);


        WelcomePage welcomepage = new WelcomePage();
        CreateAccountPage createaccountpage = new CreateAccountPage();


        mainpanel.setLayout(cl);
        login.setLayout(null);

//        Text for title
        title = new JLabel("Health Sentinel");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.1),900,60);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.blue);
        login.add(title);

//        Text for username
        user = new JLabel("Username");
        user.setBounds((int) (WIDTH *0.32), (int) (HEIGHT *0.4),150,40);
        user.setForeground(Color.black);
        user.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        login.add(user);

//        User input for username
        userfield = new TextField(20);
        userfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.4),300,40);
        userfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        login.add(userfield);

//        Text for password
        pass = new JLabel("Password");
        pass.setBounds((int) (WIDTH *0.32),(int) (HEIGHT *0.5),150,40);
        pass.setForeground(Color.black);
        pass.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        login.add(pass);

//        User input for password
        passfield = new JPasswordField(20);
        passfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.5),300,40);
        passfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        login.add(passfield);

//        Button for login
//        Button navigates to welcome page if username and password are in database
        logBut = new JButton("Login");
        logBut.setBounds((int) (WIDTH *0.465),(int) (HEIGHT *0.6),150,60);
        logBut.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        logBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usertext = userfield.getText();
                String passwordtext = String.valueOf(passfield.getPassword());
                System.out.println(passwordtext);
                int valid = 0;

                try {
                    System.out.println("Entered");
                    Statement s = conn.createStatement();
                    String sqlStr = "SELECT * FROM doctors WHERE id =1;";
                    ResultSet rset = s.executeQuery(sqlStr);

                    while (rset.next()) {
                        String usernamedb = rset.getString("username");
                        String passwordnamedb = rset.getString("password");
                        if (usernamedb.equals(usertext)){
                            System.out.println("Username successful");
                            valid ++;
                            if (passwordnamedb.equals(passwordtext)){
                                System.out.println("Password successful");
                                valid++;
                            }
                            else{
                                System.out.println("Password failed");
                            }
                        }
                        else{
                            System.out.println("Username failed");
                        }
                    }
                    rset.close();
                    s.close();
                    conn.close();
                }
                catch (Exception ep){}

                if (valid == 2){
                    System.out.println("Login Successful");
                    cl.show(mainpanel,"2");


                }
            }
        });
        login.add(logBut);

        createAccount  = new JButton("Create an account ");
        createAccount.setBounds( (int) (WIDTH *0.38),(int)( HEIGHT*0.7),400,60);
        createAccount.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        createAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(mainpanel,"3");

            }
        });
        login.add(createAccount);




        mainpanel.add(login,"1");
        mainpanel.add(welcomepage,"2");
        mainpanel.add(createaccountpage,"3");

        frame.add(mainpanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



    }
}
