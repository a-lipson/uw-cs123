package me.alipson;

import java.util.*;

public class Line { // really more like a graph
  // private List<Point> points = new ArrayList<Point>();
  private SortedSet<Point> points;

  public Line() {
    this.points = new TreeSet<>();
  }

  public void addPoint(Point point) {
    this.points.add(point); // why use this here?
  }

  public Point getStart() {
    return points.first();
  }

  public Point getEnd() {
    return points.last();
  }

  public int getNumPoints() {
    return points.size();
  }

  public double getLength() {
    double length = 0;

    if (points.size() < 2) {
      return length; // no length if less than 2 points
    }

    Point previousPoint = null;
    for (Point currentPoint : points) {
      if (previousPoint != null) {
        length += previousPoint.distanceTo(currentPoint);
      }
      previousPoint = currentPoint;
    }

    // Object[] pointsArray = points.toArray();
    // for (int i = 0; i < pointsArray.length - 1; i++) {
    // length += ((Point) pointsArray[i]).distanceTo((Point) pointsArray[i + 1]);
    // }

    return length;
  }

  @Override
  public String toString() {
    String lineString = "";
    return lineString;
  }
}
