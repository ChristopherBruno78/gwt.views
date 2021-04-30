package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.client.UIObject;
import elemental2.dom.HTMLElement;

public class DOMFactory {

    /**
     * STATIC METHODS
     */
    static DOMElement create(HTMLElement el, DOMProps props) {
        return new DOMElement(el, props);
    }

    public static DOMElement create(String tagName, DOMProps props, String innerHtml) {
        DOMElement domElement = create(tagName, props);
        domElement.setInnerHtml(innerHtml);
        return domElement;
    }

    public static DOMElement create(String tagName, UIObject... children) {
        return create(tagName, new DOMProps(), children);
    }

    public static DOMElement create(String tagName, DOMProps props, UIObject... children) {
        int len = children.length,
                i = 0;
        DOMElement domElement = new DOMElement(tagName, props);
        for (; i < len; i++) {
            UIObject child = children[ i ];
            //add UIObject to the root dom element
            if (child != null) {
                domElement.append(child);
            }
        }
        return domElement;
    }

    public static DOMElement div() {
        return new DOMElement("div", null);
    }

    public static DOMElement div(DOMProps props) {
        return new DOMElement("div", props);
    }

    public static DOMElement div(String innerHtml) {
        DOMElement el = div();
        el.setInnerHtml(innerHtml);
        return el;
    }

    public static DOMElement div(DOMProps props, UIObject... uiObjects) {
        return create("div", props, uiObjects);
    }

    public static DOMElement div(UIObject... uiObjects) {
        return create("div", new DOMProps(), uiObjects);
    }

    public static DOMElement label(DOMProps props, String innerText) {
        DOMElement e = new DOMElement("label", props);
        e.setInnerHtml(innerText);
        return e;
    }

    public static DOMElement label(String innerText) {
        return label(new DOMProps(), innerText);
    }

    public static DOMElement span(DOMProps props, UIObject... uiObjects) {
        DOMElement e = new DOMElement("span", props);
        e.append(uiObjects);
        return e;
    }

}