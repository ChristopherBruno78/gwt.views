package com.edusoftwerks.gwt.views.client.ui.splitview;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.google.gwt.core.client.GWT;
import elemental2.dom.HTMLElement;

public abstract class SplitView extends View<SplitViewProps> {

    private static final int ONE_HUNDRED_PERCENT = -2;
    static int splitViewID = 0;

    static {
        Theme.get().SplitViewCss().ensureInjected();
    }

    private final SplitViewDivider divider;
    View<?> leftTopView;
    View<?> bottomRightView;
    private int staticPaneLength;

    public SplitView(SplitViewProps props) {
        super(props);
        staticPaneLength = props.staticPaneLength();
        divider = new SplitViewDivider(this);
    }

    static void setLayout(HTMLElement $el, Layout layout) {
        $el.style.position = "absolute";
        if (layout.left > -1) {
            $el.style.left = (layout.left + "px");
        } else {
            $el.style.removeProperty("left");
        }
        if (layout.right > -1) {
            $el.style.right = (layout.right + "px");
        } else {
            $el.style.removeProperty("right");
        }
        if (layout.top > -1) {
            $el.style.top = (layout.top + "px");
        } else {
            $el.style.removeProperty("top");
        }
        if (layout.bottom > -1) {
            $el.style.bottom = (layout.bottom + "px");
        } else {
            $el.style.removeProperty("bottom");
        }
        if (layout.width > -1) {
            $el.style.setProperty("width", layout.width + "px");
        } else {
            if (layout.width == ONE_HUNDRED_PERCENT) {
                $el.style.setProperty("width", "100%");
            } else {
                $el.style.removeProperty("width");
            }
        }
        if (layout.height > -1) {
            $el.style.setProperty("height", layout.height + "px");
        } else {
            if (layout.height == ONE_HUNDRED_PERCENT) {
                $el.style.setProperty("height", "100%");
            } else {
                $el.style.removeProperty("height");
            }
        }
    }

    protected abstract View<?> renderTopLeft();

    protected abstract View<?> renderBottomRight();

    public int getStaticPaneLength() {
        return staticPaneLength;
    }

    public void setStaticPaneLength(int l) {
        staticPaneLength = l;
    }

    void layoutViews() {
        append(renderTopLeft(), renderBottomRight());
        SplitViewOrientation orientation = this.props.orientation();
        SplitViewFlex flex = this.props.flex();
        if (leftTopView != null) {
            if (leftTopView.getParent() == null) {
                getParent().append(leftTopView);
            }
            leftTopView.setStyleAttribute("overflow", "auto");
            Layout l = new Layout();
            l.left = 0;
            l.top = 0;
            l.right = (orientation == SplitViewOrientation.VERTICAL && flex == SplitViewFlex.TOP_LEFT)
                    ? staticPaneLength
                    : -1;
            l.bottom = (orientation == SplitViewOrientation.HORIZONTAL && flex == SplitViewFlex.TOP_LEFT)
                    ? staticPaneLength
                    : -1;
            l.width = (orientation == SplitViewOrientation.HORIZONTAL)
                    ? ONE_HUNDRED_PERCENT
                    : (flex == SplitViewFlex.TOP_LEFT ? -1 : staticPaneLength);
            l.height = (orientation == SplitViewOrientation.VERTICAL)
                    ? ONE_HUNDRED_PERCENT
                    : (flex == SplitViewFlex.TOP_LEFT ? -1 : staticPaneLength);
            SplitView.setLayout(leftTopView.getElement(), l);
        }
        if (divider.getParent() == null) {
            getParent().append(divider);
        }
        if (bottomRightView != null) {
            if (bottomRightView.getParent() == null) {
                getParent().append(bottomRightView);
            }
            bottomRightView.setStyleAttribute("overflow", "auto");
            Layout l = new Layout();
            l.left = (orientation == SplitViewOrientation.VERTICAL && flex == SplitViewFlex.BOTTOM_RIGHT)
                    ? staticPaneLength
                    : -1;
            l.top = (orientation == SplitViewOrientation.HORIZONTAL && flex == SplitViewFlex.BOTTOM_RIGHT)
                    ? staticPaneLength
                    : -1;
            l.right = 0;
            l.bottom = 0;
            l.width = (orientation == SplitViewOrientation.HORIZONTAL)
                    ? ONE_HUNDRED_PERCENT
                    : (flex == SplitViewFlex.BOTTOM_RIGHT ? -1 : staticPaneLength);
            l.height = (orientation == SplitViewOrientation.VERTICAL)
                    ? ONE_HUNDRED_PERCENT
                    : (flex == SplitViewFlex.BOTTOM_RIGHT ? -1 : staticPaneLength);
            SplitView.setLayout(bottomRightView.getElement(), l);
        }
    }

    private void layout() {
        layoutViews();
        if (divider != null) {
            divider.setDividerOffset(staticPaneLength);
            divider.layout();
        }
    }

    @Override
    public void didEnterDocument() {
        DOM.insertComment("SplitView-" + ++splitViewID, parent.getElement());
        layout();
        addEventListeners();
    }

    @Override
    public void insert(int index, UIObject view) {
        if (view instanceof View<?>) {
            if (leftTopView == null) {
                leftTopView = (View<?>) view;
                leftTopView.performRender();
                return;
            }
            if (bottomRightView == null) {
                bottomRightView = (View<?>) view;
                bottomRightView.performRender();
            }
        }
    }

    @Override
    public void append(UIObject... views) {
        int l = views.length, i = 0;
        for (; i < l; i++) {
            insert(i, views[i]);
        }
    }

    @Override
    protected DOMElement render() {
        return null;
    }
}
