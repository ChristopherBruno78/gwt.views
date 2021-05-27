package com.edusoftwerks.gwt.views.client.ui.radio;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

public class RadioProps extends ControlProps<RadioProps> {

    public RadioProps() {
        selected(false);
        disabled(false);
    }

    public Boolean selected() {
        return (Boolean) values.get("selected");
    }

    public RadioProps selected(boolean flag) {
        return set("selected", flag);
    }
}
