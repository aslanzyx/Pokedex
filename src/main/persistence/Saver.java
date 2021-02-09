package persistence;

import model.PokemonToView;
import model.Stats;
import model.Team;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Saver utility
public class Saver {
    private static final String DIR = "data/";

    // EFFECT: save pokemon to be a pkm file
    public static void savePokemon(PokemonToView pkm) {
        String content = pkm.toSaveableContent();

        try {
            File file = new File(DIR + pkm.getName() + ".pkm");
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Saving Error");
        }
    }

    // EFFECT: save team to a team file
    public static void saveTeam(Team team) {
        try {
            File file = new File(DIR + team.getName() + ".team");
            FileWriter writer = new FileWriter(file);
            String content = team.toSaveableContent();
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            System.out.println("Saving error");
        }
    }
}
