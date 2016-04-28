package com.example.root.automute;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Erick on 27/04/2016.
 */
public class MuteService extends Service {

    final class MuteThread implements Runnable{
        int serviceId;
        AudioManager audio;
        int ringVolume;
        boolean isMuted = false;
        MuteThread(int serviceId) {
            this.serviceId = serviceId;
            audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            ringVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
            System.out.println("Volumen actual: " + ringVolume);
        }


        @Override
        public void run() {
            if (isMuted) {
                audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, ringVolume, 0);
                isMuted = false;
            } else {
                System.out.println("No esta en mute ");
                audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                System.out.println("Nuevo volumen " + audio.getStreamVolume(AudioManager.STREAM_RING));
                isMuted = true;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MuteThread thread = new MuteThread(startId);
        thread.run();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
