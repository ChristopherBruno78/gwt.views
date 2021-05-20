package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.ui.dialog.Dialog;
import com.edusoftwerks.gwt.views.client.ui.dialog.DialogProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class DialogTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return label("This is a test");
    }

    public void run() {
        super.run();
        Dialog d = new Dialog(new DialogProps().title("My Dialog Window")) {
            @Override
            public DOMElement renderContent() {
                return null;
            }
        };
        d.open();
    }

}
