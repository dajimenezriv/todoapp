package com.zepo_lifestyle.hack_your_life.presenters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.classes.Habit;
import com.zepo_lifestyle.hack_your_life.holders.HabitHolder;
import com.zepo_lifestyle.hack_your_life.models.HabitModel;
import com.zepo_lifestyle.hack_your_life.views.EditHabitView;
import com.zepo_lifestyle.hack_your_life.views.MainView;
import com.zepo_lifestyle.hack_your_life.views.NewHabitView;

public class HabitPresenter extends Presenter {

    private HabitModel model;

    HabitPresenter(MainView view) {
        super(view);

        model = new HabitModel(view);

        for (Habit habit : model.getHabits()) {
            array.add(habit);
        }
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void init() {
        /* Private Functions */

        checkCompleted();

        /* Title */

        view.setTitle(R.string.habits);

        /* Bottom Text */

        updateBottomText();
    }

    private void checkCompleted() {
        for (int i = 0; i < array.size(); i++) {
            Habit habit = (Habit) array.get(i);

            if (habit.checkCompleted()) {
                array.updateItemAt(i, habit);
                model.updateHabit(habit);
            }
        }
    }

    /*
     * RecyclerView
     *
     *
     *
     * */

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    @NonNull
    public HabitHolder onCreateViewHolder(ViewGroup parent, int view_type) {
        return new HabitHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_habits, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, final int pos) {
        final HabitHolder h = (HabitHolder) rv_holder;

        /* Get Item */

        final Habit habit = (Habit) array.get(pos);

        /* Values */

        String name = habit.getSortedName();
        String description = habit.getSortedDescription();
        int progress = habit.getProgress();
        String time = habit.getTime();
        boolean urgent = habit.getUrgent();

        /* Layout */

        h.name.setText(name);
        h.description.setText(description);
        h.progress.setText(view.getString(R.string.progress, progress));
        h.time.setText(time);

        /* Exceptions */

        if (urgent) h.urgent.setVisibility(View.VISIBLE);
        else h.urgent.setVisibility(View.GONE);
        if (description.equals("")) h.description.setVisibility(View.GONE);
        else h.description.setVisibility(View.VISIBLE);

        if (habit.getCompleted()) h.complete.setImageResource(R.drawable.finish);
        else {
            /* Values */

            int today_image = habit.getTodayImage();

            /* Layout */

            h.complete.setImageResource(today_image);

            /* Listeners */

            h.complete.setOnClickListener((View v) -> {
                position = pos;
                decreasePosition();
            });

            h.complete.setOnLongClickListener((View v) -> {
                position = pos;
                restartPosition();
                return true;
            });
        }

        /* Listeners */

        h.root.setOnClickListener((View v) -> {
            position = pos;
            edit();
        });

        h.edit.setOnClickListener((View v) -> {
            position = pos;
            edit();
        });

        h.delete.setOnClickListener((View v) -> {
            position = pos;
            delete();
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    /*
     * Bottom Text
     *
     *
     *
     * */

    public void updateBottomText() {
        view.setBottomText(view.getString(R.string.total_habits, array.size()));
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add() {
        view.startActivityForResult(new Intent(view, NewHabitView.class), MainPresenter.ADD);
    }

    public void saveAdd(long id) {
        /* Get Item */

        Habit habit = model.getHabitById(id);

        /* Modify Array */

        array.add(habit);

        /* Bottom Text */

        updateBottomText();
    }

    public void edit() {
        view.startActivityForResult(new Intent(view, EditHabitView.class).putExtra("id", array.get(position).getId()), MainPresenter.EDIT);
    }

    public void saveEdit(long id) {
        /* Get Item */

        Habit habit = model.getHabitById(id);

        /* Modify Array */

        array.updateItemAt(position, habit);
    }


    public void delete() {
        /* Get Item */

        last_deleted = array.get(position);

        /* Modify Array */

        array.removeItemAt(position);

        /* Bottom Text */

        updateBottomText();

        /* Private Functions */

        view.showUndo();

        /* Update DB */

        model.deleteHabit((Habit) last_deleted);
    }

    public void undo() {
        /* Update DB */

        model.newHabit((Habit) last_deleted);

        /* Modify Array */

        array.add(last_deleted);

        /* Bottom Text */

        updateBottomText();
    }

    /*
     * Complete
     *
     *
     *
     * */

    /* Decrease position if today_char is different of DONE or NON_REGISTERED */

    private void decreasePosition() {
        Habit habit = (Habit) array.get(position);

        /* Private Functions */

        char today_char = habit.getTodayChar();

        if (today_char == '1') habit.setTodayChar(Habit.DONE);
        else {
            if (today_char != Habit.DONE && today_char != Habit.NON_REGISTERED) {
                int n = Character.getNumericValue(today_char) - 1;
                today_char = (char) (n + '0');
                habit.setTodayChar(today_char);
            }
        }

        /* Modify Array */

        array.updateItemAt(position, habit);

        /* Update DB */

        model.updateHabit(habit);
    }

    /* Restart position if different of NON_REGISTERED */

    private void restartPosition() {
        Habit habit = (Habit) array.get(position);

        /* Private Functions */

        habit.setTodayChar(habit.getBaseTodayChar());

        /* Modify Array */

        array.updateItemAt(position, habit);

        /* Update DB */

        model.updateHabit(habit);
    }

    /*
     * Pickers
     *
     *
     *
     * */

}
