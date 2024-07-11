package me.alipson.ciphers;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class TestCaesarShift {

    @Test
    @DisplayName("CaesarShift 'A'-'Z'")
    public void testStandardEncodingRange() {
        assumeTrue(Cipher.MIN_CHAR == (int) ('A') && Cipher.MAX_CHAR == (int) ('Z'));

        CaesarShift testCaesarShift = new CaesarShift(6);

        assertEquals("NKRRU", testCaesarShift.encrypt("HELLO"));
        assertEquals("HELLO", testCaesarShift.decrypt("NKRRU"));
    }

    @Test
    @DisplayName("CaesarShift - ' '-'}'")
    public void testWideEncodingRange() {
        assumeTrue(Cipher.MIN_CHAR == (int) (' ') && Cipher.MAX_CHAR == (int) ('}'));

        CaesarShift testCaesarShift = new CaesarShift(100);

        String t1 = testCaesarShift.encrypt("HELLO");

        assertEquals("NKRRU", testCaesarShift.encrypt("HELLO"));
        assertEquals("HELLO", testCaesarShift.decrypt("NKRRU"));
    }
}
