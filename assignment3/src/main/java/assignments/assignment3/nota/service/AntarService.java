package assignments.assignment3.nota.service;

// import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private boolean selesai = false;
    @Override
    public String doWork() {
        this.selesai = true;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        return this.selesai;
    }

    @Override
    public long getHarga(int berat) {
        if (2000 > berat*500) {
            return 2000;
        }
        return berat*500;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
