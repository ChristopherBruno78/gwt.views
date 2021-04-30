package com.edusoftwerks.gwt.views.client.dom;

import elemental2.dom.*;

import java.util.Arrays;

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

    public static MouseEvent cloneMouseEvent(MouseEvent oldEvent) {
        MouseEventInit init = MouseEventInit.create();
        init.setButton(oldEvent.button);
        init.setClientX(oldEvent.clientX);
        init.setClientY(oldEvent.clientY);
        init.setButtons(oldEvent.buttons);
        init.setAltKey(oldEvent.altKey);
        init.setCtrlKey(oldEvent.ctrlKey);
        init.setScreenX(oldEvent.screenX);
        init.setScreenY(oldEvent.screenY);
        init.setMetaKey(oldEvent.metaKey);
        init.setShiftKey(oldEvent.shiftKey);
        init.setRelatedTarget(oldEvent.relatedTarget);
        return new MouseEvent(oldEvent.type, init);
    }

    public static KeyboardEvent cloneKeyboardEvent(KeyboardEvent oldEvent) {
        KeyboardEventInit init = KeyboardEventInit.create();
        init.setChar(oldEvent.char_);
        init.setCode(oldEvent.code);
        return new KeyboardEvent(oldEvent.type, init);
    }

    public static boolean isMouseEvent(Event evt) {
        return Arrays.asList(
                Events.ONMOUSEOVER, Events.ONMOUSEOUT, Events.ONMOUSELEAVE, Events.ONMOUSEDOWN, Events.ONMOUSEMOVE, Events.ONMOUSEUP, Events.ONMOUSEENTER
        ).contains(evt.type);
    }

    public static boolean isKeyboardEvent(Event evt) {
        return Arrays.asList(
                Events.ONKEYDOWN, Events.ONKEYUP, Events.ONKEYDOWN
        ).contains(evt.type);
    }

}
