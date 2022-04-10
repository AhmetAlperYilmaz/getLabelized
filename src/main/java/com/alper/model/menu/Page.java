package com.alper.model.menu;

import java.util.Arrays;
import java.util.List;


public class Page {

    private String uri;
    private String name;
    private String icon;
    private boolean showInMenu;
    private boolean isParent;
    private List<Page> childPages;

    public Page(String name, String icon, Page... childPages) {
        this.name = name;
        this.icon = icon;
        this.childPages = Arrays.asList(childPages);
        this.isParent = true;
        this.showInMenu = true;
    }

    public Page(String uri, String name, String icon, boolean showInMenu) {
        String pagePrefix = "/";
        this.uri = pagePrefix + uri;
        this.name = name;
        this.icon = icon;
        this.showInMenu = showInMenu;
        this.isParent = false;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean showInMenu() {
        return showInMenu;
    }

    public void setShowInMenu(boolean showInMenu) {
        this.showInMenu = showInMenu;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public List<Page> getChildPages() {
        return childPages;
    }

    public void setChildPages(List<Page> childPages) {
        this.childPages = childPages;
    }
}
