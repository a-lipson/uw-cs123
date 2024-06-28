package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

// TODO: Write your implementation to Subsitution here!

public class Substitution extends Cipher {

    private String shifter;

    public Substitution() {
    }

    public Substitution(String shifter) {
        if (isValidShifter(shifter)) {
            throw new IllegalArgumentException();
        }

        this.shifter = shifter;
    }

    // sets the shifter string.
    // throws an IllegalArgumentException if the shifter string is not valid.
    public void setShifter(String shifter) {
        if (isValidShifter(shifter)) {
            throw new IllegalArgumentException();
        }

        this.shifter = shifter;
    }

    @Override
    public String encrypt(String input) {
        String ciphertext = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int shifterIndex = Cipher.getCharIndex(c);
            ciphertext += shifter.charAt(shifterIndex);
        }

        return ciphertext;
    }

    @Override
    public String decrypt(String input) {
        String plaintext = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = shifter.indexOf(c);
            plaintext += shifter.charAt(index);
        }

        return plaintext;
    }

    // checks that a shifter is valid:
    // if shifter length is equal to the length of the cipher key set,
    // if shifter does not contain duplicate chars,
    // if shifter does not contain any invalid chars outside the encodable range,
    // returns true if valid and false if not.
    private boolean isValidShifter(String shifter) {
        return shifter.length() == Cipher.TOTAL_CHARS ||
                !containsDuplicate(shifter) ||
                !containsInvalid(shifter);
    }

    // checks if a shifter key contains duplicate chars,
    // returns true is duplicates are present and false if not.
    public boolean containsDuplicate(String shifter) { // protected modifier if were allowed
        Set<Character> chars = new HashSet<>();
        for (int i = 0; i < shifter.length(); i++) {
            char c = shifter.charAt(i);
            if (chars.contains(c)) {
                return true;
            }
            chars.add(c);
        }
        return false;
    }

    // checks if a shifter key contains invalid chars outside the encodable range,
    // returns true is an invalid char is present and false if not.
    public boolean containsInvalid(String shifter) {
        for (int i = 0; i < shifter.length(); i++) {
            if (!Cipher.isValidChar(shifter.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
