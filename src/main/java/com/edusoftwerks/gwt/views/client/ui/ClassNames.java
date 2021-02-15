package com.edusoftwerks.gwt.views.client.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Utiliy class uses to build DOM element classNames
 */
public class ClassNames {

    private final List<String> classNames = new ArrayList<>();

    private ClassNames () {
    }

    /**
     * The initial classname
     *
     * @param className
     * @return ClassNames instance
     */
    public static ClassNames start (String className) {
        return start(className, true);
    }

    /**
     * Adds the className only if flag is true
     *
     * @param className
     * @param flag
     * @return ClassNames instance
     */
    public static ClassNames start (String className, boolean flag) {
        return new ClassNames().add(className, flag);
    }

    /**
     * Adds a className
     *
     * @param className
     * @return ClassNames instance
     */
    public ClassNames add (String className) {
        return add(className, true);
    }

    /**
     * Adds a className conditional on boolean flag
     *
     * @param className
     * @param flag
     * @return ClassNames instance
     */
    public ClassNames add (String className, boolean flag) {
        if( flag && className != null ) {
            classNames.add(className);
        }
        return this;
    }

    /**
     * Builds the className string
     *
     * @return className string
     */
    public String build () {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (String v : classNames) {
            if( index > 0 ) {
                sb.append(" ");
            }
            sb.append(v);
            index++;
        }
        return sb.toString();
    }
}

