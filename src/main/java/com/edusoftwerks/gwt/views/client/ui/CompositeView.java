package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.UIProps;
import elemental2.dom.HTMLElement;

import java.util.List;

public abstract class CompositeView<T extends UIProps> extends UIObject {

    protected T props;
    private View view;

    public CompositeView(T props) {
        this.props = props;
    }

    protected void init(View view) {
        this.view = view;
    }

    @Override
    public HTMLElement getElement() {
        return view.getElement();
    }

    @Override
    public List<UIObject> getChildren() {
        return view.getChildren();
    }

    @Override
    public UIObject getParent() {
        return view.getParent();
    }

    @Override
    public void setParent(UIObject parent) {
        view.setParent(parent);
    }

    @Override
    public T getProps() {
        return props;
    }

    @Override
    public void performRender() {
        view.performRender();
        parseProps(props);
    }

    public boolean isHidden() {
        return view.isHidden();
    }

    public void setHidden(boolean b) {
        view.setHidden(b);
    }

    public Integer getWidth() {
        return view.getWidth();
    }

    public Integer getHeight() {
        return view.getHeight();
    }

    public boolean isRendered() {
        return view.isRendered();
    }

    @Override
    protected void fireDidEnterDocument() {
        view.fireDidEnterDocument();
        didEnterDocument();
    }

    @Override
    protected void fireDidLeaveDocument() {
        view.fireDidLeaveDocument();
        didLeaveDocument();
    }

}
