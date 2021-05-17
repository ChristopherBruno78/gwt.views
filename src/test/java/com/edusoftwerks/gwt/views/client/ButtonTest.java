package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonType;
import com.google.gwt.user.client.Window;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ButtonTest extends GwtViewsTest {

    private Button btn;

    @Override
    UIObject render() {
        return div(new DOMProps()
                        .style("display", "flex")
                        .style("flex-direction", "column")
                        .style("margin", "12px")
                        .width(200),
                btn = new Button("Default", ButtonType.DEFAULT),
                new Button(new ButtonProps()
                        .text("Primary")
                        .type(ButtonType.PRIMARY)
                        .icon("icon-check-solid")
                ),
                new Button("Success", ButtonType.SUCCESS),
                new Button("Danger", ButtonType.DANGER),
                new Button("Info", ButtonType.INFO),
                new Button("Warning", ButtonType.WARNING),
                new Button("Inverse", ButtonType.INVERSE),
                new Button("Outline", ButtonType.OUTLINE),
                new Button("Link", ButtonType.LINK),
                new Button(new ButtonProps()
                        .width(32)
                        .height(32)
                        .iconOnly(true)
                        .tooltip("CheckMark")
                        .icon("icon-check-solid")
                )
        );
    }

    @Override
    protected void addEventListeners() {
        btn.addActionListener(new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                Window.alert("CLICK!");
            }
        });
    }

}
