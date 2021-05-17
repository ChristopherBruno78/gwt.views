package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.client.dom.ClassNames;
import com.edusoftwerks.gwt.views.client.dom.DOM;
import com.edusoftwerks.gwt.views.shared.geometry.Rectangle;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

import java.util.*;

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
public abstract class UIObject {

    private final Map<String, ArrayList<EventListener>> eventListenerMap = new HashMap<>();

    /**
     * Returns the low-level Element that represents this View in the DOM
     *
     * @return the DOM Element
     */
    public abstract HTMLElement getElement();

    /**
     * Returns the UIObject's parent, if it exists
     *
     * @return UIObject parent
     */
    public abstract UIObject getParent();

    /**
     * Sets the View parent
     *
     * @param parent
     */
    protected abstract void setParent(UIObject parent);

    /**
     * Returns a list of all the View's children
     * this View is the parent of the children
     *
     * @return List of U type UIObjects
     */
    public abstract List<UIObject> getChildren();

    /**
     * Called once when the View enters the DOM
     */
    public void didEnterDocument() {
    }

    public void setInnerHtml(String text) {
        getElement().innerHTML = text;
    }

    public String getInnerHtml() {
        return getElement().innerHTML;
    }

    /**
     * Called once when the View leaves the DOM
     */
    public void didLeaveDocument() {
    }

    protected abstract void performRender();

    void fireDidEnterDocument() {
        didEnterDocument();
        List<UIObject> children = getChildren();
        for (UIObject uiObject : children) {
            uiObject.fireDidEnterDocument();
        }
    }

    void fireDidLeaveDocument() {
        removeEventListeners();
        didLeaveDocument();
        List<UIObject> children = getChildren();
        for (UIObject uiObject : children) {
            uiObject.fireDidLeaveDocument();
        }

    }

    public void addEventListener(String event, EventListener listener) {
        if (!eventListenerMap.containsKey(event)) {
            eventListenerMap.put(event, new ArrayList<>());
        }
        eventListenerMap.get(event).add(listener);
        getElement().addEventListener(event, listener);
    }

    public void removeEventListener(String event) {
        if (eventListenerMap.containsKey(event)) {
            List<EventListener> listeners = eventListenerMap.get(event);
            for (EventListener listener : listeners) {
                getElement().removeEventListener(event, listener);
            }
            eventListenerMap.remove(event);
        }
    }

    public void removeEventListeners() {
        String[] eventListeners = eventListenerMap.keySet().toArray(new String[ 0 ]);
        for (int i = 0; i < eventListeners.length; i++) {
            removeEventListener(eventListeners[ i ]);
        }
    }

    /**
     * Gets the Props of the UIObject
     *
     * @return
     */
    public abstract UIProps<?> getProps();

    /**
     * Adds className to the UIObject
     *
     * @param name
     */
    public void addClassName(String name) {
        String[] klasses = name.split(" ");
        for (String klass : klasses) {
            UIProps<?> props = getProps();
            if (props != null) {
                props.className(ClassNames.start(props.className())
                        .add(klass.trim())
                        .build());
            }
            if (getElement() != null) {
                getElement().classList.add(klass.trim());
            }
        }
    }

    /**
     * sets/replaces the className of UIObject
     *
     * @param name
     */
    public void setClassName(String name) {
        UIProps<?> props = getProps();
        if (props != null) {
            if (name != null) {
                props.className(name);
            }
        }
        if (getElement() != null) {
            getElement().className = name;
        }
    }

    /**
     * Removes ${name} from the className of the UIObject
     *
     * @param name
     */
    public void removeClassName(String name) {
        UIProps<?> props = getProps();
        if (props != null) {
            String className = props.className();
            if (className != null) {
                className = className.replace(name, "");
                props.className(className);
            }
        }
        if (getElement() != null) {
            getElement().classList.remove(name);
        }
    }

    /**
     * sets an attribute key of the element to value. Setting to null
     * removes the attribute
     *
     * @param key
     * @param value
     */
    public void setAttribute(String key, Object value) {
        UIProps<?> props = getProps();
        if (props != null) {
            props.attr(key, value);
        }
        if (getElement() != null) {
            if (value != null) {
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
    public void setStyleAttribute(String key, Object value) {
        UIProps<?> props = getProps();
        if (props != null) {
            props.style(key, value);
        }
        HTMLElement $el = getElement();
        if ($el != null) {
            if (value != null) {
                $el.style.set(key, value.toString());
            } else {
                $el.style.removeAttribute(key);
            }
        }
    }

    /**
     * Removes an attribute
     *
     * @param key
     */
    public void removeAttribute(String key) {
        UIProps<?> props = getProps();
        if (props != null) {
            props.attributes.remove(key);
        }
        if (getElement() != null) {
            getElement().removeAttribute(key);
        }
    }

    /**
     * removes a style attribute
     *
     * @param key
     */
    public void removeStyleAttribute(String key) {
        UIProps<?> props = getProps();
        if (props != null) {
            props.styles.remove(key);
        }
        HTMLElement el = getElement();
        if (el != null) {
            el.style.removeAttribute(key);
        }
    }

    public void insert(int index, UIObject view) {
        view.setParent(this);
        view.performRender();
        getChildren().add(index, view);
        if (getElement() != null && view.getElement() != null) {
            if (index > -1 && index < getElement().childElementCount) {
                getElement().insertBefore(view.getElement(), getElement().childNodes.getAt(index));
            } else {
                getElement().appendChild(view.getElement());
            }
        }
        view.fireDidEnterDocument();
    }

    public void append(UIObject... views) {
        if (views != null) {
            for (UIObject view : views) {
                insert(getChildren().size(), view);
            }
        }
    }

    public void remove(UIObject view) {
        int index = getChildren().indexOf(view);
        remove(index);
    }

    public void remove(int index) {
        if (index > -1 && index < getChildren().size()) {
            UIObject uiObject = getChildren().get(index);
            uiObject.setParent(null);
            getChildren().remove(index);
            if (getElement() != null && uiObject.getElement() != null) {
                uiObject.fireDidLeaveDocument();
                getElement().removeChild(uiObject.getElement());
            }

        }
    }

    /**
     * Removes the UIObject from the parent and the DOM.
     */
    public void removeFromParent() {
        if (getParent() != null) {
            int index = getParent().getChildren().indexOf(this);
            getParent().remove(index);
        }
    }

    /**
     * Gets the bounding box of the UIObject
     *
     * @return Rectangle with origin and size set
     */
    public Rectangle getGeometry() {
        return DOM.getBoundingRectClient(getElement());
    }

    protected void parseProps(UIProps<?> props) {
        if (props != null) {
            String className = props.className();
            if (className != null && className.length() > 0) {
                addClassName(className);
            }
            if (props.hidden()) {
                getElement().classList.add("v-hidden");
            }
            Iterator<String> attrIt = props.attributesIterator();
            while (attrIt.hasNext()) {
                String attr = attrIt.next();
                String val = props.attr(attr).toString();
                getElement().setAttribute(attr, val);
            }
            Iterator<String> styleIt = props.stylesIterator();
            while (styleIt.hasNext()) {
                String attr = styleIt.next();
                String val = props.style(attr).toString();
                HTMLElement $el = getElement();
                $el.style.set(attr, val);
            }
        }
    }

}
