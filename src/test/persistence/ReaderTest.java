package persistence;

import exceptions.StatInvalidException;
import exceptions.TeamFullException;
import model.PokemonToView;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {
    @BeforeEach
    public void testBefore() {
        // Reader are test as static class
    }

    @Test
    public void testConstructor() {
        Reader demo = new Reader();
        assertNotNull(demo);
    }

    @Test
    public void testReadPokemonIOException() {
        try {
            Reader.readPokemon("xxx");
            fail("Show not find");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    public void testReadPokemon() {
        try {
            PokemonToView pkm = Reader.readPokemon("bulbasaur");
            assertEquals("bulbasaur", pkm.getName());
        } catch (IOException e) {
            fail("show pass");
        }
    }

    @Test
    public void testReadTeamIOException() {
        try {
            Reader.readTeam("xxx");
            fail("Show not find");
        } catch (IOException e) {
            // Pass
        } catch (TeamFullException | StatInvalidException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadTeam() {
        try {
            Team team = Reader.readTeam("alpha");
            assertEquals("alpha", team.getName());
        } catch (IOException e) {
            fail("should pass");
        } catch (TeamFullException | StatInvalidException e) {
            e.printStackTrace();
        }
    }
}
