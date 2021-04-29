package com.edusoftwerks.gwt.views.client.ui.button;

public enum ButtonType {
    DEFAULT,
    PRIMARY,
    DANGER,
    SUCCESS,
    INFO,
    OUTLINE,
    WARNING,
    INVERSE,
    LINK;

    public String toString() {
        return name().toLowerCase();
    }
}
