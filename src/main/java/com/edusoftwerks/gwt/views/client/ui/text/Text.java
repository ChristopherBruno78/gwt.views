package com.edusoftwerks.gwt.views.client.ui.text;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import elemental2.dom.*;
import jsinterop.base.Js;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class Text extends Control<TextProps> {

    protected DOMElement input = null;
    private String lastValue = null;
    private boolean isError = false;
    private EventListener onTextChangeListener;

    static {
        Theme.get().TextCss().ensureInjected();
    }

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

    public boolean isSecure() {
        return this.props.secure();
    }

    public void setPlaceholder(String placeholder) {
        this.props.placeholder(placeholder);
        if (isRendered()) {
            input.setAttribute("placeholder", placeholder);
        }
    }

    public String getPlaceholder() {
        return this.props.placeholder();
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
        return div(new DOMProps()
                        .className(
                                ClassNames.start("v-Text")
                                        .add("multiline", this.props.multiline())
                                        .add("is-error", isError)
                                        .build()
                        )
                        .attr("tabIndex", -1)
                        .attr("role", "textbox")
                        .attr("aria-disabled", isDisabled()),
                input = this.renderControl()
        );
    }

    protected DOMElement renderControl() {
        if (this.props.multiline()) {
            return create("textarea",
                    new DOMProps()
                            .className("v-Text-input")
                            .attr("rows", "1")
                            .attr("tabIndex", isDisabled() ? -1 : 0)
                            .attr("name", this.props.name())
                            .attr("placeholder", this.props.placeholder())
            );
        } else {
            return create("input", new DOMProps()
                    .className("v-Text-input")
                    .attr("type", this.props.secure() ? "password" : "text")
                    .attr("tabIndex", isDisabled() ? -1 : 0)
                    .attr("name", this.props.name())
                    .attr("placeholder", this.props.placeholder())
            );

        }
    }

    private EventListener onKeyPressListener;
    private EventListener onFocusListener;
    private EventListener onBlurListener;
    private EventListener onInputListener;

    @Override
    protected void addEventListeners() {
        HTMLElement $input = input.getElement();
        $input.addEventListener(Events.ONFOCUS, onFocusListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                addClassName("is-focused");
                //forward event to main component
                Events.fireEvent(Events.ONFOCUS, getElement());
            }
        });
        $input.addEventListener(Events.ONBLUR, onBlurListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                removeClassName("is-focused");
                //forward event to main component
                Events.fireEvent(Events.ONBLUR, getElement());
            }
        });
        $input.addEventListener(Events.ONKEYPRESS, onKeyPressListener = new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (!props.multiline()) {
                    KeyboardEvent keyboardEvent = Js.cast(evt);
                    if (keyboardEvent.code.equals(KeyCodes.Enter)) {
                        evt.preventDefault();
                        fireActions();
                    }
                }

            }
        });
        $input.addEventListener(Events.ONINPUT, onInputListener = this::handleChange);
        super.addEventListeners();
    }

    private void handleChange(Event evt) {
        setText(getRawValue());
    }

    @Override
    public void removeEventListeners() {
        HTMLInputElement $input = Js.cast(input.getElement());
        $input.removeEventListener(Events.ONKEYPRESS, onKeyPressListener);
        $input.removeEventListener(Events.ONFOCUS, onFocusListener);
        $input.removeEventListener(Events.ONBLUR, onBlurListener);
        $input.removeEventListener(Events.ONINPUT, onInputListener);
        super.removeEventListeners();
    }

    @Override
    public void didEnterDocument() {
        setText(this.props.text());
        if (this.props.readOnly()) {
            input.setAttribute("readOnly", "true");
        }
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

}
