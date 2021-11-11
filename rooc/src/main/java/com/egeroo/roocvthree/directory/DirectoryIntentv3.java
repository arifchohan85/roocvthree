package com.egeroo.roocvthree.directory;

public class DirectoryIntentv3 {

    private int id;
    private String parent;
    private int parentid;
    private int intentid;
    private String text;
    private int grandparentid;
    private String child;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getIntentid() {
        return intentid;
    }

    public void setIntentid(int intentid) {
        this.intentid = intentid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGrandparentid() {
        return grandparentid;
    }

    public void setGrandparentid(int grandparentid) {
        this.grandparentid = grandparentid;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
