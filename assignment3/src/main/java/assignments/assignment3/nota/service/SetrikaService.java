package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    boolean selesai = false;
    @Override
    public String doWork() {
        selesai = true;
        return "Sedang menyetrika...";
    }

    @Override
    public boolean isDone() {
        return this.selesai;
    }

    @Override
    public long getHarga(int berat) {
        return berat*1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
