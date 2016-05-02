package com.example.root.automute;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abanda on 20/04/16.
 */
public class Home extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     View v =inflater.inflate(R.layout.content_home,container,false);

        return v;
    }
}
