package com.zepo_lifestyle.hack_your_life.presenters;

import android.app.Activity;
import android.content.Intent;

import com.zepo_lifestyle.hack_your_life.views.MainView;

public class MainPresenter {

    static final int ADD = 0;
    static final int EDIT = 1;
    private static final int LIST = 0;
    private static final int TASK = 1;
    private static final int HABIT = 2;
    private static final int PRODUCT = 3;

    private MainView view;

    private Presenter current_presenter;

    private ListPresenter list;
    private TaskPresenter task;
    private HabitPresenter habit;
    private ProductPresenter product;

    /*
     * List
     * Task
     * Task Completed
     * Habit
     * Product
     *
     * */
    private int position;

    public MainPresenter(MainView view) {
        this.view = view;

        list();
    }

    /*
     * Open Presenter
     *
     *
     *
     * */

    private void list() {
        if (list == null) list = new ListPresenter(view);

        current_presenter = list;
        position = LIST;
        openPresenter();
    }

    public void task() {
        /* Double Tap Task Menu */
        if (current_presenter == task) {
            list();
            return;
        }

        if (task == null) task = new TaskPresenter(view);

        current_presenter = task;
        position = TASK;
        openPresenter();
    }

    public void habit() {
        if (habit == null) habit = new HabitPresenter(view);

        current_presenter = habit;
        position = HABIT;
        openPresenter();
    }

    public void product() {
        if (product == null) product = new ProductPresenter(view);

        current_presenter = product;
        position = PRODUCT;
        openPresenter();
    }

    private void openPresenter() {
        current_presenter.init();

        view.setAdapter(current_presenter);
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add() {
        current_presenter.add();
    }

    public void undo() {
        current_presenter.undo();
    }

    /*
     * Back Button
     *
     *
     *
     * */

    public void onBackPressed() {
        if (position == TASK) list();
        else view.finish();
    }

    /*
     * Activity Result
     * Add / Edit
     *
     *
     * */

    public void onActivityResult(int request_code, int result_code, Intent data) {
        if (result_code == Activity.RESULT_OK) {
            long id = data.getLongExtra("id", -1);

            if (request_code == ADD) current_presenter.saveAdd(id);
            else if (request_code == EDIT) current_presenter.saveEdit(id);
        }
    }

}
