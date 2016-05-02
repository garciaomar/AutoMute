package com.example.root.automute;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Omar on 27/04/2016.
 */
public class MuteService extends Service {

    final class MuteThread implements Runnable{
        int serviceId;
        AudioManager audio;
        int initialVolume;
        boolean isMuted = false;
        MuteThread(int serviceId) {
            this.serviceId = serviceId;
            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            initialVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
            System.out.println("Volumen actual: " + initialVolume);
        }


        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    try {
                        this.wait(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if (isMuted) {
                    audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, initialVolume, 0);
                    isMuted = false;
                } else {
                    System.out.println("No esta en mute ");
                    audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                    System.out.println("Nuevo volumen " + audio.getStreamVolume(AudioManager.STREAM_RING));
                    isMuted = true;
                }
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new MuteThread(startId));
        thread.start();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {

    }
}
