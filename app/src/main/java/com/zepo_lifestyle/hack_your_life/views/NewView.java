package com.zepo_lifestyle.hack_your_life.views;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public abstract class NewView extends CustomActivity {

    /*
     * Actions
     *
     *
     *
     * */

    public abstract void confirm(View v);

    public void saveChanges(long id) {
        setResult(Activity.RESULT_OK, new Intent().putExtra("id", id));
        finish();
    }

    /*
     * Back Button
     *
     *
     *
     * */

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED, new Intent());
        finish();
    }

}