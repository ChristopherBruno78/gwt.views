package com.edusoftwerks.gwt.views.client.ui.button;

import com.edusoftwerks.gwt.views.client.ui.menu.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuButtonProps extends ButtonProps {

    private List<MenuItem> menuItems = new ArrayList<>();

    public MenuButtonProps() {
        super();
        iconOnly(false);
        type(ButtonType.DEFAULT);
        tooltipTimeout(1000);
        dropIcon("icon-angle-down-solid");
    }

    public List<MenuItem> menuItems() {
        return menuItems;
    }

    public MenuButtonProps menuItems(MenuItem... items) {
        menuItems = new ArrayList<>(Arrays.asList(items));
        return this;
    }

    public MenuButtonProps dropIcon(String icon) {
        return (MenuButtonProps) set("dropIcon", icon);
    }

    public String dropIcon() {
        return (String) values.get("dropIcon");

    }
}


