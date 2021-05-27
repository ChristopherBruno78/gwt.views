package com.edusoftwerks.gwt.views.client.ui.menu;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class MenuItem extends Control<MenuItemProps> {

    static {
        Theme.get().MenuCss().ensureInjected();
    }

    Menu menu;
    boolean canBeActive = true;

    public MenuItem(MenuItemProps props) {
        super(props);
    }

    void setActive(boolean flag) {
        if (isRendered() && canBeActive) {
            if (flag) {
                addClassName("is-active");
                menu.setActiveIndex(getIndex());
                if (hasSubmenu()) {
                    HTMLElement $el = getElement();
                    this.props.submenu().show($el.offsetLeft + $el.offsetWidth, $el.offsetTop - 7);
                }
            } else {
                if (hasSubmenu()) {
                    this.props.submenu().close();
                }
                removeClassName("is-active");
            }
        }
    }

    boolean hasSubmenu() {
        return this.props.submenu() != null;
    }

    Menu submenu() {
        return this.props.submenu();
    }

    void setMenu(Menu menu) {
        this.menu = menu;
        if (hasSubmenu()) {
            this.submenu().supermenu = menu;
        }
    }

    int getIndex() {
        return menu.getItems().indexOf(this);
    }

    @Override
    protected DOMElement render() {
        if (this.props.submenu() != null) {
            return create(
                    "li",
                    new DOMProps()
                            .className(ClassNames.start("v-MenuItem")
                                    .add("v-no-select")
                                    .add("v-MenuItem--hasIcon", this.props.icon() != null)
                                    .add("is-disabled", isDisabled())
                                    .build())
                            .attr("aria-disabled", isDisabled())
                            .attr("role", "menuitem"),
                    (this.props.icon() != null
                            ? create("i", new DOMProps().className("icon " + this.props.icon()))
                            : null),
                    div(new DOMProps().className("label v-no-select").attr("unselectable", "on"), this.props.text()),
                    create("i", new DOMProps().className("chevron-right font-icon icon-chevron-right")));
        } else {
            return create(
                    "li",
                    new DOMProps()
                            .className(ClassNames.start("v-MenuItem")
                                    .add("v-no-select")
                                    .add("v-MenuItem--hasIcon", this.props.icon() != null)
                                    .add("is-disabled", isDisabled())
                                    .build())
                            .attr("aria-disabled", isDisabled())
                            .attr("role", "menuitem"),
                    (this.props.icon() != null
                            ? create("i", new DOMProps().className("font-icon " + this.props.icon()))
                            : null),
                    div(new DOMProps().className("label v-no-select").attr("unselectable", "on"), this.props.text()));
        }
    }

    @Override
    protected void addEventListeners() {

        addEventListener(Events.MOUSEOVER, evt -> {
            setActive(true);
        });

        addEventListener(Events.MOUSEOUT, new MouseEventListener() {
            @Override
            public void handleMouseEvent(MouseEvent event) {
                if (hasSubmenu()) {
                    HTMLElement $el = menu.getElement();
                    double x = event.clientX;
                    setActive(x >= $el.offsetLeft + $el.offsetWidth);
                } else {
                    setActive(false);
                }
            }
        });

        addEventListener(Events.CLICK, evt -> performAction());

        super.addEventListeners();
    }

    void performAction() {
        if (isDisabled() || hasSubmenu()) {
            return;
        }
        Events.fireEvent(Events.ACTION, this);
        menu.close();
        Menu supermenu = menu.supermenu;
        while (supermenu != null) {
            supermenu.close();
            supermenu = supermenu.supermenu;
        }
    }
}
