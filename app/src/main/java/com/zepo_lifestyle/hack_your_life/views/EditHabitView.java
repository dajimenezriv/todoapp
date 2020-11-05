package com.zepo_lifestyle.hack_your_life.views;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.HabitEditPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditHabitView extends NewView {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.days_left)
    TextView days_left;
    @BindView(R.id.init_date)
    TextView init_date;
    @BindView(R.id.finish_date)
    TextView finish_date;
    @BindView(R.id.progress)
    TextView progress;
    @BindView(R.id.progress_bar)
    ProgressBar progress_bar;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private HabitEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_habit);

        ButterKnife.bind(this);
        progress_bar.setMax(100);

        presenter = new HabitEditPresenter(this, getIntent().getLongExtra("id", -1));
        recyclerView();
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void setName(String n) {
        name.setText(n);
    }

    public void setDescription(String d) {
        description.setText(d);
    }

    public void setTime(String t) {
        time.setText(t);
    }

    public void setDaysLeft(String dl) {
        days_left.setText(dl);
    }

    public void setInitDate(String id) {
        init_date.setText(id);
    }

    public void setFinishDate(String id) {
        finish_date.setText(id);
    }

    public void setProgressBar(int p) {
        progress.setText(getString(R.string.progress, p));
        progress_bar.setProgress(p);
    }

    public void setUrgent(boolean u) {
        urgent.setChecked(u);
    }

    /*
     * Time Picker
     *
     *
     *
     * */

    public void time(View v) {
        presenter.time();
    }

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(View v) {
        String n = name.getText().toString();
        String d = description.getText().toString();
        boolean u = urgent.isChecked();
        presenter.confirm(n, d, u);
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    private void recyclerView() {
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recycler_view.setLayoutManager(lm);
        recycler_view.setAdapter(presenter);
        recycler_view.scrollToPosition(presenter.getTodayPosition());
    }

}