package com.edusoftwerks.gwt.views.client.ui.toast;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.ClassNames;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.RootView;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.google.gwt.user.client.Timer;

import java.util.List;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.span;

public class Toaster extends View<ToasterProps> {

    static {
        Theme.get().ToastCss().ensureInjected();
    }

    private ToasterAnimationState animationState;

    public Toaster(ToasterPosition position) {
        this(new ToasterProps().position(position));
    }

    public Toaster(ToasterProps props) {
        super(props);
        RootView.get().append(this);
    }

    @Override
    protected DOMElement render() {
        ToasterPosition p = this.props.position();
        return div(new DOMProps()
                .className(
                        ClassNames.start("v-Toast-container")
                                .add("top", p == ToasterPosition.TOP_LEFT || p == ToasterPosition.TOP_CENTER || p == ToasterPosition.TOP_RIGHT)
                                .add("left", p == ToasterPosition.TOP_LEFT || p == ToasterPosition.BOTTOM_LEFT || p == ToasterPosition.TOP_CENTER || p == ToasterPosition.BOTTOM_CENTER)
                                .add("right", p == ToasterPosition.TOP_RIGHT || p == ToasterPosition.BOTTOM_RIGHT || p == ToasterPosition.TOP_CENTER || p == ToasterPosition.BOTTOM_CENTER)
                                .add("bottom", p == ToasterPosition.BOTTOM_LEFT || p == ToasterPosition.BOTTOM_CENTER || p == ToasterPosition.BOTTOM_RIGHT)
                                .build()
                ));
    }

    public void toast(Toast view) {
        ToasterPosition p = this.props.position();
        View<DOMProps> sp = new View<DOMProps>() {
            @Override
            protected DOMElement render() {
                return span("");
            }
        };
        if (p == ToasterPosition.TOP_LEFT || p == ToasterPosition.TOP_CENTER || p == ToasterPosition.TOP_RIGHT) {
            insert(0, sp);
        } else {
            append(sp);
        }
        sp.append(view);
        setAnimationState(ToasterAnimationState.ENTER);
        final Timer t = new Timer() {
            @Override
            public void run() {
                setAnimationState(ToasterAnimationState.APPEAR);
            }
        };
        t.schedule(25);
    }

    public void setPosition(ToasterPosition position) {
        this.props.position(position);
        if (isRendered()) {
            setClassName(
                    ClassNames.start("v-Toast-container")
                            .add("top", position == ToasterPosition.TOP_LEFT || position == ToasterPosition.TOP_CENTER || position == ToasterPosition.TOP_RIGHT)
                            .add("left", position == ToasterPosition.TOP_LEFT || position == ToasterPosition.BOTTOM_LEFT || position == ToasterPosition.TOP_CENTER || position == ToasterPosition.BOTTOM_CENTER)
                            .add("right", position == ToasterPosition.TOP_RIGHT || position == ToasterPosition.BOTTOM_RIGHT || position == ToasterPosition.TOP_CENTER || position == ToasterPosition.BOTTOM_CENTER)
                            .add("bottom", position == ToasterPosition.BOTTOM_LEFT || position == ToasterPosition.BOTTOM_CENTER || position == ToasterPosition.BOTTOM_RIGHT)
                            .build()
            );
        }
    }

    public ToasterPosition getPosition() {
        return this.props.position();
    }

    void setAnimationState(ToasterAnimationState animationState) {
        List<UIObject> children = getChildren();
        for (UIObject child : children) {
            List<UIObject> toasts = child.getChildren();
            if (toasts.size() > 0) {
                UIObject first = child.getChildren().get(0);
                if (first != null) {
                    if (this.animationState != null) {
                        first.removeClassName(this.animationState.toString());
                    }
                    if (animationState != null) {
                        first.addClassName(animationState.toString());
                    }
                }
            }
        }
        this.animationState = animationState;
    }

}
