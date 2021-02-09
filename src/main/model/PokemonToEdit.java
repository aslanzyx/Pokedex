package model;

import exceptions.StatInvalidException;

// Pokemon object to edit in teambuilder mode
public class PokemonToEdit extends PokemonToView {

    private static final int MAX_LEVEL = 100;
    private int level;

    public PokemonToEdit() {
        super();
        this.level = 50;
    }

    // EFFECT: return a saveable content
    @Override
    public String toSaveableContent() {
        String content = super.toSaveableContent();
        String iv = "";
        String bs = "";

        for (Stat s : stats) {
            iv = iv.concat(s.getIV() + ",");
            bs = bs.concat(s.getBS() + ",");
        }

        content = content.concat(iv.substring(0, iv.length() - 1) + "\n");
        content = content.concat(bs.substring(0, bs.length() - 1) + "\n");
        content = content.concat(level + "\n");

        return content;
    }

    @Override
    public String toString() {
        String content = "";
        content = content.concat("Pokemon with id: " + id + "\n");
        content = content.concat("Name: " + name + "\n");
        content = content.concat("Type: " + type + "\n");
        int i = 0;
        for (Stat s : stats) {
            content = content.concat(Stats.STATS_ORDER[i] + ": " + s.calculate(level) + "\n");
            i++;
        }
        return content;
    }

    // getter and setters
    public void setLevel(int level) throws StatInvalidException {
        if (level <= MAX_LEVEL && level > 0) {
            this.level = level;
//            System.out.println("Pokemon has been set to level " + level);
        } else {
//            System.out.println("invalid level");
            throw new StatInvalidException();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setIV(int i, int value) throws StatInvalidException {
        stats.get(i).setIV(value);
    }

    public void setBS(int i, int value) throws StatInvalidException {
        stats.get(i).setBS(value);
    }

}
