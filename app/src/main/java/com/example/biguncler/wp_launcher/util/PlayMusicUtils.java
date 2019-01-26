package com.example.biguncler.wp_launcher.util;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class PlayMusicUtils {
    private static MediaPlayer mediaPlayer;

    public static void playSound(Activity activity, int resId) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        if (mediaPlayer.isPlaying()) {
            return;
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.reset();
                    }
                }
        );

        AssetFileDescriptor file = activity.getResources().openRawResourceFd(resId);
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.prepare();
        } catch (Exception e) {
            mediaPlayer = null;
            e.printStackTrace();
        }
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
