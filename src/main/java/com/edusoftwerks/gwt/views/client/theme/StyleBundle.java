package com.edusoftwerks.gwt.views.client.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface StyleBundle extends ClientBundle {

    StyleBundle INSTANCE = GWT.create(StyleBundle.class);

    @Source({"UI.css"})
    CssResource UiCss();

    @Source({"RootView.css"})
    CssResource RootViewCss();

    @Source({"Button.css"})
    CssResource ButtonCss();

    @Source({"PopOver.css"})
    CssResource PopOverCss();

    @Source({"SplitView.css"})
    CssResource SplitViewCss();

}
