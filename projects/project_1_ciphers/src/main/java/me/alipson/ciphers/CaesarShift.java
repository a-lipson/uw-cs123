package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

/**
 * A simple alphebetic substitution ciphers which shifts each letter of the
 * alphabet by a given amount to the left.
 */
public class CaesarShift extends Substitution {

    /**
     * Default constructor for CaesarShfit which validates the shifting position and
     * sets the shifter.
     * 
     * @param shift the number of positions int to shift.
     */
    public CaesarShift(int shift) {
        if (shift <= 0) {
            throw new IllegalArgumentException();
        }

        setShifter(caesarShifter(shift));
    }

    /**
     * Create a shifter string from the provided int shift positions.
     * 
     * @param shift the number of positions int to shift.
     * 
     * @return the adjusted shifter String.
     */
    private String caesarShifter(int shift) {
        Queue<Character> encodableChars = new LinkedList<>(Cipher.getEncodableChars());

        for (int i = 0; i < shift % encodableChars.size(); i++) { // shift ordered encodable chars
            Character first = encodableChars.poll(); // remove & gather first elem
            encodableChars.offer(first); // add first to end of queue
        }

        String shifter = "";

        while (!encodableChars.isEmpty()) { // build shifter from queue
            shifter += encodableChars.poll();
        }

        return shifter;
    }

}
