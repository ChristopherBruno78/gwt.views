package com.edusoftwerks.gwt.views.client.ui.toast;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.google.gwt.user.client.Timer;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

/**
 * Toast is a UI component for displaying information in an area
 * of the application
 * <p>
 * Custom Events: CLOSE, ACTION
 */
public abstract class Toast extends View<ToastProps> {

    private Button actionBtn = null, closeBtn = null;
    private Timer lifeSpanTimer;

    public Toast(ToastProps props) {
        super(props);
    }

    protected abstract DOMElement renderContent();

    private Button renderAction() {
        if (this.props.actionText() != null) {
            return new Button(
                    new ButtonProps()
                            .text(this.props.actionText())
            );
        }
        return null;
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps().className(
                ClassNames.start("v-Toast")
                        .add("intent", this.props.intent() != ToastIntent.DEFAULT)
                        .add(this.props.intent().toString())
                        .add("v-Toast--hasIcon", this.props.icon() != null)
                        .build()
                ).attr("role", "dialog"),
                this.props.icon() != null ? DOMFactory.create("i", new DOMProps()
                        .className(ClassNames.start("icon font-icon")
                                .add(this.props.icon())
                                .build()
                        )) : null,
                div(new DOMProps().className("v-Toast-content"),
                        div(new DOMProps().className("message"),
                                this.renderContent()
                        ),
                        div(new DOMProps().className("action"),
                                actionBtn = this.renderAction()
                        )
                ),
                div(new DOMProps().className("v-divider")),
                closeBtn = new Button(new ButtonProps()
                        .className("close")
                        .icon("icon-times-solid")
                        .iconOnly(true)
                )
        );
    }

    @Override
    protected void addEventListeners() {
        if (actionBtn != null) {
            actionBtn.addEventListener(Events.ACTION, evt -> Events.fireEvent(Events.ACTION, this));
        }
        if (closeBtn != null) {
            closeBtn.addEventListener(Events.ACTION, evt -> close());
        }
        super.addEventListeners();
    }

    @Override
    public void didEnterDocument() {
        lifeSpanTimer = new Timer() {
            @Override
            public void run() {
                close();
            }
        };
        lifeSpanTimer.schedule(this.props.lifespan() * 1000);
    }

    public void close() {
        removeClassName(ToasterAnimationState.APPEAR.toString());
        addClassName(ToasterAnimationState.EXIT.toString());
        Timer t = new Timer() {
            @Override
            public void run() {
                removeClassName(ToasterAnimationState.EXIT.toString());
                getParent().removeFromParent();
                Events.fireEvent(Events.CLOSE, Toast.this);
            }
        };
        t.schedule(400);
        if (lifeSpanTimer != null) {
            lifeSpanTimer.cancel();
        }
    }

}
