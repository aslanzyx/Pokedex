package model;

import exceptions.StatInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StatTest {

    Stat stat;

    @BeforeEach
    public void testBefore() {
        stat = new Stat();
    }

    @Test
    public void testConstructor() {
        Stat demo = new Stat();
        assertEquals(0, demo.getSS());
        assertEquals(0, demo.getIV());
        assertEquals(0, demo.getBS());
    }

    @Test
    public void testGetAndSetSS() {
        stat.setSS(1);
        assertEquals(1, stat.getSS());
    }

    @Test
    public void testGetAndSetIV() {
        try {
            stat.setIV(1);
            assertEquals(1, stat.getIV());
        } catch (StatInvalidException e) {
            fail("Should pass");
        }
    }

    @Test
    public void testGetAndSetIVInvalid() {
        try {
            stat.setIV(32);
            fail("Should not pass");
        } catch (StatInvalidException e) {
            // pass
        }
    }

    @Test
    public void testGetAndSetBS() {
        try {
            stat.setBS(1);
            assertEquals(1, stat.getBS());
        } catch (StatInvalidException e) {
            fail("Should pass");
        }
    }

    @Test
    public void testGetAndSetBSInvalid() {
        try {
            stat.setBS(253);
            fail("Should not pass");
        } catch (StatInvalidException e) {
            // pass
        }
    }

}
