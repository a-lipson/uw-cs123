package me.alipson.multiswapcipher;

import java.util.*;

public class MultiSwapCipher implements Cipher {

    private List<Character> swaps;

    public MultiSwapCipher() {

    }

    public MultiSwapCipher(List<Character> swaps) {
        setSwaps(swaps);
    }

    public void setSwaps(List<Character> swaps) {
        if (swaps.size() < 2) {
            throw new IllegalArgumentException("Swap key list too short");
        }

        for (char c : swaps) {
            if (c < Cipher.MIN_CHAR || c > Cipher.MAX_CHAR) {
                throw new IllegalArgumentException("Swap key outside encodable range");
            }
        }

        this.swaps = swaps;
    }

    // gives uppercase version of input string to encrypt/decrypt
    private String validateInput(String input) {
        return input.toUpperCase();
    }

    public String encrypt(String input) {
        if (this.swaps == null) {
            throw new IllegalStateException("Swap key list not initialized");
        }

        String plaintext = validateInput(input);

        String ciphertext = "";

        for (int i = 0; i < plaintext.length(); i++) {
            char c = plaintext.charAt(i);
            int index = swaps.indexOf(c);
            if (index == -1) { // if char not in swap key list
                ciphertext += c;
            } else {
                int swapCharIndex = (index + 1) % swaps.size();
                ciphertext += swaps.get(swapCharIndex);
            }

        }

        return ciphertext;

    }

    public String decrypt(String input) {
        if (this.swaps == null) {
            throw new IllegalStateException("Swap key list not initialized");
        }

        String ciphertext = validateInput(input);

        String plaintext = "";

        for (int i = 0; i < ciphertext.length(); i++) {
            char c = ciphertext.charAt(i);
            int index = swaps.indexOf(c);
            if (index == -1) { // if char not in swap key list
                plaintext += c;
            } else {
                int swapCharIndex = (index - 1) % swaps.size();
                plaintext += swaps.get(swapCharIndex);
            }
        }

        return plaintext;

    }

}
