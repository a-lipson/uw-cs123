package me.alipson.mondrian;

import java.awt.*;
import java.util.*;

// Alexandre Lipson
// CSE 123
// 07/26/24
// C3: Mondrian Art
// TA: Daniel

public class Client {
    public static void main(String[] args) throws Exception {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to the CSE 123 Mondrian Art Generator!");

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

        int choice = 0;
        while (choice != 1 && choice != 2) {
            System.out.print("Enter 1 for a basic Mondrian or 2 for a complex Mondrian: ");
            choice = console.nextInt();
        }

        System.out.print("Enter image width (>= 300px): ");
        int width = console.nextInt();
        System.out.print("Enter image height (>= 300px): ");
        int height = console.nextInt();

        if (width < 300 || height < 300) {
            console.close();
            throw new IllegalArgumentException("Image size too small.");
        }

        Mondrian mond = new Mondrian();
        Picture pic = new Picture(width, height);
        Color[][] pixels = pic.getPixels();

        if (choice == 1) {
            mond.paintBasicMondrian(pixels);
        } else { // choice == 2
            mond.paintComplexMondrian(pixels);
        }

        pic.setPixels(pixels);
        pic.save(choice == 1 ? "basic.png" : "extension.png");
        System.out.println("Enjoy your artwork!");
        console.close();
    }
}
