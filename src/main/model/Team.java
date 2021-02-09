package model;

import exceptions.TeamFullException;

import java.util.Arrays;
import java.util.Iterator;

// Pokemon team class
public class Team implements Iterable<PokemonToEdit> {
    private static final int MAX_MEMBER = 6;

    private String name;
    private PokemonToEdit[] team;
    private int count;

    public Team(String name) {
        this.name = name;
        team = new PokemonToEdit[MAX_MEMBER];
        count = 0;
    }

    // MODIFY: this
    // EFFECT: Add a new pokemon if the pokemon is not in the team and team is not full
    public void add(PokemonToEdit pkm) throws TeamFullException {
        if (count == MAX_MEMBER) {
//            System.out.println("Only " + MAX_MEMBER + " Pokemons can be added to this team");
            throw new TeamFullException();
        }
        if (!contain(pkm.getId())) {
//            System.out.println("Pokemon " + pkm.getName() + "already exists in the team");
            team[count++] = pkm;
        }
    }

    // REQUIRE: id is meaningful
    // EFFECT: Return true if the team contain a pokemon with given id
    private boolean contain(int id) {
        for (int i = 0; i < count; i++) {
            if (team[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    // REQUIRE: Pokemon with given name can be found in this team
    // EFFECT: Find and return the pokemon with given name; Return null if the pokemon not found in the team
    public PokemonToEdit getPokemon(String name) {
        for (int i = 0; i < count; i++) {
            if (team[i].getName().equals(name)) {
                return team[i];
            }
        }
        return null;
    }

    public PokemonToEdit index(int i) {
        return team[i];
    }

    // MODIFY: this
    // EFFECT: Remove a new pokemon; Return true if the pokemon is successfully removed; Return false otherwise.
    public boolean remove(String name) {
        for (int i = 0; i < count; i++) {
            if (team[i].getName().equals(name)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    // MODIFY: this
    // EFFECT: Remove a pokemon at index i; Return true if i < count; Return false otherwise
    public boolean remove(int i) {
        if (i < count && i < 5) {
            for (int j = i; j < count; j++) {
                team[j] = team[++i];
            }
            count--;
            return true;
        } else if (i == 5) {
            count--;
            return true;
        } else {
            return false;
        }
    }

    // MODIFY: this
    // EFFECT: Rename the team
    public void rename(String name) {
        this.name = name;
    }

    // EFFECT: Return the name of current team
    public String getName() {
        return name;
    }

    // EFFECT: Return true if the team if empty
    public boolean isEmpty() {
        return count == 0;
    }

    // EFFECT: count how many pokemon is in this team
    public int count() {
        return count;
    }

    // EFFECT: Generate a saveable content
    public String toSaveableContent() {
        String content = "";
        for (int i = 0; i < count; i++) {
            content = content.concat(team[i].toSaveableContent());
        }
        return content;
    }

    // REQUIRE: team is not empty
    // EFFECT: remove a pokemon at index i
    @Override
    public String toString() {
        String content = "Team " + name + "\nwith " + count + " pokemon \n";
        for (int i = 0; i < count; i++) {
            content = content.concat("Pokemon " + i + ": \n");
            content = content.concat(team[i].toString() + "\n");
        }
        return content;
    }

    @Override
    public Iterator<PokemonToEdit> iterator() {
        return Arrays.stream(team).iterator();
    }

}
