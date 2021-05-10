package com.edusoftwerks.gwt.views.client.theme;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;

public interface StyleBundle extends ClientBundle {

    StyleBundle INSTANCE = GWT.create(StyleBundle.class);

    @Source({"UI.css"})
    CssResource UiCss();

    @Source({"RootView.css"})
    CssResource RootViewCss();

    @Source({"Button.css"})
    CssResource ButtonCss();

    @Source({"Radio.css"})
    CssResource RadioCss();

    @Source({"ComboBox.css"})
    CssResource ComboBoxCss();

    @Source({"CheckBox.css"})
    CssResource CheckBoxCss();

    @Source({"PopOver.css"})
    CssResource PopOverCss();

    @Source({"SplitView.css"})
    CssResource SplitViewCss();

    @Source({"Text.css"})
    CssResource TextCss();

    @Source({"icons.css"})
    @CssResource.NotStrict
    CssResource IconsCss();

    @DataResource.DoNotEmbed
    @DataResource.MimeType("application/x-font-woff")
    @Source({"fonts/icomoon.woff"})
    DataResource iconsWOFF();

}
