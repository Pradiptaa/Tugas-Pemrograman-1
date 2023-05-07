//=======Tugas Pemrograman 1=======\\
//Nama: Pradipta Arya Pramudita    \\
//NPM: 2206083685                  \\
//Kelas: DDP 2-F                   \\
//Kode Asdos: JHN                  \\
//=================================\\

package assignments.assignment1;

//Import yang diperlukan
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
                        input.nextLine(); //diatas
                        inputBerat = input.nextInt();
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
    public static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    //Method untuk generateID
    public static String generateId(String nama, String nomorHP) {
        int id = 7; //Set initial variable id ke nilai 7
        nama = nama.toLowerCase(); //Mengubah string nama menjadi huruf kecil
        char[] asciiName = nama.toCharArray(); //Mengubah string nama menjadi character array
        char[] asciiNumber = nomorHP.toCharArray(); //Mengubah string nomorhp menjadi character array
        //Loop untuk mengambil setiap character nama
        for (char c : asciiName) {
            //Menghentikan loop jika character merupakan spasi
            if (Character.isWhitespace(c)) {
                break;
            //Mengubah character menjadi 7 jika character bukan merupakan huruf
            } else if (!Character.isLetter(c)) {
                c = '7';
            //Mengubah character menjadi huruf kecil dan dikurangi a-1 jika character merupakan huruf
            } else {
                c = Character.toLowerCase(c);
                c -= 'a' - 1;
            }
            //Hasil ditambahkan ke id
            id += c;
        }
        //Loop untuk nomor hp
        for (char c : asciiNumber) {
            //Jika character bukan merupakan digit maka diubah menjadi 7
            if (!Character.isDigit(c)) {
                c = '7';
            //Jika character merupakan digit maka character dikurangi dengan '0'
            } else {
                c -= '0';
            }
            //Menambahkan hasil ke ID
            id += c;
        }
        //Konversi variable id menjadi string dengan format 2 digit
        String strId = String.format("%02d", id);
        if (strId.length()>2) {
            strId=strId.substring(strId.length()-2);
        }

        //Mengambil nama index 0 dan menyimpan ke variable finalName
        String finalName = nama.split(" ")[0];
        //Mengubah nama akhir menjadi kapital dan menambahkan nomor serta id
        String result = finalName.toUpperCase() + "-" + nomorHP + "-" + strId;
        //Mengembalikan variable result
        return result;
    }

    //Method untuk generate nota
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        //Format tanggal ke hari/bulan/tahun
        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //Localdate dengan format yang ditentukan
        LocalDate tanggalTerimaObj = LocalDate.parse(tanggalTerima, formatTanggal);
        //Assign variable integer
        int hargaLaundry;
        int hariSelesai;
        //Switch statement untuk set harga dan hari mengerjakan sesuai input paket yang ditentukan
        switch(paket.toLowerCase()){
            case "express": //Case input express
                hargaLaundry = 12000;
                hariSelesai = 1;
                break; 
            case "fast": //Case input fast
                hargaLaundry = 10000;
                hariSelesai = 2;
                break;
            case "reguler": //Case input reguler
                hargaLaundry = 7000;
                hariSelesai = 3;
                break;
            default: //Mengalihkan input lain dan mencetak pesan gagal
                throw new IllegalArgumentException("Paket " + paket + " tidak diketahui");
        }
        //Menambahkan jumlah hari ke LocalDate tanggal sebelumnya
        LocalDate tanggalSelesaiObj = tanggalTerimaObj.plusDays(hariSelesai);
        //Set format tanggal selesai ke format tanggal yang sudah ditentukan
        String tanggalSelesai = tanggalSelesaiObj.format(formatTanggal);

        //Menghitung total harga dan assign ke variable totalHarga
        int totalHarga = hargaLaundry * berat;
        
        //Formatting output dengan string format
        return String.format("ID    : %s\nPaket : %s\nHarga :\n%d kg x %d = %d\nTanggal Terima  : %s\nTanggal Selesai : %s",
                id, paket, berat, hargaLaundry, totalHarga, tanggalTerima, tanggalSelesai);
    }
    
    //Method untuk memisahkan nama depan
    public static String getFirstName() {
        System.out.print("Masukkan nama Anda: "); //Meminta input nama
        String nama = input.nextLine();
        String[] firstName = nama.split(" "); //Split input nama
        return firstName[0]; //Mengembalikan nama pada index ke-0
    }

    //Method untuk numCheck nomor hp
    public static String getNumber() {
        System.out.print("Masukkan nomor handphone Anda: "); //Meminta input nomor hp
        String nomorhp = input.nextLine();
        boolean numCheck = false; //Boolean untuk loop

        while (!numCheck) {
            //If condition untuk input angka
            if (nomorhp.matches("[0-9]+")) {
                numCheck = true; //Mengganti boolean numCheck menjadi true
            //Else condition apabila input selain angka
            } else {
                System.out.println("Nomor hp hanya menerima digit");
                System.out.print("Masukkan nomor handphone Anda: ");
                nomorhp = input.nextLine();
            }
        }
        return nomorhp;
    } 
}
