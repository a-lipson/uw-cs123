package takeSmallerFrom;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;
import org.junit.Test; // for @Test


public class TakeSmallerFromTest {

    @Test
    public void testEmpty() {
        LinkedIntList list1 = new LinkedIntList();
        LinkedIntList list2 = new LinkedIntList();
        list1.takeSmallerFrom(list2);
        assertEquals(list1.toString(), "");
    }

    @Test
    public void testFirstEmpty() {
        LinkedIntList list1 = new LinkedIntList(3, 16, 7, 23);
        LinkedIntList list2 = new LinkedIntList();
        list1.takeSmallerFrom(list2);
        assertEquals("3 16 7 23 ", list1.toString());
        assertEquals("", list2.toString(), "other list not empty after");
    }
}
