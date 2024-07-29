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

        int count = 0;
        int smallestGap = 10;
        int largestGap = 0;
        Mondrian m = new Mondrian();
        for (int t = 0; t < 100; t++) {

            int[] dividers = m.testChooseRandomDividers(0, 300 / 4);

            for (int i = 0; i < dividers.length - 1; i++) {
                int gap = dividers[i + 1] - dividers[i];
                if (gap >= 10)
                    count++;
                if (gap < smallestGap)
                    smallestGap = gap;
                if (gap > largestGap)
                    largestGap = gap;
            }
        }
        System.out.println(count);
        System.out.println(smallestGap);
        System.out.println(largestGap);
    }
}
