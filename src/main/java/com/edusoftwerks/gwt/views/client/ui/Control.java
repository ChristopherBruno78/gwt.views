package com.edusoftwerks.gwt.views.client.ui;

import com.edusoftwerks.gwt.views.client.dom.Events;

/**
 * Control is a base class for UI Components dealing
 * with data input that can be enabled/disabled, focused and blurred,
 * contain text and are in the browser tab order.
 * <p>
 * Custom Events: ACTION
 *
 * @param <T> ControlProps type
 */
public abstract class Control<T extends ControlProps<T>>
        extends View<T> {

    private boolean isFocused = false;

    public Control(T props) {
        super(props);
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.MOUSEENTER, evt -> addClassName("is-hover"));
        addEventListener(Events.MOUSELEAVE, evt -> removeClassName("is-hover"));
        addEventListener(Events.FOCUS, evt -> addClassName("is-focused"));
        addEventListener(Events.BLUR, evt -> removeClassName("is-focused"));
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

    protected void fireActions() {
        Events.fireEvent(Events.ACTION, this);
    }
}
