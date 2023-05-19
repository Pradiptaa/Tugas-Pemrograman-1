package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static assignments.assignment3.nota.NotaManager.toNextDay;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;

    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 160, 10, 10));

        titleLabel = new JLabel("Selamat datang di CuciCuci System!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleToLogin();
            }
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleToRegister();
            }
        });

        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleNextDay();
            }
        });

        dateLabel = new JLabel(String.format("Hari ini : %s\n", fmt.format(cal.getTime())));
        dateLabel.setHorizontalAlignment(JLabel.CENTER);

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        GroupLayout layout = new GroupLayout(mainPanel);

        mainPanel.setLayout(layout);

        // Create a vertical group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addComponent(titleLabel)
              .addComponent(loginButton)
              .addComponent(registerButton)
              .addComponent(toNextDayButton)
              .addComponent(dateLabel);
        layout.setVerticalGroup(vGroup);

        // Create a horizontal group
        GroupLayout.ParallelGroup hGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        hGroup.addComponent(titleLabel)
                .addComponent(loginButton)
                .addComponent(registerButton)
                .addComponent(toNextDayButton)
                .addComponent(dateLabel);
        layout.setHorizontalGroup(hGroup);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame.getInstance().navigateTo("REGISTER");
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame.getInstance().navigateTo("LOGIN");
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        NotaManager.toNextDay();
        dateLabel.setText(String.format("Hari ini : %s\n", fmt.format(cal.getTime())));
        JOptionPane.showOptionDialog(null, "Kamu tidur hari ini... zzz...", "This is Prince Paul's Bubble Party's ability!", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    }
}
