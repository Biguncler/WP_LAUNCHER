package com.example.biguncler.wp_launcher.mode;

/**
 * Created by Biguncler on 3/20/2019.
 */

public class CellInfo {
    public int getWith() {
        return with;
    }

    public void setWith(int with) {
        this.with = with;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AppMode getMode() {
        return mode;
    }

    public void setMode(AppMode mode) {
        this.mode = mode;
    }

    private int with;
    private int height;
    private int type;
    private AppMode mode;
}
