package com.zepo_lifestyle.hack_your_life.presenters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.classes.ElementRecyclerView;
import com.zepo_lifestyle.hack_your_life.classes.List;
import com.zepo_lifestyle.hack_your_life.classes.Task;
import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.holders.ListHolder;
import com.zepo_lifestyle.hack_your_life.holders.ListTasksHolder;
import com.zepo_lifestyle.hack_your_life.models.ListModel;
import com.zepo_lifestyle.hack_your_life.models.TaskModel;
import com.zepo_lifestyle.hack_your_life.views.MainView;
import com.zepo_lifestyle.hack_your_life.views.NewListView;

import java.util.ArrayList;

public class ListPresenter extends Presenter {

    private ListModel list_model;
    private TaskModel task_model;

    private long list_displayed;
    private int task_init_position;
    private int task_end_position;

    ListPresenter(MainView view) {
        super(view);

        list_model = new ListModel(view);
        task_model = new TaskModel(view);

        list_displayed = -1;
        task_init_position = -1;
        task_end_position = -1;

        createArray();

        for (List list : list_model.getLists()) {
            array.add(list);
        }
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void init() {
        /* Array */

        hideTasks();

        /* Title */

        view.setTitle(R.string.lists);

        /* Bottom Text */

        updateBottomText();
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    /* Display and hide tasks for each list */

    private void moreTasks() {
        long id = array.get(position).getId();

        if (list_displayed == -1) displayTasks(id);
        else if (list_displayed == id) hideTasks();
        else {
            hideTasks();
            displayTasks(id);
        }
    }

    private void displayTasks(long id) {
        list_displayed = id;

        ArrayList<Task> tasks = task_model.getPastAndTodayAndTomorrowTasksByList(id);

        for (Task task : tasks) {
            if (task_init_position == -1) task_init_position = array.add(task);
            else array.add(task);
        }

        task_end_position = task_init_position + tasks.size();
    }

    private void hideTasks() {
        for (int i = task_init_position; i < task_end_position; i++) {
            array.removeItemAt(i);
            i--;
            task_end_position--;
        }

        list_displayed = -1;
        task_init_position = -1;
        task_end_position = -1;
    }

    private void openList() {
        setUrgent();
        view.task();
    }

    /* Set all urgent to false and the selected to true */

    private void setUrgent() {
        List list = (List) array.get(position);

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) instanceof List) {
                List l = (List) array.get(i);
                l.setUrgent(l == list);
                list_model.updateList(l);
            }
        }
    }

    private void setDate(ListTasksHolder holder, String date) {
        if (date == null) return;

        int diff_days = Date.differenceBetweenTodayAndDate(date);

        if (diff_days == 0) holder.date.setText(view.getString(R.string.today));
        else if (diff_days == 1) holder.date.setText(view.getString(R.string.tomorrow));
        else holder.date.setText(Date.getPrintableDateWithYear(date));

        if (diff_days < 0) holder.date.setTextColor(view.getColor(R.color.red));
        else holder.date.setTextColor(view.getColor(R.color.blue));
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position).getType();
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int view_type) {
        if (view_type == ElementRecyclerView.HEADER)
            return new ListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_lists, parent, false));
        else
            return new ListTasksHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_list_tasks, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, final int pos) {
        if (getItemViewType(pos) == ElementRecyclerView.HEADER)
            createListHolder((ListHolder) rv_holder, pos);
        else createListTaskHolder((ListTasksHolder) rv_holder, pos);
    }

    private void createListHolder(@NonNull final ListHolder h, final int pos) {
        /* Get Item */

        final List list = (List) array.get(pos);

        /* Values */

        long id = list.getId();
        String name = list.getSortedName();
        long number_tasks = task_model.getNumberPastAndTodayAndTomorrowTasksByList(id);

        /* Layout */

        h.name.setText(name);

        /* Exceptions */

        if (number_tasks == 0) h.more.setVisibility(View.GONE);
        else h.more.setVisibility(View.VISIBLE);

        /* Listeners */

        h.root.setOnClickListener((View v) -> {
            position = pos;
            openList();
        });

        h.edit.setOnClickListener((View v) -> {
            position = pos;
            edit();
        });

        h.delete.setOnClickListener((View v) -> {
            position = pos;
            delete();
        });

        h.more.setOnClickListener((View v) -> {
            position = pos;
            moreTasks();
        });
    }

    private void createListTaskHolder(@NonNull final ListTasksHolder h, final int pos) {
        /* Get Item */

        final Task task = (Task) array.get(pos);

        /* Values */

        String name = task.getSortedName();
        String description = task.getSortedDescription();
        String date = task.getDate();
        String time = task.getTime();
        Integer repeat = task.getRepeat();
        boolean urgent = task.getUrgent();

        /* Layout */

        h.name.setText(name);
        h.description.setText(description);
        setDate(h, date);
        h.time.setText(time);

        /* Exceptions */

        if (urgent) h.urgent.setVisibility(View.VISIBLE);
        else h.urgent.setVisibility(View.GONE);
        if (description.isEmpty()) h.description.setVisibility(View.GONE);
        else h.description.setVisibility(View.VISIBLE);
        if (repeat == null) h.repeat_icon.setVisibility(View.GONE);
        else h.repeat_icon.setVisibility(View.VISIBLE);
        if (date == null) h.date.setVisibility(View.GONE);
        else h.date.setVisibility(View.VISIBLE);
        if (time == null) {
            h.time.setVisibility(View.GONE);
            h.time_icon.setVisibility(View.GONE);
        } else {
            h.time.setVisibility(View.VISIBLE);
            h.time_icon.setVisibility(View.VISIBLE);
        }
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
        view.setBottomText(view.getString(R.string.total_lists, list_model.getNumberLists()));
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add() {
        view.startActivityForResult(new Intent(view, NewListView.class), 0);
    }

    public void saveAdd(long id) {
        /* Get Item */

        List list = list_model.getListById(id);

        /* Modify Array */

        array.add(list);

        /* Bottom Text*/

        updateBottomText();
    }

    public void edit() {
        view.startActivityForResult(new Intent(view, NewListView.class).putExtra("id", array.get(position).getId()), 1);
    }

    public void saveEdit(long id) {
        /* Get Item */

        List list = list_model.getListById(id);

        /* Modify Array */

        array.updateItemAt(position, list);
    }

    public void delete() {
        /* Private Functions */

        if (array.size() == 1) {
            view.showMessage(R.string.error_delete_list);
            return;
        }

        /* Get Item */

        last_deleted = array.get(position);

        /* Modify Array */

        array.removeItemAt(position);

        /* Private Functions */

        view.showUndo();

        /* Modify DB */

        list_model.deleteList((List) last_deleted);

        /* Modify Bottom Text */

        updateBottomText();
    }

    public void undo() {
        /* Update DB */

        list_model.newList((List) last_deleted);

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

    /*
     * Pickers
     *
     *
     *
     * */

}
