package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;
import assignments.assignment4.gui.member.Loginable;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;
    private static boolean thisRole;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // membuat label id dan textfieldnya
        idLabel = new JLabel("Masukan ID Anda:");
        idTextField = new JTextField();

        // membuat label password  dan textfieldnya
        passwordLabel = new JLabel("Masukan password Anda:");
        passwordField = new JPasswordField();

        // membuat tombol login
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // membuat tombol kembali
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {

        // mengatur layout dengan grouplayout
        GroupLayout layout = new GroupLayout(mainPanel);

        mainPanel.setLayout(layout);

        // vertical group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(idLabel)
              .addComponent(idTextField)
              .addComponent(passwordLabel)
              .addComponent(passwordField)
              .addComponent(loginButton)
              .addComponent(backButton);
        layout.setVerticalGroup(vGroup);

        // horiziontal group
        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        hGroup.addComponent(idLabel)
              .addComponent(idTextField)
              .addComponent(passwordLabel)
              .addComponent(passwordField)
              .addComponent(loginButton)
              .addComponent(backButton);
        layout.setHorizontalGroup(hGroup);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);
        idTextField.setText(null);
        passwordField.setText(null);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        if (MainFrame.getInstance().login(idTextField.getText(), new String(passwordField.getPassword())) == true){
            idTextField.setText(null);
            passwordField.setText(null);
            if (thisRole == true){
                MainFrame.getInstance().navigateTo("EMPLOYEE");
            } else {
                MainFrame.getInstance().navigateTo("MEMBER");
            }
        }else {
            JOptionPane.showOptionDialog(null, "ID atau Password invalid", "Invalid ID or Password", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);        
        }
    }

    /**
     * Method untuk menentukan yang login employee atau bukan
     * */
    public static void setRole(Loginable role) {
        if (role instanceof EmployeeSystemGUI){
            thisRole = true;
            return;
        }
        thisRole = false;
    }    
}
