package com.edusoftwerks.gwt.views.client.theme;

import com.google.gwt.core.client.GWT;

public class Theme {

    public static Theme INSTANCE = GWT.create(Theme.class);

    public void inject() {
        StyleBundle.INSTANCE.UiCss().ensureInjected();
        StyleBundle.INSTANCE.RootViewCss().ensureInjected();
        StyleBundle.INSTANCE.ButtonCss().ensureInjected();
        StyleBundle.INSTANCE.PopOverCss().ensureInjected();
        StyleBundle.INSTANCE.SplitViewCss().ensureInjected();
    }

}
