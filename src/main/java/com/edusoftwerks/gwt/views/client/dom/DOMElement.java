package com.edusoftwerks.gwt.views.client.dom;

import com.edusoftwerks.gwt.views.client.UIObject;
import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

import java.util.*;

public class DOMElement extends UIObject {

    private final HTMLElement $el;
    private final DOMProps props;
    private final List<UIObject> children = new ArrayList<>();
    private UIObject parent;
    private Map<String, List<EventListener>> eventListeners = null;

    public DOMElement(HTMLElement $el) {
        this($el, new DOMProps());
    }

    public DOMElement(HTMLElement $el, DOMProps props) {
        this.props = props;
        this.$el = $el;
        performRender();
    }

    public DOMElement(String tagName, DOMProps props) {
        this((HTMLElement) DomGlobal.document.createElement(tagName), props);
    }

    @Override
    public HTMLElement getElement() {
        assert ($el != null) : "This DOMElement's element is not set;";
        return $el;
    }

    @Override
    public UIObject getParent() {
        return parent;
    }

    @Override
    protected void setParent(UIObject parent) {
        this.parent = parent;
    }

    @Override
    public List<UIObject> getChildren() {
        return children;
    }

    @Override
    public DOMProps getProps() {
        return props;
    }

    @Override
    public void didEnterDocument() {
    }

    @Override
    public void didLeaveDocument() {
    }

    @Override
    protected void performRender() {
        parseProps(this.props);
    }

    public void addEventListener(String eventType, EventListener listener) {
        if (eventListeners == null) {
            eventListeners = new HashMap<>();
        }
        List<EventListener> listeners;
        if (eventListeners.containsKey(eventType)) {
            listeners = eventListeners.get(eventType);
        } else {
            listeners = new ArrayList<>();
            eventListeners.put(eventType, listeners);
        }
        listeners.add(listener);
        $el.addEventListener(eventType, listener);
    }

    public void removeEventListener(String eventType, EventListener listener) {
        if (eventListeners.containsKey(eventType)) {
            List<EventListener> listeners = eventListeners.get(eventType);
            listeners.remove(listener);
            $el.removeEventListener(eventType, listener);
        }
    }

    public void removeEventListeners(String eventType) {
        if (eventListeners.containsKey(eventType)) {
            List<EventListener> listeners = eventListeners.get(eventType);
            for (EventListener listener : listeners) {
                removeEventListener(eventType, listener);
            }
        }
    }

    @Override
    public void remove(int index) {
        super.remove(index);
        Set<String> eventSet = eventListeners.keySet();
        for (String eventType : eventSet) {
            removeEventListeners(eventType);
        }
    }
}
