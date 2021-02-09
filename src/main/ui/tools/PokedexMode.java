package ui.tools;

import model.PokemonToView;
import model.Stat;
import model.Stats;
import persistence.Crawler;
import persistence.Reader;
import persistence.Saver;

import java.io.IOException;

// Pokedex mode
public class PokedexMode extends Runnable {
    public PokedexMode() {
        run();
    }

    // EFFECT: display welcome
    @Override
    protected void display() {
        System.out.println("Welcome to the Pokedex mode!");
    }

    // REQUIRE: input command is meaningful or can be found as a pokemon name
    // EFFECT: process command operations
    @Override
    protected void selector(String command) {
        try {
            PokemonToView pkm = Reader.readPokemon(command);
            printPokemon(pkm);
            System.out.println("\nq -> continue");
            input.next();
        } catch (IOException e) {
            System.out.println(command + " is not found natively, try searching online.");
            try {
                PokemonToView pkm = Crawler.loadPokemon(command);
                printPokemon(pkm);
                Saver.savePokemon(pkm);
                System.out.println("\nq -> continue");
                input.next();
            } catch (IOException ex) {
                System.out.println(command + "is not found");
            }
        }
    }

    // EFFECT: show commands
    @Override
    protected void help() {
        System.out.println("Enter the pokemon name to search or");
        System.out.println("q -> Back to home");
        System.out.println("q! -> Force quit");
    }

    // EFFECT: print pokemon information
    private void printPokemon(PokemonToView pkm) {
        System.out.println("Id: " + pkm.getId());
        System.out.println("Name: " + pkm.getName());
        System.out.println("Type: " + pkm.getType());
        System.out.println("Ability: " + pkm.getAbility());
        System.out.println("Species Strength: ");
        int i = 0;
        for (Stat s : pkm.getStats()) {
            System.out.println(Stats.STATS_ORDER[i++] + ": " + s.getSS());
        }
    }
}
