package com.edusoftwerks.gwt.views.client.dom;

import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.KeyboardEvent;
import jsinterop.base.Js;

public abstract class KeyboardEventListener implements EventListener {

    private static native int charCode(KeyboardEvent event) /*-{
        return event.which;
    }-*/;

    @Override
    public void handleEvent(Event evt) {
        KeyboardEvent keyboardEvent = Js.cast(evt);
        handleKeyboardEvent(keyboardEvent, charCode(keyboardEvent));
    }

    public abstract void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode);
}
