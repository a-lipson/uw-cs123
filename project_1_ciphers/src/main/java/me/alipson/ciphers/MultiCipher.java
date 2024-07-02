package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

// TODO: class doc
public class MultiCipher extends Substitution {
    private List<Cipher> ciphers;

    public MultiCipher(List<Cipher> ciphers) {
        if (ciphers == null) {
            throw new IllegalArgumentException();
        }

        this.ciphers = ciphers;
    }

    @Override
    public String encrypt(String input) {
        String ciphertext = input; // could use one variable

        for (Cipher c : ciphers) {
            ciphertext = c.encrypt(ciphertext);
        }

        return ciphertext;
    }

    @Override
    public String decrypt(String input) {
        String plaintext = input;

        for (int i = ciphers.size() - 1; i >= 0; i--) { // process through list backwards
            plaintext = ciphers.get(i).decrypt(plaintext);
        }

        return plaintext;
    }
}
