package com.edusoftwerks.gwt.views.client.ui.menu;

import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.CompositeView;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOver;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverProps;
import com.edusoftwerks.gwt.views.shared.Color;
import elemental2.dom.HTMLElement;

import java.util.List;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;

public class Menu extends CompositeView<MenuProps> {

    Menu supermenu;
    private MenuPopOver popover;
    private DOMElement menuEl;
    private HTMLElement lastFocusEl;
    private int activeIndex = -1;

    static {
        Theme.get().MenuCss().ensureInjected();
    }

    public Menu(MenuProps props) {
        super(props);
        init(popover = new MenuPopOver(
                        new PopOverProps()
                                .isCallout(false)
                                .className("v-Menu-popover")
                                .attr("tabIndex", 0)
                                .attr("role", "menu")
                                .backgroundColor(Color.whiteColor())
                )
        );
    }

    public List<MenuItem> getItems() {
        return this.props.menuItems();
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

    public boolean isHidden() {
        return popover.isHidden();
    }

    public void setWidth(int width) {
        getElement().style.setProperty("width", Math.max(width, 172) + "px");
    }

    class MenuPopOver extends PopOver {

        public MenuPopOver(PopOverProps props) {
            super(props);
        }

        @Override
        protected DOMElement renderView() {
            return menuEl = create("ul", new DOMProps().className("v-Menu"),
                    Menu.this.props.menuItems().toArray(new MenuItem[ 0 ])
            );
        }

    }

}
