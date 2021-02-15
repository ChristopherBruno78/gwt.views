package com.edusoftwerks.gwt.views.shared.geometry;

public class Rectangle {

    public Point origin;
    public Size size;

    public Rectangle () {
        this(0, 0, 0, 0);
    }

    public Rectangle (Point origin, Size size) {
        this.origin = origin;
        this.size = size;
    }

    public Rectangle (int x, int y, int width, int height) {
        this.origin = new Point(x, y);
        this.size = new Size(width, height);
    }

    @Override
    public String toString () {
        return "Rectangle(origin: " + origin + " , size: " + size + ")";
    }

    @Override
    public boolean equals (Object other) {
        if( other instanceof Rectangle ) {
            Rectangle otherRect = (Rectangle) other;
            return otherRect.origin.equals(origin) && otherRect.size.equals(size);
        }
        return false;
    }

    @Override
    public int hashCode () {
        return toString().hashCode();
    }
}
