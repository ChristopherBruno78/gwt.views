package com.edusoftwerks.gwt.views.client.ui.text;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import com.edusoftwerks.gwt.views.shared.geometry.Range;
import com.google.gwt.event.dom.client.KeyCodes;
import elemental2.dom.*;
import jsinterop.base.Js;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class Text extends Control<TextProps> {

    static {
        Theme.get().TextCss().ensureInjected();
    }

    private final String lastValue = null;
    protected DOMElement input = null;
    private boolean isError = false;
    private EventListener onKeyPressListener;
    private EventListener onFocusListener;
    private EventListener onBlurListener;
    private EventListener onInputListener;

    public Text(TextProps props) {
        super(props);
    }

    public String getText() {
        return this.props.text();
    }

    public void setText(String value) {
        this.props.text(value);
        if (isRendered()) {
            if (this.props.multiline()) {
                HTMLTextAreaElement $textArea = Js.cast(input.getElement());
                $textArea.value = value;
            } else {
                HTMLInputElement $input = Js.cast(input.getElement());
                $input.value = value;
            }
        }
    }

    public boolean isSecure() {
        return this.props.secure();
    }

    public void setSecure(boolean flag) {
        this.props.secure(flag);
        if (isRendered()) {
            if (flag) {
                input.setAttribute("type", "password");
            } else {
                input.setAttribute("type", "text");
            }
        }
    }

    public boolean isReadOnly() {
        return this.props.readOnly();
    }

    public void setReadOnly(boolean flag) {
        this.props.readOnly(flag);
        if (isRendered()) {
            if (flag) {
                setAttribute("aria-readonly", "true");
                input.setAttribute("readonly", "true");
            } else {
                setAttribute("aria-readonly", "false");
                input.removeAttribute("readonly");
            }
        }
    }

    public String getPlaceholder() {
        return this.props.placeholder();
    }

    public void setPlaceholder(String placeholder) {
        this.props.placeholder(placeholder);
        if (isRendered()) {
            input.setAttribute("placeholder", placeholder);
        }
    }

    public void clear() {
        setText("");
    }

    public void displayError(boolean flag) {
        isError = flag;
        if (isRendered()) {
            if (isError) {
                addClassName("is-error");
            } else {
                removeClassName("is-error");
            }
        }
    }

    @Override
    protected DOMElement render() {
        return div(
                new DOMProps()
                        .className(ClassNames.start("v-Text")
                                .add("multiline", this.props.multiline())
                                .add("is-error", isError)
                                .build())
                        .attr("tabIndex", -1)
                        .attr("role", "textbox")
                        .attr("aria-disabled", isDisabled()),
                input = this.renderControl());
    }

    protected DOMElement renderControl() {
        if (this.props.multiline()) {
            return create(
                    "textarea",
                    new DOMProps()
                            .className("v-Text-input")
                            .attr("rows", "1")
                            .attr("tabIndex", isDisabled() ? -1 : 0)
                            .attr("name", this.props.name())
                            .attr("placeholder", this.props.placeholder()));
        } else {
            return create(
                    "input",
                    new DOMProps()
                            .className("v-Text-input")
                            .attr("type", this.props.secure() ? "password" : "text")
                            .attr("tabIndex", isDisabled() ? -1 : 0)
                            .attr("name", this.props.name())
                            .attr("placeholder", this.props.placeholder()));
        }
    }

    @Override
    protected void addEventListeners() {
        HTMLElement $input = input.getElement();
        $input.addEventListener(
                Events.FOCUS,
                onFocusListener = evt -> {
                    addClassName("is-focused");
                    // forward event to main component
                    Events.fireEvent(Events.FOCUS, this);
                });
        $input.addEventListener(
                Events.BLUR,
                onBlurListener = evt -> {
                    removeClassName("is-focused");
                    // forward event to main component
                    Events.fireEvent(Events.BLUR, this);
                });
        $input.addEventListener(
                Events.KEYPRESS,
                onKeyPressListener = new KeyboardEventListener() {
                    @Override
                    public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                        if (!props.multiline()) {
                            if (keyCode == KeyCodes.KEY_ENTER) {
                                keyboardEvent.preventDefault();
                                fireActions();
                            }
                        }
                    }
                });
        $input.addEventListener(Events.INPUT, onInputListener = this::handleChange);
        super.addEventListeners();
    }

    private void handleChange(Event evt) {
        setText(getRawValue());
    }

    @Override
    public void removeEventListeners() {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.removeEventListener(Events.KEYPRESS, onKeyPressListener);
        $input.removeEventListener(Events.FOCUS, onFocusListener);
        $input.removeEventListener(Events.BLUR, onBlurListener);
        $input.removeEventListener(Events.INPUT, onInputListener);
        super.removeEventListeners();
    }

    @Override
    public void didEnterDocument() {
        setText(this.props.text());
        setReadOnly(this.props.readOnly());
    }

    String getRawValue() {
        if (this.props.multiline()) {
            HTMLTextAreaElement $textArea = Js.cast(input.getElement());
            return $textArea.value;
        } else {
            HTMLInputElement $input = Js.cast(input.getElement());
            return $input.value;
        }
    }

    @Override
    public void setIsFocused(boolean flag) {
        super.setIsFocused(flag);
        if (isRendered()) {
            if (isFocused()) {
                input.getElement().focus();
            } else {
                input.getElement().blur();
            }
        }
    }

    public void setSelectionRange(int begin, int end) {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.setSelectionRange(begin, end);
    }

    public Range getSelectionRange() {
        HTMLInputElement $input = Js.cast(input.getElement());
        return new Range($input.selectionStart, $input.selectionEnd);
    }
}
