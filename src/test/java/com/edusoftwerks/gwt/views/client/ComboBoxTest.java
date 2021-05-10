package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.combobox.ComboBox;
import com.edusoftwerks.gwt.views.client.ui.combobox.ComboBoxProps;
import com.google.gwt.user.client.Window;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ComboBoxTest extends GwtViewsTest {

    private ComboBox comboBox;

    @Override
    UIObject render() {
        return div(new DOMProps()
                        .margin(15),
                comboBox = new ComboBox(
                        new ComboBoxProps()
                                .width(150)
                                .options("Hawaii", "Uzbekistan", "Moscow", "Los Angeles")
                ));
    }

    @Override
    protected void addEventListeners() {
        comboBox.addActionListener(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Window.alert(comboBox.getSelectedValue());
            }
        });
    }

    @Override
    protected void didRun() {
        comboBox.setSelectedIndex(0);
    }

}
