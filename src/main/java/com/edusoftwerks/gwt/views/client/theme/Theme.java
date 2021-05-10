package com.edusoftwerks.gwt.views.client.theme;

public class Theme {

    static {
        get().IconsCss().ensureInjected();
        get().UiCss().ensureInjected();
        get().RootViewCss().ensureInjected();
    }

    public static StyleBundle get() {
        return StyleBundle.INSTANCE;
    }

}
