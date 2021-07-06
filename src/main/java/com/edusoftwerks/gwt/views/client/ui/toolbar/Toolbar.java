package com.edusoftwerks.gwt.views.client.ui.toolbar;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.dom.Events;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.MenuButton;
import com.edusoftwerks.gwt.views.client.ui.button.MenuButtonProps;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItem;
import com.edusoftwerks.gwt.views.client.ui.menu.MenuItemProps;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Timer;
import elemental2.dom.Event;
import elemental2.dom.EventListener;

import java.util.List;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class Toolbar extends View<ToolbarProps> {

    static {
        Theme.get().ToolbarCss().ensureInjected();
    }

    private DOMElement leftSection;
    private DOMElement rightSection;
    private MenuButton overflowBtn;
    private Timer measureTimer;

    public Toolbar(ToolbarProps props) {
        super(props);

        overflowBtn = new MenuButton(
                (MenuButtonProps) new MenuButtonProps()
                        .className("v-overflow-btn")
                        .style("display", "none")
                        .text("More"));
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps()
                        .className("v-Toolbar")
                        .attr("role", "toolbar"),
                leftSection = div(new DOMProps().className("v-Toolbar-section v-Toolbar-section--left"),
                        this.props.leftViews().toArray(new UIObject[0])
                ),

                rightSection = div(new DOMProps()
                                .className("v-Toolbar-section v-Toolbar-section--right"),
                        this.props.rightViews().toArray(new UIObject[0])
                )
        );
    }

    private int preMeasure() {

        int pxWidth = getWidth();

        pxWidth -= 16;

        List<UIObject> leftViews = leftSection.getChildren();
        List<UIObject> rightViews = rightSection.getChildren();

        int l = rightViews.size();

        int rWidth = 0;

        for (int i = 0; i < l; i++) {
            rWidth += DOM.getOuterWidth(rightViews.get(i).getElement());
        }

        pxWidth -= rWidth;
        l = leftViews.size();

        int lWidth = 0;

        for (int i = 0; i < l; i++) {
            UIObject v = leftViews.get(i);
            showToolItem(v, true);
            lWidth += DOM.getOuterWidth(v.getElement());
        }

        pxWidth -= lWidth;

        return pxWidth;
    }

    private void postMeasure(int pxWidth) {

        if (pxWidth < 0) {

            List<UIObject> leftViews = leftSection.getChildren();
            int l;

            pxWidth -= 75;
            l = leftViews.size();

            while (pxWidth < 0 && l > -1) {
                l--;
                if (l > -1)
                    pxWidth += (leftViews.get(l).getElement().offsetWidth);
            }

            if (leftViews.size() - l < 2) {
                l--;
            }
            if (l < 0) {
                l = 0;
            }
            overflowBtn.getMenu().removeAll();
            for (int i = l; i < leftViews.size(); i++) {
                UIObject v = leftViews.get(i);
                if (v != overflowBtn) {
                    if (v instanceof Button) {
                        final Button b = (Button) v;
                        MenuItemProps menuItemProps = new MenuItemProps()
                                .icon(b.getIcon())
                                .text(b.getText())
                                .disabled(b.isDisabled());

                        if (b instanceof MenuButton) {
                            menuItemProps.submenu(
                                    ((MenuButton) b).getMenu()
                            );
                        }
                        MenuItem item = new MenuItem(menuItemProps);
                        overflowBtn.getMenu().append(item);
                        item.addEventListener(Events.ACTION, evt -> Events.fireEvent(Events.ACTION, b));

                    }
                    showToolItem(v, false);
                }
            }
        }
    }

    /**
     * Shows and hides item based on overflow
     * @param item
     * @param flag
     */
    private void showToolItem(UIObject item, boolean flag) {
        if (flag) {
            item.setAttribute("aria-hidden", false);
            item.setStyleAttribute("display", "inline-block");
        } else {
            item.setAttribute("aria-hidden", true);
            item.setStyleAttribute("display", "none");
        }
    }

    private void doMeasure() {

        showToolItem(overflowBtn, false);

        int m = preMeasure();

        if (m < 0) {
            GWT.log("overflow");
            postMeasure(m);
            showToolItem(overflowBtn, overflowBtn.getMenu().getItems().size() > 0);

        } else {
            showToolItem(overflowBtn, false);
        }
    }

    @Override
    public void didEnterDocument() {
        Scheduler.ScheduledCommand cmd = new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                doMeasure();
            }
        };
        Scheduler.get().scheduleDeferred(cmd);
        leftSection.append(overflowBtn);
    }

    @Override
    public void onResize() {
        if (this.props.preventOverflow()) {
            measureTimer = new Timer() {
                @Override
                public void run() {
                    doMeasure();
                }
            };
            measureTimer.schedule(100);
        }
    }
}
