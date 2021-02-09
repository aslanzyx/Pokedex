package model;

import exceptions.StatInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {
    Stats stats;

    @BeforeEach
    public void beforeTest() {
        stats = new Stats();
    }

    @Test
    public void testConstrutor() {
        Stats demo = new Stats();
        for (Stat s : demo) {
            assertEquals(0, s.getSS());
            assertEquals(0, s.getIV());
            assertEquals(0, s.getBS());
        }
        assertEquals(0, demo.sum());
    }

    @Test
    public void testSum() {
        assertEquals(0, stats.sum());
        int i = 0;
        for (Stat s : stats) {
            s.setSS(i++);
        }
        assertEquals(15, stats.sum());
    }

    @Test
    public void testGet() {
        int i = 0;
        for (Stat s : stats) {
            s.setSS(i++);
        }
        for (int j = 0; i < 6; i++) {
            assertEquals(j, stats.get(j).getSS());
        }
    }

    @Test
    public void testValid() throws StatInvalidException {
        assertTrue(stats.valid());
        for (Stat stat : stats) {
            stat.setBS(252);
        }
        assertFalse(stats.valid());
    }

    @Test
    public void testIterator() {
        Iterator<Stat> iter = stats.iterator();
        while (iter.hasNext()) {
            Stat s = iter.next();
            assertEquals(0, s.getSS());
            assertEquals(0, s.getIV());
            assertEquals(0, s.getBS());
        }
    }

    @Test
    public void testStatsOrder() {
        for (int i = 0; i < 6; i++) {
            assertEquals("Hit point", Stats.STATS_ORDER[0]);
            assertEquals("Attack", Stats.STATS_ORDER[1]);
            assertEquals("Special attack", Stats.STATS_ORDER[2]);
            assertEquals("Defence", Stats.STATS_ORDER[3]);
            assertEquals("Special", Stats.STATS_ORDER[4]);
            assertEquals("Speed", Stats.STATS_ORDER[5]);
        }
    }

}
