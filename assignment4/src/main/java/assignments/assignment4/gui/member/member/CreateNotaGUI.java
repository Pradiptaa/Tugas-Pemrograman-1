package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // membuat label-label
        paketLabel = new JLabel("Paket Laundry:");
        beratLabel = new JLabel("Berat Cucian (Kg):");

        // membuat textfield berat dan combobox paket
        beratTextField = new JTextField();
        paketComboBox = new JComboBox<>(new String[]{"Express", "Fast", "Reguler"});

        // membuat tombol showpaket
        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPaket();
            }
        });

        // membuat checkbox-checkbox
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/Kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/4Kg pertama, kemudian 500/Kg)");

        // membuat tombol buat nota
        createNotaButton = new JButton("Buat Nota");
        createNotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNota();
            }
        });

        // membua tombol kembali
        backButton = new JButton("Kembali");
        backButton.setPreferredSize(new Dimension(140, 40));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleBack();
            }
        });


        // Set up main panel, Feel free to make any changes
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

        // horizontal group
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(paketLabel)
                .addComponent(beratLabel)
                .addComponent(setrikaCheckBox)
                .addComponent(antarCheckBox)
                .addComponent(createNotaButton)
                .addComponent(backButton)
                )
              .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(paketComboBox)
                .addComponent(beratTextField)
                )
             .addComponent(showPaketButton);
        layout.setHorizontalGroup(hGroup);

        // vertical group
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(paketLabel)
                .addComponent(paketComboBox)
                .addComponent(showPaketButton)
                )
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(beratLabel)
                .addComponent(beratTextField)
                )
            .addComponent(setrikaCheckBox)
            .addComponent(antarCheckBox)
            .addComponent(createNotaButton)
            .addComponent(backButton);
        layout.setVerticalGroup(vGroup);

    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {

        // handle input berat bukan digit
        if (!(beratTextField.getText().matches("\\d+"))){

            JOptionPane.showOptionDialog(null, "Berat Cucian harus berisi angka", "Error", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
            
        // handle input berat negatif
        } else {

            if (Integer.parseInt(beratTextField.getText()) <= 0){
                JOptionPane.showOptionDialog(null, "Berat Cucian harus berisi bilangan positif", "Error", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
                return;
        }
        }

        int beratInt = 0;
        if (beratTextField.getText().matches("^[0-9]+$")) {//mengecek apakah string yang diinput digit atau bukan
            // regex untuk kode digit
            beratInt = Integer.parseInt(beratTextField.getText());//mengubah string ke dalam integer
            //berat cucian < 2 dan > 0
            if (beratInt == 1) {
                JOptionPane.showOptionDialog(null, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                beratInt = 2;
            } else if (beratInt >= 2) {//berat cucian sudah valid
            } else {//berat cucian < 1
                System.out.println("Harap masukkan berat cucian Anda dalam bilangan positif.");
            }
        } else {
            System.out.println("Harap masukkan berat cucian Anda dalam bilangan positif.");
        }

        Nota notaBaru = new Nota(memberSystemGUI.getLoggedInMember(), beratInt, paketComboBox.getSelectedItem().toString(), fmt.format(cal.getTime()));

        // menambah service setrika
        if (setrikaCheckBox.isSelected()){
            SetrikaService setrika = new SetrikaService();
            notaBaru.addService(setrika);
        }

        // menambah service antar
        if (antarCheckBox.isSelected()){
            LaundryService antar = new AntarService();
            notaBaru.addService(antar);
        }

        memberSystemGUI.getLoggedInMember().addNota(notaBaru);
        NotaManager.addNota(notaBaru);

        int okKlik = JOptionPane.showOptionDialog(null, "Nota berhasil dibuat!", "Success", 
        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        // me reset textfield jika di klik ok
        if (okKlik == 0){
            beratTextField.setText("");
            paketComboBox.setSelectedItem("Express");
            setrikaCheckBox.setSelected(false);
            antarCheckBox.setSelected(false);
        }

    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // mereset teextfield
        beratTextField.setText("");
        paketComboBox.setSelectedItem("Express");
        setrikaCheckBox.setSelected(false);
        antarCheckBox.setSelected(false);
        MainFrame.getInstance().navigateTo("MEMBER");
        
    }
}
