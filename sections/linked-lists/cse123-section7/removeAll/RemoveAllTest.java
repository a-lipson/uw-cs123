package removeAll;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;
import org.junit.Test; // for @Test


public class RemoveAllTest {

    @Test
    public void testRemoveAll() {
        LinkedIntList list = new LinkedIntList();
        list.add(2);
        list.add(-4);
        list.add(12);
        list.add(0);
        list.add(2);
        list.add(2);
        assertEquals(3, list.removeAll(2));
    }

}