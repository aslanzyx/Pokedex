package persistence;

import exceptions.StatInvalidException;
import exceptions.TeamFullException;
import model.PokemonToView;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SaverTest {
    PokemonToView pkm;
    Team team;

    @BeforeEach
    public void testBefore() throws IOException, TeamFullException, StatInvalidException {
        pkm = Crawler.loadPokemon("eternatus");
        team = Reader.readTeam("test");
    }

    @Test
    public void testConstructor() {
        Saver demo = new Saver();
        assertNotNull(demo);
    }

    @Test
    public void testSavePokemon() throws IOException {
        Saver.savePokemon(pkm);
        PokemonToView neo = Reader.readPokemon("eternatus");
        assertEquals(pkm.getName(), neo.getName());
    }

    @Test
    public void testSaveTeam() throws IOException, TeamFullException, StatInvalidException {
        int current = team.count();
        Saver.saveTeam(team);
        Team newTeam = Reader.readTeam("test");
    }
}
