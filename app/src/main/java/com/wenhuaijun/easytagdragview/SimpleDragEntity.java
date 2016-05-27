package com.wenhuaijun.easytagdragview;
public class SimpleDragEntity implements IDragEntity {
    private int id;
    private String tag;
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "id: "+id+" tag: "+ tag;
    }
}
