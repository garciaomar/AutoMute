package com.example.root.automute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by abanda on 21/04/16.
 */
public class Places extends Fragment {
    Switch homeSwitch, workSwitch;
    TextView tv;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.content_places,container,false);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        homeSwitch = (Switch) getActivity().findViewById(R.id.homeSwitch);
        homeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getActivity(), MuteService.class);
                    startMuteService(intent, "AutoMute activado en Casa");
                } else {
                    Intent intent = new Intent(getActivity(), MuteService.class);
                    stopMuteService(intent, "AutoMute desactivado en Casa");
                }
            }
        });

        workSwitch = (Switch) getActivity().findViewById(R.id.workSwitch);
        workSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getActivity(), MuteService.class);
                    startMuteService(intent, "AutoMute activado en Trabajo");
                } else {
                    Intent intent = new Intent(getActivity(), MuteService.class);
                    stopMuteService(intent, "AutoMute desactivado en Trabajo");
                }
            }
        });
    }

    public void startMuteService(Intent intent, String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        getActivity().startService(intent);
    }

    public void stopMuteService(Intent intent, String msg) {
        Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        getActivity().stopService(intent);
    }
}
