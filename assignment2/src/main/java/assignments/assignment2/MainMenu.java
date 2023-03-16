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

//PILIHAN 1 ========================================================================================================================================
    private static void handleGenerateUser() {
        //Meminta input nama
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
        //boolean untuk newmember
        boolean newMember = false;
        //While loop untuk validasi member baru
        while (newMember == false) {
            for (int i=0; i<memberList.size(); i++) {
                if (memberList.get(i).getNama().equalsIgnoreCase(nama) && memberList.get(i).getNomorhp().equals(nomorhp)) {
                    System.out.println("Member dengan nama "+ nama +" dan nomor hp "+ nomorhp +" sudah ada!");
                    return;
                } 
            }
            newMember = true;
        }
        //Menambah member baru ke ArrayList
        Member id = new Member(nama, nomorhp);
        memberList.add(id);
        //Output apabila member baru
        System.out.println("Berhasil membuat member dengan ID "+ generateId(nama, nomorhp));

    }
//PILIHAN 2 ========================================================================================================================================
    private static void handleGenerateNota() {
        //Initial variables
        boolean check= true;
        String inputPaket = "";
        String[] allowedPakets = {"express", "fast", "reguler"};
        int hargaLaundry = 0;
        int hariSelesai = 0;
        String inputBerat = "";
        int beratInt = 0;
        //Meminta input ID
        System.out.println("Masukkan ID member: ");
        String checkid = input.nextLine();
        int idnota = 0;
        boolean memberexist = false;

        Member member1 = new Member("","");

        //For loop check member baru atau tidak
        for (int j = 0; j < memberList.size(); j++) {
            if (memberList.get(j).getId().equals(checkid)) {
                member1 = memberList.get(j);
                memberList.get(j).addBonuscounter();
                memberexist = true;
            } else {
                continue;
            }
                //Loop untuk paket laundry
                if (memberexist) {
                while (check) {
                    System.out.println("Masukkan paket laundry: ");
                    inputPaket = input.nextLine().toLowerCase();
                    //If condition bila input termasuk dalat list allowedPaket
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
                    inputBerat = input.nextLine();
                    boolean numCheck = false; //Boolean untuk loop
                    beratInt= Integer.parseInt(inputBerat);

                    while (!numCheck) {
                        //If condition untuk input angka
                        if (inputBerat.matches("[0-9]+")) {
                            numCheck = true; //Mengganti boolean numCheck menjadi true
                            if (beratInt <=0) {
                                System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan positif.");
                                break;
                            } else if (beratInt == 1) {
                                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
                                beratInt = 2;
                                break;
                            } else if (beratInt >=2) {
                                break;
                            }
                        //Else condition apabila input selain angka
                        } else {
                            System.out.println("Harap masukkan berat cucian Anda dalam bentuk bilangan.");
                            System.out.print("Masukkan nomor handphone Anda: ");
                            inputBerat = input.nextLine();
                        }
                    }
                    break;
                }
            
                //Print berhasil
                System.out.println("Berhasil menambahkan nota!");
                System.out.printf("[ID Nota = %d]\n",idnota);

                //Add nota baru ke ArrayList yang sudah ditentukan
                Nota newNota = new Nota(member1, inputPaket, beratInt, fmt.format(cal.getTime()));
                notaList.add(newNota);
                newNota.setHariSelesai(inputPaket);
                newNota.setReady(false);

                //Format tanggal ke dd/MM/yyyy
                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
                Calendar newCal = Calendar.getInstance();
                //set Time ke cal.getTime
                newCal.setTime(cal.getTime());
                //Menambah tanggal untuk tanggal selesai
                newCal.add(Calendar.DAY_OF_YEAR, newNota.getHariselesai());
                String tanggalSelesai = fmt.format(newCal.getTime());
                //Menghitung harga
                int totalHarga = beratInt*hargaLaundry;

                //Print id dan paket
                System.out.println("ID      : "+checkid);
                System.out.println("Paket   : "+inputPaket);

                //Bonuscounter jika 3x menggunakan
                if(member1.getBonuscounter() == 3) {
                    int Diskon = totalHarga/2;
                    System.out.println("Harga: \n"+beratInt+" kg x "+hargaLaundry+" = "+totalHarga+" = "+Diskon+" (Discount member 50%%!!!!)");
                    member1.resetBonuscounter(); //reset apabila sudah mendapat bonus
                } else {
                    System.out.println("Harga: \n"+beratInt+" kg x "+hargaLaundry+" = "+totalHarga);
                }
                System.out.printf("Tanggal Terima  : %s\n", fmt.format(cal.getTime()));
                System.out.printf("Tanggal Selesai : %s\n", tanggalSelesai);
                System.out.println("Status      	: Belum bisa diambil :(");
                //Menambah index idnota
                idnota += 1;
            }
            // Jika id tidak ditemukan
            else {
                System.out.println("Member dengan ID "+checkid+ " tidak ditemukan!");
                break;
            }
        }
    }
    

//PILIHAN 3 ========================================================================================================================================
    private static void handleListNota() {
        //Print info sesuai size notaList
        System.out.println("Terdaftar " + notaList.size() + " nota dalam sistem.");

        //for loop untuk Status sesuai tanggal ready
        for (Nota nota : notaList) {
            if (nota.getReady() ) {
                System.out.println("- [" + nota.getIdNota() + "] Status: Sudah dapat diambil!");
            } else {
                System.out.println("- [" + nota.getIdNota() + "] Status: Belum bisa diambil :(");
            }
        }
    }

//PILIHAN 4 ========================================================================================================================================
    private static void handleListUser() {
        //Print info jumlah member sesuai size member
        System.out.printf("Terdaftar %d member dalam sistem.\n", memberList.size());
        //for loop untuk nama dan id
        for(int i = 0; i < memberList.size(); i++){
            System.out.printf("- %s : %s\n", memberList.get(i).getId(), memberList.get(i).getNama());
        }
    }

//PILIHAN 5 ========================================================================================================================================
    private static void handleAmbilCucian() {
        //Initial variables
        String idAmbil = "";
        int idInt = 0;
        boolean checkAmbil = true;

        //while loop untuk pengambilan
        while (checkAmbil) {
            //Meminta input
            System.out.println("Masukkan ID nota yang akan diambil: ");
            idAmbil = input.nextLine();

            if (idAmbil.matches("^[0-9]+$")) { // mengecek apakah id nota yang diinput angka apa bukan
                idInt = Integer.parseInt(idAmbil);
                boolean found = false;
                //for loop untuk info kesiapan laundry
                for (int x = 0; x < notaList.size(); x++) {
                    if (notaList.get(x).getIdNota() == idInt) {
                        found = true;
                        if (notaList.get(x).getReady()) {
                            System.out.printf("Nota dengan ID %d berhasil diambil!\n", idInt);
                            notaList.remove(x);
                        } else {
                            System.out.printf("Nota dengan ID %d gagal diambil!\n", idInt);
                        }
                        break;
                    }
                }
                if (!found) { //If condition bila id nota tidak ada
                    System.out.printf("Nota dengan ID %d tidak ditemukan!\n", idInt);
                }
                checkAmbil = false;
            } else { // input id nota bukan angka
                System.out.println("ID nota harus berupa angka!");
            }
        }
    }

//PILIHAN 6 ========================================================================================================================================
    private static void handleNextDay() {
        //Print tidur
        System.out.println("Dek Depe tidur hari ini... zzz...");
        //Menambah hari
        cal.add(Calendar.DAY_OF_YEAR, 1);

        //Update info laundry yang sudah bisa diambil
        for (int j = 0; j < notaList.size(); j++) {
            notaList.get(j).kurangSisaHari();
            notaList.get(j).isLaundryReady();
            if (notaList.get(j).getReady()) {
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!\n", notaList.get(j).getIdNota());
            }
        }
        //Print selamat pagi
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