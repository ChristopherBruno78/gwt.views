package com.edusoftwerks.gwt.views.client.ui;

public class DOMFactory {
    /**
     * STATIC METHODS
     */
    public static DOMElement create (String tagName, DOMProps props, String innerHtml) {
        DOMElement domElement = create(tagName, props);
        domElement.setInnerHtml(innerHtml);
        return domElement;
    }

    public static DOMElement create (String tagName, UIObject<?, ?>... children) {
        return create(tagName, new DOMProps(), children);
    }

    public static DOMElement create (String tagName, DOMProps props, UIObject<?, ?>... children) {

        int len = children.length,
                i = 0;
        DOMElement domElement = new DOMElement(tagName, props);
        for (; i < len; i++) {
            UIObject<?, ?> child = children[i];
            //add UIObject to the root dom element
            if( child != null ) {
                domElement.append(child);
            }
        }
        return domElement;
    }

    public static DOMElement div () {
        return new DOMElement("div", null);
    }

    public static DOMElement div (String innerHtml) {
        DOMElement el = div();
        el.setInnerHtml(innerHtml);
        return el;
    }

    public static DOMElement div (UIObject<?, ?>... uiObjects) {
        return create("div", new DOMProps(), uiObjects);
    }
}
