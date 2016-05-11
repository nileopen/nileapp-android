package com.nile.app.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nalib.frame.activity.NaBaseActivity;

public class MainActivity extends NaBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
