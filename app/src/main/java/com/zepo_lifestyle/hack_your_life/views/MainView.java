package com.zepo_lifestyle.hack_your_life.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.zepo_lifestyle.hack_your_life.R;
import com.zepo_lifestyle.hack_your_life.presenters.MainPresenter;
import com.zepo_lifestyle.hack_your_life.presenters.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainView extends CustomActivity {

    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bottom_text)
    TextView bottom_text;
    @BindView(R.id.menu)
    BottomNavigationView menu;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        ButterKnife.bind(this);

        recyclerView();
        presenter = new MainPresenter(this);

        menu();
    }

    /*
     * Information
     *
     *
     *
     * */

    public void setTitle(String string) {
        title.setText(string);
    }

    public void setTitle(int string) {
        setTitle(getString(string));
    }

    public void setBottomText(String text) {
        bottom_text.setText(text);
    }

    /*
     * Menu
     *
     *
     *
     * */

    private void menu() {
        menu.setOnNavigationItemSelectedListener((@NonNull MenuItem item) -> {
            switch (item.getItemId()) {
                case R.id.task:
                    task();
                    break;
                case R.id.habit:
                    habit();
                    break;
                case R.id.product:
                    product();
                    break;
            }
            return true;
        });
    }

    public void task() {
        presenter.task();
    }

    private void habit() {
        presenter.habit();
    }

    private void product() {
        presenter.product();
    }

    /*
     * Options
     *
     *
     *
     * */

    public void add(View view) {
        presenter.add();
    }

    public void showUndo() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.root), "Undo", Snackbar.LENGTH_LONG);
        snackbar.setAction("undo", v -> undo());
        snackbar.show();
    }

    private void undo() {
        presenter.undo();
    }

    /*
     * RecyclerView
     *
     *
     *
     * */

    private void recyclerView() {
        recycler_view = findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void setAdapter(Presenter presenter) {
        recycler_view.setAdapter(presenter);
    }

    /*
     * Back Button
     *
     *
     *
     * */

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    /*
     * Activity Result
     *
     *
     *
     * */

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data) {
        presenter.onActivityResult(request_code, result_code, data);
    }

}