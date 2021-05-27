package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.radio.Radio;
import com.edusoftwerks.gwt.views.client.ui.radio.RadioProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class RadioTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return div(
                new DOMProps().margin(15),
                new Radio(new RadioProps().name("group1").text("Radio 1")),
                new Radio(new RadioProps().name("group1").selected(true).text("Radio 2")),
                new Radio(new RadioProps().name("group1").text("Radio 3")));
    }
}
