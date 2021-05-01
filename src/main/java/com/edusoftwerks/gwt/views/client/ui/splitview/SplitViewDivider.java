package com.edusoftwerks.gwt.views.client.ui.splitview;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.ui.RootView;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.shared.geometry.Point;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;
import elemental2.dom.MouseEvent;
import jsinterop.base.Js;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

class SplitViewDivider extends View<SplitViewProps> {

    private boolean mouseDown = false;
    private int offset;
    private Point start;
    private final Layout layout = new Layout();
    private final SplitView splitView;

    public SplitViewDivider(SplitView splitView) {
        super(splitView.getProps());
        this.splitView = splitView;
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps().className(
                ClassNames.start("v-SplitDivider")
                        .add("static", this.props.maxStaticPaneLength().equals(this.props.minStaticPaneLength()))
                        .add("vertical", this.props.orientation() == SplitViewOrientation.VERTICAL)
                        .add("horizontal", this.props.orientation() == SplitViewOrientation.HORIZONTAL)
                        .build()
        ));
    }

    @Override
    protected void addEventListeners() {
        addEventListener(Events.ONMOUSEDOWN, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                evt.stopPropagation();
                evt.preventDefault();
                MouseEvent mouseEvent = Js.cast(evt);
                mouseDown = true;
                offset = getDividerOffset();
                start = new Point((int) mouseEvent.clientX, (int) mouseEvent.clientY);
                RootView.get().setStyleAttribute("cursor", (SplitViewDivider.this.props.orientation() == SplitViewOrientation.VERTICAL) ? "ew-resize" : "ns-resize");
                DOM.setCapture(getElement());
//
            }
        });
        addEventListener(Events.ONMOUSEUP, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                mouseDown = false;
                DOM.releaseCapture(getElement());
                RootView.get().setStyleAttribute("cursor", "default");
            }
        });
        addEventListener(Events.ONMOUSEMOVE, new EventListener() {
            @Override
            public void handleEvent(Event evt) {
                if (mouseDown) {
                    HTMLElement $parent = getParent().getElement();
                    int sign = props.flex() == SplitViewFlex.TOP_LEFT ? -1 : 1;
                    SplitViewOrientation orientation = props.orientation();
                    MouseEvent mouseEvent = Js.cast(evt);
                    int delta = (orientation == SplitViewOrientation.VERTICAL) ? (int) (mouseEvent.clientX - start.x) : (int) (mouseEvent.clientY - start.y);
                    int newOffset = Math.max(props.minStaticPaneLength(),
                            Math.min(props.maxStaticPaneLength(), offset + sign * delta));
                    newOffset = Math.min(Math.max(newOffset, 0),
                            (orientation == SplitViewOrientation.VERTICAL) ?
                                    $parent.clientWidth :
                                    $parent.clientHeight
                    );
                    setDividerOffset(newOffset);
                    splitView.setStaticPaneLength(newOffset);
                    splitView.layoutViews();
                    splitView.leftTopView.onResize();
                    splitView.bottomRightView.onResize();
                }

            }
        });

    }

    void layout() {
        int thickness = this.props.dividerThickness();
        if (this.props.orientation() == SplitViewOrientation.VERTICAL) {
            layout.top = 0;
            layout.bottom = 0;
            layout.width = thickness;
        } else {
            layout.left = 0;
            layout.right = 0;
            layout.height = thickness;
        }
        SplitView.setLayout(getElement(), layout);
    }

    int getDividerOffset() {
        SplitViewFlex flex = this.props.flex();
        SplitViewOrientation orientation = this.props.orientation();
        if (orientation == SplitViewOrientation.VERTICAL) {
            return (flex == SplitViewFlex.TOP_LEFT) ? layout.right : layout.left;
        } else {
            return (flex == SplitViewFlex.TOP_LEFT) ? layout.bottom : layout.top;
        }
    }

    void setDividerOffset(double offset) {
        SplitViewFlex flex = this.props.flex();
        SplitViewOrientation orientation = this.props.orientation();
        if (orientation == SplitViewOrientation.VERTICAL) {
            layout.left = (int) ((flex == SplitViewFlex.TOP_LEFT) ? -1 : offset);
            layout.right = (int) ((flex == SplitViewFlex.TOP_LEFT) ? offset : -1);
        } else {
            layout.top = (int) ((flex == SplitViewFlex.TOP_LEFT) ? -1 : offset);
            layout.bottom = (int) ((flex == SplitViewFlex.TOP_LEFT) ? offset : -1);
        }
        SplitView.setLayout(getElement(), layout);
    }

}