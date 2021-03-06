package com.edusoftwerks.gwt.views.client.ui.checkbox;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import com.google.gwt.event.dom.client.KeyCodes;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.KeyboardEvent;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class Switch extends Control<CheckBoxProps> {

    static {
        Theme.get().CheckBoxCss().ensureInjected();
    }

    public Switch(CheckBoxProps props) {
        super(props);
    }

    public boolean getValue() {
        return this.props.checked();
    }

    public void setValue(boolean value) {
        this.props.checked(value);
        if (isRendered()) {
            removeClassName("is-checked");
            setAttribute("aria-checked", this.props.checked());
            if (value) {
                addClassName("is-checked");
            }
        }
    }

    @Override
    protected DOMElement render() {
        return div(
                new DOMProps()
                        .className(ClassNames.start("v-Switch")
                                .add("v-no-select")
                                .add("is-disabled", isDisabled())
                                .add("is-checked", this.props.checked())
                                .build())
                        .attr("tabIndex", this.isDisabled() ? -1 : 0)
                        .attr("role", "checkbox"),
                div(new DOMProps().className("indicator")),
                label(this.props.text()));
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.MOUSEDOWN, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                evt.preventDefault();
            }
        });
        addEventListener(Events.CLICK, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (isDisabled()) {
                    return;
                }
                setValue(!getValue());
                fireActions();
            }
        });
        addEventListener(Events.KEYDOWN, new KeyboardEventListener() {
            @Override
            public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                if (isDisabled()) {
                    return;
                }
                if (keyCode == KeyCodes.KEY_SPACE || keyCode == KeyCodes.KEY_ENTER) {
                    setValue(!getValue());
                    fireActions();
                }
            }
        });
        super.addEventListeners();
    }
}
