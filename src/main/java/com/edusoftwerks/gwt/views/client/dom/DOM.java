package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import elemental2.dom.*;

/**
 * Extends the capabilities of the standard GWT DOM object
 */
public class DOM {

    /**
     * Checks if an element has focus
     *
     * @param element the element to check
     * @return true if the element has focus, false otherwise
     */
    public static native boolean hasFocus (HTMLElement element) /*-{
        return element.ownerDocument.activeElement === element;
    }-*/;

    /**
     * Gets the document's activeElement
     *
     * @param element any element inside the document
     * @return an Element object that is the activeElement in the document
     */
    public static native HTMLElement getFocus () /*-{
        return document.activeElement;
    }-*/;

    /**
     * Creates a comment in the DOM
     *
     * @param comment
     * @return returns the comment DOM element
     */
    public static Comment createComment (String comment) {
        return DomGlobal.document.createComment(comment);
    }

    public static native boolean isDisplayed (HTMLElement element) /*-{
        return element.offsetParent !== null;
    }-*/;

    private static native DOMRect rectClient (HTMLElement element) /*-{
        return element.getBoundingClientRect();
    }-*/;

    private static int ROUND(double x) {
        return (int) Math.round(x);
    }

    public static Rectangle getBoundingRectClient (HTMLElement element) {
        DOMRect obj = rectClient(element);
        return new Rectangle(ROUND(obj.x),ROUND(obj.y),ROUND(obj.width), ROUND(obj.height));
    }

    public static boolean isVisible (HTMLElement element) {
        if( element != null ) {
            return !(element.style.getPropertyValue("visibility")
                    .equalsIgnoreCase("hidden") ||
                    !isDisplayed(element));
        }
        return false;
    }

    public static boolean isTabbable (HTMLElement element) {

        if( DOM.isVisible(element) ) {
            if( element.hasAttribute("tabIndex") ) {
                if( Integer.parseInt(element.getAttribute("tabIndex")) > -1 ) {
                    return true;
                }
            }
            String tagName = element.tagName
                    .toLowerCase();
            return tagName.equals("button") || tagName.equals("input") || tagName.equals("select") || tagName.equals("textarea");
        }
        return false;
    }

    public static HTMLElement getLastTabbableElement (HTMLElement element) {

        int count = element.childElementCount,
                i = count - 1;
        for (; i >= 0; i--) {
            HTMLElement child = (HTMLElement) element.childNodes.getAt(i);
            if( DOM.isTabbable(child) ) {
                return child;
            }
            HTMLElement t = DOM.getLastTabbableElement(child);
            if( t != null ) {
                return t;
            }
        }
        return null;
    }

    public static HTMLElement getFirstTabbableElement (HTMLElement element) {

        int count = element.childElementCount,
                i = 0;
        for (; i < count; i++) {
            HTMLElement child = (HTMLElement) element.childNodes.getAt(i);
            if( DOM.isTabbable(child) ) {
                return child;
            }
            HTMLElement t = DOM.getFirstTabbableElement(child);
            if( t != null ) {
                return t;
            }
        }
        return null;
    }

    public static native int getOuterHeight (HTMLElement $el)/*-{
        var style = $wnd.getComputedStyle($el),
            tm = parseInt(style.marginTop, 10),
            bm = parseInt(style.marginBottom, 10);

        return tm + $el.offsetHeight + bm;

    }-*/;

    public static native int getOuterWidth (HTMLElement $el)/*-{
        var style = $wnd.getComputedStyle($el),
            lm = parseInt(style.marginLeft, 10),
            rm = parseInt(style.marginRight, 10);
        return lm + $el.offsetWidth + rm;
    }-*/;

    public static native String getValue (HTMLElement el) /*-{
        return el.value;
    }-*/;

    public static native String setValue (HTMLElement el, String value) /*-{
        el.value = value;
    }-*/;

    public static HTMLMetaElement getMetaTagWithName (String name) {

        NodeList<Element> metaElementList = DomGlobal.document.getElementsByTagName("meta");
        for (int i = 0; i < metaElementList.getLength(); i++) {
            HTMLMetaElement metaElement = (HTMLMetaElement) metaElementList.getAt(i);
            if( metaElement.name
                    .equals(name) ) {
                return metaElement;
            }
        }
        return null;
    }

    public native static int getBrowserScrollbarWidth () /*-{
        var inner = $wnd.document.createElement('p');
        inner.style.width = "100%";
        inner.style.height = "200px";

        var outer = $wnd.document.createElement('div');
        outer.style.position = "absolute";
        outer.style.top = "0px";
        outer.style.left = "0px";
        outer.style.visibility = "hidden";
        outer.style.width = "200px";
        outer.style.height = "150px";
        outer.style.overflow = "hidden";
        outer.appendChild(inner);

        $wnd.document.body.appendChild(outer);
        var w1 = inner.offsetWidth;
        outer.style.overflow = 'scroll';
        var w2 = inner.offsetWidth;

        if (w1 === w2) {
            w2 = outer.clientWidth;
        }

        $wnd.document.body.removeChild(outer);
        return (w1 - w2) + 1;
    }-*/;
}
