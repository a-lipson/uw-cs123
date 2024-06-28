package me.alipson.ciphers;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

// TODO: Write your implementation to CaesarKey here!

public class CaesarKey extends Substitution {

    public CaesarKey(String key) {
        super(caesarShifter(key));
    }

    // generates shifter string from caaser cipher key string
    private static String caesarShifter(String key) {
        String shifter = key;

        for (char c : Cipher.getEncodableChars()) {
            if (key.indexOf(c) < 0) { // char not in string
                shifter += c;
            }
        }

        return shifter;

    }

    // checks whether key is valid:
    // if key is not empty,
    // if key does not contain duplicate chars,
    // if key does not contain any invalid chars outside the encodable range,
    // returns true if valid and false if not.
    private boolean isValidKey(String key) {
        return !(key.isEmpty() || containsDuplicate(key) || containsInvalid(key));
    }
}
