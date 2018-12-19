package com.fundamentos.abisu.contactosmas.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View.OnClickListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fundamentos.abisu.contactosmas.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapFragment extends Fragment implements OnMapReadyCallback, OnClickListener, GoogleMap.OnMarkerDragListener {

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;

    private Geocoder geocoder;
    private List<Address> addressList;

    private MarkerOptions maker;

    private FloatingActionButton fab;

    public MapFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);

        fab.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if(mapView!=null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        LatLng place = new LatLng(17.5307481, 99.4986123);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        maker = new MarkerOptions();
        maker.position(place);
        maker.title("Mi marcador");
        maker.draggable(true);
        maker.snippet("Datos");
        maker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_star_on));

        gMap.addMarker(maker);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(place));
        gMap.animateCamera(zoom);

        gMap.setOnMarkerDragListener(this);

        geocoder = new Geocoder(getContext(), Locale.getDefault());
    }

    @Override
    public void onClick(View view) {
        this.checkIfGPSIsEnabled();
    }

    private void checkIfGPSIsEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal==0){
                showInfoAlert();
            }
        }catch (Settings.SettingNotFoundException e){
            e.printStackTrace();
        }
    }

    private void showInfoAlert(){
        new AlertDialog.Builder(getContext()).setTitle("Señal GPS").setMessage("Señal GPS deshabilitada. Te gustaria habilitarla")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar",null)
                .show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        double latitude = marker.getPosition().latitude;
        double longitude = marker.getPosition().longitude;

        try {
            addressList = geocoder.getFromLocation(latitude,longitude,1);
        }catch (IOException e){
            e.printStackTrace();
        }

        String address = addressList.get(0).getAddressLine(0);
        String city = addressList.get(0).getLocality();
        String state = addressList.get(0).getAdminArea();
        String country = addressList.get(0).getCountryName();
        String postalCode = addressList.get(0).getPostalCode();

        marker.setSnippet(address);
        marker.showInfoWindow();

        Toast.makeText(getContext(), "address: " + address + "\n" +
                "city: "+city+"\n"+
                "state: "+state+"\n"+
                "country: "+country+"\n"+
                "postalCode: "+postalCode+"\n", Toast.LENGTH_SHORT).show();
    }
}
