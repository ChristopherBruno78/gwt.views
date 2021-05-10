package com.edusoftwerks.gwt.views.client.ui.combobox;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.ClassNames;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLSelectElement;
import jsinterop.base.Js;

import java.util.List;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ComboBox extends Control<ComboBoxProps> {

    private DOMElement input;

    static {
        Theme.get().ComboBoxCss().ensureInjected();
    }

    public ComboBox(ComboBoxProps props) {
        super(props);
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps()
                        .className(ClassNames.start("v-ComboBox")
                                .add("is-disabled", isDisabled())
                                .build())
                        .attr("role", "listbox")
                        .attr("aria-disabled", isDisabled()),
                this.input = create("select",
                        new DOMProps()
                                .attr("name", this.props.name())
                                .className("v-Combo-input v-no-select")
                )
        );
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.ONCHANGE, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (isDisabled()) {
                    return;
                }
                HTMLSelectElement selectElement = Js.cast(input.getElement());
                setSelectedIndex(selectElement.selectedIndex);
                fireActions();
            }
        });
    }

    @Override
    public void insert(int index, UIObject option) {
        if (option instanceof Option) {
            this.props.options().add(index, ((Option) option).getText());
            if (isRendered()) {
                input.insert(index, option);
            }
        }

    }

    @Override
    public void remove(UIObject option) {
        if (option instanceof Option) {
            Option o = (Option) option;
            this.props.options().remove(o.getText());
        }
        if (isRendered()) {
            input.remove(option);
        }

    }

    @Override
    public void append(UIObject... options) {
        for (UIObject option : options) {
            if (option instanceof Option) {
                insert(this.props.options().size(), option);
            }
        }
    }

    public void addOption(String value) {
        append(new Option(value, (this.props.options().size() + 1)));
    }

    public List<String> getOptions() {
        return this.props.options();
    }

    public void updateOptions(String... options) {
        if (isRendered()) {
            input.setInnerHtml("");
        }
        this.props.options().clear();
        for (String option : options) {
            addOption(option);
        }
    }

    @Override
    public void didEnterDocument() {
        updateOptions(this.props.options().toArray(new String[ 0 ]));
        setSelectedIndex(this.props.select());
        setDisabled(isDisabled());
    }

    public int getSelectedIndex() {
        return this.props.select();
    }

    public void setSelectedIndex(int index) {
        this.props.select(index);
        if (isRendered()) {
            HTMLSelectElement selectElement = Js.cast(input.getElement());
            if (index != selectElement.selectedIndex) {
                selectElement.selectedIndex = index;
            }
        }
    }

    public String getSelectedValue() {
        if (getSelectedIndex() > -1) {
            return this.props.options().get(getSelectedIndex());
        }
        return null;
    }

    public void setSelectedValue(String value) {
        int index = this.props.options().indexOf(value);
        setSelectedIndex(index);
    }

    @Override
    public void setDisabled(boolean flag) {
        super.setDisabled(flag);
        if (isRendered()) {
            HTMLSelectElement selectElement = Js.cast(input.getElement());
            selectElement.disabled = flag;

        }
    }

}
