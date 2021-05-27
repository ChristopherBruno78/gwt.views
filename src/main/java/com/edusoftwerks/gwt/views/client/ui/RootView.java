package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.google.gwt.user.client.Window;
import elemental2.dom.DomGlobal;

public final class RootView extends View<DOMProps> {

    private static final RootView INSTANCE = new RootView();
    // private static Timer resizeTimer = null;

    static {
        Theme.get().RootViewCss().ensureInjected();
    }

    private RootView() {
        super(new DOMProps());
        performRender();
    }

    public static RootView get() {
        return INSTANCE;
    }

    @Override
    protected void addEventListeners() {
        Window.addResizeHandler(resizeEvent -> RootView.this.onResize());
    }

    @Override
    protected DOMElement render() {
        DOMElement $el = new DOMElement("div", new DOMProps().className("v-Root"));
        DomGlobal.document.body.appendChild($el.getElement());
        return $el;
    }
}
