package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.ClassNames;
import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import elemental2.dom.Element;
import elemental2.dom.HTMLElement;

import java.util.Iterator;
import java.util.List;

/**
 * Base class of anything visible in a user interface. Every UIObject is backed
 * by an associated DOM Element.
 * <p>
 * Must be given a Props generic type T which extends Abstract Props
 * and the type U which is the type of children allowed to be appended to
 * the UIObject.
 * <p>
 * For example:
 * <pre>
 * {@code
 * public class MyView extends UIObject<MyViewProps, UIObject> {
 *     //code here
 * }
 * }
 * </pre>
 */

public abstract class UIObject<T extends UIProps<T>, U extends UIObject<?, ?>> {

    /**
     * Returns the low-level Element that represents this View in the DOM
     *
     * @return the DOM Element
     */
    public abstract HTMLElement getElement ();

    /**
     * Returns the UIObject's parent, if it exists
     *
     * @return UIObject parent
     */
    public abstract UIObject<?, ?> getParent ();

    /**
     * Sets the View parent
     *
     * @param parent
     */
    protected abstract void setParent (UIObject<?, ?> parent);

    /**
     * Returns a list of all the View's children
     * this View is the parent of the children
     *
     * @return List of Views
     */
    public abstract List<UIObject<?, ?>> getChildren ();

    /**
     * Gets the Props of the View
     *
     * @return
     */
    public abstract T getProps ();

    /**
     * Called once when the View enters the DOM
     */
    public abstract void didEnterDocument ();

    /**
     * Called once when the View leaves the DOM
     */
    public abstract void didLeaveDocument ();

    void performRender () {
    }

    /**
     * Adds className to the UIObject
     *
     * @param name
     */
    public void addClassName (String name) {
        if( name != null ) {
            T props = getProps();
            if( props != null ) {
                props.className(ClassNames.start(props.className())
                        .add(name)
                        .build());
            }

            if( getElement() != null ) {
                getElement().classList.add(name);
            }
        }
    }

    /**
     * sets/replaces the className of UIObject
     *
     * @param name
     */

    public void setClassName (String name) {
        T props = getProps();
        if( props != null ) {
            if( name != null ) {
                props.className(name);
            }
        }
        if( getElement() != null ) {
            getElement().className = name;
        }
    }

    /**
     * Removes ${name} from the className of the UIObject
     *
     * @param name
     */
    public void removeClassName (String name) {
        T props = getProps();
        if( props != null ) {
            String className = props.className();
            if( className != null ) {
                className = className.replace(name, "");
                props.className(className);
            }
        }
        if( getElement() != null ) {
            getElement().classList.remove(name);
        }
    }

    public String getInnerHtml () {
        if( getElement() != null ) {
            return getElement().innerHTML;
        }

        return null;
    }

    /**
     * Sets the inner HTML of the UI Object
     *
     * @param innerHtml
     */
    public void setInnerHtml (String innerHtml) {
        if( getElement() != null ) {
            getElement().innerHTML = innerHtml;
        }
    }

    /**
     * sets an attribute key of the element to value. Setting to null
     * removes the attribute
     *
     * @param key
     * @param value
     */
    public void setAttribute (String key, Object value) {
        T props = getProps();
        if( props != null ) {
            props.attr(key, value);
        }
        if( getElement() != null ) {
            if( value != null ) {
                getElement().setAttribute(key, value.toString());
            } else {
                removeAttribute(key);
            }
        }
    }

    /**
     * Sets a style attribute key of the element to value. Setting to null
     * removes the attribute.
     *
     * @param key
     * @param value
     */
    public void setStyleAttribute (String key, Object value) {
        T props = getProps();
        if( props != null ) {
            props.style(key, value);
        }
        HTMLElement el = getElement();
        if( el != null ) {
            if( value != null ) {
                el.style.set(key, value.toString());
            } else {
                el.style.removeAttribute(key);
            }
        }
    }

    /**
     * Removes an attribute
     *
     * @param key
     */
    public void removeAttribute (String key) {
        T props = getProps();
        if( props != null ) {
            props.attributes.remove(key);
        }
        if( getElement() != null ) {
            getElement().removeAttribute(key);
        }
    }

    /**
     * removes a style attribute
     *
     * @param key
     */
    public void removeStyleAttribute (String key) {
        T props = getProps();
        if( props != null ) {
            props.styles.remove(key);
        }
        HTMLElement el = getElement();
        if( el != null ) {
            el.style.removeAttribute(key);
        }
    }

    void fireDidEnterDocument () {
        didEnterDocument();
        List<UIObject<?, ?>> children = getChildren();
        for (UIObject<?, ?> uiObject : children) {
            uiObject.fireDidEnterDocument();
        }
    }

    void fireDidLeaveDocument () {
        didLeaveDocument();
        List<UIObject<?, ?>> children = getChildren();
        for (UIObject<?, ?> uiObject : children) {
            uiObject.fireDidLeaveDocument();
        }
    }

    public void insert (int index, U view) {
        view.setParent(this);
        view.performRender();
        getChildren().add(index, view);
        if( getElement() != null && view.getElement() != null ) {
            if( index > -1 && index < getElement().childElementCount ) {
                getElement().insertBefore(view.getElement(), getElement().childNodes.getAt(index));
            } else {
                getElement().appendChild(view.getElement());
            }

            view.fireDidEnterDocument();
        }
    }

    public void append (U... views) {
        if( views != null ) {
            for (U view : views) {
                insert(getChildren().size(), view);
            }
        }
    }

    public void remove (U view) {
        int index = getChildren().indexOf(view);
        remove(index);
    }

    public void remove (int index) {
        if( index > -1 && index < getChildren().size() ) {
            UIObject<?, ?> uiObject = getChildren().get(index);
            uiObject.setParent(null);
            getChildren().remove(index);
            if( getElement() != null && uiObject.getElement() != null ) {
                getElement().removeChild(uiObject.getElement());
                uiObject.fireDidLeaveDocument();
            }
        }
    }

    /**
     * Removes the UIObject from the parent and the DOM.
     */
    public void removeFromParent () {
        if( getParent() != null ) {
            int index = getParent().getChildren().indexOf(this);
            getParent().remove(index);
        }
    }

    /**
     * Gets the bounding box of the UIObject
     * @return Rectangle with origin and size set
     */
    public Rectangle getGeometry() {
        return DOM.getBoundingRectClient(getElement());
    }

    protected void parseProps (T props) {

        if( props != null ) {
            String className = props.className();
            if( className != null && className.length() > 0 ) {
                getElement().classList.add(className);
            }
            if( props.hidden() ) {
                getElement().classList.add("v-hidden");
            }
            Iterator<String> attrIt = props.attributesIterator();
            while (attrIt.hasNext()) {
                String attr = attrIt.next();
                String val = props.getAttribute(attr);
                if( val != null ) {
                    getElement().setAttribute(attr, val);
                }
            }
            Iterator<String> styleIt = props.stylesIterator();
            while (styleIt.hasNext()) {
                String attr = styleIt.next();
                String val = props.getStyle(attr);
                Element el = getElement();
                if( val != null && el instanceof HTMLElement ) {
                    HTMLElement htmlEl = (HTMLElement) el;
                    htmlEl.style.set(attr, val);
                }
            }
        }
    }
}
