package com.edusoftwerks.gwt.views.client.ui.checkbox;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

public class CheckBoxProps extends ControlProps<CheckBoxProps> {

    public CheckBoxProps() {
        super();
        checked(false);
        disabled(false);
    }

    public Boolean checked() {
        return (Boolean) values.get("checked");
    }

    public CheckBoxProps checked(boolean flag) {
        return set("checked", flag);
    }

}
