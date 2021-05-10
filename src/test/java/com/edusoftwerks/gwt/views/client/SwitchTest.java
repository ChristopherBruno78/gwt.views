package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.checkbox.CheckBoxProps;
import com.edusoftwerks.gwt.views.client.ui.checkbox.Switch;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class SwitchTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return div(new DOMProps()
                        .margin(15)
                , new Switch(
                        new CheckBoxProps()
                                .text("Switch Me")));
    }

}
