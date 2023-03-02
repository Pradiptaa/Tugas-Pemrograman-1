package assignments.assignment1;

import java.util.Scanner;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        while (true){
            printMenu(); //Mencetak pesan selamat datang dan memanggil method printMenu
            //Meminta input pilihan
            System.out.print("Pilihan: ");
            int pilihan= input.nextInt();
            input.nextLine();
            System.out.println("================================");
            //If statement bila pilihan nomor 1
            if (pilihan == 1) {
                String nama = getFirstName(); //Assign method getFirstName ke variable nama
                String nomorhp = getNumber(); //Assign method getNumber ke variable nomorhp
                System.out.println("ID Anda: "+generateId(nama, nomorhp)); //Mencetak ID dengan memanggil method generate id dengan variable nama dan nomorhp
            
            //Else if statement bila pilihan nomor 2
            } else if (pilihan == 2) {
                String showName = getFirstName(); //Assign method getFirstName ke variable showName
                String showNumber = getNumber(); //Assign method getNumber ke variable showNumber
                //Meminta input tanggal
                System.out.print("Masukkan tanggal terima: ");
                String tanggal = input.nextLine();
                //List untuk paket yang valid
                String[] allowedPakets = {"express", "fast", "reguler"};
                //Membuat boolean untuk loop condition
                boolean test = true;
                //String untuk input paket
                String inputPaket = "";
                while (test) {
                    System.out.print("Masukkan paket laundry: "); //Meminta input paket
                    inputPaket = input.next().toLowerCase(); //lowercase seluruh huruf dalam input
                    //If condition saat input ada dalam list
                    if (Arrays.asList(allowedPakets).contains(inputPaket)) {
                        break; //break loop bila input valid
                    //Else if condition untuk "?" 
                    } else if (inputPaket.equals("?")) {
                        showPaket(); //Memanggil method showPaket untuk memperlihatkan paket yang dapat dipilih
                    //Else condition input tidak valid
                    } else {
                        System.out.println("Paket "+inputPaket+" tidak diketahui");
                        System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                    }
                }

                //Integer untuk input berat
                int inputBerat = 0;
                while (test) {
                    System.out.print("Masukkan berat cucian Anda [Kg]: "); //Meminta input berat cucian
                    //Menggunakan try catch untuk cek validasi input
                    try {
                        inputBerat = input.nextInt();
                        input.nextLine();
                        //If condition apabila input kurang/ sama dengan 0
                        if (inputBerat <= 0) {
                            throw new Exception("Harap masukkan berat cucian Anda dalam bentuk bilangan positif."); //Message untuk meminta input berat cucian yang benar
                        } else {
                            test = false; //Else condition memberhentikan loop
                        }
                    //Catch untuk input selain angka
                    } catch (NumberFormatException ex) {
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan.");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                System.out.println("Nota Laundry "); //Print Judul Nota Laundry
                //Memanggil method generateNota yang didalamnya memanggil method generateID dengan variable showName, showNumber, inputPaket, inputBerat, tanggal
                System.out.println(generateNota(generateId(showName, showNumber), inputPaket, inputBerat, tanggal)); 

            //Else if condition apabila pilihan 0
            } else if (pilihan == 0) {
                System.out.println("Terima kasih telah menggunakan NotaGenerator!"); //Mencetak terimakasih
                break; //break dari loop
            //Else condition apabila input pilihan selain 0, 1, 2
            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali."); //Mencetak pesan perintah salah
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

    //Method untuk generateID
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
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerimaObj = LocalDate.parse(tanggalTerima, formatTanggal);
        int hargaLaundry;
        int hariSelesai;
    
        switch(paket.toLowerCase()){
            case "express":
                hargaLaundry = 12000;
                hariSelesai = 1;
                break;
            case "fast":
                hargaLaundry = 10000;
                hariSelesai = 2;
                break;
            case "reguler":
                hargaLaundry = 7000;
                hariSelesai = 3;
                break;
            default:
                throw new IllegalArgumentException("Paket " + paket + " tidak diketahui");
        }
    
        LocalDate tanggalSelesaiObj = tanggalTerimaObj.plusDays(hariSelesai);
        String tanggalSelesai = tanggalSelesaiObj.format(formatTanggal);
        int totalHarga = hargaLaundry * berat;
    
        return String.format("ID    : %s\nPaket : %s\nHarga :\n%d kg x %d = %d\nTanggal Terima  : %s\nTanggal Selesai : %s",
                id, paket, berat, hargaLaundry, totalHarga, tanggalTerima, tanggalSelesai);
    }
    
    
    public static String getFirstName() {
        System.out.print("Masukkan nama Anda: ");
        String nama = input.nextLine();
        String[] firstName = nama.split(" ");
        return firstName[0];
    }
    public static String getNumber() {
        System.out.print("Masukkan nomor handphone Anda: ");
        String nomorhp = input.nextLine();
        boolean numCheck = false;

        while (!numCheck) {
            if (nomorhp.matches("[0-9]+")) {
                numCheck = true;
                
            } else {
                System.out.println("Nomor hp hanya menerima digit");
            }
        }
        return nomorhp;
    }
}
