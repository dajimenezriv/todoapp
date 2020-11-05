package com.zepo_lifestyle.hack_your_life.views;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.HabitNewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewHabitView extends NewView {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.days_to_finish)
    EditText days_to_finish;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.init_date)
    TextView init_date;

    private HabitNewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_habit);

        ButterKnife.bind(this);

        presenter = new HabitNewPresenter(this);
        recyclerView();
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void setTime(String t) {
        time.setText(t);
    }

    public void setInitDate(String id) {
        init_date.setText(id);
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
     * Date Dialog
     *
     *
     *
     * */

    public void initDate(View v) {
        presenter.initDate();
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
        String dtf = days_to_finish.getText().toString();
        boolean u = urgent.isChecked();
        presenter.confirm(n, d, dtf, u);
    }

    /*
     * Recycler View
     *
     *
     *
     * */

    private void recyclerView() {
        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(lm);
        rv.setAdapter(presenter);
    }

}