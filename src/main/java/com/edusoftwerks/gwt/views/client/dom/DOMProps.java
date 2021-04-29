package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.client.UIProps;
import com.google.gwt.resources.client.DataResource;

/**
 * this class makes the UIProps class concrete and adds utility methods
 * for adding properties to DOMElements
 */
public class DOMProps extends UIProps<DOMProps> {

    public DOMProps backgroundImage(DataResource dataResource) {
        return style("background-image",
                "url(" + dataResource.getSafeUri().asString() + ")"
        );
    }

    public String backgroundImage() {
        return style("background-image");
    }
}
