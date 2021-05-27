package com.edusoftwerks.gwt.views.client.ui.splitview;

import com.edusoftwerks.gwt.views.client.UIProps;

public class SplitViewProps extends UIProps<SplitViewProps> {

    public SplitViewProps() {
        dividerThickness(8);
        staticPaneLength(200);
        minStaticPaneLength(0);
        maxStaticPaneLength(10000);
        orientation(SplitViewOrientation.VERTICAL);
        flex(SplitViewFlex.TOP_LEFT);
    }

    public Integer dividerThickness() {
        return (Integer) values.get("dividerThickness");
    }

    public SplitViewProps dividerThickness(int thickness) {
        return set("dividerThickness", thickness);
    }

    public Integer staticPaneLength() {
        return (Integer) values.get("staticPaneLength");
    }

    public SplitViewProps staticPaneLength(int l) {
        return set("staticPaneLength", l);
    }

    public Integer minStaticPaneLength() {
        return (Integer) values.get("minStaticPaneLength");
    }

    public SplitViewProps minStaticPaneLength(int l) {
        return set("minStaticPaneLength", l);
    }

    public Integer maxStaticPaneLength() {
        return (Integer) values.get("maxStaticPaneLength");
    }

    public SplitViewProps maxStaticPaneLength(int l) {
        return set("maxStaticPaneLength", l);
    }

    public SplitViewOrientation orientation() {
        return (SplitViewOrientation) values.get("orientation");
    }

    public SplitViewProps orientation(SplitViewOrientation orientation) {
        return set("orientation", orientation);
    }

    public SplitViewFlex flex() {
        return (SplitViewFlex) values.get("flex");
    }

    public SplitViewProps flex(SplitViewFlex flex) {
        return set("flex", flex);
    }
}
