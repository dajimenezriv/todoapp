package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

public abstract class ElementRecyclerView {

    public static int HEADER = 0;
    public static int CHILD = 1;

    long id;

    ElementRecyclerView(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return CHILD;
    }

    public abstract String getSortedString();

    @NonNull
    @Override
    public abstract String toString();

}
