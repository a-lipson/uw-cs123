package me.alipson.ciphers;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

/**
 * Extends the Substitution Cipher to provide a encryption using a keyword; the
 * shifter is build from the remaining alphabet not using within the keyword.
 */
public class CaesarKey extends Substitution {

    /**
     * Default constructor for CaesarKey which takes and sets a key after
     * validation.
     * 
     * @param key the keyword String to use for the shifter.
     * 
     * @throws IllegalArgumentException if the keyword string is not valid.
     * @see CaesarKey#isValidKey
     */
    public CaesarKey(String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException();
        }

        setShifter(caesarKeyShifter(key));
    }

    /**
     * Generates shifter string from caeser cipher key string
     * 
     * @param key the keyword String to generate the shifter from.
     * 
     * @return the shifter String.
     */
    private static String caesarKeyShifter(String key) {
        String shifter = key;

        for (char c : Cipher.getEncodableChars()) {
            if (key.indexOf(c) < 0) { // char not in string
                shifter += c;
            }
        }

        return shifter;
    }

    /**
     * Checks whether key is valid:
     * if key is not empty,
     * if key does not contain duplicate chars,
     * if key does not contain any invalid chars outside the encodable range,
     * returns true if all are valid and false if at least one is not.
     * 
     * @param key the keyword to validate.
     * 
     * @return the validity of the keyword; <code>true<\code> if valid, and
     *         <code>false<\code> otherwise.
     */
    private boolean isValidKey(String key) {
        return !(key.isEmpty() ||
                containsDuplicate(key) ||
                containsInvalid(key));
    }
}
