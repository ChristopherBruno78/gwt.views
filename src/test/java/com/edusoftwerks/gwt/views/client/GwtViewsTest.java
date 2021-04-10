package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import elemental2.dom.DomGlobal;

public abstract class GwtViewsTest {

    abstract DOMElement render();
    public void run() {
        DomGlobal.document.body.appendChild(render().getElement());
    }

}
