package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    boolean selesai = false;
    @Override
    public String doWork() {
        selesai = true;
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        return this.selesai;
    }

    @Override
    public long getHarga(int berat) {
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
