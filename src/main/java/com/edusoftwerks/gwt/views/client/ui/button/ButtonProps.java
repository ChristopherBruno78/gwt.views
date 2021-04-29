package com.edusoftwerks.gwt.views.client.ui.button;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

public class ButtonProps extends ControlProps<ButtonProps> {

    public ButtonProps() {
        super();
        iconOnly(false);
        isToggle(false);
        type(ButtonType.DEFAULT);
        tooltipTimeout(1000);
    }

    public String icon() {
        return (String) values.get("icon");
    }

    public ButtonProps icon(String icon) {
        return set("icon", icon);
    }

    public Boolean iconOnly() {
        return (Boolean) values.get("iconOnly");
    }

    public ButtonProps iconOnly(boolean flag) {
        return set("iconOnly", flag);
    }

    public ButtonType type() {
        return (ButtonType) values.get("type");
    }

    public ButtonProps type(ButtonType type) {
        return set("type", type);
    }

    public String tooltip() {
        return (String) values.get("tooltip");
    }

    public ButtonProps tooltip(String tooltip) {
        return set("tooltip", tooltip);
    }

    public Boolean isToggle() {
        return (Boolean) values.get("isToggle");
    }

    public ButtonProps isToggle(boolean flag) {
        return set("isToggle", flag);
    }

    public Integer tooltipTimeout() {
        return (Integer) values.get("tooltipTimeout");
    }

    public ButtonProps tooltipTimeout(int timeout) {
        return set("tooltipTimeout", timeout);
    }
}
