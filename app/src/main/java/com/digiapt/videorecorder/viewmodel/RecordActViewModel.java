package com.digiapt.videorecorder.viewmodel;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class RecordActViewModel {

    private MediaRecorder recorder;
    private boolean isRecording = false;
    private String videpoPath = "";

    public void initRecorder() {
        recorder = new MediaRecorder();
        try {
            recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
            CamcorderProfile cpHigh = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            recorder.setProfile(cpHigh);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        videpoPath = "/sdcard/"+System.currentTimeMillis()+"_videocapture.mp4";
        recorder.setOutputFile(videpoPath);
    }

    public void prepareRecorder(SurfaceHolder surface) throws Exception{
        initRecorder();
        recorder.setPreviewDisplay(surface.getSurface());
        recorder.prepare();
    }

    public String getVideoPath() {
        return videpoPath;
    }

    public void startRecord() {
        isRecording = true;
        recorder.start();
    }

    public void stopRecord() {
        isRecording = false;
        recorder.stop();
    }

    public void clearRecorder() {
        if(recorder != null && isRecording) {
            recorder.stop();
            recorder.release();
        }
        recorder = null;
    }
}
