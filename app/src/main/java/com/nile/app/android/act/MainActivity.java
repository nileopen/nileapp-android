package com.nile.app.android.act;

import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.nalib.fwk.app.NaBaseActivity;
import com.nile.app.android.R;
import com.nile.uninstall.UninstallJni;

public class MainActivity extends NaBaseActivity {

    private final static String Tag = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.logo_image);
        Uri logoUri = Uri.parse("https://raw.githubusercontent.com/liaohuqiu/fresco-docs-cn/docs/static/fresco-logo.png");
        simpleDraweeView.setImageURI(logoUri);

        SimpleDraweeView aniView = (SimpleDraweeView) findViewById(R.id.ani_image);
        Uri aniImageUri = Uri.parse("https://camo.githubusercontent.com/588a2ef2cdcfb6c71e88437df486226dd15605b3/687474703a2f2f737261696e2d6769746875622e71696e6975646e2e636f6d2f756c7472612d7074722f73746f72652d686f7573652d737472696e672d61727261792e676966");
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(aniImageUri)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        aniView.setController(controller);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Android 5.0以上主进程被杀后，fork出的进程也会被杀，所以无法实现监听
            UninstallJni ujni = new UninstallJni();
            ujni.setUrl("http://www.youja.cn");
            ujni.registerUninstall(this);

//        }
//        String us = ujni.getUserSerial(this);
//        Log.e("uninstall_jni", "us=" + us);
    }
}
