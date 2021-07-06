package com.edusoftwerks.gwt.views.client.ui.toolbar;

import com.edusoftwerks.gwt.views.client.UIObject;
import com.edusoftwerks.gwt.views.client.UIProps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolbarProps extends UIProps<ToolbarProps> {

    List<UIObject> rightViews = new ArrayList<>();
    List<UIObject> leftViews = new ArrayList<>();

    public ToolbarProps() {
        preventOverflow(true);
    }
    public List<UIObject> leftViews() {
        return leftViews;
    }

    public ToolbarProps leftViews(UIObject... views) {
        leftViews = Arrays.asList(views);
        return this;
    }

    public List<UIObject> rightViews() {
        return rightViews;
    }

    public ToolbarProps rightViews(UIObject... views) {
        rightViews = Arrays.asList(views);
        return this;
    }

    public Boolean preventOverflow() {
        return (Boolean) values.get("preventOverflow");
    }

    public ToolbarProps preventOverflow(boolean flag) {
        return set("preventOverflow", flag);
    }


}

