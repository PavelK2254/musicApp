package com.example.development.myapplication.Player;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.development.myapplication.R;

import java.io.IOException;



public class PlayerFragment extends Fragment implements MediaPlayer.OnPreparedListener,MediaController.MediaPlayerControl,SurfaceHolder.Callback,MediaPlayer.OnErrorListener {

    MediaController mediaController;
    MediaPlayer mediaPlayer;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Snackbar mySnackbar;
   TextView mNowPlayingText;
    private ProgressBar spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.player_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = (ProgressBar)view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mySnackbar = Snackbar.make(getView(), getActivity().getString(R.string.video_fail_error), Snackbar.LENGTH_SHORT);
        try {
            mediaPlayer.setDataSource(getArguments().getString("url"));
        } catch (IOException e) {
            e.printStackTrace();
            mySnackbar.show();
            closeFragment();
        }catch (NullPointerException e){
            mySnackbar.show();
            closeFragment();
        }
        mediaController = new MediaController(this.getActivity());
        surfaceView = getView().findViewById(R.id.surfaceViewMain);
        surfaceView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mediaController.show();
                return true;
            }
        });
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mediaPlayer.prepareAsync();



    }

    private void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStackImmediate();
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(this.getActivity().findViewById(R.id.main_audio_view));
        mediaController.setEnabled(true);
        mp.start();
        mediaController.show();
        spinner.setVisibility(View.GONE);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
         mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void start() {
    mediaPlayer.start();
    }

    @Override
    public void pause() {
    mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mySnackbar.show();
        closeFragment();
        return false;
    }
}
