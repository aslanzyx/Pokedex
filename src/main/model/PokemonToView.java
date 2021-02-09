package model;

import java.util.Objects;

// Pokemon object just to view in pokedex mode
public class PokemonToView {
    protected int id;
    protected String name;

    //    private int level;
    protected String ability;
    protected String type;
    protected Stats stats;

    public PokemonToView() {
        this.stats = new Stats();
    }

    // EFFECT: generate a string to be saved in a team
    public String toSaveableContent() {
        String content = "";
        content = content.concat(id + "\n");
        content = content.concat(name + "\n");
        content = content.concat(type + "\n");
        content = content.concat(ability + "\n");

        for (Stat s : stats) {
            content = content.concat(s.getSS() + ",");
        }
        return content.substring(0, content.length() - 1).concat("\n");
    }

    // getter and setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSS(int i, int value) {
        stats.get(i).setSS(value);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbility() {
        return ability;
    }

    public String getType() {
        return type;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PokemonToView pokemonToView = (PokemonToView) o;
        return id == pokemonToView.id
                && Objects.equals(name, pokemonToView.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        String content = "";
        content = content.concat("Pokemon with id: " + id + "\n");
        content = content.concat("Name: " + name + "\n");
        content = content.concat("Type: " + type + "\n");
        int i = 0;
        for (Stat s : stats) {
            content = content.concat(Stats.STATS_ORDER[i] + ": " + s.getSS() + "\n");
            i++;
        }
        return content;
    }
}
