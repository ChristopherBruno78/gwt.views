package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.client.UIProps;
import com.google.gwt.resources.client.DataResource;

/**
 * this class makes the UIProps class concrete and adds utility methods for adding properties to
 * DOMElements
 */
public class DOMProps extends UIProps<DOMProps> {

    public DOMProps backgroundImage(DataResource dataResource) {
        return style("background-image", "url(" + dataResource.getSafeUri().asString() + ")");
    }

    public String padding() {
        return style("padding");
    }

    public DOMProps padding(Integer padding) {
        return style("padding", padding + "px");
    }

    public String margin() {
        return style("margin");
    }

    public DOMProps margin(Integer margin) {
        return style("margin", margin + "px");
    }

    public String backgroundImage() {
        return style("background-image");
    }
}
