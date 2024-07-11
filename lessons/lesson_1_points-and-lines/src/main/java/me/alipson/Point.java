package me.alipson;

// This class represents a 2-dimensional cartesian point
public class Point implements Comparable<Point> {
  private int x;
  private int y;

  // Constructs a point with the provided 'x' and 'y' values
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // Returns the distance from this Point to the provided 'other' Point
  public double distanceTo(Point other) {
    return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
  }

  // Returns whether or not this Point is equal to the provided 'other' Object
  @Override
  public boolean equals(Object other) {
    if (other == null || !(other instanceof Point)) {
      return false;
    } else {
      Point otherPoint = (Point) other;
      return otherPoint.x == this.x && otherPoint.y == this.y;
    }
  }

  // compare by horizontal x value
  @Override
  public int compareTo(Point other) {
    return (int) Math.ceil(this.x - other.x);
  }

  // Returns a string representation of this Point
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }
}
