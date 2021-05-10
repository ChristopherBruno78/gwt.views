package com.edusoftwerks.gwt.views.client.ui.combobox;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComboBoxProps extends ControlProps<ComboBoxProps> {

    private List<String> optionsList = new ArrayList<>();

    public ComboBoxProps() {
        select(-1);
        disabled(false);
    }

    public Integer select() {
        return (Integer) values.get("select");
    }

    public ComboBoxProps select(int index) {
        return set("select", index);
    }

    public List<String> options() {
        return optionsList;
    }

    public ComboBoxProps options(String... options) {
        optionsList = new ArrayList<>(Arrays.asList(options));
        return this;
    }

}
