package com.example.root.automute;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by abanda on 21/04/16.
 */
public class Places extends Fragment {

    LinearLayout linearLayout;
    View switchLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_places,container,false);
        linearLayout = (LinearLayout)v.findViewById(R.id.places_list);

        SharedPreferences placesPrefs = getActivity().getSharedPreferences("Places", Context.MODE_PRIVATE);
        SharedPreferences statusPrefs = getActivity().getSharedPreferences("Status",Context.MODE_PRIVATE);
        HashMap<String,String> places=  (HashMap)placesPrefs.getAll();
        HashMap<String,String>  status=  (HashMap)statusPrefs.getAll();

        final SharedPreferences.Editor editor = statusPrefs.edit();

        if ((places.size()!=0 && status.size()!=0) && (places.size()==status.size())){
            Set keyset = places.keySet();
            Iterator keyIterator = keyset.iterator();
            while (keyIterator.hasNext()){
                final String key = (String)keyIterator.next();
                switchLayout = inflater.inflate(R.layout.switch_layout, null);

                //Se asigna la etiqueta para el switch
                TextView nameTv = (TextView) switchLayout.getRootView().findViewById(R.id.myplace_name);
                nameTv.setText(key);

                //Se asigna el stado del switch (Checked o unchecked) dependiendo de la encontrado en las shared preferences
                final Switch switchWidget = (Switch) switchLayout.getRootView().findViewById(R.id.myplace_switch);
                switchWidget.setChecked("ON".equals(status.get(key)));

                switchWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (!isChecked) {
                            editor.putString(key, "OFF");
                        } else {
                            editor.putString(key, "ON");
                        }
                        editor.commit();
                    }
                });
                //Se agrega el switchLayout al LinearLayout de content_places.xml
                linearLayout.addView(switchLayout);

            }
        }

        return v;
    }

}
