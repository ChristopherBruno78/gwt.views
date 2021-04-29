package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public abstract class DivView extends View<DOMProps> {

    public DivView() {
        this(new DOMProps());
    }

    public DivView(DOMProps props) {
        super(props);
    }

    @Override
    protected DOMElement render() {
        return div();
    }
}
