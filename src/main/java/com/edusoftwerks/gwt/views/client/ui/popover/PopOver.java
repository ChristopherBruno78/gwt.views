package com.edusoftwerks.gwt.views.client.ui.popover;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.RootView;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Event;
import elemental2.dom.HTMLElement;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.create;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public abstract class PopOver extends View<PopOverProps> {

    static {
        Theme.get().PopOverCss().ensureInjected();
    }

    private DOMElement callout;
    private DOMElement triangle;

    public PopOver() {
        this(new PopOverProps());
    }

    public PopOver(PopOverProps props) {
        super(props);
        RootView.get().append(this);
        setHidden(true);
    }

    @Override
    protected DOMElement render() {
        return div(
                new DOMProps().className("v-PopOver").attr("role", "dialog"),
                this.renderView(),
                callout = create(
                        "b",
                        new DOMProps().className(getCalloutClassName()),
                        triangle = create("b", new DOMProps().className(getCalloutTriangleClassName()))));
    }

    private String getCalloutClassName() {
        PopOverPosition position = this.props.position();
        PopOverEdge edge = this.props.edge();
        return ClassNames.start("v-PopOver-callout")
                .add("v-PopOver-callout-none", !this.props.isCallout())
                .add("v-PopOver-callout--t", position == PopOverPosition.TOP)
                .add("v-PopOver-callout--b", position == PopOverPosition.BOTTOM)
                .add("v-PopOver-callout--r", position == PopOverPosition.RIGHT)
                .add("v-PopOver-callout--l", position == PopOverPosition.LEFT)
                .add("v-PopOver-callout--top", edge == PopOverEdge.TOP)
                .add("v-PopOver-callout--bottom", edge == PopOverEdge.BOTTOM)
                .add("v-PopOver-callout--right", edge == PopOverEdge.RIGHT)
                .add("v-PopOver-callout--left", edge == PopOverEdge.LEFT)
                .add("v-PopOver-callout--middle", edge == PopOverEdge.MIDDLE)
                .add("v-PopOver-callout--center", edge == PopOverEdge.CENTER)
                .build();
    }

    private String getCalloutTriangleClassName() {
        PopOverPosition position = this.props.position();
        return ClassNames.start("v-PopOver-triangle")
                .add("v-PopOver-triangle-none", !this.props.isCallout())
                .add("v-PopOver-triangle--t", position == PopOverPosition.TOP)
                .add("v-PopOver-triangle--b", position == PopOverPosition.BOTTOM)
                .add("v-PopOver-triangle--r", position == PopOverPosition.RIGHT)
                .add("v-PopOver-triangle--l", position == PopOverPosition.LEFT)
                .build();
    }

    protected abstract DOMElement renderView();

    public void close() {
        setHidden(true);
        Events.fireEvent(Events.CLOSE, this);
    }

    public void open() {
        setHidden(false);
    }

    public void show(View<?> alignWithView) {
        show(alignWithView, 0, 0);
    }

    public void show(View<?> alignWithView, int offsetLeft, int offsetTop) {
        PopOverPosition position = this.props.position();
        PopOverEdge edge = this.props.edge();
        HTMLElement atNode = alignWithView.getElement();
        int atNodeWidth = atNode.offsetWidth + 7, atNodeHeight = atNode.offsetHeight;
        Rectangle rect = DOM.getBoundingRectClient(atNode);
        int left = rect.origin.x;
        int top = rect.origin.y;
        GWT.log("TOP=" + top);
        switch (position) {
            case RIGHT: {
                left += atNodeWidth;
            }
            case LEFT:
                {
                    switch (edge) {
                        case MIDDLE: {
                            atNodeHeight = atNodeHeight >> 1;
                        }
                        case BOTTOM: {
                            top += atNodeHeight;
                            break;
                        }
                    }
                }
                break;
            case BOTTOM: {
                top += atNodeHeight;
            }
            case TOP: {
                switch (edge) {
                    case CENTER: {
                        atNodeWidth = atNodeWidth >> 1;
                    }
                    case RIGHT:
                        {
                            left += atNodeWidth;
                        }
                        break;
                }
            }
        }
        top += offsetTop;
        left += offsetLeft;
        HTMLElement $layer = getElement();
        $layer.style.left = left + "px";
        $layer.style.top = top + "px";
        int deltaLeft = 0, deltaTop = 0;
        // Adjust positioning
        switch (position) {
            case LEFT: {
                deltaLeft -= $layer.offsetWidth;
                deltaLeft -= 8;
            }
            case RIGHT:
                {
                    switch (edge) {
                        case MIDDLE:
                            {
                                deltaTop -= $layer.offsetHeight >> 1;
                            }
                            break;
                        case BOTTOM:
                            {
                                deltaTop -= $layer.offsetHeight;
                            }
                            break;
                    }
                }
                break;
            case TOP: {
                deltaTop -= ($layer.offsetHeight);
                deltaTop -= 18;
            }
            case BOTTOM: {
                deltaTop -= 3;
                switch (edge) {
                    case CENTER:
                        {
                            deltaLeft -= $layer.offsetWidth >> 1;
                        }
                        break;
                    case RIGHT:
                        {
                            deltaLeft -= $layer.offsetWidth;
                        }
                        break;
                }
            }
        }
        adjustPosition(left, top, deltaLeft, deltaTop);
        HTMLElement $triangle = triangle.getElement();
        $triangle.style.backgroundColor = this.props.backgroundColor().toHexString();
        $triangle.style.borderColor = this.props.borderColor().toHexString();
        $layer.style.backgroundColor = this.props.backgroundColor().toHexString();
        open();
    }

    private void adjustPosition(int left, int top, int deltaLeft, int deltaTop) {
        HTMLElement $el = getElement();
        int calloutDelta = 0;
        PopOverPosition position = this.props.position();
        boolean calloutIsAtTopOrBottom = position == PopOverPosition.TOP || position == PopOverPosition.BOTTOM;
        int gap = RootView.get().getWidth() - left - deltaLeft - getWidth();
        // check right edge
        if (gap < 0) {
            deltaLeft += gap;
            deltaLeft -= 6;
            if (this.props.isCallout() && calloutIsAtTopOrBottom) {
                calloutDelta += gap;
                calloutDelta -= 6;
            }
        }
        // check left edge
        gap = left + deltaLeft;
        if (gap < 0) {
            deltaLeft -= gap;
            deltaLeft += 7;
            if (this.props.isCallout() && calloutIsAtTopOrBottom) {
                calloutDelta -= (gap + 1);
            }
        }
        // check bottom edge
        gap = RootView.get().getHeight() - top - deltaTop - getHeight();
        if (gap < 0) {
            deltaTop += gap;
            deltaTop -= 7;
            if (this.props.isCallout() && !calloutIsAtTopOrBottom) {
                calloutDelta += gap;
                calloutDelta -= 5;
            }
        }
        // check top edge
        gap = top + deltaTop;
        if (gap < 0) {
            deltaTop -= gap;
            deltaTop += 7;
            if (this.props.isCallout() && !calloutIsAtTopOrBottom) {
                calloutDelta -= gap;
                calloutDelta += 5;
            }
        }
        top += deltaTop;
        left += deltaLeft;
        $el.style.top = top + "px";
        $el.style.left = left + "px";
        HTMLElement $callout = callout.getElement();
        $callout.removeAttribute("style");
        if (this.props.isCallout() && calloutDelta != 0) {
            if (calloutIsAtTopOrBottom) {
                $callout.style.left = ($callout.offsetLeft - calloutDelta) + "px";
            } else {
                $callout.style.top = ($callout.offsetTop - calloutDelta) + "px";
            }
        }
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.MOUSEDOWN, Event::stopPropagation);
        if (this.props.isTransient()) {
            RootView.get().addEventListener(Events.MOUSEDOWN, evt -> close());
        }
    }

    public PopOverPosition getPosition() {
        return this.props.position();
    }

    public void setPosition(PopOverPosition position) {
        this.props.position(position);
        if (callout != null) {
            callout.setClassName(getCalloutClassName());
        }
        if (triangle != null) {
            triangle.setClassName(getCalloutTriangleClassName());
        }
    }

    public PopOverEdge getEdge() {
        return this.props.edge();
    }

    public void setEdge(PopOverEdge edge) {
        this.props.edge(edge);
        if (callout != null) {
            callout.setClassName(getCalloutClassName());
        }
    }
}
