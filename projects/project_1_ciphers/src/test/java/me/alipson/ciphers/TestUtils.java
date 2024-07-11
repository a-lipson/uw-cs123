package me.alipson.ciphers;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class TestUtils {

    @Test
    @DisplayName("Cipher Utility Method Tests")
    public void testCipherUtilities() {
        assertTrue(Cipher.MIN_CHAR == (int) ('A'));

        assertEquals(0, Cipher.getCharIndex('A'));
        assertEquals(1, Cipher.getCharIndex('B'));

        assumeTrue(Cipher.isValidChar('A'));
        assumeFalse(Cipher.isValidChar('a'));
    }

    @Test
    @DisplayName("Substitution Utility Method Tests")
    public void testSubstitutionUtilities() {
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        // example substitution cipher that does nothing
        Substitution testSubstitution = new Substitution(
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        assertTrue(testSubstitution.containsDuplicate("HELLO"));
        assertTrue(testSubstitution.containsInvalid("a"));
    }

}
