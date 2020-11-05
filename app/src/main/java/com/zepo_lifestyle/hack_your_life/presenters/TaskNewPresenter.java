package com.zepo_lifestyle.hack_your_life.presenters;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.classes.Task;
import com.zepo_lifestyle.hack_your_life.models.TaskModel;
import com.zepo_lifestyle.hack_your_life.views.NewTaskView;

import com.zepo_lifestyle.hack_your_life.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TaskNewPresenter {

    private NewTaskView view;
    private TaskModel model;

    private Task task;
    private long id;

    public TaskNewPresenter(NewTaskView view, long list, long id) {
        this.view = view;
        this.id = id;

        model = new TaskModel(view);

        if (id == -1 && list != -1) {
            task = Task.createTask(list, null, null);

            task.setDate(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new java.util.Date()));

            view.setTitle(view.getString(R.string.new_task));
        } else if (id != -1) {
            task = model.getTaskById(id);

            Integer repeat = task.getRepeat();
            view.setTitle(view.getString(R.string.edit_task));
            view.setName(task.getName());
            view.setDescription(task.getDescription());
            view.setRepeatCheck(repeat != null);
            view.setRepeatTime(Integer.toString(task.getRepeatTime()));
            view.setRepeat((repeat == null) ? 0 : repeat);
            view.setUrgent(task.getUrgent());
        } else {
            view.onBackPressed();
        }

        String date = task.getDate();
        String time = task.getTime();

        view.setDate((date == null) ? view.getString(R.string.never) : Date.getPrintableDateWithYear(date));
        view.setTime((time == null) ? view.getString(R.string.never) : time);
    }

    /*
     * Date Picker
     *
     *
     *
     * */

    public void date() {
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String date = task.getDate();

        if (date != null) {
            String[] date_array = date.split("/");
            year = Integer.parseInt(date_array[0]);
            month = Integer.parseInt(date_array[1]) - 1;
            day = Integer.parseInt(date_array[2]);
        }

        DatePickerDialog dpd = new DatePickerDialog(view, android.R.style.Theme_Material_Dialog, (DatePicker v, int y, int m, int d) -> {
            c.set(y, m, d);

            task.setDate(new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(c.getTime()));
            view.setDate(Date.getPrintableDateWithYear(task.getDate()));
        }, year, month, day);

        dpd.show();
    }

    public void cancelDate() {
        task.setDate(null);
        view.setDate(view.getString(R.string.never));
    }

    /*
     * Time Picker
     *
     *
     *
     * */

    public void time() {
        String time = task.getTime();

        int hour = 0;
        int minutes = 0;

        if (time != null) {
            hour = Integer.parseInt(time.split(":")[0]);
            minutes = Integer.parseInt(time.split(":")[1]);
        }

        TimePickerDialog tpd = new TimePickerDialog(view, android.R.style.Theme_Material_Dialog, (TimePicker v, int hh, int mm) -> {
            task.setTime(hh, mm);
            view.setTime(task.getTime());
        }, hour, minutes, false);

        tpd.show();
    }

    public void cancelTime() {
        task.cancelTime();
        view.setTime(view.getString(R.string.never));
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(String name, String description, boolean repeat_check, int repeat, String repeat_time, boolean urgent) {
        if (name.equals("")) {
            view.showMessage(R.string.error_name_empty);
            return;
        }

        if (repeat_check) {
            if (task.getDate() == null) {
                view.showMessage(R.string.error_date);
                return;
            }

            try {
                int rt = Integer.parseInt(repeat_time);

                if (rt <= 0) {
                    view.showMessage(R.string.error_repeat_time_greater_than_zero);
                    return;
                }

                task.setRepeatTime(rt);
                task.setRepeat(repeat);
            } catch (NumberFormatException e) {
                view.showMessage(R.string.error_repeat_time);
                return;
            }
        } else {
            task.setRepeat(null);
            task.setRepeatTime(0);
        }

        task.setName(name);
        task.setDescription(description);
        task.setUrgent(urgent);

        saveChanges();
    }

    /*
     * Save Changes
     *
     *
     *
     * */

    private void saveChanges() {
        if (id == -1) view.saveChanges(model.newTask(task));
        else {
            model.updateTask(task);
            view.saveChanges(task.getId());
        }
    }

}
