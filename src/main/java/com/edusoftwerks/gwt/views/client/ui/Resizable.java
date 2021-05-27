package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import com.edusoftwerks.gwt.views.shared.geometry.Size;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class Resizable {

    private static final int DRAG_START_THRESHOLD = 10;
    private final UIObject resizeView;
    private final Size maxSize;
    private final Size minSize;
    private DOMElement resizeHandle;
    private boolean mouseDown = false;
    private int startX = -1;
    private int startY = -1;
    private int dragCount = 0;
    private boolean dragStarted = false;

    private Resizable(UIObject view) {
        this(view, new Size(1, 1), new Size(10000, 10000));
    }

    private Resizable(UIObject resizeView, Size min, Size max) {
        this.resizeView = resizeView;
        minSize = min;
        maxSize = max;
        setup();
    }

    public static Resizable makeResizable(UIObject view, Size min, Size max) {
        return new Resizable(view, min, max);
    }

    public static Resizable makeResizable(UIObject view) {
        return new Resizable(view, new Size(100, 100), new Size(Integer.MAX_VALUE, Integer.MAX_VALUE));
    }

    private void setup() {
        resizeHandle = div(new DOMProps().className("v-Resize-handle"));
        resizeView.append(resizeHandle);
        resizeHandle.addEventListener(Events.MOUSEDOWN, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                if (resizeHandle != null) {
                    event.preventDefault();
                    event.stopPropagation();
                    DOM.setCapture(resizeHandle.getElement());
                    mouseDown = true;
                    Rectangle rect = DOM.getBoundingRectClient(resizeHandle.getElement());
                    startX = (int) (event.clientX - rect.origin.x);
                    startY = (int) (event.clientY - rect.origin.y);
                }
            }
        });
        resizeHandle.addEventListener(Events.MOUSEUP, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                mouseDown = false;
                if (resizeHandle != null) {
                    DOM.releaseCapture(resizeHandle.getElement());
                    dragCount = 0;
                    dragStarted = false;
                    // ResizeEvent.fire(Resizable.this, ResizeEvent.RESIZE_END);
                }
            }
        });
        resizeHandle.addEventListener(Events.MOUSEMOVE, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                if (mouseDown && resizeView != null) {
                    int x = (int) (event.clientX + startX);
                    int y = (int) (event.clientY + startY);
                    HTMLElement $el = resizeView.getElement();
                    resizeView.setStyleAttribute(
                            "width", Math.max(minSize.width, Math.min(maxSize.width, x - $el.offsetLeft)) + "px");
                    resizeView.setStyleAttribute(
                            "height", Math.max(minSize.height, Math.min(maxSize.height, y - $el.offsetTop)) + "px");
                    dragCount++;
                    if (dragCount > DRAG_START_THRESHOLD && !dragStarted) {
                        dragStarted = true;
                        Events.fireEvent(Events.RESIZE_START, resizeView);
                    } else if (dragStarted) {
                        Events.fireEvent(Events.RESIZE, resizeView);
                    }
                }
            }
        });
    }

    public Size getMaxSize() {
        return maxSize;
    }

    public Size getMinSize() {
        return minSize;
    }
}
