package com.example.root.automute;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private FloatingActionButton fab;
    private  LatLng markerCoo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_map, container, false);
       SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#757575")));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            createMarker(markerCoo);
                fab.setEnabled(false);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#757575")));

            }
        });
        return v;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                fab.setEnabled(true);
                fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#287272")));
                markerCoo = latLng;
            }
        });
        // Add a marker in Sydney and move the camera
    /*    LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    public void createMarker(LatLng latLng){
        AlertDialog dialog = createDialog(latLng);
        dialog.show();
    }
    public AlertDialog createDialog(final LatLng latlng){
        AlertDialog.Builder bld =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View view = getActivity().getLayoutInflater().inflate(R.layout.new_place,null);
        bld.setView(view).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText txt = (EditText)view.findViewById(R.id.nwplace);
                mMap.addMarker(new MarkerOptions().position(latlng).title(txt.getText().toString()));
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).setTitle("Agregar Lugar");
        return bld.create();
    }
}
