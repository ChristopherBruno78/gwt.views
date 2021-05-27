package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.checkbox.CheckBox;
import com.edusoftwerks.gwt.views.client.ui.checkbox.CheckBoxProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class CheckBoxTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return div(new DOMProps().margin(15), new CheckBox(new CheckBoxProps().text("Check Me")));
    }
}
