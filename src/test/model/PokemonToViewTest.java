package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PokemonToViewTest {
    PokemonToView pkm;

    @BeforeEach
    public void beforeTest() {
        pkm = new PokemonToView();
    }

    @Test
    public void testConstructor() {
        PokemonToView demo = new PokemonToView();
        assertEquals(0, demo.getStats().sum());
    }

    @Test
    public void testToSaveableContent() {
        assertEquals("0\n" +
                        "null\n" +
                        "null\n" +
                        "null\n" +
                        "0,0,0,0,0,0\n",
                pkm.toSaveableContent());
    }

    @Test
    public void testToString() {
        assertEquals("Pokemon with id: 0\n" +
                "Name: null\n" +
                "Type: null\n" +
                "Hit point: 0\n" +
                "Attack: 0\n" +
                "Special attack: 0\n" +
                "Defence: 0\n" +
                "Special: 0\n" +
                "Speed: 0\n", pkm.toString());
    }

    @Test
    public void testMinorGetterAndSetter() {
        assertEquals(0, pkm.getId());
        assertNull(pkm.getName());
        assertNull(pkm.getAbility());
        assertNull(pkm.getType());
        pkm.setId(100);
        pkm.setName("demo");
        pkm.setAbility("Clear Body");
        pkm.setType("Grass");
//        pkm.setLevel(99);
//        pkm.setLevel(101);
        assertEquals(100, pkm.getId());
        assertEquals("demo", pkm.getName());
        assertEquals("Clear Body", pkm.getAbility());
        assertEquals("Grass", pkm.getType());

        for (int i = 0; i < 6; i++) {
            pkm.setSS(i, i);
            assertEquals(i, pkm.getStats().get(i).getSS());
        }
    }
}
