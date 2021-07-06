package com.edusoftwerks.gwt.views.client.ui.button;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.ui.RootView;
import com.edusoftwerks.gwt.views.client.ui.menu.Menu;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItem;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuProps;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Window;
import elemental2.dom.*;
import jsinterop.base.Js;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;

public class MenuButton extends Button {

    private Menu menu;

    public MenuButton(MenuButtonProps props) {
        super(props);

        MenuProps menuProps = new MenuProps()
                .menuItems(props.menuItems().toArray(new MenuItem[0]));

        menu = new Menu(menuProps);
    }

    public Menu getMenu() {
        return menu;
    }

    private void showMenuView() {

        final HTMLElement el = getElement();
        menu.setWidth(getWidth());

        int top = el.offsetTop;
        int topOffset = el.offsetHeight;

        if (top + topOffset + menu.getHeight() > RootView.get().getHeight()) {
            topOffset = -1 * topOffset;
        }

        Rectangle rect = DOM.getBoundingRectClient(el);
        menu.show(rect.origin.x, top + topOffset);

    }

    @Override
    protected DOMElement render() {
        DOMElement $el = super.render();
        String className = $el.getElement().className;
        $el.setClassName(
                ClassNames.start("v-MenuButton")
                        .add(className)
                        .build()
        );
        $el.append(
                create("i", new DOMProps()
                        .className("font-icon " + ((MenuButtonProps) props).dropIcon()))
        );
        return $el;
    }

    @Override
    protected void addEventListeners() {
        super.addEventListeners();
        addEventListener(Events.KEYDOWN, new KeyboardEventListener() {
            @Override
            public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                if (isDisabled()) {
                    return;
                }
                if (keyCode == KeyCodes.KEY_SPACE ||
                        keyCode == KeyCodes.KEY_ENTER) {
                    keyboardEvent.preventDefault();

                    if (menu.isHidden()) {
                        showMenuView();
                        //menu.setActiveIndex(0);
                    } else {
                        removeClassName("is-active");
                        menu.close();
                    }
                }
            }
        });
        menu.addEventListener(Events.CLOSE, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                removeClassName("is-active");
            }
        });
    }

    @Override
    protected void onMouseDown(Event evt) {
        evt.stopPropagation();
        if (isDisabled()) {
            return;
        }

        MouseEvent mouseEvent = Js.cast(evt);
        if (mouseEvent.button != 0) {
            return;
        }

        if (menu.isHidden()) {
            super.onMouseDown(evt);
            showMenuView();
        } else {
            evt.preventDefault();
            removeClassName("is-active");
            menu.close();
        }
    }

    @Override
    protected void onMouseUp(Event evt) {
        DOM.releaseCapture(getElement());

    }




}
