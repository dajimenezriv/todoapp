package com.zepo_lifestyle.hack_your_life.views;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.ProductNewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewProductView extends NewView {

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.urgent)
    CheckBox urgent;
    @BindView(R.id.pending)
    CheckBox pending;

    private ProductNewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_product);

        ButterKnife.bind(this);

        presenter = new ProductNewPresenter(this, getIntent().getLongExtra("id", -1));
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

    public void setDescription(String m) {
        description.setText(m);
    }

    public void setPrice(String p) {
        price.setText(p);
    }

    public void setUrgent(boolean u) {
        urgent.setChecked(u);
    }

    public void setPending(boolean p) {
        pending.setChecked(p);
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
        String p = price.getText().toString();
        boolean u = urgent.isChecked();
        boolean pe = pending.isChecked();
        presenter.confirm(n, d, p, u, pe);
    }

}