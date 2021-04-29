package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.UIProps;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.google.gwt.user.client.ui.RequiresResize;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;

public abstract class View<T extends UIProps<T>> extends UIObject
        implements RequiresResize {

    protected DOMElement domElement;
    protected UIObject parent;
    protected T props;
    private final List<UIObject> subViews = new ArrayList<>();
    private boolean isRendered = false;

    public View(T props) {
        this.props = props;
    }

    @Override
    public HTMLElement getElement() {
        if (domElement != null) {
            return domElement.getElement();
        }
        return null;
    }

    @Override
    public UIObject getParent() {
        return parent;
    }

    @Override
    protected void setParent(UIObject parent) {
        this.parent = parent;
    }

    @Override
    public List<UIObject> getChildren() {
        return subViews;
    }

    @Override
    public T getProps() {
        return props;
    }

    protected abstract void addEventListeners();

    protected abstract DOMElement render();

    @Override
    public void performRender() {
        if (isRendered) {
            return;
        }
        this.domElement = this.render();
        if (this.domElement != null) {
            parseProps(this.props);
            addEventListeners();
        }
        isRendered = true;
    }

    public void onResize() {
        for (UIObject o : getChildren()) {
            if (o instanceof RequiresResize) {
                ((RequiresResize) o).onResize();
            }
        }
    }

    public boolean isRendered() {
        return isRendered;
    }

    public void setHidden(boolean flag) {
        this.props.hidden(flag);
        if (isRendered()) {
            if (this.props.hidden()) {
                addClassName("v-hidden");
                setAttribute("aria-hidden", true);
            } else {
                removeClassName("v-hidden");
                setAttribute("aria-hidden", false);
            }
        }
    }

    public boolean isHidden() {
        return this.props.hidden();
    }

    public int getWidth() {
        return getElement().offsetWidth;
    }

    public int getHeight() {
        return getElement().offsetHeight;
    }
}
