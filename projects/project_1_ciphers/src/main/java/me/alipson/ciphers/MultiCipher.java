package me.alipson.ciphers;

import java.util.*;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

/**
 * Provides a way to combine multiple ciphers in series for encryption and
 * decryption
 */
public class MultiCipher extends Substitution {
    private List<Cipher> ciphers;

    /**
     * Default constructor for MultiCipher
     * 
     * @param ciphers the list of ciphers to use.
     * 
     * @throws IllegalArgumentException if the list of ciphers is null.
     */
    public MultiCipher(List<Cipher> ciphers) {
        if (ciphers == null) {
            throw new IllegalArgumentException();
        }

        this.ciphers = ciphers;
    }

    /**
     * Encrypts input using the series of shifters from the cipher list.
     * 
     * @param input the input String to encrypt.
     * 
     * @return the encrypted String.
     */
    @Override
    public String encrypt(String input) {
        String ciphertext = input; // could use one variable

        for (Cipher c : ciphers) {
            ciphertext = c.encrypt(ciphertext);
        }

        return ciphertext;
    }

    /**
     * Decrypts input using the series of shifters from the cipher list.
     * 
     * @param input the input String to decrypt.
     * 
     * @return the decrypted String.
     */
    @Override
    public String decrypt(String input) {
        String plaintext = input;

        for (int i = ciphers.size() - 1; i >= 0; i--) { // process through list backwards
            plaintext = ciphers.get(i).decrypt(plaintext);
        }

        return plaintext;
    }
}
