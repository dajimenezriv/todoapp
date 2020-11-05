package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

public class List extends Item {

    public List(long id, String name, boolean urgent) {
        super(id, name, urgent);
    }

    /*
     * Default Constructor
     *
     *
     *
     * */

    public static List createList(String name, Boolean urgent) {
        return new List(
                -1,
                name,
                (urgent != null) && urgent);
    }

    /*
     * Element RecyclerView
     *
     *
     *
     * */

    public int getType() {
        return HEADER;
    }

    public String getSortedString() {
        return Long.toString(id);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

}
