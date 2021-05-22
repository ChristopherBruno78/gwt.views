package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.dom.MouseEventListener;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

public class Draggable {

    private static int DRAG_START_THRESHOLD = 10;
    private UIObject dragView;
    private UIObject dragHandle;
    private int startX = -1;
    private int startY = -1;
    private int dragCount = 0;
    private boolean constrainInsideParent = false;
    private boolean mouseDown = false;
    private boolean dragStarted = false;

    private Draggable(UIObject view, UIObject handle, boolean constrain) {
        this.dragView = view;
        if (handle == null) {
            handle = view;
        }
        this.dragHandle = handle;
        this.constrainInsideParent = constrain;
        this.dragView.setStyleAttribute("position", "absolute");
        this.dragHandle.addClassName("v-draggable");
        addEventListeners();
    }

    private void addEventListeners() {
        dragHandle.addEventListener(Events.ONMOUSEDOWN, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                event.preventDefault();
                DOM.setCapture(dragHandle.getElement());
                mouseDown = true;
                Rectangle rect = DOM.getBoundingRectClient(dragHandle.getElement());
                startX = (int) (event.clientX - rect.origin.x);
                startY = (int) (event.clientY - rect.origin.y);

            }
        });
        dragHandle.addEventListener(Events.ONMOUSEUP, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                mouseDown = false;
                if (dragHandle != null) {
                    DOM.releaseCapture(dragHandle.getElement());
                    if(dragCount >= DRAG_START_THRESHOLD) {
                        Events.fireEvent(Events.ONDRAGFINISH, dragView.getElement());
                    }
                    dragCount = 0;
                    dragStarted = false;
                }
            }
        });
        dragHandle.addEventListener(Events.ONMOUSEMOVE, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                if (mouseDown && dragView != null) {
                    int x = (int) (event.clientX - startX);
                    int y = (int) (event.clientY - startY);
                    HTMLElement $parent = dragView.getParent().getElement();
                    HTMLElement $el = dragView.getElement();
                    if (constrainInsideParent) {
                        x = Math.max(0, Math.min(x, $parent.offsetWidth - $el.offsetWidth));
                        y = Math.max(0, Math.min(y, $parent.offsetHeight - $el.offsetHeight));
                    }
                    $el.style.left = x + "px";
                    $el.style.top = y + "px";
                    dragCount++;
                    if (dragCount > DRAG_START_THRESHOLD && !dragStarted) {
                        dragStarted = true;
                        Events.fireEvent(Events.ONDRAGSTART, $el);
                    } else if (dragStarted) {
                        Events.fireEvent(Events.ONDRAG, $el);
                    }
                }
            }
        });
    }

    public static Draggable makeDraggable(final UIObject view, boolean constrain) {
        return makeDraggable(view, null, constrain);
    }

    public static Draggable makeDraggable(final UIObject view, UIObject handle, boolean constrain) {
        return new Draggable(view, handle, constrain);
    }

}
