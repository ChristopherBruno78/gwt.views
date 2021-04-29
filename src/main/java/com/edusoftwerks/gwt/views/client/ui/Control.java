package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.dom.Events;
import elemental2.dom.EventListener;

public abstract class Control<T extends ControlProps<T>>
        extends View<T> {

    private boolean isFocused = false;

    public Control(T props) {
        super(props);
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.ONFOCUS, evt -> addClassName("is-focused"));
        addEventListener(Events.ONBLUR, evt -> removeClassName("is-focused"));
    }

    public boolean isDisabled() {
        return this.props.disabled();
    }

    public void setDisabled(boolean isDisabled) {
        this.props.disabled(isDisabled);
        if (isRendered()) {
            setAttribute("aria-disabled", isDisabled() ? "true" : "false");
            if (isDisabled()) {
                addClassName("is-disabled");
            } else {
                removeClassName("is-disabled");
            }
        }
    }

    public boolean isFocused() {
        return isFocused;
    }

    public void setIsFocused(boolean flag) {
        this.isFocused = flag;
        if (isRendered()) {
            if (this.isFocused) {
                getElement().focus();
            } else {
                getElement().blur();
            }
        }
    }

    public int getTabIndex() {
        return getElement().tabIndex;
    }

    public void setTabIndex(int index) {
        if (isRendered()) {
            setAttribute("tabIndex", index);
        }
    }

    public String getText() {
        return this.props.text();
    }

    public void setText(String text) {
        this.props.text(text);
    }

    public void addActionListener(EventListener actionListener) {
        addEventListener(Events.ONACTION, actionListener);
    }

    protected void fireActions() {
        Events.fireEvent(Events.ONACTION, getElement());
    }
}
