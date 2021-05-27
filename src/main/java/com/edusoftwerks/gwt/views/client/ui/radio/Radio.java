package com.edusoftwerks.gwt.views.client.ui.radio;

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

public class Radio extends Control<RadioProps> {

    static {
        Theme.get().RadioCss().ensureInjected();
    }

    private DOMElement input;
    private DOMElement labelEl;
    private EventListener onChangeListener;
    private EventListener onFocusListener;
    private EventListener onBlurListener;

    public Radio(RadioProps props) {
        super(props);
    }

    public boolean getValue() {
        return this.props.selected();
    }

    public void setValue(boolean value) {
        this.props.selected(value);
        if (isRendered()) {
            setAttribute("aria-checked", this.props.selected());
            HTMLInputElement $input = Js.cast(input.getElement());
            if ($input.checked != this.props.selected()) {
                $input.checked = this.props.selected();
            }
        }
    }

    @Override
    protected DOMElement render() {
        final String id = Document.get().createUniqueId();
        return div(
                new DOMProps()
                        .className("v-Radio")
                        .attr("aria-disabled", isDisabled())
                        .attr("role", "radio"),
                input = create(
                        "input",
                        new DOMProps()
                                .attr("id", id)
                                .attr("type", "radio")
                                .attr("name", this.props.name())
                                .attr("tabIndex", isDisabled() ? -1 : 0)),
                div(new DOMProps().className("indicator")),
                labelEl = label(new DOMProps().className("label v-no-select").attr("for", id), this.props.text()));
    }

    @Override
    protected void addEventListeners() {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.addEventListener(
                Events.CHANGE,
                onChangeListener = new EventListener() {
                    @Override
                    public void handleEvent(Event evt) {}
                });
        $input.addEventListener(
                Events.FOCUS,
                onFocusListener = new EventListener() {
                    @Override
                    public void handleEvent(Event evt) {}
                });
        $input.addEventListener(
                Events.BLUR,
                onBlurListener = new EventListener() {
                    @Override
                    public void handleEvent(Event evt) {}
                });
        super.addEventListeners();
    }

    @Override
    public void removeEventListeners() {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.removeEventListener(Events.CHANGE, onChangeListener);
        $input.removeEventListener(Events.FOCUS, onFocusListener);
        $input.removeEventListener(Events.BLUR, onBlurListener);
        super.removeEventListeners();
    }

    @Override
    public void didEnterDocument() {
        setValue(this.props.selected());
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
