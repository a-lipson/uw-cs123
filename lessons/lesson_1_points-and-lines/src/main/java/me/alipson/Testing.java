package me.alipson;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.*;
import jdk.jfr.Timestamp;

public class Testing {
  @Test
  @DisplayName("Point tests")
  public void testPoint() {
    Point p = new Point(0, 0);

    assertEquals(1, p.distanceTo(new Point(0, 1)));
    assertEquals(Math.sqrt(2), p.distanceTo(new Point(1, 1)));

    assertTrue(p.equals(new Point(0, 0)));
    assertFalse(p.equals(new Point(0, 1)));

    assertEquals("(0, 0)", p.toString());
  }

  @Test
  @DisplayName("Line constructor & getters")
  public void testLineConstructor() {
    Line l = new Line();

    l.addPoint(new Point(0, 0));
    l.addPoint(new Point(1, 1));

    assertEquals(new Point(0, 0), l.getStart());
    assertEquals(new Point(1, 1), l.getEnd());

    l.addPoint(new Point(-1, -1));
    assertEquals(new Point(-1, -1), l.getStart());
    assertEquals(new Point(1, 1), l.getEnd());
  }

  @Test
  @DisplayName("Line exceptions")
  public void testLineExceptions() {
    assertThrows(NoSuchElementException.class, () -> {
      Line l = new Line();
      l.getStart();
    });

    assertThrows(NoSuchElementException.class, () -> {
      Line l = new Line();
      l.getEnd();
    });
  }

  @Test
  @DisplayName("Line length")
  public void testLineLength() {
    Line l = new Line();
    assertEquals(0, l.getLength());

    l.addPoint(new Point(0, 0));
    assertEquals(0, l.getLength());

    l.addPoint(new Point(1, 1));
    assertEquals(Math.sqrt(2), l.getLength(), 0.001);
  }

  @Test
  @DisplayName("Line duplicates")
  public void testLineDuplicates() {
    Line l = new Line();
    l.addPoint(new Point(0, 0));
    l.addPoint(new Point(0, 0));

    assertEquals(1, l.getNumPoints());
  }
}
