package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.Draggable;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class DraggableTest extends GwtViewsTest {

    DOMElement dragElement;

    @Override
    UIObject render() {
        return dragElement = div(
                new DOMProps()
                        .style("background", "#d0d0d0")
                        .style("width", "100px")
                        .style("height", "100px"),
                label("Drag Me"));
    }

    @Override
    protected void addEventListeners() {
        Draggable.makeDraggable(dragElement, true);
    }
}
