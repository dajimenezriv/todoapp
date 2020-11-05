package com.zepo_lifestyle.hack_your_life.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.ListNewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewListView extends NewView {

    @BindView(R.id.name)
    EditText name;

    private ListNewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_list);

        ButterKnife.bind(this);

        presenter = new ListNewPresenter(this, getIntent().getLongExtra("id", -1));
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

    /*
     * Actions
     *
     *
     *
     * */

    public void confirm(View v) {
        String n = name.getText().toString();
        presenter.confirm(n);
    }

}