package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

public abstract class Item extends ElementRecyclerView {

    String name;
    boolean urgent;

    Item(long id, String name, boolean urgent) {
        super(id);

        this.name = name;
        this.urgent = urgent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getUrgent() {
        return urgent;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public String getSortedName() {
        return sort(name);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    String sort(String str) {
        str = str.replace("\n", " ");
        return (str.length() > 30) ? str.substring(0, 30) + "..." : str;
    }

    /*
     * Element RecyclerView
     *
     *
     *
     * */

    @NonNull
    @Override
    public String toString() {
        return id + "."
                + name + "."
                + ((urgent) ? "1" : "0");
    }

}
