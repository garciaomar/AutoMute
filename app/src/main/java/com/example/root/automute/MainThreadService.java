package com.example.root.automute;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Omar on 28/04/2016.
 */
public class MainThreadService extends Service {
    AudioManager audio;
    int initialVolume;
    boolean isMuted = false;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
            try {
                this.wait(10000);
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
