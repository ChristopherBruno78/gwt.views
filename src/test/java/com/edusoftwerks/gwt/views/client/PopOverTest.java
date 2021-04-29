package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonType;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOver;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverEdge;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverPosition;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverProps;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class PopOverTest extends GwtViewsTest {

    private Button btn;
    private Button closeBtn;
    private PopOver popOver;

    public PopOverTest() {
        popOver = new PopOver(new PopOverProps()
                .edge(PopOverEdge.MIDDLE)
                .position(PopOverPosition.RIGHT)) {
            @Override
            protected DOMElement renderView() {
                return div(new DOMProps()
                                .width(200)
                                .style("padding", "15px"),
                        closeBtn = new Button("Click", ButtonType.PRIMARY));
            }
        };
    }

    @Override
    UIObject render() {
        return btn = new Button(new ButtonProps()
                .style("position", "absolute")
                .style("left", "50px")
                .style("top", "50px")
                .text("PopOver Test")
        );
    }

    @Override
    public void addEventListeners() {
        btn.addEventListener(Events.ONACTION, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                popOver.show(btn, 0, 0);
            }
        });
        closeBtn.addEventListener(Events.ONACTION, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                popOver.close();
            }
        });
    }
}
