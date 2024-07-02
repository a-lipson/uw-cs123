package me.alipson.ciphers;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

// TODO: class doc
public class CaesarKey extends Substitution {

    public CaesarKey(String key) {
        if (!isValidKey(key)) {
            throw new IllegalArgumentException();
        }

        setShifter(caesarKeyShifter(key));
    }

    // generates shifter string from caeser cipher key string
    private static String caesarKeyShifter(String key) {
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
    // returns true if all are valid and false if at least one is not.
    private boolean isValidKey(String key) {
        return !(key.isEmpty() ||
                containsDuplicate(key) ||
                containsInvalid(key));
    }
}
