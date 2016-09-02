package com.nile.naliweather.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nalib.fwk.app.NaBaseActivity;
import com.nalib.fwk.exception.NaHttpException;
import com.nalib.fwk.http.INaCallBack;
import com.nalib.fwk.http.INaParams;
import com.nalib.fwk.http.INaRequest;
import com.nalib.fwk.http.NaHttpParams;
import com.nalib.fwk.http.NaHttpRequest;
import com.nalib.fwk.http.NaHttpType;
import com.nalib.fwk.http.NaHttpUtil;
import com.nalib.fwk.json.NaJsonUtil;
import com.nalib.fwk.utils.NaLog;
import com.nile.app.naliweather.R;
import com.nile.naliweather.data.WeatherResponse;


public class MainActivity extends NaBaseActivity implements View.OnClickListener {

    private static final String Tag = "MainActivity";

    private Button btFresh;

    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWindowStatusBarColor(R.color.colorPrimary);
    }

    @Override
    protected void findViews() {
        btFresh = (Button) findViewById(R.id.btFresh);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
    }

    @Override
    protected void registerListeners() {
        btFresh.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btFresh:
                refresh();
                break;
            case R.id.tvInfo:
                break;
        }
    }

    private void refresh() {
        try {
            INaParams params = new NaHttpParams();
            try {
                params.addParam("cityname", new String("上海".getBytes(), "utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            params.addParam("key", "b2b6bd36fc846b24e10055acfc03792b");
            INaRequest request = new NaHttpRequest("http://op.juhe.cn/onebox/weather/query", NaHttpType.Get, new INaCallBack() {
                @Override
                public void onError(NaHttpException e, int id) {
                    NaLog.e(Tag, "id=" + id + ",err=" + e.getMessage());
                }

                @Override
                public void onResponse(final String response, int id) {
                    NaLog.e(Tag, "id=" + id + ",response=" + response);
                    try {
                        Object obj = NaJsonUtil.stringToBean(response, WeatherResponse.class);
                        WeatherResponse weatherResponse = (WeatherResponse) obj;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvInfo.setText(response);
                        }
                    });
                }
            });
            request.setParams(params);

            NaHttpUtil.getNaHttp().setRequest(request);
        } catch (NaHttpException e) {
            e.printStackTrace();
        }
    }
}
