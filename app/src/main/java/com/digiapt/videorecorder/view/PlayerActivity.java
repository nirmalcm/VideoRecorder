package com.digiapt.videorecorder.view;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.digiapt.videorecorder.R;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        receiveIntent();
        playVideo();
    }

    private void receiveIntent() {
        videoPath = getIntent().getExtras().getString("video_path");
    }

    private String videoPath = "";
    private void playVideo() {
        VideoView videoView =(VideoView)findViewById(R.id.videoView1);
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri= Uri.parse(videoPath);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}
