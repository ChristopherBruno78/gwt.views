package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.client.UIObject;
import elemental2.dom.Event;

public class Events {

    /**
     * Standard
     **/
    public static final String MOUSEDOWN = "mousedown";
    public static final String MOUSEUP = "mouseup";
    public static final String MOUSEMOVE = "mousemove";
    public static final String MOUSEOVER = "mouseover";
    public static final String MOUSEOUT = "mouseout";
    public static final String MOUSEENTER = "mouseenter";
    public static final String MOUSELEAVE = "mouseleave";
    public static final String CLICK = "click";
    public static final String KEYDOWN = "keydown";
    public static final String KEYUP = "keyup";
    public static final String KEYPRESS = "keypress";
    public static final String FOCUS = "focus";
    public static final String BLUR = "blur";
    public static final String CHANGE = "change";
    public static final String INPUT = "input";

    /**
     * Custom Events - not all views respond
     **/
    public static final String ACTION = "action";
    public static final String CLOSE = "close";
    public static final String CANCEL = "cancel";
    public static final String RESIZE = "resize";
    public static final String RESIZE_FINISH = "resizefinish";
    public static final String RESIZE_START = "resizestart";
    public static final String DRAG = "drag";
    public static final String DRAG_FINISH = "dragfinish";
    public static final String DRAG_START = "dragstart";

    public static void fireEvent(String eventName, UIObject target) {
        if (target != null) {
            target.getElement().dispatchEvent(new Event(eventName));
        }
    }
}
