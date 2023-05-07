package assignments.assignment3.user.menu;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
// import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment1.NotaGenerator;
import java.util.Arrays;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice) {
        boolean logout = false;
        switch (choice) {
            case 1:
                createNota();
                System.out.println("Nota berhasil dibuat!");
                break;
            case 2:
                for (Nota nota : super.loginMember.getNotaList()){
                    System.out.println(nota);
                }
                break;
            case 3:
                logout = true;
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                break;
        }
        return logout;
    }
    
    private void createNota() {
        String paket;
        NotaGenerator.showPaket();
        while (true) { // menggunakan code dari Nota Generator
            System.out.println("Masukkan paket laundry: ");
            paket = super.in.nextLine();
            String paketLower = paket.toLowerCase();
            if (paketLower.equals("express") || paketLower.equals("reguler") || paketLower.equals("fast") || paketLower.equals("?")) {
                if (paketLower.equals("?")) {
                    NotaGenerator.showPaket();
                } else {
                    break;
                }
            } else {
                System.out.println("Paket " + paketLower + " tidak diketahui "); // jika jenis paket tidak diketahui
                System.out.println("[ketik ? untuk mencari tahu jenis paket] ");
            }
        }
        System.out.println("Masukkan berat cucian Anda [Kg]:"); // meminta input berat cucian
        int beratInt;
        while (true) { // menghandle error cucian dengan menggunakan code dari Nota Generator
            String berat = super.in.nextLine();
            if (berat.matches("^[0-9]+$")) {//mengecek apakah string yang diinput digit atau bukan
                // regex untuk kode digit
                beratInt = Integer.parseInt(berat);//mengubah string ke dalam integer
                //berat cucian < 2 dan > 0
                if (beratInt == 1) {
                    System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                    beratInt = 2;
                    break;
                } else if (beratInt >= 2) {//berat cucian sudah valid
                    break;
                } else {//berat cucian < 1
                    System.out.println("Harap masukkan berat cucian Anda dalam bilangan positif.");
                }
            } else {
                System.out.println("Harap masukkan berat cucian Anda dalam bilangan positif.");
            }
        }
    
        //membuat nota baru
        Nota nota = new Nota(super.loginMember, beratInt, paket, NotaManager.fmt.format(NotaManager.cal.getTime()));
        nota.addService(new CuciService()); // menambahkan service cuci ke nota
    
        //menambahkan setrika service
        System.out.print("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?\nHanya tambah 1000 / kg :0\n[Ketik x untuk tidak mau]: ");
        String setrika = super.in.nextLine();
        if (!setrika.equalsIgnoreCase("x")) {
            nota.addService(new SetrikaService());
        }
    
        //menambahkan antar service
        System.out.print("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!\nCuma 2000 / 4kg, kemudian 500 / kg\n[Ketik x untuk tidak mau]: ");
        String antar = super.in.nextLine();
        if (!antar.equalsIgnoreCase("x")) {
            nota.addService(new AntarService());
        }

        super.loginMember.addNota(nota); // menambahkan nota ke list nota member
        NotaManager.addNota(nota); // menambahkan nota ke list NotaManager

    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) {
        Member[] temp = Arrays.copyOf(memberList, memberList.length + 1);
        temp[memberList.length] = member;
        memberList = temp;
    }
}