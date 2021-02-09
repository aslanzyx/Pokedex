package persistence;

import model.PokemonToView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CrawlerTest {
    @BeforeEach
    public void beforeTest() {
        // This class is used as static class
    }

    @Test
    public void testConstructor() {
        Crawler demo = new Crawler();
        assertNotNull(demo);
    }

    @Test
    public void testloadPokemon() {
        try {
            PokemonToView pkm = Crawler.loadPokemon("eternatus");
            System.out.println(pkm);
        } catch (IOException e) {
            fail("shoud pass");
        }
    }
}
