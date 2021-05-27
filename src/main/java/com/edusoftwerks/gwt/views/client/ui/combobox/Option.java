package com.edusoftwerks.gwt.views.client.ui.combobox;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;

class Option extends DOMElement {

    public Option(String text, Integer id) {
        super("option", new DOMProps().attr("id", id).attr("value", text));
        setText(text);
    }

    public String getText() {
        return getElement().textContent;
    }

    public void setText(String text) {
        setInnerHtml(text);
    }
}
