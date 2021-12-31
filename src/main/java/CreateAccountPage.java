import javax.swing.*;
import java.awt.*;

public class CreateAccountPage extends JPanel {
    private JLabel title;
    private JLabel user;
    private JLabel pass;
    private TextField userfield;
    private TextField passfield;
    private JButton confirm;
    private int WIDTH = 1200;
    private int HEIGHT = 800;

    public CreateAccountPage(){
        setLayout(null);
        setSize(1200,800);
        //        Text for title
        title = new JLabel("<html>Enter Username <br/> and Password");
        title.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.05),900,200);
        title.setFont(new Font("Times New Roman",Font.BOLD, 60));
        title.setForeground(Color.black);
        add(title);

//        Text for username
        user = new JLabel("Username");
        user.setBounds((int) (WIDTH *0.32), (int) (HEIGHT *0.4),150,40);
        user.setForeground(Color.black);
        user.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(user);

//        User input for username
        userfield = new TextField(20);
        userfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.4),300,40);
        userfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(userfield);

//        Text for password
        pass = new JLabel("Password");
        pass.setBounds((int) (WIDTH *0.32),(int) (HEIGHT *0.5),150,40);
        pass.setForeground(Color.black);
        pass.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(pass);

//        User input for password
        passfield = new TextField(20);
        passfield.setBounds((int) (WIDTH *0.5),(int) (HEIGHT *0.5),300,40);
        passfield.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(passfield);

        confirm = new JButton("Confirm");
        confirm.setBounds((int) (WIDTH *0.465),(int) (HEIGHT *0.6),150,60);
        confirm.setFont(new Font("Times New Roman",Font.PLAIN, 32));
        add(confirm);

    }
}
