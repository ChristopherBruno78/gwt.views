package com.edusoftwerks.gwt.views.client.ui.button;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Control;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOver;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverEdge;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverPosition;
import com.edusoftwerks.gwt.views.client.ui.popover.PopOverProps;
import com.edusoftwerks.gwt.views.shared.Color;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import com.google.gwt.user.client.Timer;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.*;

public class Button extends Control<ButtonProps> {

    private DOMElement $iconEl;
    private DOMElement $iconElParent;
    private DOMElement $labelEl;
    private boolean toggleOn = false;
    private PopOver toolTip;
    private Timer clearToolTipTimer;
    private Timer showToolTipTimer;

    static {
        Theme.get().ButtonCss().ensureInjected();
    }

    public Button(ButtonProps props) {
        super(props);
        if (this.props.tooltip() != null) {
            toolTip = new PopOver(new PopOverProps()
                    .className("v-Tooltip")
                    .backgroundColor(Color.colorFromHex("#fafafa"))
                    .borderColor(Color.colorFromHex("#bbbbbb"))
                    .position(PopOverPosition.TOP)
                    .isCallout(true)
                    .edge(PopOverEdge.CENTER)
            ) {
                @Override
                protected DOMElement renderView() {
                    return label(Button.this.props.tooltip());
                }
            };
        }
    }

    public Button(String text, ButtonType type) {
        this(new ButtonProps().text(text).type(type));
    }

    public Button(String text) {
        this(new ButtonProps().text(text));
    }

    @Override
    protected DOMElement render() {
        return create(
                "button",
                new DOMProps()
                        .attr("aria-disabled", isDisabled())
                        .attr("tabIndex", this.isDisabled() ? -1 : 0)
                        .className(
                                ClassNames
                                        .start("v-Button")
                                        .add(this.props.type().toString())
                                        .add("v-Button--hasIcon", this.props.icon() != null)
                                        .add("v-Button--iconOnly", this.props.iconOnly())
                                        .add("is-disabled", isDisabled())
                                        .build()
                        ),
                this.props.icon() != null
                        ? $iconElParent =
                        div(new DOMProps().className("icon"),
                                $iconEl = create(
                                        "i",
                                        new DOMProps()
                                                .className("font-icon " + this.props.icon())))
                        : null,
                !this.props.iconOnly() ? $labelEl = label(new DOMProps().className("label"), this.props.text()) : null);
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.ONMOUSEDOWN, evt -> {
            evt.preventDefault();
            addClassName("is-active");
            DOM.setCapture(getElement());
        });
        addEventListener(Events.ONMOUSEUP, evt -> {
            removeClassName("is-active");
            DOM.releaseCapture(getElement());
        });
        addEventListener(Events.ONMOUSEOVER, evt -> {
            addClassName("is-hover");
            showTooltip();
        });
        addEventListener(Events.ONMOUSEOUT, evt -> {
            removeClassName("is-hover");
            hideTooltip();
        });
        addEventListener(Events.ONCLICK, evt -> fireActions());
        super.addEventListeners();
    }

    public void setText(String text) {
        this.props.text(text);
        if (isRendered() && $labelEl != null) {
            $labelEl.setInnerHtml(text);
        }
    }

    public String getText() {
        return this.props.text();
    }

    public void setIcon(String icon) {
        this.props.icon(icon);
        if (isRendered()) {
            if ($iconEl != null) {
                $iconEl.setClassName(icon);
            } else {
                $iconElParent =
                        div(new DOMProps().className("icon"),
                                $iconEl = create(
                                        "i",
                                        new DOMProps()
                                                .className(this.props.icon())));
                getElement().insertBefore($iconElParent.getElement(), $labelEl.getElement());
            }
        }
    }

    public String getTooltip() {
        return this.props.tooltip();
    }

    public void setTooltip(String tooltip) {
        this.props.tooltip(tooltip);
    }

    public boolean isToggled() {
        return toggleOn;
    }

    public void setToggled(boolean flag) {
        toggleOn = flag;
        if (toggleOn) {
            addClassName("is-active");
        } else {
            removeClassName("is-active");
        }
    }

    private void hideTooltip() {
        if (toolTip != null) {
            if (showToolTipTimer != null) {
                showToolTipTimer.cancel();
            }
            clearToolTipTimer = new Timer() {
                @Override
                public void run() {
                    toolTip.close();
                }
            };
            clearToolTipTimer.schedule(0);
        }
    }

    private void showTooltip() {
        if (clearToolTipTimer != null) {
            clearToolTipTimer.cancel();
        }
        if (toolTip != null && !isDisabled()) {
            Rectangle rect = DOM.getBoundingRectClient(getElement());
            int offsetY = rect.origin.y;
            showToolTipTimer = new Timer() {
                @Override
                public void run() {
                    PopOverPosition position = PopOverPosition.TOP;
                    if (offsetY - 28 < 0) {
                        position = PopOverPosition.BOTTOM;
                    }
                    toolTip.setPosition(position);
                    toolTip.show(Button.this);
                }
            };
            showToolTipTimer.schedule(this.props.tooltipTimeout());
        }
    }

}
