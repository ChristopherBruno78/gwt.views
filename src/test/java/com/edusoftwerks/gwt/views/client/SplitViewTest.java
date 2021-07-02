package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonType;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitView;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitViewFlex;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitViewOrientation;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitViewProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class SplitViewTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return new SplitView(
                new SplitViewProps().flex(SplitViewFlex.TOP_LEFT).orientation(SplitViewOrientation.VERTICAL)) {
            @Override
            protected View<?> renderTopLeft() {
                return new View<DOMProps>() {
                    @Override
                    protected DOMElement render() {
                        return div(
                                new DOMProps()
                                        .style("background", "#eee").style("border-right", "1px solid #bbb"),
                                div(new DOMProps()
                                                .style("display", "flex")
                                                .style("flex-direction", "column")
                                                .style("width", "100%")
                                                .style("height", "100%"),
                                        new Button(new ButtonProps()
                                                .text("Test")
                                                .style("margin", "15px")
                                                .type(ButtonType.PRIMARY))));
                    }
                };
            }

            @Override
            protected View<?> renderBottomRight() {
                return new View<DOMProps>() {
                    @Override
                    protected DOMElement render() {
                        return div(new DOMProps().style("background", "#fff"));
                    }
                };
            }

            @Override
            protected void addEventListeners() {}
        };
    }
}
