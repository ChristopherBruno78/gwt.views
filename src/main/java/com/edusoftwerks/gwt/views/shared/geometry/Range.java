package com.edusoftwerks.gwt.views.shared.geometry;

/**
 * A Range class [start, end]
 */
public class Range {

    public int start;
    public int end;

    public Range (int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int length () {
        return end - start;
    }

    @Override
    public boolean equals (Object other) {
        if( other instanceof Range ) {
            Range otherRange = (Range) other;
            return otherRange.start == start && otherRange.end == end;
        }
        return false;
    }

    @Override
    public int hashCode () {
        return ("" + start + "," + end).hashCode();
    }
}
