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

    /**
     */
    public void paintBasicMondrian(Color[][] pixels) {
        this.height = pixels.length;
        this.width = pixels[0].length;

        basicHelper(new ColorMatrix(pixels));
    }

    /**
     */
    private void basicHelper(ColorMatrix pixels) {
        if (pixels.isMinimalCell()) { // fill cell
            // fill();
        } else if (pixels.isQuarterWidth()) {

        } else if (pixels.isQuarterHeight()) {
        }
    }

    /**
     */
    public void paintComplexMondrian(Color[][] pixels) {
    }

    /**
     * Fills a image matrix of Color pixels with a white rectangular region starting
     * from
     * the position (x1,y1) inclusive to (x2,y2) exclusive.
     *
     * @param pixels the starting Color[][] pixel image matrix.
     * @param x1     the left-most x coordinate int
     * @param x2     the right-most x coordinate int
     * @param y1     the top-most y coordinate int
     * @param y2     the bottom-most y coordinate int
     */
    public static void fill(Color[][] pixels, int x1, int x2, int y1, int y2) {
        for (int y = y1 + 1; y < y2 - 1; y++) {
            for (int x = x1 + 1; x < x2 - 1; x++) {
                pixels[y][x] = Color.WHITE;
            }
        }
    }

    /**
     */
    private class Vec2 {
        int x;
        int y;

        /**
         */
        public Vec2(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * ColorMatrix is a wrapper class for a 2D Color matrix array, Color[][],
     * and provides methods for accessing the height and width of the pixel matrix
     * image.
     */
    private class ColorMatrix {
        Color[][] pixels;

        /**
         */
        public ColorMatrix(Color[][] pixels) {
            this.pixels = pixels;
        }

        /**
         */
        public boolean isMinimalCell() {
            return isQuarterWidth() && isQuarterHeight();
        }

        /**
         */
        public boolean isQuarterHeight() {
            return pixels[0].length < height;

        }

        /**
         */
        public boolean isQuarterWidth() {
            return pixels.length < width;
        }
    }
}
