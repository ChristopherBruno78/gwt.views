package com.edusoftwerks.gwt.views.client;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;

public interface IconBundle extends ClientBundle {

    @Source("SVG/tick.svg")
    @DataResource.MimeType("image/svg+xml")
    DataResource tick();
}
