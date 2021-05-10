package com.edusoftwerks.gwt.views.client.ui.text;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

public class TextProps extends ControlProps<TextProps> {

    public TextProps() {
        disabled(false);
        readOnly(false);
        multiline(false);
        secure(false);
        text("");
        mask("");
        maskPlaceholder("_");
    }

    public Boolean readOnly() {
        return (Boolean) values.get("readOnly");
    }

    public TextProps readOnly(boolean flag) {
        return set("readOnly", flag);
    }

    public Boolean multiline() {
        return (Boolean) values.get("multiline");
    }

    public TextProps multiline(boolean flag) {
        return set("multiline", flag);
    }

    public Boolean secure() {
        return (Boolean) values.get("secure");
    }

    public TextProps secure(boolean flag) {
        return set("secure", flag);
    }

    public String placeholder() {
        return (String) values.get("placeholder");
    }

    public TextProps placeholder(String value) {
        return set("placeholder", value);
    }

    public String mask() {
        return (String) values.get("mask");
    }

    public TextProps mask(String value) {
        return set("mask", value);
    }

    public String maskPlaceholder() {
        return (String) values.get("maskPlaceholder");
    }

    public TextProps maskPlaceholder(String value) {
        return set("maskPlaceholder", value);
    }

}
