package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // membuat label nama dan textfield nama
        nameLabel = new JLabel("Masukan nama Anda:");
        nameTextField = new JTextField();

        // membuat label noHP dan textfield noHP
        phoneLabel = new JLabel("Masukan nomor handphone Anda:");
        phoneTextField = new JTextField();

        // membuat label password dan textfield passowrd
        passwordLabel = new JLabel("Masukan password Anda:");
        passwordField = new JPasswordField();

        // membuat tombol register
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleRegister();
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

        // mengatur layout komponen dengan grouplayout
        GroupLayout layout = new GroupLayout(mainPanel);

        mainPanel.setLayout(layout);

        // vertical group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(nameLabel)
              .addComponent(nameTextField)
              .addComponent(phoneLabel)
              .addComponent(phoneTextField)
              .addComponent(passwordLabel)
              .addComponent(passwordField)
              .addComponent(registerButton)
              .addComponent(backButton);
        layout.setVerticalGroup(vGroup);

        // horizontal group
        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        hGroup.addComponent(nameLabel)
              .addComponent(nameTextField)
              .addComponent(phoneLabel)
              .addComponent(phoneTextField)
              .addComponent(passwordLabel)
              .addComponent(passwordField)
              .addComponent(registerButton)
              .addComponent(backButton);
        layout.setHorizontalGroup(hGroup);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame.getInstance().navigateTo(HomeGUI.KEY);

        // mereset textfield
        nameTextField.setText(null);
        phoneTextField.setText(null);
        passwordField.setText(null);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {


        //handle field nama dan password kosong
        if (nameTextField.getText().equals("")){
            JOptionPane.showOptionDialog(null, "Field nama tidak boleh kosong", "Error", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
        else if (new String(passwordField.getPassword()).equals("")){
            JOptionPane.showOptionDialog(null, "Field password tidak boleh kosong", "Error", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }

        //handle field noHP dan handle input bukan digit
        if (phoneTextField.getText().equals("")){
            JOptionPane.showOptionDialog(null, "Field nomor handphone tidak boleh kosong", "Error", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
        else{
            if ((!phoneTextField.getText().matches("\\d+"))){
            JOptionPane.showOptionDialog(null, "Nomor handphone harus berisi angka", "Invalid Phone Number", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            phoneTextField.setText(null);
            return;
        }
        } 


        Member regis = loginManager.register(nameTextField.getText(), phoneTextField.getText(), new String(passwordField.getPassword()));

        // jika member sudah ada
        if (regis == null){
            JOptionPane.showOptionDialog(null, 
            "User dengan nama " + nameTextField.getText() + " dan nomor hp " + phoneTextField.getText() + " sudah ada!" , "Error", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);

        // jika berhasil membuat member
        } else {
            
            JOptionPane.showOptionDialog(null, "Berhasil membuat user dengan ID " + regis.getId(), "Registration Successful", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

            // mereset textfield
            nameTextField.setText(null);
            phoneTextField.setText(null);
            passwordField.setText(null);
            MainFrame.getInstance().navigateTo("HOME");
        }
    }
}
