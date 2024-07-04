package me.alipson.extracredit;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

public class SubstitutionRandom extends Cipher {

    private String shifter;
    private int digits;
    private int seed;

    public SubstitutionRandom(int digits) {
        if (digits <= 0 || digits > 9) {
            throw new IllegalArgumentException();
        }

        this.digits = digits;
        setRandomShifter();
    }

    private void setRandomShifter() {
        String randomShifter = "";

        List<Character> encodableChars = Cipher.getEncodableChars();

        Collections.shuffle(encodableChars, new Random(seed));

        for (Character c : encodableChars) {
            randomShifter += c;
        }

        this.shifter = randomShifter;

    }

    private void setSeed() {
        int newSeed = 0;
        Random random = new Random();
        for (int i = 0; i < digits; i++) {
            newSeed += random.nextInt(10);
        }

        this.seed = newSeed;
    }

    private String getSeedString() {
        return String.format("%0" + digits + "d", seed);
    }

    @Override
    public String encrypt(String input) {

        // randomly generate seed every time encrypt message
        setSeed();

        // place seed at beginning of encrypted message
        String ciphertext = getSeedString();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int shifterIndex = Cipher.getCharIndex(c);
            ciphertext += shifter.charAt(shifterIndex);
        }

        return ciphertext;
    }

    private String extractSeed(String input) {
        return input.substring(0, digits);
    }

    @Override
    public String decrypt(String input) {
        String plaintext = "";

        // update current seed
        seed = Integer.parseInt(extractSeed(input));

        // remove from input
        input = input.substring(digits, input.length());

        setRandomShifter();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = shifter.indexOf(c);
            plaintext += Cipher.getEncodableChars().get(index);
        }

        return plaintext;
    }
}
