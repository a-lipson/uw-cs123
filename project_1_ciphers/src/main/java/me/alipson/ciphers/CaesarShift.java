package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

// TODO: class doc
public class CaesarShift extends Substitution {

    public CaesarShift(int shift) {
        if (shift <= 0) {
            throw new IllegalArgumentException();
        }

        setShifter(caesarShifter(shift));
    }

    // create a shifter string from the provided int shift positions
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
