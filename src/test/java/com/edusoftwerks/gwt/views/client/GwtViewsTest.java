package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.ui.RootView;

public abstract class GwtViewsTest {

    abstract UIObject render();

    protected void addEventListeners() {
    }

    public void run() {
        RootView.get().append(render());
        addEventListeners();
    }
}
