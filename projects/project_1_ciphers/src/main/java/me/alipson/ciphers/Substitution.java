package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

/**
 * Extends Cipher and provides base class for performing alphabetic shift
 * encryption and decryption.
 */
public class Substitution extends Cipher {

    private String shifter;

    /**
     * Empty constructor for new Substitution.
     */
    public Substitution() {
    }

    /**
     * Default constructor for Substitution object which takes and sets a shifter
     * key after validation.
     * 
     * @param shifter the shifter key used to instantiate a new Substitution object.
     * 
     * @throws IllegalArgumentException if shifter key is invalid.
     * @see Substitution#isValidShifter
     */
    public Substitution(String shifter) {
        if (!isValidShifter(shifter)) {
            throw new IllegalArgumentException();
        }

        this.shifter = shifter;
    }

    /**
     * Setter for the shifter.
     * 
     * @param shifter the shifter String to set.
     * 
     * @throws IllegalArgumentException if the shifter string is not valid.
     * @see Substitution#isValidShifter
     */
    public void setShifter(String shifter) {
        if (!isValidShifter(shifter)) {
            throw new IllegalArgumentException();
        }

        this.shifter = shifter;
    }

    /**
     * Encrypts input using the shifter.
     * 
     * @param input the input String to encrypt.
     * 
     * @return the encrypted String.
     */
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

    /**
     * Decrypts input using the shifter.
     * 
     * @param input the input String to decrypt.
     * 
     * @return the decrypted String.
     */
    @Override
    public String decrypt(String input) {
        String plaintext = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = shifter.indexOf(c);
            plaintext += Cipher.getEncodableChars().get(index);
        }

        return plaintext;
    }

    /**
     * Checks that a shifter is valid:
     * if shifter length is equal to the length of the cipher key set,
     * if shifter does not contain duplicate chars,
     * if shifter does not contain any invalid chars outside the encodable range,
     * returns true if valid and false if not.
     * 
     * @param shifter the shifter key to test.
     * 
     * @return the validity of the shifter, <code>true<\code> if valid,
     *         and <code>false<\code> otherwise.
     */
    private boolean isValidShifter(String shifter) {
        return !(shifter.length() != Cipher.TOTAL_CHARS ||
                containsDuplicate(shifter) ||
                containsInvalid(shifter));
    }

    /**
     * Checks if a shifter key contains duplicate chars,
     * returns true is duplicates are present and false if not.
     * 
     * @param shifter the shifter to check.
     * 
     * @return whether the shifter contains duplicate characters, <code>true<\code>
     *         if yes, and <code>false<\code> otherwise.
     */
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

    /**
     * Checks if a shifter key contains invalid chars outside the encodable range,
     * returns true is an invalid char is present and false if not.
     * 
     * @param shifter the shifter to check.
     * 
     * @return whether the shifter contains duplicate characters outside the
     *         encodable range, <code>true<\code>
     *         if yes, and <code>false<\code> otherwise.
     */
    public boolean containsInvalid(String shifter) {
        for (int i = 0; i < shifter.length(); i++) {
            if (!Cipher.isValidChar(shifter.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}
