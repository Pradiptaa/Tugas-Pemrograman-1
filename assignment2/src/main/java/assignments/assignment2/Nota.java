package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Nota {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private int idNota;
    private Member member;
    private String paket;
    private int berat;
    private String tanggalMasuk;
    private int hariSelesai;
    private boolean ready;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNota = idNota;

    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public Member getMember() {
        return this.member;
    }

    public String getPaket() {
        return this.paket;
    }

    public int getBerat() {
        return this.berat;
    }

    public String getTanggal() {
        return this.tanggalMasuk;
    }
    
    public int getIdNota() {
        return this.idNota;
    }

    public int getHariselesai() {
        return this.hariSelesai;
    }

    public void setHariSelesai(String paket) {
        if(paket.equalsIgnoreCase("express")){
            this.hariSelesai = 1;
        }else if(paket.equalsIgnoreCase("fast")){
            this.hariSelesai = 2;
        }else if (paket.equalsIgnoreCase("reguler")) {
            this.hariSelesai = 3;
        }
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
    public boolean getReady() {
        return this.ready;
    }

    // Method to check if laundry is ready for pickup
    public boolean isLaundryReady(){
        if (this.hariSelesai == 0) {
            this.ready = true;
        }
        return ready;
    }

    // Method to mark laundry as ready for pickup
    public void laundryReady(){
        this.ready = true;
    }

    // Method to decrease remaining days for laundry to be completed
    public void kurangSisaHari(){
        if(this.hariSelesai > 0){
            this.hariSelesai--;
        }
    }
}
