package deleteBack;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.io.*;
import java.util.*;
import org.junit.Test; // for @Test


public class DeleteBackTest {

    @Test
    public void testDeleteBackEmpty() {
        LinkedIntList list = new LinkedIntList();
        assertThrows(NoSuchElementException.class, () -> {
            list.deleteBack();
        });
    }

    @Test
    public void testDeleteBackOneNode() {
        LinkedIntList list = new LinkedIntList();
        list.add(3);
        assertEquals(list.deleteBack(), 3);
        assertThrows(NoSuchElementException.class, () -> {
            list.deleteBack();
        });
    }

}
