package com.edusoftwerks.gwt.views.shared.geometry;

/**
 * Size class
 */
public class Size {

    public int width;
    public int height;

    public Size (int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString () {
        return "Size(" + width + "," + height + ")";
    }

    @Override
    public boolean equals (Object other) {
        if( other instanceof Size ) {
            Size otherSz = (Size) other;
            return otherSz.width == width && otherSz.height == height;
        }
        return false;
    }

    @Override
    public int hashCode () {
        return ("" + width + "," + height).hashCode();
    }
}
