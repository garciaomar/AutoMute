package com.example.root.automute;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by abanda on 20/04/16.
 */
public class Home extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View v =inflater.inflate(R.layout.content_home,container,false);
        TextView activePlaces = (TextView) v.findViewById(R.id.placeView);
        SharedPreferences prefs2 = getActivity().getSharedPreferences("Status", Context.MODE_PRIVATE);
        HashMap<String,String> status=  (HashMap)prefs2.getAll();
        int actplc=0;
        Set keyset = status.keySet();
        Iterator keyIterator = keyset.iterator();
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            if (status.get(key).equalsIgnoreCase("on")) {
            actplc++;
            }
        }
        activePlaces.setText(actplc+"");
        return v;
    }
}
