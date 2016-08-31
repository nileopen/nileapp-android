package com.nile.app.android.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nile.app.android.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btCamera = (Button) findViewById(R.id.btCamera);
        btCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCameraActivity();
            }
        });

        Button btMain = (Button) findViewById(R.id.btMain);
        btMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goMainActivity();
            }
        });
    }

    private void goCameraActivity() {
        startActivity(new Intent(this, CameraActivity.class));
    }

    private void goMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
