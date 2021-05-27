package com.edusoftwerks.gwt.views.client.ui.popover;

import com.edusoftwerks.gwt.views.client.UIProps;
import com.edusoftwerks.gwt.views.shared.Color;

public class PopOverProps extends UIProps<PopOverProps> {

    public PopOverProps() {
        isCallout(true);
        isTransient(true);
        backgroundColor(Color.whiteColor());
        borderColor(Color.colorFromHex("#bbbbbb"));
        position(PopOverPosition.BOTTOM);
        edge(PopOverEdge.CENTER);
    }

    public Boolean isCallout() {
        return (Boolean) values.get("callout");
    }

    public PopOverProps isCallout(boolean flag) {
        return set("callout", flag);
    }

    public Boolean isTransient() {
        return (Boolean) values.get("transient");
    }

    public PopOverProps isTransient(boolean flag) {
        return set("transient", flag);
    }

    public Color backgroundColor() {
        return (Color) values.get("backgroundColor");
    }

    public PopOverProps backgroundColor(Color color) {
        return set("backgroundColor", color);
    }

    public Color borderColor() {
        return (Color) values.get("borderColor");
    }

    public PopOverProps borderColor(Color color) {
        return set("borderColor", color);
    }

    public PopOverEdge edge() {
        return (PopOverEdge) values.get("edge");
    }

    public PopOverProps edge(PopOverEdge edge) {
        return set("edge", edge);
    }

    public PopOverPosition position() {
        return (PopOverPosition) values.get("position");
    }

    public PopOverProps position(PopOverPosition position) {
        return set("position", position);
    }
}
