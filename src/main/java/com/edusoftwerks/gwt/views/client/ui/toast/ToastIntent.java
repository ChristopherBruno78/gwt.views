package com.edusoftwerks.gwt.views.client.ui.toast;

public enum ToastIntent {
    DEFAULT,
    PRIMARY,
    DANGER,
    SUCCESS,
    INFO,
    WARNING,
    INVERSE;

    public String toString() {
        return name().toLowerCase();
    }
}
