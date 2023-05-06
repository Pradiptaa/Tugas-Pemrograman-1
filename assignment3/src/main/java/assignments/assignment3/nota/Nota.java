package assignments.assignment3.nota;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import java.time.LocalDate;
import java.util.Arrays;
import java.time.format.DateTimeFormatter;

public class Nota {
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

    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket.toLowerCase();
        this.tanggalMasuk = tanggal;
        this.isDone = false;
        this.id = ++totalNota;

        DateTimeFormatter formatTanggal = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalMulai = LocalDate.parse(tanggal, formatTanggal);
        LocalDate selesaiDate;

        if (paket=="express") {
            selesaiDate = tanggalMulai.plusDays(1);
            this.tanggalSelesai = selesaiDate.format(formatTanggal);
            this.sisaHariPengerjaan = 1;
            this.baseHarga = 12000;
        } else if(paket=="fast") {
            selesaiDate = tanggalMulai.plusDays(2);
            this.tanggalSelesai = selesaiDate.format(formatTanggal);
            this.sisaHariPengerjaan = 2;
            this.baseHarga = 10000;
        } else if (paket=="reguler") {
            selesaiDate = tanggalMulai.plusDays(3);
            this.tanggalSelesai = selesaiDate.format(formatTanggal);
            this.sisaHariPengerjaan = 3;
            this.baseHarga = 7000;
        }
    }

    public void addService(LaundryService service){
        LaundryService[] newServices = Arrays.copyOf(services, services.length+1);
        newServices[services.length]=service;
        services=newServices;
    }

    public String kerjakan(){
        // ini bingung
        String selesai= "";
        for (LaundryService service : services) {
            if (!isDone) {
                selesai = "Sudah selesai";
                break;
            } else {
                selesai=service.doWork();
                break;
            }
        }
        return String.format("Nota %d : %s.\n", this.id, selesai);
    }

    public void toNextDay() {
        if (isDone) {
            sisaHariPengerjaan--;
        }
    }

    public long calculateHarga(){
        long harga = 0;
        harga = harga + (baseHarga * berat);
        for (LaundryService service : services) {
            harga = harga + service.getHarga(berat);
        }
        
        if (sisaHariPengerjaan < 0){
            harga = harga - (sisaHariPengerjaan * -1 * 2000);
        }
        
        return harga;        
    }

    public String getNotaStatus(){
        if (!isDone) {
            return String.format("Nota %d : Sudah selesai", this.id);
        } else {
            return String.format("Nota %d : Belum selesai", this.id);
        }
    }

    @Override
    public String toString(){
        String hasil = "";
        hasil += "[ID Nota = " + this.id + "]\n";
        hasil += "ID    : " + member.getId() + "\n";
        hasil += "Paket : " + this.paket + "\n";
        hasil += "Harga :\n";
        hasil += berat + " kg x " + baseHarga + " = " + (berat * baseHarga) + "\n";
        hasil += "tanggal terima  : " + tanggalMasuk + "\n";
        hasil += "tanggal selesai : " + tanggalSelesai + "\n";
        hasil += "--- SERVICE LIST ---\n";
        for (LaundryService service : services) {
            hasil += "-" + service.getServiceName() + " @ Rp." + service.getHarga(berat) + "\n";
        }
        if (sisaHariPengerjaan < 0){
            hasil += "Harga Akhir: " + calculateHarga() + " Ada kompensasi keterlambatan " + (sisaHariPengerjaan * -1) + " * 2000 hari\n";
        }else {
            hasil += "Harga Akhir: " + calculateHarga() + "\n";
        }
        return hasil;
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
}
