package model;

import exceptions.TeamFullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.Reader;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {
    Team team;
    PokemonToEdit pkm0;
    PokemonToEdit pkm1;
    PokemonToEdit pkm2;

    @BeforeEach
    public void beforeTest() throws IOException {
        team = new Team("test");
        pkm0 = Reader.readPokemon("bulbasaur");
        pkm1 = Reader.readPokemon("charmander");
        pkm2 = Reader.readPokemon("squirtle");
    }

    @Test
    public void testConstructor() {
        Team temp = new Team("demo");
        assertTrue(temp.isEmpty());
        assertEquals(0, temp.count());
    }


    @Test
    public void testIndex() throws TeamFullException {
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertEquals(pkm0, team.index(0));
        assertEquals(pkm1, team.index(1));
        assertEquals(pkm2, team.index(2));
    }

    @Test
    public void testAdd() {
        try {
            team.add(pkm0);
            team.add(pkm1);
            team.add(pkm2);
            assertEquals(3, team.count());
            team.add(pkm0);
            team.add(pkm1);
            team.add(pkm2);
            assertEquals(3, team.count());
        } catch (TeamFullException e) {
            fail("Should pass");
        }

    }

    @Test
    public void testAddTeamFull() {
        try {
            for (int i = 0; i < 7; i++) {
                PokemonToEdit pkm = new PokemonToEdit();
                pkm.setId(i);
                team.add(pkm);
            }
            System.out.println(team.count());
            fail("Should throw an exception");
        } catch (TeamFullException e) {
            // pass
        }
    }

    @Test
    public void testRemoveByName() throws TeamFullException {
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertTrue(team.remove("squirtle"));
        assertEquals(2, team.count());
        assertFalse(team.remove(2));
    }

    @Test
    public void testRemoveByIndex() throws TeamFullException {
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertTrue(team.remove(2));
        assertEquals(2, team.count());
        assertFalse(team.remove("squirtle"));
    }

    @Test
    public void testIsEmpty() throws TeamFullException {
        assertTrue(team.isEmpty());
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertFalse(team.isEmpty());
        team.remove(0);
        team.remove(1);
        team.remove(2);
        assertFalse(team.isEmpty());
        team.remove(0);
        assertTrue(team.isEmpty());
    }

    @Test
    public void testCount() throws TeamFullException {
        assertEquals(0, team.count());
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertEquals(3, team.count());
        team.remove(0);
        team.remove(0);
        team.remove(0);
        assertEquals(0, team.count());
    }

    @Test
    public void testRename() {
        String testName = "team-alpha";
        team.rename(testName);
        String newName = team.getName();
        assertEquals(newName, testName);
    }

    @Test
    public void testGetName() {
        assertEquals("test", team.getName());
    }

    @Test
    public void testTosaveableContent() throws TeamFullException {
        assertEquals("", team.toSaveableContent());
        team.add(pkm0);
        team.add(pkm1);
        team.add(pkm2);
        assertEquals("1\n" +
                        "bulbasaur\n" +
                        "grass/Poison\n" +
                        "chlorophyll/Overgrow\n" +
                        "45,49,49,45,65,65\n" +
                        "0,0,0,0,0,0\n" +
                        "0,0,0,0,0,0\n" +
                        "50\n" +

                        "4\n" +
                        "charmander\n" +
                        "fire\n" +
                        "blaze/solar power\n" +
                        "39,52,43,65,60,50\n" +
                        "0,0,0,0,0,0\n" +
                        "0,0,0,0,0,0\n" +
                        "50\n" +

                        "7\n" +
                        "squirtle\n" +
                        "Water\n" +
                        "rain-dish torrent\n" +
                        "44,48,65,40,50,64\n" +
                        "0,0,0,0,0,0\n" +
                        "0,0,0,0,0,0\n" +
                        "50\n",
                team.toSaveableContent());
    }

    @Test
    public void testToString() {
        assertEquals("Team test", team.toString().split("\n")[0]);
    }

    @Test
    public void testIterator() throws TeamFullException {
        for (int i = 0; i < 6; i++) {
            PokemonToEdit pkm = new PokemonToEdit();
            pkm.setId(i);
            team.add(pkm);

        }
        Iterator<PokemonToEdit> iter = team.iterator();
        int i = 0;
        while (iter.hasNext()) {
            assertEquals(i++, iter.next().getId());
        }
    }
}
