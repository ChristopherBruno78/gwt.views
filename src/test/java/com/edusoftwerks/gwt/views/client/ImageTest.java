package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.google.gwt.core.client.GWT;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ImageTest extends GwtViewsTest {

    IconBundle INSTANCE = GWT.create(IconBundle.class);

    @Override
    UIObject render() {
        return div(new DOMProps()
                .height(24)
                .width(24)
                .attr("alt", "tick")
                .backgroundImage(INSTANCE.tick())
        );
    }
}
