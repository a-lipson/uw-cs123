package weave;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;
import org.junit.Test; // for @Test


public class WeaveTest {

    @Test
    public void testEmpty() {
        LinkedIntList list1 = new LinkedIntList();
        LinkedIntList list2 = new LinkedIntList();
        list1.weave(list2);
        assertEquals(list1.toString(), "");
    }

    @Test
    public void testSpec() {
        LinkedIntList list1 = new LinkedIntList(0, 2, 4, 6, 8);
        LinkedIntList list2 = new LinkedIntList(1, 3, 5, 7, 9);
        list1.weave(list2);
        assertEquals("0 1 2 3 4 5 6 7 8 9 ", list1.toString());
        assertEquals("", list2.toString(), "other list not empty after");
    }
    
}
