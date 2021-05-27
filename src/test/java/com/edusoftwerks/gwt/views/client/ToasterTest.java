package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonType;
import com.edusoftwerks.gwt.views.client.ui.combobox.ComboBox;
import com.edusoftwerks.gwt.views.client.ui.combobox.ComboBoxProps;
import com.edusoftwerks.gwt.views.client.ui.toast.*;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public class ToasterTest extends GwtViewsTest {

    private Button toastBtn;
    private ComboBox positionCtrl;
    private ComboBox intentCtrl;

    @Override
    UIObject render() {
        return div(
                new DOMProps().style("margin", "25px").style("width", "220px"),
                label("Position:"),
                positionCtrl = new ComboBox(new ComboBoxProps()
                        .options((Object[]) ToasterPosition.values())
                        .select(0)),
                label("Intent:"),
                intentCtrl = new ComboBox(new ComboBoxProps()
                        .options((Object[]) ToastIntent.values())
                        .select(0)),
                toastBtn = new Button(new ButtonProps()
                        .type(ButtonType.PRIMARY)
                        .text("Toast")
                        .style("margin-top", "15px")
                        .style("width", "220px")));
    }

    @Override
    public void addEventListeners() {
        Toaster toaster =
                new Toaster(new ToasterProps().position(ToasterPosition.valueOf(positionCtrl.getSelectedValue())));
        positionCtrl.addEventListener(Events.CHANGE, evt -> {
            toaster.setPosition(ToasterPosition.valueOf(positionCtrl.getSelectedValue()));
        });
        toastBtn.addEventListener(Events.ACTION, evt -> {
            toaster.toast(
                    new Toast(new ToastProps()
                            .intent(ToastIntent.valueOf(intentCtrl.getSelectedValue()))
                            .icon("icon-search")) {
                        @Override
                        protected DOMElement renderContent() {
                            return label(
                                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
                        }
                    });
        });
    }
}
