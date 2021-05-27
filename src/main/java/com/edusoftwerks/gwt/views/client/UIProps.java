package com.edusoftwerks.gwt.views.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UIProps<T extends AbstractProps<T>> extends AbstractProps<T> {

    final Map<String, Object> attributes = new HashMap<>();
    final Map<String, Object> styles = new HashMap<>();

    public UIProps() {
        hidden(false);
    }

    private Integer pixelToInt(String px) {
        int i = px.indexOf("px");
        return Integer.parseInt(px.substring(0, i));
    }

    public String className() {
        return (String) values.get("className");
    }

    public T className(String className) {
        return set("className", className);
    }

    public T attr(String attribute, Object value) {
        attributes.put(attribute, value);
        return (T) this;
    }

    public String attr(String attribute) {
        if (attributes.containsKey(attribute)) {
            Object value = attributes.get(attribute);
            if (value != null) {
                return value.toString();
            }
        }
        return "";
    }

    public T style(String attribute, Object value) {
        styles.put(attribute, value);
        return (T) this;
    }

    public String style(String attribute) {
        if (styles.containsKey(attribute)) {
            Object value = styles.get(attribute);
            if (value != null) {
                return value.toString();
            }
        }
        return "";
    }

    public Iterator<String> attributesIterator() {
        return attributes.keySet().iterator();
    }

    public Iterator<String> stylesIterator() {
        return styles.keySet().iterator();
    }

    public Boolean hidden() {
        return (Boolean) get("hidden");
    }

    public T hidden(boolean flag) {
        return set("hidden", flag);
    }

    public Integer height() {
        return pixelToInt(style("height"));
    }

    public T height(Integer height) {
        return style("height", height + "px");
    }

    public Integer width() {
        return pixelToInt(style("width"));
    }

    public T width(Integer width) {
        return style("width", width + "px");
    }
}
