package model;

import exceptions.StatInvalidException;

// Stat for ss iv and bs
public class Stat {
    private static final int MAX_IV = 31;
    private static final int MAX_BS = 252;

    private int speciesStrength;
    private int individualValue;
    private int baseStat;

    public Stat() {
        speciesStrength = 0;
        individualValue = 0;
        baseStat = 0;
    }

    public int getSS() {
        return speciesStrength;
    }

    public void setSS(int speciesStrength) {
        this.speciesStrength = speciesStrength;
    }

    public int getIV() {
        return individualValue;
    }

    public void setIV(int individualValue) throws StatInvalidException {
        if (individualValue >= 0 && individualValue <= MAX_IV) {
            this.individualValue = individualValue;
        } else {
            throw new StatInvalidException();
        }
    }

    public int getBS() {
        return baseStat;
    }

    public void setBS(int baseStat) throws StatInvalidException {
        if (baseStat >= 0 && baseStat <= MAX_BS) {
            this.baseStat = baseStat;
        } else {
            throw new StatInvalidException();
        }
    }

    public int calculate(int level) {
        return (speciesStrength * 2 + individualValue + baseStat / 4) * level / 100 + 5;
    }

}
