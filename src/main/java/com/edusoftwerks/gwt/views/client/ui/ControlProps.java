package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.UIProps;

public abstract class ControlProps<T extends ControlProps<T>> extends UIProps<T> {

    public ControlProps() {
        disabled(false);
    }

    public T disabled(boolean flag) {
        return set("disabled", flag);
    }

    public Boolean disabled() {
        return (Boolean) values.get("disabled");
    }

    public String text() {
        return (String) values.get("text");
    }

    public T text(String text) {
        return set("text", text);
    }

    public String name() {
        return (String) values.get("name");
    }

    public T name(String name) {
        return set("name", name);
    }

    public String id() {
        return (String) values.get("id");
    }

    public T id(String id) {
        return set("id", id);
    }
}
