package com.edusoftwerks.gwt.views.client.ui.menu;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.CompositeView;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOver;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverProps;
import com.edusoftwerks.gwt.views.shared.Color;
import com.google.gwt.event.dom.client.KeyCodes;
import elemental2.dom.HTMLElement;
import elemental2.dom.KeyboardEvent;

import java.util.List;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;

public class Menu extends CompositeView<MenuProps> {

    static {
        Theme.get().MenuCss().ensureInjected();
    }

    private final MenuPopOver popover;
    Menu supermenu;
    private DOMElement menuEl;
    private HTMLElement lastFocusEl;
    private int activeIndex = -1;

    public Menu(MenuProps props) {
        super(props);

        List<MenuItem> items = getItems();
        for (MenuItem item : items) {
            item.setMenu(this);
        }

        init(popover = new MenuPopOver(new PopOverProps()
                        .isCallout(false)
                        .className("v-Menu-popover")
                        .attr("tabIndex", 0)
                        .attr("role", "menu")
                        .backgroundColor(Color.whiteColor())));
    }

    public List<MenuItem> getItems() {
        return this.props.menuItems();
    }

    @Override
    public void append(UIObject... items) {
        for (UIObject item : items) {
            insert(this.props.menuItems().size(), item);
        }
    }

    @Override
    public void insert(int index, UIObject item) {
        if (item instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) item;
            menuItem.setMenu(this);
            this.props.menuItems().add(index, menuItem);
            if (isRendered()) {
                menuEl.insert(index, item);
            }
        }
    }

    @Override
    public void remove(UIObject item) {
        if (item instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) item;
            menuItem.setMenu(null);
            this.props.menuItems().remove(menuItem);
            if (isRendered()) {
                menuEl.remove(item);
            }
        }
    }

    public void show(int xPos, int yPos) {
        if (isRendered()) {
            popover.setStyleAttribute("left", xPos + "px");
            popover.setStyleAttribute("top", yPos + "px");
            popover.open();

            activeIndex = -1;
            focus();
            clearSelection();
        }
    }

    public void removeAll() {
        List<MenuItem> items = getItems();
        while(items.size() > 0) {
            remove(items.get(0));
            items = getItems();
        }
    }

    public void close() {
        if (isRendered()) {
            popover.close();
            List<MenuItem> items = getItems();
            for (MenuItem item : items) {
                item.setActive(false);
            }
            activeIndex = -1;
            if (lastFocusEl != null) {
                lastFocusEl.focus();
            }
        }
    }

    void focus() {
        if (lastFocusEl == null) {
            lastFocusEl = DOM.getFocus();
        }
        getElement().focus();
    }



    void setActiveIndex(int index) {

        if (index != activeIndex) {
            MenuItem selItem;
            List<MenuItem> items = getItems();
            if (activeIndex > -1) {
                selItem = items.get(activeIndex);
                if (selItem != null) {
                    selItem.setActive(false);
                }
            }

            activeIndex = index;

            if (activeIndex > -1 && activeIndex < items.size()) {
                selItem = items.get(activeIndex);
                if (selItem != null) {
                    selItem.setActive(true);
                    focus();
                }
            }
        }
    }

    public boolean isHidden() {
        return popover.isHidden();
    }

    public void setWidth(int width) {
        getElement().style.setProperty("width", Math.max(width, 172) + "px");
    }

    private native void clearSelection() /*-{
        var sel = $wnd.getSelection ? $wnd.getSelection() : $doc.selection;
        if (sel) {
            if (sel.removeAllRanges) {
                sel.removeAllRanges();
            } else if (sel.empty) {
                sel.empty();
            }
        }
    }-*/;

    class MenuPopOver extends PopOver {

        public MenuPopOver(PopOverProps props) {
            super(props);
        }

        @Override
        protected DOMElement renderView() {
            return menuEl = create(
                    "ul",
                    new DOMProps().className("v-Menu"),
                    Menu.this.props.menuItems().toArray(new MenuItem[0]));
        }

        @Override
        public void addEventListeners() {
            addEventListener(Events.MOUSEDOWN, evt -> {
                Menu.this.focus();
            });

            addEventListener(Events.KEYDOWN, new KeyboardEventListener() {
                @Override
                public void handleKeyboardEvent(KeyboardEvent keyboardEvent, int keyCode) {
                    List<MenuItem> menuItems = Menu.this.getItems();
                    if (keyCode == KeyCodes.KEY_DOWN) {
                        int i = activeIndex + 1;
                        while (i < menuItems.size()) {
                            MenuItem item = menuItems.get(i);
                            if (item.canBeActive) {
                                setActiveIndex(i);
                                break;
                            }
                            i++;
                        }
                    } else if (keyCode == KeyCodes.KEY_UP) {
                        int i = activeIndex - 1;
                        while (i > -1) {
                            MenuItem item = menuItems.get(i);
                            if (item.canBeActive) {
                                setActiveIndex(i);
                                break;
                            }
                            i--;
                        }
                    } else if (keyCode == KeyCodes.KEY_RIGHT) {
                        MenuItem sel = menuItems.get(activeIndex);
                        if (sel != null) {
                            if (sel.hasSubmenu()) {
                                sel.submenu().setActiveIndex(0);
                            }
                        }
                    } else if (keyCode == KeyCodes.KEY_LEFT) {
                        if (supermenu != null) {
                            supermenu.focus();
                            setActiveIndex(-1);
                            close();
                        }
                    } else if (keyCode == KeyCodes.KEY_ESCAPE) {
                        Menu.this.close();
                    } else if (keyCode == KeyCodes.KEY_ENTER) {
                        MenuItem selected = menuItems.get(activeIndex);
                        if (selected != null) {
                            selected.performAction();
                        }
                    }
                }
            });

            super.addEventListeners();
        }
    }
}
