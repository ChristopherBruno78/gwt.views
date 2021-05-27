package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.menu.Menu;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItem;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItemProps;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuProps;
import com.google.gwt.user.client.Window;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class MenuTest extends GwtViewsTest {

    private Menu menu;
    private MenuItem m1;
    private MenuItem m2;

    @Override
    UIObject render() {
        return div(
                new DOMProps(),
                menu = new Menu(new MenuProps()
                        .menuItems(
                                m1 = new MenuItem(new MenuItemProps().text("Item 1")),
                                m2 = new MenuItem(
                                        new MenuItemProps().text("Item 2").icon("icon-search")))));
    }

    @Override
    public void didRun() {
        m1.addEventListener(Events.ACTION, evt -> {
            Window.alert("Hello, World");
        });
        menu.show(100, 100);
    }
}
