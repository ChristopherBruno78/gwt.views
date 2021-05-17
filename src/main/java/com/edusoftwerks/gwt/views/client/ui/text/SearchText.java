package com.edusoftwerks.gwt.views.client.ui.text;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public final class SearchText extends Text {

    private Button cancelBtn;

    static {
        Theme.get().TextCss().ensureInjected();
    }

    public SearchText(TextProps props) {
        super(props);
        if (this.props.placeholder() == null) {
            this.props.placeholder("Search");
        }
    }

    protected DOMElement renderControl() {
        assert (!this.props.multiline()) : "SearchText does not support multiline";
        return super.renderControl();
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps()
                        .className("v-SearchText v-Text")
                        .attr("role", "search")
                        .attr("aria-disabled", isDisabled()),
                create("i", new DOMProps().className("font-icon icon-search")),
                input = renderControl(),
                cancelBtn = new Button(
                        new ButtonProps()
                                .className("v-SearchText-reset")
                                .icon("icon-cancel-circle")
                                .iconOnly(true)
                )
        );
    }

    @Override
    public void addEventListeners() {
        cancelBtn.addActionListener(evt -> {
            clear();
            cancelBtn.setHidden(true);
            Events.fireEvent(Events.ONCANCEL, getElement());
        });
        addEventListener(Events.ONINPUT, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                cancelBtn.setHidden(getText().length() == 0);
            }
        });
        super.addEventListeners();

    }

    @Override
    public void didEnterDocument() {
        cancelBtn.setHidden(true);
        super.didEnterDocument();
    }

}