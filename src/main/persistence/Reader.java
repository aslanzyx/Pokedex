package persistence;

import exceptions.StatInvalidException;
import exceptions.TeamFullException;
import model.PokemonToEdit;
import model.PokemonToView;
import model.Stats;
import model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

// Reader tool to read pokemons and teams
public class Reader {
    private static final String DIR = "data/";

    // REQUIRE: pokemon file with the given name exists in the data folder
    // EFFECT: read pokemon file by a given name
    public static PokemonToEdit readPokemon(String name) throws IOException {
        File file = new File(DIR + name + ".pkm");
        List<String> content = Files.readAllLines(file.toPath());

        PokemonToEdit pkm = new PokemonToEdit();
        pkm.setId(Integer.parseInt(content.get(0)));
        pkm.setName(content.get(1));
        pkm.setType(content.get(2));
        pkm.setAbility(content.get(3));
        String[] ss = content.get(4).split(",");
        for (int i = 0; i < 6; i++) {
            pkm.setSS(i, Integer.parseInt(ss[i]));
        }
        return pkm;
    }

    // REQUIRE: team file with the given name exists in the data folder
    // EFFECT: read team file by a given name
    public static Team readTeam(String name) throws IOException, StatInvalidException, TeamFullException {
//        System.out.println("Reading " + name);
        File file = new File(DIR + name + ".team");
        List<String> content = Files.readAllLines(file.toPath());

        Team team = new Team(name);

        for (int i = 0; i < content.size(); i += 8) {
            PokemonToEdit pkm = new PokemonToEdit();
            pkm.setId(Integer.parseInt(content.get(i)));
            pkm.setName(content.get(i + 1));
            pkm.setAbility(content.get(i + 2));
            pkm.setType(content.get(i + 3));
            String[] ss = content.get(i + 4).split(",");
            String[] iv = content.get(i + 5).split(",");
            String[] bs = content.get(i + 6).split(",");

            for (int j = 0; j < 6; j++) {
                pkm.setSS(j, Integer.parseInt(ss[j]));
                pkm.setIV(j, Integer.parseInt(iv[j]));
                pkm.setBS(j, Integer.parseInt(bs[j]));
            }

            pkm.setLevel(Integer.parseInt(content.get(i + 7)));
            team.add(pkm);
        }
        return team;
    }
}
