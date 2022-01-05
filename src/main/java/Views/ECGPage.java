package Views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import Controller.UIController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ECGPage implements ActionListener{


    int WIDTH = 1000;
    int HEIGHT = 800;

    static GraphicsConfiguration gc;
    static JFrame MainFrame = new JFrame(gc);
    private JPanel HRPanel;
    private JPanel HRPanel1;
    private JLabel LiveHRBox;
    private JPanel ECGBox;
    private JLabel TimeSelectBox;
    private JLabel TimeSelectWhite;
    private JLabel HRTitle;
    private JLabel PatientName;
    private JLabel HRupdated;
    private JTextField TimeSelect;
    private int HR = 0;


    protected final Color RED = new Color(195,60,86);
    protected final Color BLUE  = new Color(37,78,112);
    protected final Color GREY = new Color(159,159,159);

    public ECGPage(){
        HRPanel = new JPanel();
        HRPanel.setLayout(null);
        HRPanel.setPreferredSize(new Dimension(1000,800));

        displayComponents();    // add display components to HR panel

        // add HR panel to main frame
        MainFrame.setSize(new Dimension(WIDTH, HEIGHT));
        MainFrame.getContentPane().add(HRPanel);
        MainFrame.setVisible(true);

    }

    public void displayComponents() {
        // main title format
        HRTitle = new JLabel("Heart Rate");
        HRTitle.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.06),900,60);
        HRTitle.setFont(new Font("Roboto",Font.BOLD, 60));
        HRTitle.setForeground(Color.BLUE);
        HRPanel.add(HRTitle);

        // patient name format
        PatientName = new JLabel("Ana Lopez");
        PatientName.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.12),900,60);
        PatientName.setFont(new Font("Roboto",Font.BOLD, 40));
        PatientName.setForeground(Color.RED);
        HRPanel.add(PatientName);

        // Live HR display box set-up
        LiveHRBox = new JLabel(" ");
        LiveHRBox.setForeground(Color.BLUE);
        LiveHRBox.setBounds((int) (WIDTH *0.37),(int) (HEIGHT *0.22),450,280);
        LiveHRBox.setBackground(Color.BLUE);
        LiveHRBox.setOpaque(true);
        HRPanel.add(LiveHRBox);

        // Time Selection box set-up
        TimeSelectBox = new JLabel(" ");
        TimeSelectBox.setBounds((int) (WIDTH *0.87),(int) (HEIGHT *0.22),450,280);
        TimeSelectBox.setBackground(Color.BLUE);
        TimeSelectBox.setOpaque(true);
        HRPanel.add(TimeSelectBox);

        // time selection and inner white box
        TimeSelect = new JTextField("default text",20);
        TimeSelect.setBounds((int) (WIDTH *0.9),(int) (HEIGHT *0.25),390,230);
        TimeSelect.setForeground(Color.BLUE);
        TimeSelect.setOpaque(true);
        TimeSelect.setBackground(Color.WHITE);
        TimeSelect.setVisible(true);
        TimeSelect.addActionListener((ActionListener) this);
        HRPanel.add(TimeSelect);

        // updating HR and inner white box
        HRupdated = new JLabel("Hello");
        HRupdated.setBounds((int) (WIDTH *0.396),(int) (HEIGHT *0.25),390,230);
        HRupdated.setForeground(Color.BLUE);
        HRupdated.setOpaque(true);
        HRupdated.setBackground(Color.WHITE);
        HRupdated.setVisible(true);
        HRPanel.add(HRupdated);

        Timer timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                HR = HR+1;
                HRupdated.setText(String.valueOf(HR));
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

    }
    public void actionPerformed(ActionEvent evt) {
        String text = TimeSelect.getText();
        System.out.println(text);
    }
    public JPanel getmainpanel() {
        return HRPanel;
    }
}
