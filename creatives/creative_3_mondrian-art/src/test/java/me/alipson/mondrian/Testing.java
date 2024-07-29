package me.alipson.mondrian;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Testing
 */
public class Testing {

    @Test
    public void testChooseRandomDividers() {
        Mondrian mond = new Mondrian();
        for (int t = 0; t < 100; t++) {

            int[] dividers = mond.testChooseRandomDividers(0, 300 / 4);

            for (int i = 0; i < dividers.length - 1; i++) {
                assertTrue(dividers[i + 1] - dividers[i] > 10);
            }
        }
    }
}
