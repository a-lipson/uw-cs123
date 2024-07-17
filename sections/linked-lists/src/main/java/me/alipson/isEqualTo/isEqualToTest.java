package isEqualTo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;
import org.junit.Test; // for @Test


public class isEqualToTest {

    @Test
    public void testEqualsEmpty() {
        LinkedIntList list1 = new LinkedIntList();
        LinkedIntList list2 = new LinkedIntList();
        assertTrue(list1.isEqualTo(list2));
    }

    @Test
    public void testEquals() {
        LinkedIntList list1 = setUp1();
        LinkedIntList list2 = setUp1();
        assertTrue(list1.isEqualTo(list2));
    }


}
