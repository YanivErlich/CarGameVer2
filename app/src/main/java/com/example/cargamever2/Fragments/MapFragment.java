package com.example.cargamever2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.cargamever2.Model.Record;
import com.example.cargamever2.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        return view;
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }


    public void markLocation(double longitude, double latitude ) {
        if (googleMap != null) {
            LatLng location = new LatLng(longitude, latitude);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title("My Location");
            googleMap.addMarker(markerOptions);

            // Move the camera to the location
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, 10);
            googleMap.moveCamera(cameraUpdate);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }
}
