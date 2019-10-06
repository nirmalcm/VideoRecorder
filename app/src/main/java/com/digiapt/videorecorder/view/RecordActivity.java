package com.digiapt.videorecorder.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.digiapt.videorecorder.R;
import com.digiapt.videorecorder.viewmodel.RecordActViewModel;

public class RecordActivity extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    private RecordActViewModel mRecordActViewModel;
    private SurfaceView mSurfaceView;
    private SurfaceHolder holder;
    private Button mRecord;
    private Button mPlay;

    private static final String TEXT_START  =   "start";
    private static final String TEXT_STOP   =   "stop";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setScreenrientation();
        setContentView(R.layout.activity_record);
        if(verifyStoragePermissions(this)) {
            initialise();
            setRecordButton(TEXT_START);
        }
    }

    private void initialise() {
        mRecordActViewModel = new RecordActViewModel();
        mSurfaceView = findViewById(R.id.surface_camera);

        mRecord = findViewById(R.id.record);
        mRecord.setOnClickListener(this);

        mPlay = findViewById(R.id.play);
        mPlay.setOnClickListener(this);

        holder = mSurfaceView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceView.setClickable(true);
        mSurfaceView.setOnClickListener(this);
    }

    public static boolean verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_EXTERNAL_STORAGE) {
            initialise();
            setRecordButton(TEXT_START);
        }
    }

    private void setRecordButton(String text) {
        mRecord.setText(text);
        mRecord.setTag(mRecord.getId(), text);
    }

    private void setScreenrientation() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mRecordActViewModel.prepareRecorder(surfaceHolder);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Something went wrong !", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mRecordActViewModel.clearRecorder();
    }

    public void playVideo() {
        String videoPath = mRecordActViewModel.getVideoPath();
        Intent intent = new Intent(RecordActivity.this, PlayerActivity.class);
        intent.putExtra("video_path", videoPath);
        startActivity(intent);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.record) {
            String text = (String) mRecord.getTag(mRecord.getId());
            if(text.equalsIgnoreCase(TEXT_START)) {
                mRecordActViewModel.startRecord();
                setRecordButton(TEXT_STOP);
            }  else if(text.equalsIgnoreCase(TEXT_STOP)) {
                mRecordActViewModel.stopRecord();
                setRecordButton(TEXT_START);
            }
        }
        if(view.getId() == R.id.play) {
            playVideo();
        }
    }
}
