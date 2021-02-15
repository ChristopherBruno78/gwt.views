package com.edusoftwerks.gwt.views.client.ui;

import com.google.gwt.dev.util.collect.HashMap;
import elemental2.dom.DomGlobal;
import elemental2.dom.EventListener;
import elemental2.dom.HTMLElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DOMElement extends UIObject<DOMProps, UIObject<?, ?>> {

    private final HTMLElement $el;
    private final DOMProps props;
    private final List<UIObject<?, ?>> children = new ArrayList<>();
    private UIObject<?, ?> parent;
    private Map<String, List<EventListener>> eventListeners = null;

    DOMElement (String tagName, DOMProps props) {
        this.props = props;
        this.$el = (HTMLElement) DomGlobal.document.createElement(tagName);
        parseProps(this.props);
    }

    @Override
    public HTMLElement getElement () {
        assert ($el != null) : "This DOMElement's element is not set;";
        return $el;
    }

    @Override
    public UIObject<?, ?> getParent () {
        return parent;
    }

    @Override
    public void setParent (UIObject<?, ?> parent) {
        this.parent = parent;
    }

    @Override
    public List<UIObject<?, ?>> getChildren () {
        return children;
    }

    @Override
    public DOMProps getProps () {
        return props;
    }

    @Override
    public void didEnterDocument () {
    }

    @Override
    public void didLeaveDocument () {
    }

    public void addEventListener (String eventType, EventListener listener) {

        if( eventListeners == null ) {
            eventListeners = new HashMap<>();
        }
        List<EventListener> listeners;
        if( eventListeners.containsKey(eventType) ) {
            listeners = eventListeners.get(eventType);
        } else {
            listeners = new ArrayList<>();
            eventListeners.put(eventType, listeners);
        }
        listeners.add(listener);
        $el.addEventListener(eventType, listener);
    }

    public void removeEventListener (String eventType, EventListener listener) {

        if( eventListeners.containsKey(eventType) ) {
            List<EventListener> listeners = eventListeners.get(eventType);
            listeners.remove(listener);
            $el.removeEventListener(eventType, listener);
        }
    }

    public void removeEventListeners (String eventType) {

        if( eventListeners.containsKey(eventType) ) {
            List<EventListener> listeners = eventListeners.get(eventType);
            for (EventListener listener : listeners) {
                removeEventListener(eventType, listener);
            }
        }
    }

    @Override
    public void remove (int index) {
        super.remove(index);
        Set<String> eventSet = eventListeners.keySet();
        for (String eventType : eventSet) {
            removeEventListeners(eventType);
        }
    }
}