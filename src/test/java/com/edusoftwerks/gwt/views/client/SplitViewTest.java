package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitView;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitViewProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class SplitViewTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return new SplitView(new SplitViewProps()) {
            @Override
            protected View<?> renderTopLeft() {
                return new View<DOMProps>() {
                    @Override
                    protected DOMElement render() {
                        return div(new DOMProps()
                                .style("background", "#eee")
                                .style("border-right", "1px solid #bbb")
                        );
                    }
                };
            }

            @Override
            protected View<?> renderBottomRight() {
                return new View<DOMProps>() {
                    @Override
                    protected DOMElement render() {
                        return div(new DOMProps()
                                .style("background", "#fff"));
                    }
                };
            }

            @Override
            protected void addEventListeners() {
            }
        };
    }

}
