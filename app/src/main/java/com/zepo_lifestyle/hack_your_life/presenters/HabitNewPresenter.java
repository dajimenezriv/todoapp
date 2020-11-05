package com.zepo_lifestyle.hack_your_life.presenters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.classes.Habit;
import com.zepo_lifestyle.hack_your_life.holders.DaysOfWeekHolder;
import com.zepo_lifestyle.hack_your_life.models.HabitModel;
import com.zepo_lifestyle.hack_your_life.views.NewHabitView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HabitNewPresenter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NewHabitView view;
    private HabitModel model;

    private Habit habit;

    public HabitNewPresenter(NewHabitView view) {
        this.view = view;

        model = new HabitModel(view);

        habit = Habit.createHabit(null, null);

        view.setTime(habit.getTime());
        view.setInitDate(Date.getPrintableDateWithYear(habit.getInitDate()));
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    @Override
    @NonNull
    public DaysOfWeekHolder onCreateViewHolder(ViewGroup parent, int view_type) {
        return new DaysOfWeekHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_days_register, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, final int pos) {
        /* Holder */

        final DaysOfWeekHolder h = (DaysOfWeekHolder) rv_holder;

        /* Values */

        int day = Habit.DAYS[pos];

        /* Layout */

        h.day.setText(day);
        setImage(h, pos);

        /* Listeners */

        h.day.setOnClickListener((View v) -> {
            increasePosition(pos);
            setImage(h, pos);
        });

        h.day.setOnLongClickListener((View v) -> {
            resetPosition(pos);
            setImage(h, pos);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return Habit.DAYS.length;
    }

    /*
     * Days Register
     *
     *
     *
     * */

    private void setImage(DaysOfWeekHolder h, int pos) {
        int image = habit.getDayOfWeekImage(pos);
        h.day.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);
    }

    private void increasePosition(int pos) {
        char value = habit.getDayOfWeekChar(pos);

        if (value == Habit.NON_REGISTERED) {
            habit.setDayOfWeekChar(pos, '1');
        } else {
            if (value != '6') {
                int n = Character.getNumericValue(value) + 1;
                value = (char) (n + '0');
                habit.setDayOfWeekChar(pos, value);
            }
        }
    }

    private void resetPosition(int pos) {
        habit.setDayOfWeekChar(pos, Habit.NON_REGISTERED);
    }

    /*
     * Time Picker
     *
     *
     *
     * */

    public void time() {
        String[] time_array = habit.getTime().split(":");

        TimePickerDialog tpd = new TimePickerDialog(view, android.R.style.Theme_Material_Dialog, (TimePicker v, int hour, int minutes) -> {
            habit.setTime(hour, minutes);
            view.setTime(habit.getTime());
        }, Integer.parseInt(time_array[0]), Integer.parseInt(time_array[1]), false);

        tpd.show();
    }

    /*
     * Date Picker
     *
     *
     *
     * */

    public void initDate() {
        final Calendar c = Calendar.getInstance();

        DatePickerDialog dpd = new DatePickerDialog(view, android.R.style.Theme_Material_Dialog, (DatePicker v, int year, int month, int day_of_month) -> {
            c.set(year, month, day_of_month);

            habit.setInitDate(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(c.getTime()));
            view.setInitDate(Date.getPrintableDateWithYear(habit.getInitDate()));
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        dpd.show();
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(String name, String description, String days_to_finish, boolean urgent) {
        if (name.equals("")) {
            view.showMessage(R.string.error_name_empty);
            return;
        }

        if (days_to_finish.equals("")) {
            view.showMessage(R.string.error_days_to_finish_empty);
            return;
        }

        int dtf;

        try {
            dtf = Integer.parseInt(days_to_finish);
        } catch (NumberFormatException e) {
            view.showMessage(R.string.error_days_to_finish_number);
            return;
        }

        if (Date.differenceBetweenDateAndToday(habit.getInitDate()) < 0) {
            view.showMessage(R.string.error_date_posterior);
            return;
        }

        habit.setName(name);
        habit.setDescription(description);
        habit.setFinishDate(dtf);
        habit.setDaysRegister(dtf);
        habit.setUrgent(urgent);
        habit.checkCompleted();

        saveChanges();
    }

    /*
     * Save Changes
     *
     *
     *
     * */

    private void saveChanges() {
        view.saveChanges(model.newHabit(habit));
    }

}