package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama;
    private String noHp;
    private String id;
    private int bonusCounter;

    public Member(String nama, String noHp) {
        this.nama = nama;
        this.noHp = noHp;
        this.bonusCounter = 0;
        this.id = NotaGenerator.generateId(getNama(), getNomorhp());
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNomorhp() {
        return this.noHp;
    }

    public int getBonuscounter() {
        return this.bonusCounter;
    }

    public int addBonuscounter() {
        return bonusCounter +=1;
    }

    public int resetBonuscounter() {
        return bonusCounter = 0;
    }
}
