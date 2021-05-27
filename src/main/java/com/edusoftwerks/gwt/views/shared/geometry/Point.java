package com.edusoftwerks.gwt.views.shared.geometry;

/** A Point class */
public class Point {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Point) {
            Point otherPt = (Point) other;
            return otherPt.x == x && otherPt.y == y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ("" + x + "," + y).hashCode();
    }
}
