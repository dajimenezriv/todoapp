package com.zepo_lifestyle.hack_your_life.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.TaskNewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewTaskView extends NewView {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.repeat_layout)
    LinearLayout repeat_layout;
    @BindView(R.id.repeat_check)
    CheckBox repeat_check;
    @BindView(R.id.repeat_time)
    EditText repeat_time;
    @BindView(R.id.repeat)
    RadioGroup repeat;

    private TaskNewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_task);

        ButterKnife.bind(this);

        repeatCheck();

        Intent intent = getIntent();
        presenter = new TaskNewPresenter(this, intent.getLongExtra("list", -1), intent.getLongExtra("id", -1));
    }

    /*
     * Private Functions
     *
     *
     *
     * */

    public void setTitle(String title) {
        ((TextView) findViewById(R.id.title)).setText(title);
    }

    public void setName(String n) {
        name.setText(n);
    }

    public void setDescription(String d) {
        description.setText(d);
    }

    public void setDate(String id) {
        date.setText(id);
    }

    public void setTime(String t) {
        time.setText(t);
    }

    public void setRepeatCheck(boolean rc) {
        repeat_check.setChecked(rc);
    }

    public void setRepeatTime(String rp) {
        repeat_time.setText(rp);
    }

    public void setRepeat(int t) {
        ((RadioButton) repeat.getChildAt(t)).setChecked(true);
    }

    public void setUrgent(boolean u) {
        urgent.setChecked(u);
    }

    private void repeatCheck() {
        repeat_check.setOnCheckedChangeListener((CompoundButton view, boolean is_checked) -> {
            if (is_checked) repeat_layout.setVisibility(View.VISIBLE);
            else repeat_layout.setVisibility(View.GONE);
        });
    }

    /*
     * Date Dialog
     *
     *
     *
     * */

    public void date(View v) {
        presenter.date();
    }

    public void cancelDate(View v) {
        presenter.cancelDate();
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

    public void cancelTime(View v) {
        presenter.cancelTime();
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
        boolean rc = repeat_check.isChecked();
        int r = repeat.indexOfChild(findViewById(repeat.getCheckedRadioButtonId()));
        String rt = repeat_time.getText().toString();

        presenter.confirm(n, d, rc, r, rt, u);
    }

}