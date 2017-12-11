package com.hdyg.zhimaqb.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;

import com.hdyg.zhimaqb.R;
import com.hdyg.zhimaqb.ui.CameraSurfaceView;
import com.hdyg.zhimaqb.util.LogUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends BaseActivity {
    private CameraSurfaceView mCameraSurfaceView;
    private ProgressBar progressBar;
    private String path;
    private String fileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
        Button button = (Button) findViewById(R.id.takePic);

        path = getIntent().getStringExtra("path");
        fileType = getIntent().getStringExtra("fileType");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                mCameraSurfaceView.takePicture(path, progressBar, fileType);
            }
        });

    }

}
