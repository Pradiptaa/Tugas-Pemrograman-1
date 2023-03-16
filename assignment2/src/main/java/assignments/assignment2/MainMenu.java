package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

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
        int idnota = 0;
        while (true) {
            System.out.println("Masukkan ID member: ");
            String checkid = input.nextLine();
            String[] checknama = checkid.split("-");
            String firstname=checknama[0];
            String nomor=checknama[1];
            String[] allowedPakets = {"express", "fast", "reguler"};
            boolean test = true;
            String inputPaket = "";
            for (int j=0; j<memberList.size(); j++) {
                if (memberList.get(j).getNama().equals(firstname) && memberList.get(j).getNomorhp().equals(nomor)) {
                    while (test) {
                        System.out.print("Masukkan paket laundry: "); //Meminta input paket
                        inputPaket = input.next().toLowerCase(); //lowercase seluruh huruf dalam input
                        //If condition saat input ada dalam list
                        if (Arrays.asList(allowedPakets).contains(inputPaket)) {
                            break; //break loop bila input valid
                        //Else if condition untuk "?" 
                        } else if (inputPaket.equals("?")) {
                            NotaGenerator.showPaket(); //Memanggil method showPaket untuk memperlihatkan paket yang dapat dipilih
                        //Else condition input tidak valid
                        } else {
                            System.out.println("Paket "+inputPaket+" tidak diketahui");
                            System.out.println("[ketik ? untuk mencari tahu jenis paket]");
                        }
                    }
                }
            }
            //Integer untuk input berat
                    int inputBerat = 0;
                    while (test) {
                        System.out.print("Masukkan berat cucian Anda [Kg]: "); //Meminta input berat cucian
                        input.nextLine();
                        inputBerat = input.nextInt();
                        if (inputBerat<=0) {
                            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                        } else if (inputBerat == 1) {
                            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                            inputBerat = 2;
                        } else {
                            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan.");
                        }
            }
            System.out.println("Berhasil menambahkan nota!");
            System.out.printf("[ID Nota = %d]\n",idnota);
            idnota += 1;
            NotaGenerator.generateNota(NotaGenerator.generateId(firstname, nomor), inputPaket, inputBerat, fmt.format(cal.getTime()));

        }
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
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
