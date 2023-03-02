package assignments.assignment1;

import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // TODO: Implement interface menu utama
        while (true){
            //Print 
            printMenu();
            System.out.print("Pilihan: ");
            int pilihan= input.nextInt();
            input.nextLine();
            
            if (pilihan == 1) {
                System.out.println("================================");
                System.out.print("Masukkan nama Anda: ");
                String nama = input.nextLine();
                String[] space = nama.split(" ");
                String first = space[0];
                System.out.println("Masukkan nomor handphone Anda: ");
                String nomorhp = "";
                boolean numCheck = false;
        
                while (!numCheck) {
                    if (nomorhp.matches("[0-9]+")) {
                        numCheck = true;
                        
                    } else {
                        System.out.println("Nomor hp hanya menerima digit");
                    }
                }
                System.out.println("ID Anda: "+generateId(first, nomorhp));
            } else if (pilihan == 2) {
                System.out.print("Masukkan tanggal terima: ");
                String tanggal = input.nextLine();
            }else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP) {
        int id = 7;
        nama = nama.toLowerCase();
        char[] asciiName = nama.toCharArray();
        char[] asciiNumber = nomorHP.toCharArray();

        for (char c : asciiName) {
            if (Character.isWhitespace(c)) {
                break;
            } else if (!Character.isLetter(c)) {
                c = '7';
            } else {
                c = Character.toLowerCase(c);
                c -= 'a' - 1;
            }
            id += c;
        }

        for (char c : asciiNumber) {
            if (!Character.isDigit(c)) {
                c = '7';
            } else {
                c -= '0';
            }
            id += c;
        }

        String strId = String.format("%02d", id);

        String finalName = nama.split(" ")[0];
        String result = finalName.toUpperCase() + "-" + nomorHP + "-" + strId;

        return result;
    }


    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // TODO: Implement generate nota sesuai soal.
        return null;
    }
}
