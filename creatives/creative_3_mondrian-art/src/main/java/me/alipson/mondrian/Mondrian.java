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
    private static final int SPLIT_BUFFER = 10;

    /**
     */
    public void paintBasicMondrian(Color[][] pixels) {
        this.height = pixels.length;
        this.width = pixels[0].length;
        this.pixels = pixels;

        basicHelper(
                new Region(new Point(0, width), new Point(0, height)),
                new Random());
    }

    /**
     */
    private void basicHelper(Region region, Random rand) {
        if (region.isQuarterWidth() && region.isQuarterHeight()) { // fill cell
            region.fill(randomBasicColor(rand));
        } else if (region.isQuarterWidth()) { // divide horizontally
            Region.RegionPair newRegions = region.splitHorizontal(rand);
            basicHelper(newRegions.first, rand);
            basicHelper(newRegions.second, rand);
        } else if (region.isQuarterHeight()) { // divide vertically
            Region.RegionPair newRegions = region.splitVertical(rand);
            basicHelper(newRegions.first, rand);
            basicHelper(newRegions.second, rand);
        } else { // divide horizontally and vertically
            Region.RegionQuad newRegions = region.splitQuadrants(rand);
            basicHelper(newRegions.topLeft, rand);
            basicHelper(newRegions.topRight, rand);
            basicHelper(newRegions.botLeft, rand);
            basicHelper(newRegions.botRight, rand);
        }
    }

    /**
     */
    public void paintComplexMondrian(Color[][] pixels) {
    }

    /**
     */
    private Color randomBasicColor(Random rand) {
        return COLORS[rand.nextInt(COLORS.length)];
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

        public Region(Point topLeft, Point botRight) {
            this.topLeft = topLeft;
            this.botRight = botRight;
        }

        // FIX: way to not create so many new objects?
        public RegionPair splitHorizontal(Random rand) {
            int midY = chooseRandomHorizontalDivider(rand);
            return new RegionPair(
                    new Region(topLeft, new Point(botRight.x, midY)),
                    new Region(new Point(topLeft.x, midY), botRight));
        }

        public RegionPair splitVertical(Random rand) {
            int midX = chooseRandomVerticalDivider(rand);
            return new RegionPair(
                    new Region(topLeft, new Point(midX, botRight.y)),
                    new Region(new Point(midX, topLeft.y), botRight));
        }

        public RegionQuad splitQuadrants(Random rand) {
            int midX = chooseRandomVerticalDivider(rand);
            int midY = chooseRandomHorizontalDivider(rand);
            return new RegionQuad(
                    new Region(topLeft, new Point(midX, midY)),
                    new Region(new Point(midX, topLeft.y), new Point(botRight.x, midY)),
                    new Region(new Point(topLeft.x, midY), new Point(midX, botRight.y)),
                    new Region(new Point(midX, midY), botRight));
        }

        private int chooseRandomHorizontalDivider(Random rand) {
            return chooseRandomSubregion(topLeft.y, botRight.y, rand);
        }

        private int chooseRandomVerticalDivider(Random rand) {
            return chooseRandomSubregion(topLeft.x, botRight.x, rand);
        }

        /**
         */
        public int chooseRandomSubregion(int min, int max, Random rand) {
            // + 1 to consider inclusive max
            return rand.nextInt(min + SPLIT_BUFFER, max - SPLIT_BUFFER + 1);
        }

        /**
         */
        // public boolean isMinimalCell() {
        // return isQuarterWidth() && isQuarterHeight();
        // }

        /**
         */
        public boolean isQuarterHeight() {
            return botRight.y - topLeft.y < height;
        }

        /**
         */
        public boolean isQuarterWidth() {
            return botRight.x - topLeft.x < width;
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

        class RegionPair {
            public Region first;
            public Region second;

            public RegionPair(Region first, Region second) {
                this.first = first;
                this.second = second;
            }
        }

        class RegionQuad {
            public Region topLeft;
            public Region topRight;
            public Region botLeft;
            public Region botRight;

            public RegionQuad(Region topLeft, Region topRight, Region botLeft, Region botRight) {
                this.topLeft = topLeft;
                this.topRight = topRight;
                this.botLeft = botLeft;
                this.botRight = botRight;
            }
        }
    }
}
