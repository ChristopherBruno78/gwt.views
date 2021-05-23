package com.edusoftwerks.gwt.views.client.ui.toast;

import com.edusoftwerks.gwt.views.client.UIProps;

public class ToastProps extends UIProps<ToastProps> {

    public ToastProps() {
        intent(ToastIntent.PRIMARY);
        lifespan(5);
    }

    public ToastIntent intent() {
        return (ToastIntent) values.get("intent");
    }

    public ToastProps intent(ToastIntent type) {
        return set("intent", type);
    }

    public String icon() {
        return (String) values.get("icon");
    }

    public ToastProps icon(String icon) {
        return set("icon", icon);
    }

    public String actionText() {
        return (String) values.get("actionText");
    }

    public ToastProps actionText(String text) {
        return set("actionText", text);
    }

    public Integer lifespan() {
        return (Integer) values.get("lifespan");
    }

    public ToastProps lifespan(Integer lifespan) {
        return set("lifespan", lifespan);
    }

}
