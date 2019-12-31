package stylist.com.glamhub;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SpecicStation extends AppCompatActivity {
    MapView mapView;
    private GoogleMap googleMap;
    private final LatLng mDefaultLocation = new LatLng(-1.2921,36.8219);
    private static final int DEFAULT_ZOOM = 15;
    private  String flat,fStationName,fCost;
    private  String flong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specic_station);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle extras=getIntent().getExtras();
        flat=extras.getString("flat");
        flong=extras.getString("flong");
        fStationName=extras.getString("fStation");
        fCost=extras.getString("fCost");

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setTrafficEnabled(true);
                float lat=Float.valueOf(flat);
                float llong=Float.valueOf(flong);
                //   googleMap.setMyLocationEnabled(true);
                LatLng fStation = new LatLng(lat,llong);



                googleMap.addMarker(new MarkerOptions().position(fStation).title(fStationName).snippet(fCost).icon(BitmapDescriptorFactory.fromResource(R.drawable.gasb)));


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fStation, DEFAULT_ZOOM));
                //googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                // CameraPosition cameraPosition=new CameraPosition.Builder().target(locations).zoom(15).build();
                // googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

}
