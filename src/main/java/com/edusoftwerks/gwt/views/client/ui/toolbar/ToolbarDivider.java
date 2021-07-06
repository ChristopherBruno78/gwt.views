package com.edusoftwerks.gwt.views.client.ui.toolbar;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.dom.DOMElement;
import com.edusoftwerks.gwt.views.client.dom.DOMProps;
import com.edusoftwerks.gwt.views.client.ui.View;

import static com.edusoftwerks.gwt.views.client.dom.DOMFactory.div;

public class ToolbarDivider extends View<DOMProps> {

    public ToolbarDivider() {
        super(new DOMProps().className("v-Toolbar-divider"));
    }

    @Override
    protected DOMElement render() {
        return div(this.props);
    }
}
