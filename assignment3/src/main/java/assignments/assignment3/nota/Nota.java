package assignments.assignment3.nota;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

import java.util.Arrays;
import java.util.Calendar;

public class Nota {
    //Attributes
    private Member member;
    private String paket;
    private LaundryService[] services;
    private long baseHarga;
    private int sisaHariPengerjaan;
    private  int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    static public int totalNota;

    //Constructors
    public Nota(Member member, int berat, String paket, String tanggal) {
        //Assign param ke variable
        this.member = member;
        this.berat = berat;
        this.paket = paket.toLowerCase();
        this.tanggalMasuk = tanggal;
        this.isDone = false;
        this.id = totalNota++;
        this.services= new LaundryService[0];

        this.addService(new CuciService()); //Menambah cuci service


        if (paket=="express") {
            this.sisaHariPengerjaan = 1;
            this.baseHarga = 12000;
        } else if(paket=="fast") {
            this.sisaHariPengerjaan = 2;
            this.baseHarga = 10000;
        } else if (paket=="reguler") {
            this.sisaHariPengerjaan = 3;
            this.baseHarga = 7000;
        }

        Calendar copyOfCal = Calendar.getInstance();
        copyOfCal.setTime (NotaManager.cal.getTime());
        copyOfCal.add(Calendar.DAY_OF_YEAR, sisaHariPengerjaan);
        this.tanggalSelesai = NotaManager.fmt.format(NotaManager.cal.getTime());
    }

    public void addService(LaundryService service){
        // Membuat Array untuk service dan copy
        LaundryService[] newServices = Arrays.copyOf(this.services, services.length+1);
        newServices[this.services.length]=service;
        //overwrite dengan array yang baru
        this.services=newServices;
    }

    public String kerjakan() {
        int i = 0;
        while (i < this.services.length) {
            // Mengecek status isDone dari tiap layanan pada array services
            if (!this.services[i].isDone()) {
                // Return String Nota-idNota : pekerjaan/layanan yang sedang dilakukan
                String result = "Nota " + this.id + " : " + this.services[i].doWork()+ "\n";
                // Ketika loop sampai di layanan paling akhir / sedang mengerjakan layanan terakhir dari array Services
                if (i == this.services.length - 1) {
                    this.isDone = true;
                }
                return result;
            }
            i++;
        }
        return this.getNotaStatus();
    }
    

    public void toNextDay() {
        //Mengurangi sisa hari pengerjaan
        if (!this.isDone()) {
            this.sisaHariPengerjaan--;
        }
    }

    public long calculateHarga(){
        //Inisiasi harga
        long harga = 0;
        harga += this.getBaseHarga() * this.getBerat();
        for (LaundryService service : this.services) {
            harga += service.getHarga(this.getBerat()); //Menambah harga dengan layanan yang dipilih
        }
        
        if (this.sisaHariPengerjaan < 0){ 
            harga += sisaHariPengerjaan * 2000; //Harga yang akan dikurangi karena terlambat
        }
        
        return harga;        
    }

    public String getNotaStatus(){
        //Inisiasi pesan
        String notaselesai = String.format("Nota %d :", this.id);
        if (this.isDone()) {
            return notaselesai += "Sudah selesai.\n";
        } else {
            return notaselesai += "Belum selesai.\n";
        }
    }

    @Override
    public String toString(){
        //Inisiasi hasil
        String hasil = "";

        hasil += "[ID Nota = " + this.id + "]\n";
        hasil += "ID    : " + member.getId() + "\n";
        hasil += "Paket : " + this.paket + "\n";
        hasil += "Harga :\n";
        hasil += this.getBerat() + " kg x " + this.getBaseHarga() + " = " + (this.getBerat() * this.getBaseHarga()) + "\n";
        hasil += "tanggal terima  : " + this.getTanggal() + "\n";
        hasil += "tanggal selesai : " + getTanggalSelesai() + "\n";
        hasil += "--- SERVICE LIST ---\n";

        for (LaundryService service : this.getServices()) {
            hasil += "-" + service.getServiceName() + " @ Rp." + service.getHarga(this.getBerat()) + "\n";
        }
        if (sisaHariPengerjaan < 0){
            hasil += "Harga Akhir: " + this.calculateHarga() + " Ada kompensasi keterlambatan " + (this.sisaHariPengerjaan*-1) + " * 2000 hari\n";
        }else {
            hasil += "Harga Akhir: " + this.calculateHarga() + "\n";
        }
        return hasil;
    }

    // Method untuk menentukan tanggalSelesai
    public String setTanggalSelesai(){
        Calendar copyOfCal = Calendar.getInstance();
        copyOfCal.setTime (NotaManager.cal.getTime() );
        copyOfCal.add(Calendar.DAY_OF_YEAR, sisaHariPengerjaan);
        String tanggalSelesai = NotaManager.fmt.format(copyOfCal.getTime());
        return tanggalSelesai;
    }

    // Dibawah ini adalah getter

    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }

    public long getBaseHarga(){
        return this.baseHarga;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }
}
