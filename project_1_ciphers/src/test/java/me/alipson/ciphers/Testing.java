package me.alipson.ciphers;

// Alexandre Lipson
// 06/27/2024
// CSE 123
// P1: Ciphers
// TA: Daniel

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class Testing {

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'G' Spec Example")
    public void subAGTest() {
        // Remember that you can change MIN_CHAR AND MAX_CHAR
        // in Cipher.java to make testing easier! For this
        // example test, we are using MIN_CHAR = A and MAX_CHAR = G

        // Skip this test if the constants have changed
        // assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('G'));

        // Cipher testSubstitution = new Substitution("GCBEAFD");
        // assertEquals("FGE", testSubstitution.encrypt("FAD"));
        // assertEquals("BAD", testSubstitution.decrypt("CGE"));

        // Per the spec, we should throw an IllegalArgumentException if
        // the length of the shifter doesn't match the number of characters
        // within our Cipher's encodable range
        assertThrows(IllegalArgumentException.class, () -> {
            new Substitution("GCB");
        });
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - 'A'-'Z' Shifter")
    public void subAZTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));
        //
        // // Reverse alphabetic
        Cipher testSubstitution = new Substitution(
                "ZYXWVUTSRQPONMLKJIHGFEDCBA");
        assertEquals("UZW", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("YZW"));
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - ' '-'}' Shifter")
    public void subComplexTest() {
        // Skip this test if the constants have changed
        // assumeTrue(Cipher.MIN_CHAR == (int) (' ') && Cipher.MAX_CHAR == (int) ('}'));

        // Swapping lowercase a<->b
        // Cipher testSubstitution = new Substitution(
        // " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
        // "bacdefghijklmnopqrstuvwxyz{|}");
        // assertEquals("FAD", testSubstitution.encrypt("FAD"));
        // assertEquals("fbd", testSubstitution.encrypt("fad"));
        // assertEquals("BAD", testSubstitution.decrypt("BAD"));
        // assertEquals("bad", testSubstitution.decrypt("abd"));
    }

    @Test
    @DisplayName("TODO: CaesarKey - 'A'-'Z'")
    public void keyAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        CaesarKey testCaesarKey = new CaesarKey("TIN");
        // TODO: Create a new CaesarKey("TIN"), encrypt the message "HELLO" and check
        // the
        // result's accurate. Then, take the encrypted message, decrypt it, and
        // check the result's accurate
        String test1 = testCaesarKey.encrypt("HELLO");
        System.out.println(test1);
        String test2 = testCaesarKey.decrypt("HELLO");
        assertEquals(false, testCaesarKey.encrypt("HELLO"));
        assertEquals("", testCaesarKey.decrypt("HELLO"));
    }

    @Test
    @DisplayName("TODO: CaesarShift - 'A'-'Z' Shifter")
    public void shiftAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        // TODO: Create a new CaesarShift(6), encrypt the message "HELLO" and check the
        // result's accurate. Then, take the encrypted message, decrypt it, and
        // check the result's accurate
        assertEquals(true, false, "Assertion 1 not yet implemented!");
        assertEquals(true, false, "Assertion 2 not yet implemented!");
    }

    @Test
    @DisplayName("TODO: MultiCipher - 'A'-'Z' Shifter")
    public void multiAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        // TODO: Create a new MultiCipher with ciphers CaesarKey("TIN") and
        // CaesarShift(6)),
        // encrypt the message "HELLO", and check the result's accurate. Then, take
        // the encrypted message, decrypt it, and check the result's accurate
        assertEquals(true, false, "Assertion 1 not yet implemented!");
        assertEquals(true, false, "Assertion 2 not yet implemented!");
    }

    @Test
    @DisplayName("Cipher Utility Method Tests")
    public void testCipherUtilities() {
        assertEquals(0, Cipher.getCharIndex('A'));
        assertEquals(1, Cipher.getCharIndex('B'));

        assumeTrue(Cipher.isValidChar('A'));
        assumeTrue(!Cipher.isValidChar('a'));
    }
}
