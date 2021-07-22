package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.button.MenuButton;
import com.edusoftwerks.gwt.views.client.ui.button.MenuButtonProps;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItem;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItemProps;
import com.edusoftwerks.gwt.views.client.ui.text.SearchText;
import com.edusoftwerks.gwt.views.client.ui.text.TextProps;
import com.edusoftwerks.gwt.views.client.ui.toolbar.Toolbar;
import com.edusoftwerks.gwt.views.client.ui.toolbar.ToolbarDivider;
import com.edusoftwerks.gwt.views.client.ui.toolbar.ToolbarProps;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.google.gwt.user.client.Window.alert;

public class ToolbarTest extends GwtViewsTest {

    private Button btn4;

    @Override
    UIObject render() {
        return div(new DOMProps(),
                new Toolbar(new ToolbarProps()
            .leftViews(
                    new Button("Archive"),
                    new ToolbarDivider(),
                    new Button("My Button 2"),
                    new MenuButton((MenuButtonProps) new MenuButtonProps()
                        .menuItems(
                                new MenuItem(new MenuItemProps().text("Item 1")),
                                new MenuItem(new MenuItemProps().text("Item 2")),
                                new MenuItem(new MenuItemProps().text("Item 3")),
                                new MenuItem(new MenuItemProps().text("Item 4")),
                                new MenuItem(new MenuItemProps().text("Item 5")),
                                new MenuItem(new MenuItemProps().text("Item 6")),
                                new MenuItem(new MenuItemProps().text("Item 7")),
                                new MenuItem(new MenuItemProps().text("Item 8"))
                        ).text("Menu Btn")
                    ),
                    btn4 = new Button("My Button 4")
            )
            .rightViews(
                   new SearchText(new TextProps()),
                   new Button(new ButtonProps()
                           .iconOnly(true)
                           .icon("icon-check-solid")
                   )
            )
        ));
    }

    @Override
    protected void addEventListeners() {
        btn4.addEventListener(Events.ACTION, evt -> {
             alert("hello, world");
        });
    }
}
