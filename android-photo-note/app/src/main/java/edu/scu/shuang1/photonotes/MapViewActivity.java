package edu.scu.shuang1.photonotes;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    LatLng photoLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        //add map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        double latitude = bundle.getDouble("latitude");
        double longitude = bundle.getDouble("longitude");

        photoLocation = new LatLng(latitude, longitude);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.addMarker(new MarkerOptions().position(photoLocation).title("You take photo here").snippet("Android, CA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(photoLocation, 17.0f));
        // set up UI control
        UiSettings ui = mMap.getUiSettings();
        ui.setAllGesturesEnabled(true);
        ui.setCompassEnabled(true);
        ui.setZoomControlsEnabled(true);
    }
}
