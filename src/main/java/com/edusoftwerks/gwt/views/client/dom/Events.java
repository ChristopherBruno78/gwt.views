package com.edusoftwerks.gwt.views.client.dom;

import elemental2.dom.Event;
import elemental2.dom.HTMLElement;

public class Events {

    public static final String ONMOUSEDOWN = "mousedown";
    public static final String ONMOUSEUP = "mouseup";
    public static final String ONMOUSEMOVE = "mousemove";
    public static final String ONMOUSEOVER = "mouseover";
    public static final String ONMOUSEOUT = "mouseout";
    public static final String ONMOUSEENTER = "mouseenter";
    public static final String ONMOUSELEAVE = "mouseleave";
    public static final String ONCLICK = "click";
    public static final String ONKEYDOWN = "keydown";
    public static final String ONKEYUP = "keyup";
    public static final String ONKEYPRESS = "keypress";
    public static final String ONFOCUS = "focus";
    public static final String ONBLUR = "blur";
    public static final String ONACTION = "action";
    public static final String ONCLOSE = "close";

    public static void fireEvent(String eventName, HTMLElement target) {
        if (target != null) {
            target.dispatchEvent(new Event(eventName));
        }
    }
}
