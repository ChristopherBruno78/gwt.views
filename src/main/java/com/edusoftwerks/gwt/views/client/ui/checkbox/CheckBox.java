package com.edusoftwerks.gwt.views.client.ui.checkbox;

import com.edusoftwerks.gwt.views.client.dom.ClassNames;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import com.google.gwt.dom.client.Document;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLInputElement;
import jsinterop.base.Js;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.*;

public class CheckBox extends Control<CheckBoxProps> {

    private DOMElement input;
    private DOMElement labelEl;

    static {
        Theme.get().CheckBoxCss().ensureInjected();
    }

    public CheckBox(CheckBoxProps props) {
        super(props);
    }

    public boolean getValue() {
        return this.props.checked();
    }

    public void setValue(boolean value) {
        this.props.checked(value);
        if (isRendered()) {
            setAttribute("aria-checked", this.props.checked());
            HTMLInputElement $input = Js.cast(input.getElement());
            if ($input.checked != this.props.checked()) {
                $input.checked = this.props.checked();
            }
        }
    }

    @Override
    protected DOMElement render() {
        final String id = Document.get().createUniqueId();
        return div(new DOMProps()
                        .className(ClassNames.start("v-Checkbox")
                                .add("is-disabled", isDisabled())
                                .build()
                        )
                        .attr("aria-checked", this.getValue())
                        .attr("role", "checkbox"),
                input = create("input", new DOMProps()
                        .attr("id", id)
                        .attr("type", "checkbox")
                        .attr("name", this.props.name())
                        .attr("tabIndex", isDisabled() ? -1 : 0)
                ),
                div(new DOMProps().className("indicator")),
                labelEl = label(new DOMProps()
                                .className("label v-no-select")
                                .attr("for", id),
                        this.props.text()
                )
        );
    }

    private EventListener onChangeListener;
    private EventListener onFocusListener;
    private EventListener onBlurListener;

    @Override
    protected void addEventListeners() {
        final HTMLInputElement $input = Js.cast(input.getElement());
        $input.addEventListener(Events.ONCHANGE, onChangeListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (isDisabled()) {
                    return;
                }
                setValue($input.checked);
                fireActions();
            }
        });
        $input.addEventListener(Events.ONFOCUS, onFocusListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                addClassName("is-focused");
            }
        });
        $input.addEventListener(Events.ONBLUR, onBlurListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                removeClassName("is-focused");
            }
        });
    }

    @Override
    public void removeEventListeners() {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.removeEventListener(Events.ONCHANGE, onChangeListener);
        $input.removeEventListener(Events.ONFOCUS, onFocusListener);
        $input.removeEventListener(Events.ONBLUR, onBlurListener);
        super.removeEventListeners();
    }

    @Override
    public void didEnterDocument() {
        setValue(this.props.checked());
        setDisabled(this.props.disabled());
    }

    @Override
    public void setDisabled(boolean isDisabled) {
        super.setDisabled(isDisabled);
        if (input != null) {
            HTMLInputElement $input = Js.cast(input.getElement());
            $input.disabled = isDisabled;
        }
    }

    @Override
    public void setTabIndex(int index) {
        super.setTabIndex(index);
        if (input != null) {
            HTMLInputElement $input = Js.cast(input.getElement());
            $input.tabIndex = index;
        }
    }

    public void setText(String text) {
        this.props.text(text);
        if (isRendered()) {
            labelEl.setInnerHtml(text);
        }
    }

}
