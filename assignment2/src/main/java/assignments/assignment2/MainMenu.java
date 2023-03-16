package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Scanner;
import java.time.LocalDate;

import assignments.assignment1.NotaGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static ArrayList<Nota> notaList;
    private static ArrayList<Member> memberList;

    public static void main(String[] args) {
        boolean isRunning = true;
        notaList = new ArrayList<Nota>();
        memberList = new ArrayList<Member>();

        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    private static void handleGenerateUser() {
        // TODO: handle generate user
        System.out.println("Masukkan nama Anda: ");
        String nama = input.nextLine();

        System.out.println("Masukkan nomor handphone Anda: "); //Meminta input nomor hp
        String nomorhp = input.nextLine();
        boolean numCheck = true; //Boolean untuk loop

        while (numCheck) {
            //If condition untuk input angka
            if (nomorhp.matches("[0-9]+")) {
                numCheck = false; //Mengganti boolean numCheck menjadi true
            //Else condition apabila input selain angka
            } else {
                System.out.println("Field nomor hp hanya menerima digit.");
                System.out.print("Masukkan nomor handphone Anda: ");
                nomorhp = input.nextLine();
            }
        }

        boolean newMember = false;
        while (newMember == false) {
            for (int i=0; i<memberList.size(); i++) {
                if (memberList.get(i).getNama().equalsIgnoreCase(nama) && memberList.get(i).getNomorhp().equals(nomorhp)) {
                    System.out.println("Member dengan nama "+ nama +" dan nomor hp "+ nomorhp +" sudah ada!");
                    return;
                } 
            }
            newMember = true;
        }
        Member id = new Member(nama, nomorhp);
        memberList.add(id);
        System.out.println("Berhasil membuat member dengan ID "+ generateId(nama, nomorhp));

    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        boolean check= true;
        String inputPaket = "";
        String[] allowedPakets = {"express", "fast", "reguler"};
        int hargaLaundry = 0;
        int hariSelesai = 0;
        int inputBerat = 0;

        System.out.println("Masukkan ID member: ");
        String checkid = input.nextLine();
        int idnota = 0;

        Member member1 = new Member("","");

        for (int j = 0; j < memberList.size(); j++) {
            if (memberList.get(j).getId().equals(checkid)) {
                member1 = memberList.get(j);
                memberList.get(j).addBonuscounter();

                while (check) {
                    System.out.println("Masukkan paket laundry: ");
                    inputPaket = input.nextLine().toLowerCase();
                    if (Arrays.asList(allowedPakets).contains(inputPaket)) {
                        if (inputPaket.equals("express")){
                            hargaLaundry = 12000;
                            hariSelesai = 1;
                        } else if (inputPaket.equals("fast")) {
                            hargaLaundry = 10000;
                            hariSelesai = 2;
                        } else if (inputPaket.equals("reguler")) {
                            hargaLaundry = 7000;
                            hariSelesai = 3;
                        }
                        break;
                    //Else if condition untuk "?" 
                    } else if (inputPaket.equals("?")) {
                        NotaGenerator.showPaket(); //Memanggil method showPaket untuk memperlihatkan paket yang dapat dipilih
                    //Else condition input tidak valid
                    } else {
                        System.out.println("Paket "+inputPaket+" tidak diketahui");
                        System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                    }
                }
                while (check) {
                    System.out.print("Masukkan berat cucian Anda [Kg]: "); //Meminta input berat cucian
                    inputBerat = input.nextInt(); 
                    if (inputBerat<=0) {
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        break;
                    } else if (inputBerat == 1) {
                        System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                        inputBerat = 2;
                        break;
                    } else if (inputBerat >=2) {
                        break; 
                    } else {
                        System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan.");
                    }  
                }
                System.out.println("Berhasil menambahkan nota!");
                System.out.printf("[ID Nota = %d]\n",idnota);
                idnota += 1;

                DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate tanggalTerima = LocalDate.parse(fmt.format(cal.getTime()), formatTanggal);
                LocalDate tanggalSelesainya = tanggalTerima.plusDays(hariSelesai);
                String tanggalSelesai = tanggalSelesainya.format(formatTanggal);

                int totalHarga = inputBerat*hargaLaundry;

                Nota newNota = new Nota(member1, inputPaket, inputBerat, fmt.format(cal.getTime()));

                newNota.setHariSelesai(hariSelesai);
                newNota.setReady(false);

                System.out.println("ID      : "+checkid);
                System.out.println("Paket   : "+inputPaket);

                if(member1.getBonuscounter() == 3) {
                    int Diskon = totalHarga/2;
                    System.out.println("Harga: \n"+inputBerat+" kg x "+hargaLaundry+" = "+totalHarga+" = "+Diskon+" (Discount member 50%%!!!!)");
                    member1.resetBonuscounter();
                } else {
                    System.out.println("Harga: \n"+inputBerat+" kg x "+hargaLaundry+" = "+totalHarga);
                }
                System.out.printf("Tanggal Terima  : %s\n", fmt.format(cal.getTime()));
                System.out.printf("Tanggal Selesai : %s\n", tanggalSelesai);
                System.out.println("Status      	: Belum bisa diambil :(");
            } else {
                System.out.println("Member dengan ID "+checkid+" tidak ditemukan!");
            }
        }
    }


    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem.");

        for (Nota nota : notaList) {
            if (nota.getReady()) {
                System.out.println("- [" + nota.getIdNota() + "] Status: Sudah dapat diambil!");
            } else {
                System.out.println("- [" + nota.getIdNota() + "] Status: Belum bisa diambil :(");
            }
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        for(int i = 0; i < memberList.size(); i++){
            System.out.printf("- %s : %s\n", memberList.get(i).getId(), memberList.get(i).getNama());
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID nota yang akan diambil: ");
        String idAmbil = input.nextLine();
        boolean checkAmbil = true;
        ArrayList<Nota> notaList= new ArrayList<Nota>();
        ArrayList<Nota> notaSimpan = new ArrayList<Nota>(notaList.size()-1);

        while (checkAmbil) {
            String ambilLaundry = input.nextLine();
            if (ambilLaundry.matches("^[0-9]+$")) { // mengecek apakah id nota yang diinput angka apa bukan
                int ambilInt = Integer.parseInt(ambilLaundry); // mengubah input string menjadi int
        
                boolean notaDitemukan = false;
                for (Nota nota : notaList) {
                    if (nota.getIdNota() == ambilInt) {
                        notaDitemukan = true;
                        // mengecek status nota yang ingin diambil
                        if (!nota.getReady()) {
                            System.out.printf("Nota dengan ID %d gagal diambil!\n", nota.getIdNota());
                        } else {
                            System.out.printf("Nota dengan ID %d berhasil diambil!\n", nota.getIdNota());
                            notaList.remove(nota);
                            notaSimpan.addAll(notaList); // menambahkan semua nota yang tidak diambil ke dalam notaSimpan
                            notaList = notaSimpan; // mengubah notaList menjadi nota yang baru
                        }
                        break;
                    }
                }
        
                if (!notaDitemukan) {
                    System.out.printf("Nota dengan ID %d tidak ditemukan!\n", ambilInt);
                }
                break;
            } else { // input id nota bukan angka
                System.out.println("ID nota berbentuk angka!");
            }
        }
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
        System.out.println("Dek Depe tidur hari ini... zzz...");
        cal.add(Calendar.DAY_OF_YEAR, 1);

        for (int j = 0; j < notaList.size(); j++) {
            notaList.get(j).kurangSisaHari();
            if (notaList.get(j).getReady()) {
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", notaList.get(j).getIdNota());
            }
        }
        System.out.println("Selamat pagi dunia!");
        System.out.println("Dek Depe: It's CuciCuci Time.");
    }

    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }

}
