package me.alipson.ciphers;

import java.util.*;

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
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('G'));

        Cipher testSubstitution = new Substitution("GCBEAFD");
        assertEquals("FGE", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("CGE"));

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

        // Reverse alphabetic
        Cipher testSubstitution = new Substitution(
                "ZYXWVUTSRQPONMLKJIHGFEDCBA");
        assertEquals("UZW", testSubstitution.encrypt("FAD"));
        assertEquals("BAD", testSubstitution.decrypt("YZW"));
    }

    @Test
    @DisplayName("EXAMPLE TEST CASE - ' '-'}' Shifter")
    public void subComplexTest() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) (' ') && Cipher.MAX_CHAR == (int) ('}'));

        // Swapping lowercase a<->b
        Cipher testSubstitution = new Substitution(
                " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`" +
                        "bacdefghijklmnopqrstuvwxyz{|}");
        assertEquals("FAD", testSubstitution.encrypt("FAD"));
        assertEquals("fbd", testSubstitution.encrypt("fad"));
        assertEquals("BAD", testSubstitution.decrypt("BAD"));
        assertEquals("bad", testSubstitution.decrypt("abd"));
    }

    @Test
    @DisplayName("CaesarKey - 'A'-'Z'")
    public void keyAZOne() { // can we rename tests?
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        CaesarKey testCaesarKey = new CaesarKey("TIN");

        assertEquals("EBJJM", testCaesarKey.encrypt("HELLO"));
        assertEquals("HELLO", testCaesarKey.decrypt("EBJMM"));
    }

    @Test
    @DisplayName("CaesarShift - 'A'-'Z' Shifter")
    public void shiftAZOne() { // can we raname tests?
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        CaesarShift testCaesarShift = new CaesarShift(6);

        assertEquals("NKRRU", testCaesarShift.encrypt("HELLO"));
        assertEquals("HELLO", testCaesarShift.decrypt("NKRRU"));
    }

    @Test
    @DisplayName("MultiCipher - 'A'-'Z' Shifter")
    public void multiAZOne() {
        // Skip this test if the constants have changed
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        MultiCipher testMultiCipher = new MultiCipher(List.of(new CaesarKey("TIN"), new CaesarShift(6)));

        assertEquals("KHPPS", testMultiCipher.encrypt("HELLO"));
        assertEquals("HELLO", testMultiCipher.decrypt("KHPPS"));
    }

}
