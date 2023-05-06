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
        if (selesai) {
            return true;
        }
        return false;
    }

    @Override
    public long getHarga(int berat) {
        return berat*11000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
