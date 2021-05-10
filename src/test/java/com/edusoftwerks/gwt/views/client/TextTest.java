package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.text.SearchText;
import com.edusoftwerks.gwt.views.client.ui.text.Text;
import com.edusoftwerks.gwt.views.client.ui.text.TextProps;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class TextTest extends GwtViewsTest {

    Text text;
    SearchText searchText;

    @Override
    UIObject render() {
        return div(new DOMProps()
                        .margin(15),
                text = new Text(new TextProps()
                        .height(32)
                        .secure(true)
                        .multiline(false)),
                searchText = new SearchText(new TextProps())
        );
    }

    @Override
    public void addEventListeners() {
        text.addEventListener(Events.ONINPUT, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                GWT.log(text.getText());
            }
        });
        text.addActionListener(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                GWT.log("submit");
            }
        });
        searchText.addEventListener(Events.ONCANCEL, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                searchText.setSecure(true);
            }
        });

    }

}
