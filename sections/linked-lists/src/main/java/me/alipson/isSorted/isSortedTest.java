package isSorted;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;


public class IsSortedTest {

    @Test
    public void testIsSorted1() {
        LinkedIntList list = new LinkedIntList();
        list.add(-1);
        list.add(3);
        list.add(9);
        list.add(20);
        assertTrue(list.isSorted());
    }


}
