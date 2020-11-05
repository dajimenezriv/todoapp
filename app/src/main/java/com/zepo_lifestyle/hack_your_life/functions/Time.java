package com.zepo_lifestyle.hack_your_life.functions;

public class Time {

    /*
     * @params
     * hour
     * minutes
     *
     * @returns
     * ??:??
     * */
    public static String setTime(int hour, int minutes) {
        return Functions.zeroes(Integer.toString(hour)) + ":" + Functions.zeroes(Integer.toString(minutes));
    }

}
