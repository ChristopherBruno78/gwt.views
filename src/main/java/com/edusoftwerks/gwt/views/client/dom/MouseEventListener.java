package com.edusoftwerks.gwt.views.client.dom;

import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

public abstract class MouseEventListener implements EventListener {

    @Override
    public void handleEvent(Event evt) {
        handleMouseEvent(Js.cast(evt));
    }

    public abstract void handleMouseEvent(MouseEvent event);

}
