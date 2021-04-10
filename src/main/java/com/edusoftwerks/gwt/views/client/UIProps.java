package com.edusoftwerks.gwt.views.client;

import com.edusoftwerks.gwt.views.shared.AbstractProps;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UIProps<T extends AbstractProps<T>> extends AbstractProps<T> {

    final Map<String, Object> attributes = new HashMap<>();
    final Map<String, Object> styles = new HashMap<>();

    public UIProps() {
        hidden(false);
    }

    public String className () {
        return (String) values.get("className");
    }

    public T className (String className) {
        return set("className", className);
    }

    public T attr (String attribute, Object value) {
        attributes.put(attribute, value);
        return (T) this;
    }

    public String getAttribute (String attribute) {
        Object attr = attributes.get(attribute);
        if( attr != null ) {
            return attr.toString();
        }
        return null;
    }

    public T style (String attribute, Object value) {
        styles.put(attribute, value);
        return (T) this;
    }

    public String getStyle (String attribute) {
        Object s = styles.get(attribute);
        if( s != null ) {
            return s.toString();
        }
        return null;
    }

    public Iterator<String> attributesIterator () {
        return attributes.keySet()
                .iterator();
    }

    public Iterator<String> stylesIterator () {
        return styles.keySet()
                .iterator();
    }

    public Boolean hidden () {
        return (Boolean) get("hidden");
    }

    public T hidden (boolean flag) {
        return set("hidden", flag);
    }
}
