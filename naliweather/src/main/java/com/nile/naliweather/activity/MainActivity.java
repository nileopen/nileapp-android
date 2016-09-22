package com.nile.naliweather.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.nalib.fwk.app.NaBaseActivity;
import com.nalib.fwk.utils.NaLog;
import com.nile.app.naliweather.R;
import com.nile.naliweather.api.WeatherApiCallback;
import com.nile.naliweather.api.WeatherApiImpl;
import com.nile.naliweather.api.bean.Item;
import com.nile.naliweather.view.EyeView;
import com.nile.naliweather.view.PullDownListView;
import com.nile.naliweather.view.YProgressView;
import com.nile.naliweather.view.adpater.ListAdpater;

import java.util.List;


public class MainActivity extends NaBaseActivity implements View.OnClickListener {

    private static final String Tag = "MainActivity";

//    private Button btFresh;
//
//    private TextView tvInfo;

    private PullDownListView pullDownListView;
    private EyeView eyeView;
    private YProgressView progressView;
    private ListAdpater mAdapter;

    private String cityName = "上海";

    private WeatherApiCallback mWetherApiCallback = new WeatherApiCallback() {
        @Override
        public void onResult(int ret, String error) {
//            if (ret == Callback.Result_Success) {
            mAdapter.notifyDataSetChanged();
//            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWindowStatusBarColor(R.color.colorPrimary);
    }

    private void refresh() {
        WeatherApiImpl.getWeatherApi().getDataInfo(cityName);
    }

    @Override
    protected void findViews() {
//        btFresh = (Button) findViewById(R.id.btFresh);
//        tvInfo = (TextView) findViewById(R.id.tvInfo);
        pullDownListView = (PullDownListView) findViewById(R.id.pullDownListView);
        eyeView = (EyeView) findViewById(R.id.eyeView);
        progressView = (YProgressView) findViewById(R.id.progressView);
    }

    @Override
    protected void registerListeners() {
//        btFresh.setOnClickListener(this);
        mAdapter = new ListAdpater(this, new ListAdpater.DataChangeListener() {
            @Override
            public List<Item> getData() {
                return WeatherApiImpl.getWeatherApi().getDataInfo(cityName);
            }
        });

        pullDownListView.setAdapter(mAdapter);
        pullDownListView.setOnPullHeightChangeListener(new PullDownListView.OnPullHeightChangeListener() {
            @Override
            public void onTopHeightChange(int headerHeight, int pullHeight) {
                float progress = (float) pullHeight / (float) headerHeight;

                if (progress < 0.5) {
                    progress = 0.0f;
                } else {
                    progress = (progress - 0.5f) / 0.5f;
                }


                if (progress > 1.0f) {
                    progress = 1.0f;
                }

                if (!pullDownListView.isRefreshing()) {
                    eyeView.setProgress(progress);
                }
            }

            @Override
            public void onBottomHeightChange(int footerHeight, int pullHeight) {
                float progress = (float) pullHeight / (float) footerHeight;

                if (progress < 0.5) {
                    progress = 0.0f;
                } else {
                    progress = (progress - 0.5f) / 0.5f;
                }

                if (progress > 1.0f) {
                    progress = 1.0f;
                }

                if (!pullDownListView.isRefreshing()) {
                    progressView.setProgress(progress);
                }

            }

            @Override
            public void onRefreshing(final boolean isTop) {
                NaLog.e(Tag, "onRefreshing isTop=" + isTop);
                if (isTop) {
                    eyeView.startAnimate();
                    refresh();
                } else {
                    progressView.startAnimate();
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        pullDownListView.pullUp();
                        if (isTop) {
                            eyeView.stopAnimate();
                        } else {
                            progressView.stopAnimate();
                        }
                    }

                }, 3000);
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerApiCallback();
    }

    private void registerApiCallback() {
        WeatherApiImpl.getWeatherApi().setCallback(mWetherApiCallback);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterApiCallback();
    }

    private void unregisterApiCallback() {
        WeatherApiImpl.getWeatherApi().setCallback(null);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btFresh:
//                refresh();
//                break;
//            case R.id.tvInfo:
//                break;
//        }
    }
}
