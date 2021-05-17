package com.edusoftwerks.gwt.views.client.ui.dialog;

import com.edusoftwerks.gwt.views.client.dom.*;
import com.edusoftwerks.gwt.views.client.theme.Theme;
import com.edusoftwerks.gwt.views.client.ui.Draggable;
import com.edusoftwerks.gwt.views.client.ui.Resizable;
import com.edusoftwerks.gwt.views.client.ui.RootView;
import com.edusoftwerks.gwt.views.client.ui.View;
import com.edusoftwerks.gwt.views.client.ui.button.Button;
import com.edusoftwerks.gwt.views.client.ui.button.ButtonProps;
import com.edusoftwerks.gwt.views.shared.geometry.Size;
import com.google.gwt.core.client.GWT;
import elemental2.dom.Event;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;
import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.label;

public abstract class Dialog extends View<DialogProps> {

    private DOMElement titleBar;
    private DOMElement titleLabel;
    private DOMElement modalMask;
    private Button closeBtn;
    private HTMLElement lastFocus;
    private static Dialog TOP_DIALOG = null;

    static {
        Theme.get().DialogCss().ensureInjected();
    }

    public Dialog(DialogProps props) {
        super(props);
    }

    public abstract DOMElement renderContent();

    public DOMElement renderTools() {
        return div(new DOMProps().className("v-Dialog-tools"),
                this.props.closable() ?
                        closeBtn = new Button(new ButtonProps()
                                .className("v-Dialog-toolBtn v-Dialog-close")
                                .iconOnly(true)
                                .icon("icon-times-solid")
                        ) : null
        );
    }

    @Override
    protected DOMElement render() {
        return div(new DOMProps()
                        .className("v-Dialog")
                        .attr("role", "dialog")
                        .style("width", this.props.width() + "px")
                        .style("height", this.props.height() + "px"),
                titleBar = div(new DOMProps()
                                .className(ClassNames
                                        .start("v-Dialog-titlebar")
                                        .add("v-draggable", this.props.draggable())
                                        .build()),
                        this.renderTools(),
                        titleLabel = label(new DOMProps().className("v-Dialog-title"), this.props.title())
                ),
                div(new DOMProps().className("v-Dialog-content"), this.renderContent())
        );
    }

    @Override
    protected void addEventListeners() {
        if (closeBtn != null) {
            closeBtn.addActionListener(new EventListener() {
                @Override
                public void handleEvent(Event evt) {
                    close();
                }
            });
        }

    }

    public void open() {
        setHidden(true);
        initModal();
        initDraggable();
        initResizable();
        TOP_DIALOG = this;
        setHidden(false);
    }

    public void close() {
        removeModal();
        Events.fireEvent(Events.ONCLOSE, getElement());
    }

    private void initModal() {
        if (this.props.modal()) {
            modalMask = div(new DOMProps().
                    className("v-Dialog-modalMask")
                    .attr("tabIndex", 0)
            );
            lastFocus = DOM.getFocus();
            modalMask.append(this);
            RootView.get().insert(0, modalMask);

        } else {
            RootView.get().append(this);
        }
    }

    private void removeModal() {
        if (this.props.modal()) {
            GWT.log("removing modal");
            modalMask.remove(this);
            RootView.get().remove(modalMask);
            modalMask = null;
            if (lastFocus != null) {
                lastFocus.focus();
            }
        } else {
            RootView.get().remove(this);
        }
    }

    private void initDraggable() {
        if (this.props.draggable()) {
            Draggable.makeDraggable(this, titleBar, true);
            addEventListener(Events.ONDRAGFINISH, evt -> {
                HTMLElement $el = getElement();
                props.x($el.offsetLeft);
                props.y($el.offsetTop);
            });
        }
    }

    private void initResizable() {
        if (this.props.resizable()) {
            Resizable.makeResizable(this, new Size(50, 50), new Size(2000, 2000));
            addEventListener(Events.ONRESIZEFINISH, new EventListener() {
                @Override
                public void handleEvent(Event evt) {
                    HTMLElement $el = getElement();
                    props.width($el.offsetWidth);
                    props.height($el.offsetHeight);
                }
            });
        }
    }

}
