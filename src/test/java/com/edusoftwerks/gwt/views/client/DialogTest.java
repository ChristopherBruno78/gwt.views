package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonType;
import com.edusoftwerks.gwt.views.client.ui.dialog.Dialog;
import com.edusoftwerks.gwt.views.client.ui.dialog.DialogProps;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class DialogTest extends GwtViewsTest {

    private Button cancelBtn;
    private Dialog d2;

    @Override
    UIObject render() {
        return label("This is a test");
    }

    public void run() {
        super.run();

        d2 = new Dialog(new DialogProps()
                .x(200)
                .y(50)
                .modal(false)
                .resizable(false)
                .title("Dialog 2"))
        {
            @Override
            public DOMElement renderContent() {
                return null;
            }
        };

        Dialog d = new Dialog(new DialogProps().title("My Dialog Window")) {
            @Override
            public DOMElement renderContent() {
                return div(
                        new DOMProps()
                                .style("position", "absolute")
                                .style("right", "20px")
                                .style("bottom", "12px"),
                        cancelBtn = new Button(new ButtonProps()
                                .type(ButtonType.PRIMARY)
                                .text("Cancel")
                                .style("width", "100px")));
            }

            @Override
            protected void addEventListeners() {
                cancelBtn.addEventListener(Events.CLICK, new EventListener() {
                    @Override
                    public void handleEvent(Event evt) {
                        d2.drop();
                    }
                });

                super.addEventListeners();
            }
        };
        d.open();




    }
}
