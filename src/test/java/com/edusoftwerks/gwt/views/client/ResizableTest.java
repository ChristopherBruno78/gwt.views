package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.Draggable;
import com.edusoftwerks.gwt.views.client.ui.Resizable;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class ResizableTest extends GwtViewsTest {

    DOMElement resizeElement;

    @Override
    UIObject render() {
        return resizeElement = div(new DOMProps()
                        .style("background", "#ddd")
                        .style("width", "200px")
                        .style("height", "200px"),
                label("Resize Me")
        );
    }

    @Override
    protected void addEventListeners() {
        Draggable.makeDraggable(resizeElement, true);
        Resizable.makeResizable(resizeElement);
        resizeElement.addEventListener(Events.ONRESIZE, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                GWT.log("RESIZE!!!!");
            }
        });
        resizeElement.addEventListener(Events.ONDRAG, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                GWT.log("DRAG!!!!");
            }
        });
    }

}
