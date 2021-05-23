package com.edusoftwerks.gwt.views.client.ui.toast;

import com.edusoftwerks.gwt.views.client.UIProps;

public class ToasterProps extends UIProps<ToasterProps> {

    public ToasterProps() {
        position(ToasterPosition.TOP_RIGHT);
    }

    public ToasterPosition position() {
        return (ToasterPosition) values.get("position");
    }

    public ToasterProps position(ToasterPosition position) {
        return set("position", position);
    }
}
