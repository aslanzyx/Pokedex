package model;

import java.util.Arrays;
import java.util.Iterator;

// Stats object hold 6 stats
public class Stats implements Iterable<Stat> {
    //    private static final int MAX_PROP = 6;
    private static final int MAX_BS = 512;
    public static final String[] STATS_ORDER = new String[]{
            "Hit point",
            "Attack",
            "Special attack",
            "Defence",
            "Special",
            "Speed"
    };

    private Stat[] stats;

    public Stats() {
        stats = new Stat[]{
                new Stat(),
                new Stat(),
                new Stat(),
                new Stat(),
                new Stat(),
                new Stat()
        };
    }

    // EFFECT: Get the sum of all properties
    public int sum() {
        int sum = 0;
        for (Stat s : stats) {
            sum += s.getSS();
        }
        return sum;
    }

    // EFFECT: Get stat by index
    public Stat get(int i) {
        return stats[i];
    }

    // EFFECT: Check if bs allocation is valid
    public boolean valid() {
        int sum = 0;
        for (Stat stat : stats) {
            sum += stat.getBS();
        }
        return sum <= MAX_BS;
    }

    @Override
    public Iterator<Stat> iterator() {
        return Arrays.stream(stats).iterator();
    }

}