package com.zepo_lifestyle.hack_your_life.views;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zepo_lifestyle.hack_your_life.R;

public abstract class CustomActivity extends Activity {

    /*
     * Show Message
     *
     *
     *
     * */

    public void showMessage(int message) {
        showMessage(getString(message));
    }

    public void showMessage(String message) {
        View layout = getLayoutInflater().inflate(R.layout.toast, findViewById(R.id.linear_layout));
        ((TextView) layout.findViewById(R.id.text)).setText(message);

        Toast toast = new Toast(this);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

}
