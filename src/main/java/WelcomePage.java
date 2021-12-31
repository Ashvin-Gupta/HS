import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JPanel{

    private JLabel t;

    public WelcomePage(){
//        setLayout(null);
        t = new JLabel("Holloway end me pls");
        t.setFont(new Font("Times New Roman",Font.BOLD, 60));
        t.setBounds(500,400,200,50);
        t.setForeground(Color.red);
        add(t);

    }
}
