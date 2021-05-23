package com.edusoftwerks.gwt.views.client.ui.toast;

public enum ToasterAnimationState {
    ENTER,
    APPEAR,
    EXIT;

    @Override
    public String toString() {
        return "v-Toast-" + name().toLowerCase();
    }
}
