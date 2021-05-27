package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ViewTest extends GwtViewsTest {

    @Override
    public DOMElement render() {
        return div(
                new DOMProps()
                        .className("red")
                        .style("height", "150px")
                        .style("width", "150px")
                        .style("background", "orange"),
                div("HELLO, WORLD"));
    }
}
