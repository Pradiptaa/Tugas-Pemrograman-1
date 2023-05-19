package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;

import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }


    @Override
    public String getPageName(){
        return KEY;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        return new JButton[]{
            new JButton("It's nyuci time"),
            new JButton("Display List Nota"),

        };
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {

        // handle jika tidak ada nota
        if (NotaManager.notaList.length == 0){
            JOptionPane.showOptionDialog(null, "Belum ada nota", "List Nota", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }

        // loop untuk menampilkan nota
        String receipt = "<html>";

        for (Nota nota : NotaManager.notaList){
            receipt += nota.getNotaStatus() + "<br>";
        }
        receipt += "</html>";

        // JOptionPane untuk menampilkan nota
        JLabel scrollPane = new JLabel(receipt);
        JOptionPane.showMessageDialog(this, scrollPane, "List Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {

        
        JOptionPane.showOptionDialog(null, "Stand back! " + loggedInMember.getNama() + " Beginning to nyuci!" , "Nyuci Time", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        // handle jika tidak ada nota
        if (NotaManager.notaList.length == 0){
            JOptionPane.showOptionDialog(null, "nothing to cuci here ", "Nyuci Results!", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE, null, null, null);
            return;
        }
        
        // loop menampilkan status nota
        String infoNota = "<html>";


        for (Nota nota : NotaManager.notaList){
            infoNota += nota.kerjakan() + "<br>";
        }

        infoNota += "</html>";

        // JOptionPane untuk menampilkan status nota
        JLabel scrollPane = new JLabel(infoNota);
        JOptionPane.showMessageDialog(this, scrollPane, "List Nota", JOptionPane.INFORMATION_MESSAGE);

    }
}
