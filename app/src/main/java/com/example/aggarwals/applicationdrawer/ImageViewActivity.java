package com.example.aggarwals.applicationdrawer;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by AGGARWAL'S on 8/2/2015.
 */
public class ImageViewActivity extends Activity {

    private ImageView imageView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view_activity);


        final String PhotoPath = getIntent().getStringExtra(AddNote.PHOTO_PATH_VIEWER_ACTIVITY);
        imageView = (ImageView)findViewById(R.id.image_view_activiy_image);
        progressBar = (ProgressBar)findViewById(R.id.image_view_progress);
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){}
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        Picasso.with(ImageViewActivity.this).load(new File(PhotoPath)).into(imageView);
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }
}
