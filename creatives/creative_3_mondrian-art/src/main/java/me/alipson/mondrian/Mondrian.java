package me.alipson.mondrian;

import java.util.*;
import java.awt.*;

// Alexandre Lipson
// CSE 123
// 07/26/24
// C3: Mondrian Art
// TA: Daniel

/**
 * Mondrian generates two different types of immitation artwork similar to Piet
 * Mondrian's style.
 * The class works with Color[][] pixel matrices from the Picture class.
 * 
 * @see #paintBasicMondrian(Color[][]) the basic Mondrian painting method
 * @see #paintComplexMondrian(Color[][]) the complex Mondrain paiting method
 */
public class Mondrian {
    private int height;
    private int width;

    private Color[][] pixels;

    private Point origin;
    private Point center;

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
        this.origin = new Point(0, 0);
        this.center = new Point(width / 2, height / 2);
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

        // NOTE: is this syntax sugar acceptable?
        if (newRegions != null)
            for (Region r : newRegions)
                basicHelper(r, rand);
    }

    /**
     * Paints a Mondrian painting with many cell divisions,
     * mean brightness decreasing with proxmity to center,
     * and possible hue range shifting by angle of the cell with respect to the top
     * left corner.
     * 
     * @param pixels the input Color[][] pixels to modify
     * 
     * @see #complexHelper(Region, Random)
     */
    public void paintComplexMondrian(Color[][] pixels) {
        Of(pixels);
        complexHelper(new Region(new Point(0, 0), new Point(width, height)), new Random());
    }

    /**
     * Recursive helper method for complex Mondrian painter;
     * recursively splits input up to MAX_SUB_CELLS times until region reaches
     * minimal size as the base case, and fills the region according to a color
     * weighting, which relates color to angular location and brightness to
     * increased distance from center.
     * 
     * @param region the Region region to measure and either subdivide into more
     *               regions if large enough, or paint if of a terminally small size
     * @param rand   the seeded Random object to use
     * 
     * @see Region#getWeightedColor(Random)
     */
    private void complexHelper(Region region, Random rand) {
        Region[] newRegions = null;

        if (region.isQuarterWidth() && region.isQuarterHeight()) { // fill cell
            region.fill(region.getWeightedColor(rand));
        } else if (region.isQuarterWidth()) { // divide horizontally
            newRegions = region.splitMultipleHorizontal(rand);
        } else if (region.isQuarterHeight()) { // divide vertically
            newRegions = region.splitMultipleVertical(rand);
        } else { // divide horizontally and vertically
            newRegions = region.splitMultipleQuadrants(rand);
        }

        if (newRegions != null)
            for (Region r : newRegions)
                complexHelper(r, rand);
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

    /**
     * Creates a new int[] array of one greater length, filling in the last position
     * with a provided int value.
     * 
     * @param xs the original int[] array
     * @param x  the x to append to the end
     * 
     * @return the modified new int[] array.
     */
    // NOTE: could have included right-hand extreme bound in #chooseRandomDividers
    private int[] appendToIntList(int[] xs, int x) {
        int[] out = new int[xs.length + 1];
        for (int i = 0; i < xs.length; i++)
            out[i] = xs[i];
        out[xs.length] = x;
        return out;
    }

    /**
     * Flattens a two dimnesional Region[][] matrix into a one dimensional list
     * by concatenating subsequent Region[] arrays onto the first.
     * 
     * @param m the Region[][] matrix to flatten
     * 
     * @return the flattened one dimnesional Region[] array.
     */
    private Region[] flattenRegionMatrix2(Region[][] m) {
        // wish that we could create generic arrays
        Region[] out = new Region[m[0].length * m.length];
        int i = 0;
        for (Region[] row : m)
            for (Region r : row)
                out[i++] = r;
        return out;
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
        // NOTE: fairly sure that the assignment was most concerned with creating excess
        // Random objects...
        // also,

        /**
         * Splits a Region into two new Regions with a random
         * horizontal divider.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the Region[2] array of new Region objects.
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
         * @return the Region[2] array of new Region objects.
         */
        public Region[] splitVertical(Random rand) {
            int midX = chooseRandomVerticalDivider(rand);
            return new Region[] {
                    new Region(topLeft, new Point(midX, botRight.y)),
                    new Region(new Point(midX, topLeft.y), botRight) };
        }

        /**
         * Splits a Region into up to MAX_SUB_CELLS new Regions with random
         * horizontal dividers.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the Region[] array of new Region objects.
         */
        public Region[] splitMultipleHorizontal(Random rand) {
            int[] ys = appendToIntList(
                    chooseRandomDividers(topLeft.y, botRight.y, rand), botRight.y);
            Region[] regions = new Region[ys.length];
            Point currTopLeft = topLeft;

            for (int i = 0; i < regions.length; i++) {
                regions[i] = new Region(currTopLeft, new Point(botRight.x, ys[i]));
                currTopLeft = new Point(topLeft.x, ys[i]);
            }

            return regions;
        }

        /**
         * Splits a Region into up to MAX_SUB_CELLS new Regions with random
         * vertical dividers.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the Region[] array of new Region objects.
         */
        public Region[] splitMultipleVertical(Random rand) {
            int[] xs = appendToIntList(
                    chooseRandomDividers(topLeft.x, botRight.x, rand), botRight.x);
            Region[] regions = new Region[xs.length];
            Point currTopLeft = topLeft;

            for (int i = 0; i < regions.length; i++) {
                regions[i] = new Region(currTopLeft, new Point(xs[i], botRight.y));
                currTopLeft = new Point(xs[i], topLeft.y);
            }

            return regions;
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
         * Splits a Region into up to MAX_SUB_CELLS^2 new Regions with random
         * horizontal and horizontal dividers.
         * 
         * @param rand the seeded Random object to use
         * 
         * @return the Region[] array of new Region objects.
         */
        public Region[] splitMultipleQuadrants(Random rand) {
            int[] xs = appendToIntList(chooseRandomDividers(topLeft.x, botRight.x, rand), botRight.x);
            int[] ys = appendToIntList(chooseRandomDividers(topLeft.y, botRight.y, rand), botRight.y);
            Region[][] regions = new Region[ys.length][xs.length];

            Point currTopLeft = topLeft;

            for (int i = 0; i < xs.length; i++) {
                for (int j = 0; j < ys.length; j++) {
                    regions[j][i] = new Region(currTopLeft, new Point(xs[i], ys[j]));
                    currTopLeft = new Point(currTopLeft.x, ys[j]);
                }
                currTopLeft = new Point(xs[i], topLeft.y);
            }

            return flattenRegionMatrix2(regions);
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
         *         between min and max.
         */
        private int chooseRandomDivider(int min, int max, Random rand)
                throws IllegalArgumentException {
            // NOTE: this method is contingent on one quarter of the minimum picture
            // dimensions, 75, being at least MIN_CELL_SIZE.
            if (min + MIN_CELL_SIZE > max - MIN_CELL_SIZE + 1)
                throw new IllegalArgumentException(String.format(
                        "Range insufficient of two cells: %d, %d to hold MIN_CELL_SIZE %d.",
                        min, max, MIN_CELL_SIZE));

            // + 1 to consider inclusive max
            return rand.nextInt(min + MIN_CELL_SIZE, max - MIN_CELL_SIZE + 1);
        }

        /**
         * Provides a sorted int[] list of up to MAX_SUB_CELLS buffered dividers,
         * leaving a minimum of MIN_CELL_SIZE between each divider and endpoints.
         *
         * @param min  the minimum int
         * @param max  the maximum int
         * @param rand the seeded Random object to use
         * 
         * @return the randomized, ordered, and appropriately spaced int[] list between
         *         min and max.
         */
        private int[] chooseRandomDividers(int min, int max, Random rand) {
            // limit divider count to ensure each cell is at least MIN_CELL_SIZE
            int maxDividers = (max - min) / (2 * MIN_CELL_SIZE);
            int dividerCount = Math.min(maxDividers, MAX_SUB_CELLS);
            int[] dividers = new int[dividerCount];

            for (int i = 0; i < dividerCount; i++) {
                // ensure that the remaining space is sufficient to evenly space out remaining
                // dividers
                dividers[i] = chooseRandomDivider(
                        min, max - (dividerCount - (i + 1)) * MIN_CELL_SIZE, rand);
                min = dividers[i];
            }

            return dividers;
        }

        /**
         * 
         * 
         * @return Color the psuedo-randomized position-weighted Color color.
         */
        public Color getWeightedColor(Random rand) {
            Point regionCenter = regionCenter();
            double halfDiagonalLength = origin.distance(center);

            // distance from center determines minimum possible brightness
            float brightness = rand.nextFloat((float) (regionCenter.distance(center) / halfDiagonalLength), 1);

            Point centerToOrigin = new Point(-center.x, -center.y);
            // centerToOrigin length is the same as half diagonal length
            double cosAngle = dot(centerToOrigin, regionCenter) / (halfDiagonalLength * regionCenter.distance(origin));
            // clamp angle to fix floating point error
            cosAngle = Math.max(-1.0, Math.min(1.0, cosAngle));

            double fromOriginFacingAngle = Math.acos(cosAngle) / Math.PI;

            final float HUE_RANGE = 0.3f;
            // compute angle from center to origin vector using dot product.
            // angle against center and origin determines hue within a fixed range of
            // HUE_RANGE.
            float hue = rand.nextFloat((float) fromOriginFacingAngle - HUE_RANGE,
                    (float) fromOriginFacingAngle + HUE_RANGE);

            hue = (float) fromOriginFacingAngle;
            System.out.println(hue);

            return Color.getHSBColor(Math.min(hue, 1), 1, brightness);
        }

        // Point utility methods

        private double dot(Point a, Point b) {
            return a.x * b.x + a.y * b.y;
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

        private Point regionCenter() {
            return new Point((topLeft.x + botRight.x) / 2, (topLeft.y + botRight.y) / 2);
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
