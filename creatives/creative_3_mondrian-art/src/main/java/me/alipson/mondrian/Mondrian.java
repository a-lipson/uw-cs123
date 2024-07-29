package me.alipson.mondrian;

import java.util.*;
import java.awt.*;

// Alexandre Lipson
// CSE 123
// 07/26/24
// C3: Mondrian Art
// TA: Daniel

public class Mondrian {
    private int height;
    private int width;

    private Color[][] pixels;

    private static final Color[] COLORS = { Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE };
    private static final int MIN_CELL_SIZE = 10;
    private static final int MAX_SUB_CELLS = 4;

    /**
     * Factory method for starting a new Mondrian art by initializing
     * height and width fields using input Color[][] pixels.
     *
     * @param pixels the Color[][] pixels on which create the Mondrian art.
     */
    private void Of(Color[][] pixels) {
        this.height = pixels.length;
        this.width = pixels[0].length;
        this.pixels = pixels;
    }

    /**
     * Sets the input Color[][] pixels to a Mondrian painting
     * of randomly painted rectangular regions.
     * 
     * @param pixels the input Color[][] pixels to modify
     *
     * @see #basicHelper(Region, Random)
     */
    public void paintBasicMondrian(Color[][] pixels) {
        Of(pixels);
        basicHelper(new Region(new Point(0, 0), new Point(width, height)), new Random());
    }

    /**
     * Recursive helper method for basic Mondrian painter; recursively splits input
     * Region after minimal size checks and, as the base case, fills in the Region
     * with a random Color color.
     * 
     * @param region the Region region to measure and either subdivide into more
     *               regions if large enough, or paint if of a terminally small size
     * @param rand   the seeded Random object to use
     */
    private void basicHelper(Region region, Random rand) {

        Region[] newRegions = null;

        if (region.isQuarterWidth() && region.isQuarterHeight()) { // fill cell
            region.fill(randomBasicColor(rand));
        } else if (region.isQuarterWidth()) { // divide horizontally
            newRegions = region.splitHorizontal(rand);
        } else if (region.isQuarterHeight()) { // divide vertically
            newRegions = region.splitVertical(rand);
        } else { // divide horizontally and vertically
            newRegions = region.splitQuadrants(rand);
        }

        // NOTE: is this syntactic sugar acceptable?
        if (newRegions != null)
            for (Region r : newRegions)
                basicHelper(r, rand);
    }

    /**
     * @see #complexHelper(Region, Random)
     */
    public void paintComplexMondrian(Color[][] pixels) {
        Of(pixels);
        complexHelper(new Region(new Point(0, 0), new Point(width, height)), new Random());
    }

    /**
     * Relates color to location
     */
    private void complexHelper(Region region, Random rand) {

    }

    /**
     * Gives a random Color color from the valid COLORS constant array.
     * 
     * @param rand the seeded Random object to use
     * 
     * @return the random Color color.
     */
    private Color randomBasicColor(Random rand) {
        return COLORS[rand.nextInt(COLORS.length)];
    }

    public int[] testChooseRandomDividers(int min, int max) {
        Point dummyPoint = new Point(0, 0);
        Region dummyRegion = new Region(dummyPoint, dummyPoint);
        return dummyRegion.chooseRandomDividers(min, max, new Random());
    }

    /**
     * Region wraps a 2D Color matrix array of Color[][] in addition to
     * two Point points which form a region of operation for a Picture object's
     * Color[][] martix.
     * The class provides methods for accessing the height and width of the pixel
     * matrix image,
     */
    private class Region {
        Point topLeft;
        Point botRight;

        /**
         * Creates a new Region object defined by two Point coordinate bounds.
         * 
         * @param topLeft  the top left Point coordinate
         * @param botright the bottom right Point coordinate
         */
        public Region(Point topLeft, Point botRight) {
            this.topLeft = topLeft;
            this.botRight = botRight;
        }

        // FIX: way to not create so many new objects?

        /**
         * Splits a Region into two new Regions with a random
         * horizontal divider.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the fixed Region[2] array of new Region objects.
         */
        public Region[] splitHorizontal(Random rand) {
            int midY = chooseRandomHorizontalDivider(rand);
            return new Region[] {
                    new Region(topLeft, new Point(botRight.x, midY)),
                    new Region(new Point(topLeft.x, midY), botRight) };
        }

        /**
         * Splits a Region into two new Regions with a random
         * vertical divider.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the fixed Region[2] array of new Region objects.
         */
        public Region[] splitVertical(Random rand) {
            int midX = chooseRandomVerticalDivider(rand);
            return new Region[] {
                    new Region(topLeft, new Point(midX, botRight.y)),
                    new Region(new Point(midX, topLeft.y), botRight) };
        }

        /**
         * Splits a Region into four new Regions with random
         * horizontal and vertical dividers.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the fixed Region[4] array of new Region objects.
         */
        public Region[] splitQuadrants(Random rand) {
            int midX = chooseRandomVerticalDivider(rand);
            int midY = chooseRandomHorizontalDivider(rand);
            return new Region[] {
                    new Region(topLeft, new Point(midX, midY)),
                    new Region(new Point(midX, topLeft.y), new Point(botRight.x, midY)),
                    new Region(new Point(topLeft.x, midY), new Point(midX, botRight.y)),
                    new Region(new Point(midX, midY), botRight) };
        }

        /**
         * Chooses a random middle int between the horizontal extremes of the Region.
         * 
         * @param rand the seeded Random object to use
         *
         * @return the middle int between the horizontal extremes.
         * 
         * @see #chooseRandomDivider(int, int, Random)
         */
        private int chooseRandomHorizontalDivider(Random rand) {
            return chooseRandomDivider(topLeft.y, botRight.y, rand);
        }

        /**
         * Chooses a random middle int between the vertical extremes of the Region.
         * 
         * @param rand the seeded Random object to use
         *
         * @return the middle int between the vertical extremes.
         * 
         * @see #chooseRandomDivider(int, int, Random)
         */
        private int chooseRandomVerticalDivider(Random rand) {
            return chooseRandomDivider(topLeft.x, botRight.x, rand);
        }

        /**
         * Returns an random int within the buffered range of a minimum and maximum
         * inclusive, ensuring that the division has MIN_CELL_SIZE units
         * between both the minimum and maximum.
         *
         * @param min  the minimum int
         * @param max  the maximum int
         * @param rand the seeded Random object to use
         * 
         * @return the randomized and appropriately spaced int
         *         buffered between min and max.
         */
        private int chooseRandomDivider(int min, int max, Random rand)
                throws IllegalArgumentException {
            // NOTE: this method is contingent on one quarter of the minimum picture
            // dimensions, 75, being at least MIN_CELL_SIZE.
            if (min + MIN_CELL_SIZE > max - MIN_CELL_SIZE + 1)
                throw new IllegalArgumentException(String.format(
                        "Range insufficient size to two cells: %d, %d of hold MIN_CELL_SIZE %d.",
                        min, max, MIN_CELL_SIZE));

            // + 1 to consider inclusive max
            return rand.nextInt(min + MIN_CELL_SIZE, max - MIN_CELL_SIZE + 1);
        }

        public int[] chooseRandomDividers(int min, int max, Random rand) {
            // limit divider count to ensure each cell is at least MIN_CELL_SIZE
            int maxDividers = (max - min) / (2 * MIN_CELL_SIZE);
            int dividerCount = Math.min(maxDividers, MAX_SUB_CELLS);
            int[] dividers = new int[dividerCount];
            // int validDividers = 0;
            System.out.printf("trying to divide %d thru %d, %d times\n", min, max, dividerCount);

            for (int i = 0; i < dividerCount; i++) {
                System.out.println("min: " + min + " max: " + max);
                dividers[i] = chooseRandomDivider(min, max - (dividerCount - (i + 1)) * MIN_CELL_SIZE, rand);
                min = dividers[i];
            }

            // naive approach
            // while (validDividers < dividerCount) {
            // boolean valid = false;
            // while (!valid) {
            //
            // int dividerCandidate = rand.nextInt(min + MIN_CELL_SIZE, max - MIN_CELL_SIZE
            // + 1);
            // for (int i = 0; i < validDividers; i++) {
            // if (Math.abs(dividers[i] - dividerCandidate) > MIN_CELL_SIZE) {
            // // add dividerCandidate to dividers
            // }
            // valid = true;
            // }
            // }
            // validDividers++;
            // }

            return dividers;
        }

        /**
         * Determines whether the current region height is
         * smaller than one quarter of the total height.
         *
         * @return whether the region is smaller,
         *         {@code true} if smaller and
         *         {@code false} if larger.
         */
        public boolean isQuarterHeight() {
            return botRight.y - topLeft.y < height / 4;
        }

        /**
         * Determines whether the current region width is
         * smaller than one quarter of the total width.
         *
         * @return whether the region is smaller,
         *         {@code true} if smaller and
         *         {@code false} if larger.
         */
        public boolean isQuarterWidth() {
            return botRight.x - topLeft.x < width / 4;
        }

        /**
         * Fills the current region of the Mondrian pixels with a given Color color,
         * starting from topLeft inclusive to botRight exclusive,
         * leaving a one pixel border around the region.
         *
         * @param color the Color color to fill
         */
        public void fill(Color color) {
            for (int y = topLeft.y + 1; y < botRight.y - 1; y++) {
                for (int x = topLeft.x + 1; x < botRight.x - 1; x++) {
                    pixels[y][x] = color;
                }
            }
        }
    }
}
