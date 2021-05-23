package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.text.MaskedText;
import com.edusoftwerks.gwt.views.client.ui.text.SearchText;
import com.edusoftwerks.gwt.views.client.ui.text.Text;
import com.edusoftwerks.gwt.views.client.ui.text.TextProps;
import com.google.gwt.core.client.GWT;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class TextTest extends GwtViewsTest {

    Text text;
    SearchText searchText;
    MaskedText maskedText;

    @Override
    UIObject render() {
        return div(new DOMProps()
                        .margin(15),
                text = new Text(new TextProps()
                        .height(32)
                        .secure(false)
                        .readOnly(false)
                        .text("Test")
                        .multiline(false)),
                searchText = new SearchText(new TextProps()),
                maskedText = new MaskedText(new TextProps().mask("999-99-9999"))
        );
    }

    @Override
    public void addEventListeners() {
        text.addEventListener(Events.ACTION, evt -> GWT.log("submit"));
        searchText.addEventListener(Events.CANCEL, evt -> searchText.setSecure(true));

    }

}
