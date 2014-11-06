package Signup;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.Cursor;
import javax.swing.JMenuItem;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JSeparator;
import Test.Register;
import Test.Register;


public class SignupInterface extends JFrame {

    private JPanel contentPane;
    private JTextField txtReallycoolacespassword;
    private JTextField txtSup;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JLabel lblRegistrationTime;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JLabel lblNewLabel;
    private JTextField textField_5;
    private final Action InfoScreen = new SwingAction();
    private Component glue;
    private Component horizontalGlue;
    private Component horizontalStrut;
    private Component horizontalStrut_1;
    private Component horizontalGlue_1;
    private JTextArea txtrInfoScreen;
    private JTextArea txtrInfoScreen2;
    private Component horizontalStrut_2;
    private Component horizontalStrut_3;
    private Component verticalStrut_4;
    private JSeparator separator;
    JScrollPane scrollPane;

    private Register myReg;

    /**
     * Launch the application.
     */
    public static void main (String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run () {
                try {
                    SignupInterface frame = new SignupInterface();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public SignupInterface () {
        setTitle("AutoACES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new CardLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, "name_65180765105292");
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        myReg = new Register();
        myReg.collectLog();
        myReg.addGui(this);

        lblUsername = new JLabel("NetID");
        panel.add(lblUsername);

        txtSup = new JTextField();
        txtSup.setText("sup39");
        panel.add(txtSup);
        txtSup.setColumns(12);

        glue = Box.createGlue();
        panel.add(glue);

        lblPassword = new JLabel("Password");
        panel.add(lblPassword);

        txtReallycoolacespassword = new JTextField();
        txtReallycoolacespassword.setText("CoolACESPassword");
        panel.add(txtReallycoolacespassword);
        txtReallycoolacespassword.setColumns(12);

        Button button = new Button("Begin Registration");
        button.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent arg0) {

                String netID = txtSup.getText();
                String password = txtReallycoolacespassword.getText();

                myReg.setUserAndPass(netID, password);
                System.out.println(netID + " and password of length " + password.length());

                int hour = Integer.parseInt(textField_2.getText());
                int minute = Integer.parseInt(textField_3.getText());
                int second = Integer.parseInt(textField_4.getText());
                int prep = Integer.parseInt(textField_5.getText());

                System.out.println(hour + minute + second);
                myReg.addTimer(hour, minute, second, prep);

                // myAlarm = new Alarm(myReg,hour,minute-prep,second);
                // myAlarm.secondaryAlarm(hour, minute, second);

                txtrInfoScreen.setText("Preparing to sign up " + netID + " with password "
                        + password + "\n At 24 hour time " + hour + ":" + minute + ":" +
                        second + "\n");
               addInfoText("Logging onto aces " + prep+ " minutes early. \n");
            }
        });

        lblRegistrationTime = new JLabel("Registration Time (hour/minute/second)");
        panel.add(lblRegistrationTime);

        textField_2 = new JTextField();
        textField_2.setText("07");
        panel.add(textField_2);
        textField_2.setColumns(3);

        textField_3 = new JTextField();
        textField_3.setText("00");
        panel.add(textField_3);
        textField_3.setColumns(3);

        textField_4 = new JTextField();
        textField_4.setText("00");
        panel.add(textField_4);
        textField_4.setColumns(3);

        lblNewLabel = new JLabel("Prep Time (minutes)");
        panel.add(lblNewLabel);

        textField_5 = new JTextField();
        textField_5.setText("02");
        panel.add(textField_5);
        textField_5.setColumns(3);

        horizontalGlue = Box.createHorizontalGlue();
        panel.add(horizontalGlue);

        horizontalStrut_1 = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut_1);

        horizontalStrut = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut);

        horizontalGlue_1 = Box.createHorizontalGlue();
        panel.add(horizontalGlue_1);

        horizontalStrut_2 = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut_2);

        horizontalStrut_3 = Box.createHorizontalStrut(20);
        panel.add(horizontalStrut_3);
        panel.add(button);

        separator = new JSeparator();
        panel.add(separator);

        txtrInfoScreen2 = new JTextArea();
        txtrInfoScreen2.setEditable(false);
        txtrInfoScreen2.setColumns(10);
        txtrInfoScreen2
                .setText("Made by Matthew Roy \n Start after midnight "
                         +
                         "(on the day of registration) \n Do not press \"Begin Registration\" twice \n");
        txtrInfoScreen2.setRows(3);
        panel.add(txtrInfoScreen2);

        txtrInfoScreen = new JTextArea(23, 37);
        scrollPane = new JScrollPane(txtrInfoScreen);
        txtrInfoScreen.setEditable(false);
        txtrInfoScreen
                .setText("Info Screen: Enter your netID and password \n It logs into aces at time - prep time");
        panel.add(scrollPane);

    }

    public void addInfoText (String info) {
        txtrInfoScreen.append(info);
    }

    private class SwingAction extends AbstractAction {
        public SwingAction () {
            putValue(NAME, "SwingAction");
            putValue(SHORT_DESCRIPTION, "Some short description");
        }

        public void actionPerformed (ActionEvent e) {
        }
    }
}
