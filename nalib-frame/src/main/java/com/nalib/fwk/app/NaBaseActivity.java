package com.nalib.fwk.app;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nalib.fwk.utils.StatusBarUtils;

public class NaBaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        findViews();
        registerListeners();
    }


    protected void setWindowStatusBarColor(int colorResId) {
        StatusBarUtils.setWindowStatusBarColor(this, colorResId);
    }

    protected void findViews() {

    }

    protected void registerListeners() {

    }
}
