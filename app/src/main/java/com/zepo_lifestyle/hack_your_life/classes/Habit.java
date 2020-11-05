package com.zepo_lifestyle.hack_your_life.classes;

import androidx.annotation.NonNull;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.functions.Time;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

public class Habit extends Item {

    public static final char DONE = 'd';
    public static final char NON_REGISTERED = 'n';

    public static final int[] DAYS = new int[]{
            R.string.monday,
            R.string.tuesday,
            R.string.wednesday,
            R.string.thursday,
            R.string.friday,
            R.string.saturday,
            R.string.sunday
    };

    private static final HashMap<Character, Integer> IMAGES_REGISTER;

    static {
        IMAGES_REGISTER = new HashMap<>();
        IMAGES_REGISTER.put(DONE, R.drawable.done);
        IMAGES_REGISTER.put(NON_REGISTERED, R.drawable.non_registered);
        IMAGES_REGISTER.put('1', R.drawable.one);
        IMAGES_REGISTER.put('2', R.drawable.two);
        IMAGES_REGISTER.put('3', R.drawable.three);
        IMAGES_REGISTER.put('4', R.drawable.four);
        IMAGES_REGISTER.put('5', R.drawable.five);
        IMAGES_REGISTER.put('6', R.drawable.six);
    }

    private String description;

    private String time;
    /* yyyy/MM/dd */
    private String init_date;
    private String finish_date;

    /*
     * d = done
     * 1...9 = times/hours
     * n = non registered
     * */
    private StringBuilder days_of_week;
    private StringBuilder days_register;

    private boolean completed;

    public Habit(long id, String name, String description, String time, String init_date, String finish_date, String days_of_week,
                 String days_register, boolean urgent, boolean completed) {

        super(id, name, urgent);

        this.description = description;

        this.time = time;
        this.init_date = init_date;
        this.finish_date = finish_date;

        this.days_of_week = new StringBuilder(days_of_week);
        this.days_register = new StringBuilder(days_register);

        this.completed = completed;
    }

    /*
     * Default Constructor
     *
     *
     *
     * */

    public static Habit createHabit(String name, Boolean urgent) {
        Habit habit = new Habit(
                -1,
                (name == null) ? "" : name,
                "",
                "00:00",
                new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new java.util.Date()),
                "",
                "11111nn",
                "",
                (urgent != null) && urgent,
                false);

        habit.setFinishDate(66);
        habit.setDaysRegister(66);
        return habit;
    }

    /*
     * Getters / Setters
     *
     *
     *
     * */

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSortedDescription() {
        return sort(description);
    }

    public String getTime() {
        return time;
    }

    public String getInitDate() {
        return init_date;
    }

    /* zeroes(yyyy/MM/dd) */
    public void setInitDate(String init_date) {
        this.init_date = Date.setDate(init_date);
    }

    public String getFinishDate() {
        return finish_date;
    }

    public void setFinishDate(int days_to_finish) {
        finish_date = calculateFinishDate(days_to_finish);
    }

    public String getDaysOfWeek() {
        return days_of_week.toString();
    }

    public String getDaysRegister() {
        return days_register.toString();
    }

    public void setDaysRegister(int days_to_finish) {
        days_register = new StringBuilder(calculateDaysRegister(days_to_finish));
    }

    public boolean getCompleted() {
        return completed;
    }

    /* zeroes(hh:mm) */
    public void setTime(int hh, int mm) {
        this.time = Time.setTime(hh, mm);
    }

    /*
     * Habit List
     *
     *
     *
     * */

    public char getBaseTodayChar() {
        return days_of_week.charAt(new DateTime().dayOfWeek().get() - 1);
    }

    public char getTodayChar() {
        return days_register.charAt(Date.differenceBetweenDateAndToday(init_date));
    }

    public void setTodayChar(char c) {
        days_register.setCharAt(Date.differenceBetweenDateAndToday(init_date), c);
    }

    public Integer getTodayImage() {
        return IMAGES_REGISTER.get(getTodayChar());
    }

    public int getProgress() {
        double length = days_register.length();
        double done = length - days_register.toString().replace(Character.toString(DONE), "").length();
        double non_registered = length - days_register.toString().replace(Character.toString(NON_REGISTERED), "").length();
        return (int) ((done / (length - non_registered)) * 100);
    }

    /*
     * New / Edit Habit
     *
     *
     *
     * */

    /*
     * Days Of Week
     *
     *
     *
     * */

    public char getDayOfWeekChar(int position) {
        return days_of_week.charAt(position);
    }

    public Integer getDayOfWeekImage(int position) {
        return IMAGES_REGISTER.get(getDayOfWeekChar(position));
    }

    public void setDayOfWeekChar(int position, char c) {
        days_of_week.setCharAt(position, c);
    }

    /*
     * Day Register
     *
     *
     *
     * */

    public char getBaseDayChar(int position) {
        DateTime day = Date.stringToDateTime(init_date).plusDays(position);
        return days_of_week.charAt(day.dayOfWeek().get() - 1);
    }

    public char getDayChar(int position) {
        return days_register.charAt(position);
    }

    public Integer getDayImage(int position) {
        return IMAGES_REGISTER.get(getDayChar(position));
    }

    public void setDayChar(int position, char c) {
        days_register.setCharAt(position, c);
    }

    public String getDayDate(int position) {
        return Date.getPrintableDateWithoutYear(Date.stringToDateTime(init_date).plusDays(position));
    }

    /*
     * Private functions
     *
     *
     *
     * */

    public boolean checkCompleted() {
        completed = Date.differenceBetweenTodayAndDate(finish_date) <= 0;
        return completed;
    }

    private String calculateDaysRegister(int days_to_finish) {
        StringBuilder str = new StringBuilder();
        DateTime id = Date.stringToDateTime(init_date);

        for (int i = 0; i < days_to_finish; i++) {
            str.append(days_of_week.charAt(id.dayOfWeek().get() - 1));
            id = id.plusDays(1);
        }

        return str.toString();
    }

    private String calculateFinishDate(int days_to_finish) {
        return Date.dateTimeToString(Date.stringToDateTime(init_date).plusDays(days_to_finish));
    }

    /*
     * Element RecyclerView
     *
     *
     *
     * */

    public String getSortedString() {
        return ((completed) ? "1" : "0") + "."
                + time + "."
                + ((urgent) ? "0" : "1");
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + "."
                + description + "."
                + ((time == null) ? "99:99" : time) + "."
                + init_date + "."
                + finish_date + "."
                + days_of_week.toString() + "."
                + days_register + "."
                + ((completed) ? "1" : "0");
    }

}