package com.edusoftwerks.gwt.views.client;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractProps<T extends AbstractProps<T>> {

    protected Map<String, Object> values = new HashMap<>();

    public Object get(String key) {
        return values.get(key);
    }

    /**
     * Set a property with name key a given value
     *
     * @param key property name
     * @param value property value
     * @return the Props object for chain calls
     */
    public T set(String key, Object value) {
        values.put(key, value);
        return (T) this;
    }
}
