package com.example.root.automute;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by abanda on 12/04/16.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    LocationManager locationManager;
    AudioManager audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Fragment myHome = new Home();
        android.support.v4.app.FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, myHome);
        fragmentTransaction1.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Volumen actual del dispositivo
        final int initialVolume = audio.getStreamVolume(AudioManager.STREAM_RING);

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                List<String> activePlaces = getActivePlaces();

                if (activePlaces.size() > 0) {
                    double currentLat = location.getLatitude();
                    double currentLon = location.getLongitude();
                    int matchCounter = 0;

                    System.out.println("Actuales: " + currentLat + ", " + currentLon);

                    SharedPreferences placesPrefs = getSharedPreferences("Places", Context.MODE_PRIVATE);
                    HashMap<String, String> places = (HashMap) placesPrefs.getAll();

                    for (String key : activePlaces) {
                        String[] coords = places.get(key).split(",");
                        double lat = Double.valueOf(coords[0]);
                        double lon = Double.valueOf(coords[1]);
                        System.out.println("Coordenadas de " + key + " : " + lat + ", " + lon);

                        if (Math.abs(currentLat - lat) <= 0.0003 && Math.abs(currentLon - lon) <= 0.0003) {
                            audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, 0, 0);
                            matchCounter += 1;
                            printToast("Estas en: " + key);
                        }
                    }
                    /*
                        Si la ubicacion actual no corresponde con ninguna ubicacion guardad por el usuario, se regresa al volumen
                        que tenia el dispositivo antes de silenciarlo.
                    */
                    if (matchCounter == 0) {
                        audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION, initialVolume, 0);
                    }

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        /*
            Se solicita una actualizacion al proveedor GPS cada 10 segundos
         */
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, listener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_home) {
            fragment = new Home();
            setTitle(R.string.Home);
        } else if (id == R.id.nav_map) {
            fragment = new Map();
            setTitle(R.string.Mapa);
        }else if (id == R.id.nav_places){
            fragment =  new Places();
            setTitle(R.string.mis_lugares);
        }
        String tag = String.valueOf(id);
        android.support.v4.app.FragmentTransaction fragmentTransaction1
                = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.content_frame, fragment);
        fragmentTransaction1.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
        Regresa una lista con las claves de los lugares del usuario que tienen status 'ON'
     */
    public List<String> getActivePlaces() {
        SharedPreferences statusPrefs = getSharedPreferences("Status", Context.MODE_PRIVATE);
        HashMap<String, String> status = (HashMap) statusPrefs.getAll();
        Set keyset = status.keySet();
        Iterator keyIterator = keyset.iterator();

        List<String> activePlaces = new ArrayList<String>();
        while (keyIterator.hasNext()){
            String key = (String)keyIterator.next();
            if (status.get(key).equals("ON")) {
                activePlaces.add(key);
            }
        }
        return activePlaces;
    }

    public void printToast(String location) {
        Toast.makeText(this, location, Toast.LENGTH_SHORT).show();
    }



}
