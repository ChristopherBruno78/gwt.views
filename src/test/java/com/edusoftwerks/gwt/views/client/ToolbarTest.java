package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.client.ui.text.SearchText;
import com.edusoftwerks.gwt.views.client.ui.text.TextProps;
import com.edusoftwerks.gwt.views.client.ui.toolbar.Toolbar;
import com.edusoftwerks.gwt.views.client.ui.toolbar.ToolbarDivider;
import com.edusoftwerks.gwt.views.client.ui.toolbar.ToolbarProps;

import static com.google.gwt.user.client.Window.alert;

public class ToolbarTest extends GwtViewsTest {

    private Button btn4;

    @Override
    UIObject render() {
        return new Toolbar(new ToolbarProps()
            .leftViews(
                    new Button("Archive"),
                    new ToolbarDivider(),
                    new Button("My Button 2"),
                    new Button("My Button 3"),
                    btn4 = new Button("My Button 4")
            )
            .rightViews(
                   new SearchText(new TextProps()),
                   new Button(new ButtonProps()
                           .iconOnly(true)
                           .icon("icon-check-solid")
                   )
            )
        );
    }

    @Override
    protected void addEventListeners() {
        btn4.addEventListener(Events.ACTION, evt -> {
             alert("hello, world");
        });
    }
}
