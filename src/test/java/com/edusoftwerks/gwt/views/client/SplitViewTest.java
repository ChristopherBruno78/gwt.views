package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.DivView;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitView;
import com.edusoftwerks.gwt.views.client.ui.splitview.SplitViewProps;

public class SplitViewTest extends GwtViewsTest {

    @Override
    UIObject render() {
        return new SplitView(new SplitViewProps()) {
            @Override
            protected View<?> renderTopLeftView() {
                return new DivView(new DOMProps()
                        .style("background", "#ddd")
                        .style("border-right", "1px solid #bbb")
                ) {
                    @Override
                    protected void addEventListeners() {
                    }
                };
            }

            @Override
            protected View<?> renderBottomRightView() {
                return new DivView(new DOMProps().style("background", "#fff")) {
                    @Override
                    protected void addEventListeners() {
                    }
                };
            }

            @Override
            protected void addEventListeners() {
            }
        };
    }

}
