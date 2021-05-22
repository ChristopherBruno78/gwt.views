package com.edusoftwerks.gwt.views.client.ui.dialog;

import com.edusoftwerks.gwt.views.client.UIProps;

public class DialogProps extends UIProps<DialogProps> {

    public DialogProps() {
        title("");
        draggable(true);
        resizable(true);
        closable(true);
        maximizable(false);
        width(300);
        height(300);
        x(0);
        y(0);
        modal(false);
    }

    public String title() {
        return (String) values.get("title");
    }

    public DialogProps title(String title) {
        return set("title", title);
    }

    public Boolean draggable() {
        return (Boolean) values.get("draggable");
    }

    public DialogProps draggable(boolean flag) {
        return set("draggable", flag);
    }

    public Boolean resizable() {
        return (Boolean) values.get("resizable");
    }

    public DialogProps resizable(boolean flag) {
        return set("resizable", flag);
    }

    public Boolean closable() {
        return (Boolean) values.get("closable");
    }

    public DialogProps closable(boolean flag) {
        return set("closable", flag);
    }

    public Boolean maximizable() {
        return (Boolean) values.get("maximizable");
    }

    public DialogProps maximizable(boolean flag) {
        return set("maximizable", flag);
    }

    public Boolean modal() {
        return (Boolean) values.get("modal");
    }

    public DialogProps modal(boolean flag) {
        return set("modal", flag);
    }

    public Integer width() {
        return (Integer) values.get("width");
    }

    public DialogProps width(int w) {
        return set("width", w);
    }

    public Integer height() {
        return (Integer) values.get("height");
    }

    public DialogProps height(int h) {
        return set("height", h);
    }

    public Integer x() {
        return (Integer) values.get("x");
    }

    public DialogProps x(int x) {
        return set("x", x);
    }

    public Integer y() {
        return (Integer) values.get("y");
    }

    public DialogProps y(int y) {
        return set("y", y);
    }

}
