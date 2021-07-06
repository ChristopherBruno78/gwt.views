package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.google.gwt.user.client.Window;
import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;

import java.util.ArrayList;
import java.util.List;

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

    public void addResizeListener(EventListener listener) {
        DomGlobal.window.addEventListener("resize", listener);
    }

    public void removeResizeListener(EventListener listener) {
        DomGlobal.window.removeEventListener("resize", listener);
    }

    @Override
    protected DOMElement render() {
        DOMElement $el = new DOMElement("div", new DOMProps().className("v-Root"));
        DomGlobal.document.body.appendChild($el.getElement());
        return $el;
    }
}
