package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.google.gwt.user.client.Window;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;

public class DOMElementTest extends GwtViewsTest {

    @Override
    public DOMElement render() {
        DOMElement el = create("button", new DOMProps(), "Hello, World");
        el.addEventListener("click", new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Window.alert(el.getGeometry().toString());
            }
        });
        return el;
    }
}
