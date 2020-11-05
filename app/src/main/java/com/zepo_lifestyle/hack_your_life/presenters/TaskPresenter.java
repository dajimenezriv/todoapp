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
import com.zepo_lifestyle.hack_your_life.classes.TaskDate;
import com.zepo_lifestyle.hack_your_life.functions.Date;
import com.zepo_lifestyle.hack_your_life.holders.TaskDateHolder;
import com.zepo_lifestyle.hack_your_life.holders.TaskHolder;
import com.zepo_lifestyle.hack_your_life.models.ListModel;
import com.zepo_lifestyle.hack_your_life.models.TaskModel;
import com.zepo_lifestyle.hack_your_life.views.MainView;
import com.zepo_lifestyle.hack_your_life.views.NewTaskView;

public class TaskPresenter extends Presenter {

    private TaskModel model;

    private List list;

    TaskPresenter(MainView view) {
        super(view);

        model = new TaskModel(view);
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void init() {
        /* Urgent or first */
        list = new ListModel(view).getUrgentList();

        /* Array */

        array.clear();

        for (Task task : model.getUncompletedTasks(list.getId())) {
            array.add(task);
        }

        updateTitles();

        /* Title */

        view.setTitle(list.getSortedName());

        /* Bottom Text */

        updateBottomText();
    }

    private void updateTitles() {
        if (array.size() == 0) return;

        for (TaskDate date : TaskDate.getDates(list.getId(), view)) {
            array.add(date);
        }

        for (int i = 0; i < array.size(); i++) {
            try {
                /* Two titles followed */
                if (array.get(i).getType() == ElementRecyclerView.HEADER && array.get(i + 1).getType() == ElementRecyclerView.HEADER) {
                    array.removeItemAt(i);
                    i--;
                }
            } catch (IndexOutOfBoundsException e) {
                /* Last item is title? */
                if (array.get(i).getType() == ElementRecyclerView.HEADER) array.removeItemAt(i);
            }
        }

        /* If first title is NEVER */
        if (array.size() != 0 && array.get(0).getType() == ElementRecyclerView.HEADER && ((TaskDate) array.get(0)).getDaysFromToday() == Date.NEVER) {
            array.removeItemAt(0);
        }
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    private void setDate(TaskHolder holder, String date) {
        if (date == null) return;

        int diff_days = Date.differenceBetweenTodayAndDate(date);

        if (diff_days == 0) holder.date.setText(view.getString(R.string.today));
        else if (diff_days == 1) holder.date.setText(view.getString(R.string.tomorrow));
        else holder.date.setText(Date.getPrintableDateWithYear(date));

        if (diff_days < 0) holder.date.setTextColor(view.getColor(R.color.red));
        else holder.date.setTextColor(view.getColor(R.color.blue));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder rv_holder, final int pos) {
        if (getItemViewType(pos) == ElementRecyclerView.CHILD)
            createTaskHolder((TaskHolder) rv_holder, pos);
        else createTaskDateHolder((TaskDateHolder) rv_holder, pos);
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position).getType();
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int view_type) {
        if (view_type == ElementRecyclerView.CHILD)
            return new TaskHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_tasks, parent, false));
        else
            return new TaskDateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_task_date, parent, false));
    }

    private void createTaskHolder(@NonNull final TaskHolder h, final int pos) {
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
        h.time.setText(time);
        setDate(h, date);

        /* Exceptions */

        if (urgent) h.urgent.setVisibility(View.VISIBLE);
        else h.urgent.setVisibility(View.GONE);
        if (description.isEmpty()) h.description.setVisibility(View.GONE);
        else h.description.setVisibility(View.VISIBLE);
        if (date == null) h.date.setVisibility(View.GONE);
        else h.date.setVisibility(View.VISIBLE);
        if (repeat == null) {
            h.repeat_icon.setVisibility(View.GONE);
            h.complete.setVisibility(View.GONE);
        } else {
            h.repeat_icon.setVisibility(View.VISIBLE);
            h.complete.setVisibility(View.VISIBLE);
        }
        if (time == null) {
            h.time.setVisibility(View.GONE);
            h.time_icon.setVisibility(View.GONE);
        } else {
            h.time.setVisibility(View.VISIBLE);
            h.time_icon.setVisibility(View.VISIBLE);
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

        h.complete.setOnClickListener((View v) -> {
            position = pos;
            nextDate();
        });
    }

    private void createTaskDateHolder(@NonNull TaskDateHolder h, final int pos) {
        /* Get Item */

        final TaskDate task_date = (TaskDate) array.get(pos);

        /* Values */

        String date = task_date.getDate();

        /* Layout */

        h.date.setText(date);
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
        view.setBottomText(view.getString(R.string.total_tasks, model.getNumberTasks(list.getId())));
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add() {
        view.startActivityForResult(new Intent(view, NewTaskView.class).putExtra("list", list.getId()), 0);
    }

    public void saveAdd(long id) {
        /* Get Item */

        Task task = model.getTaskById(id);

        /* Modify Array */

        array.add(task);

        updateTitles();

        /* Bottom Text */

        updateBottomText();
    }

    public void edit() {
        view.startActivityForResult(new Intent(view, NewTaskView.class).putExtra("id", array.get(position).getId()), 1);
    }

    public void saveEdit(long id) {
        /* Get Item */

        Task task = model.getTaskById(id);

        /* Modify Array */

        array.updateItemAt(position, task);

        updateTitles();
    }

    public void delete() {
        /* Get Item */

        last_deleted = array.get(position);

        /* Modify Array */

        array.removeItemAt(position);

        updateTitles();

        /* Bottom Text */

        updateBottomText();

        /* Private Functions */

        view.showUndo();

        /* Update DB */

        model.deleteTask((Task) last_deleted);
    }

    public void undo() {
        /* Update DB */

        model.newTask((Task) last_deleted);

        /* Modify Array */

        array.add(last_deleted);

        updateTitles();

        /* Bottom Text */

        updateBottomText();
    }

    /*
     * Complete
     *
     *
     *
     * */

    private void nextDate() {
        Task task = (Task) array.get(position);

        /* Private Functions */

        task.nextDate();

        /* Modify Array */

        array.updateItemAt(position, task);

        updateTitles();

        /* Update DB */

        model.updateTask(task);
    }

    /*
     * Pickers
     *
     *
     *
     * */

}
