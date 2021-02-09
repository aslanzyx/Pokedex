package model;

import exceptions.StatInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class PokemonToEditTest {

    PokemonToEdit pkm;

    @BeforeEach
    public void testBefore() {
        pkm = new PokemonToEdit();
    }

    @Test
    public void testConstructor() {
        PokemonToEdit demo = new PokemonToEdit();
        assertEquals(50, demo.getLevel());
    }

    @Test
    public void testToSaveableContent() {
        assertEquals("0\n" +
                "null\n" +
                "null\n" +
                "null\n" +
                "0,0,0,0,0,0\n" +
                "0,0,0,0,0,0\n" +
                "0,0,0,0,0,0\n" +
                "50\n", pkm.toSaveableContent());
    }

    @Test
    public void testToString() {
        assertEquals("Pokemon with id: 0\n" +
                "Name: null\n" +
                "Type: null\n" +
                "Hit point: 5\n" +
                "Attack: 5\n" +
                "Special attack: 5\n" +
                "Defence: 5\n" +
                "Special: 5\n" +
                "Speed: 5\n", pkm.toString());
    }

    @Test
    public void testSetLevel() {
        try {
            pkm.setLevel(100);
            assertEquals(100, pkm.getLevel());
        } catch (StatInvalidException e) {
            fail("Should pass");
        }
    }

    @Test
    public void testSetLevelInvalid() {
        try {
            pkm.setLevel(101);
            fail("Should not pass");
        } catch (StatInvalidException e) {
            // pass
        }
    }

    @Test
    public void testSetIV() {
        try {
            for (int i = 0; i < 6; i++) {
                pkm.setIV(i, 31);
                assertEquals(31, pkm.getStats().get(i).getIV());
            }
        } catch (StatInvalidException e) {
            fail("Should pass");
        }
    }

    @Test
    public void testSetIVInvalid() {
        try {
            pkm.setIV(0, 32);
            fail("Should not pass");
        } catch (StatInvalidException e) {
            // pass
        }
    }

    @Test
    public void testSetBS() {
        try {
            for (int i = 0; i < 6; i++) {
                pkm.setBS(i, 252);
                assertEquals(252, pkm.getStats().get(i).getBS());
            }
        } catch (StatInvalidException e) {
            fail("Should pass");
        }
    }

    @Test
    public void testSetBSInvalid() {
        try {
            pkm.setBS(0, 253);
            fail("Should not pass");
        } catch (StatInvalidException e) {
            // pass
        }
    }

}
