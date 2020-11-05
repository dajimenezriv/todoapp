package com.zepo_lifestyle.hack_your_life.presenters;

import android.app.TimePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.classes.Habit;
import com.zepo_lifestyle.hack_your_life.holders.DaysOfWeekHolder;
import com.zepo_lifestyle.hack_your_life.models.HabitModel;
import com.zepo_lifestyle.hack_your_life.views.EditHabitView;

public class HabitEditPresenter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private EditHabitView view;
    private HabitModel model;

    private Habit habit;

    public HabitEditPresenter(EditHabitView view, long id) {
        this.view = view;

        model = new HabitModel(view);
        habit = model.getHabitById(id);

        fillCamps();
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    private void fillCamps() {
        String finish_date = habit.getFinishDate();
        int days_left = Date.differenceBetweenTodayAndDate(finish_date);

        view.setName(habit.getName());
        view.setDescription(habit.getDescription());
        view.setTime(habit.getTime());
        view.setDaysLeft((days_left < 0) ? "0" : Integer.toString(days_left));
        view.setInitDate(Date.getPrintableDateWithYear(habit.getInitDate()));
        view.setFinishDate(Date.getPrintableDateWithYear(habit.getFinishDate()));
        view.setProgressBar(habit.getProgress());
        view.setUrgent(habit.getUrgent());
    }

    public int getTodayPosition() {
        int position = Date.differenceBetweenDateAndToday(habit.getInitDate()) - 2;
        if (position < 0) return 0;
        return position;
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

        String day = habit.getDayDate(pos);

        /* Layout */

        h.day.setText(day);
        setImage(h, pos);

        /* Listeners */

        h.day.setOnClickListener((View v) -> {
            decreasePosition(pos);
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
        return habit.getDaysRegister().length();
    }

    /*
     * Days Register
     *
     *
     *
     * */

    private void setImage(DaysOfWeekHolder h, int pos) {
        int image = habit.getDayImage(pos);
        h.day.setCompoundDrawablesWithIntrinsicBounds(0, image, 0, 0);
        view.setProgressBar(habit.getProgress());
    }

    private void decreasePosition(int pos) {
        char value = habit.getDayChar(pos);

        if (value == '1') {
            habit.setDayChar(pos, Habit.DONE);
        } else {
            if (value != Habit.DONE && value != Habit.NON_REGISTERED) {
                int n = Character.getNumericValue(value) - 1;
                value = (char) (n + '0');
                habit.setDayChar(pos, value);
            }
        }
    }

    private void resetPosition(int pos) {
        habit.setDayChar(pos, habit.getBaseDayChar(pos));
    }

    /*
     * Time
     *
     *
     *
     * */

    public void time() {
        String[] time_array = habit.getTime().split(":");

        TimePickerDialog tpd = new TimePickerDialog(view, android.R.style.Theme_Material_Dialog, ((TimePicker v, int hour, int minutes) -> {
            habit.setTime(hour, minutes);
            view.setTime(habit.getTime());
        }), Integer.parseInt(time_array[0]), Integer.parseInt(time_array[1]), false);

        tpd.show();
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(String name, String description, boolean urgent) {
        if (name.isEmpty()) {
            view.showMessage(R.string.error_name_empty);
            return;
        }

        habit.setName(name);
        habit.setDescription(description);
        habit.setUrgent(urgent);

        saveChanges();
    }

    /*
     * Save Changes
     *
     *
     *
     * */

    private void saveChanges() {
        model.updateHabit(habit);
        view.saveChanges(habit.getId());
    }

}