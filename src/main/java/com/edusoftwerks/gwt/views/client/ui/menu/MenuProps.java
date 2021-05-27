package com.edusoftwerks.gwt.views.client.ui.menu;

import com.edusoftwerks.gwt.views.client.UIProps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuProps extends UIProps<MenuProps> {

    private List<MenuItem> menuItems = new ArrayList<>();

    public List<MenuItem> menuItems() {
        return menuItems;
    }

    public MenuProps menuItems(MenuItem... items) {
        menuItems = new ArrayList<>(Arrays.asList(items));
        return this;
    }
}
