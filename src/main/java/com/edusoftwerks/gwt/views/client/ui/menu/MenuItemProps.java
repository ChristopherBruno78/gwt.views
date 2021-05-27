package com.edusoftwerks.gwt.views.client.ui.menu;

import com.edusoftwerks.gwt.views.client.ui.ControlProps;

public class MenuItemProps extends ControlProps<MenuItemProps> {

    public MenuItemProps() {
        disabled(false);
    }

    public String icon() {
        return (String) values.get("icon");
    }

    public MenuItemProps icon(String icon) {
        return set("icon", icon);
    }

    public Menu submenu() {
        return (Menu) values.get("submenu");
    }

    public MenuItemProps submenu(Menu menu) {
        return set("submenu", menu);
    }

}
