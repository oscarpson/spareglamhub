package stylist.com.glamhub;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.ArrayList;

import static stylist.com.glamhub.R.id.map;

public class Getlocation extends AppCompatActivity
        implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, OnMapReadyCallback {

    public static final String TAG = StationMap.class.getSimpleName();

    /*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    private GoogleApiClient googleApiClient;
    SharedPreferences pref;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation;
    private CameraPosition mCameraPosition;
    private static final String KEY_CAMERA_POSITION = "camera_position";

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private final LatLng mDefaultLocation = new LatLng(-1.2921, 36.8219);
    private static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private GoogleMap googleMap;
    MapView mapView;

    TextView tvLatitude, tvLongitude, tvTime;
     /*   final String TAG = "GPS";
        private final static int ALL_PERMISSIONS_RESULT = 101;
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
        private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

        TextView tvLatitude, tvLongitude, tvTime;
        LocationManager locationManager;
        Location loc;
        ArrayList<String> permissions = new ArrayList<>();
        ArrayList<String> permissionsToRequest;
        ArrayList<String> permissionsRejected = new ArrayList<>();
        boolean isGPS = false;
        boolean isNetwork = false;
        boolean canGetLocation = true;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getlocation);

        mapView = (MapView)findViewById(map);

        pref = getApplicationContext().getSharedPreferences("location", 0);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleApiClient = new GoogleApiClient
                .Builder(getApplicationContext())
                //.enableAutoManage(getActivity(), 34992, this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationChecker(mGoogleApiClient, this);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setTrafficEnabled(true);
                //updateLocationUI();

                // Get the current location of the device and set the position of the map.
                // getDeviceLocation();
                //   googleMap.setMyLocationEnabled(true);
                // editor.putString("lats",currentLatitude+"");
                // editor.putString("longs",currentLongitude+"");
                String lats, longs;
                lats = pref.getString("lats", null);
                longs = pref.getString("longs", null);
                if (lats != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(Double.valueOf(lats),
                                    Double.valueOf(longs)), DEFAULT_ZOOM));
                    LatLng now = new LatLng(Double.valueOf(lats),
                            Double.valueOf(longs));
                    LatLng petrol = new LatLng(-1.2179869, 36.8902669);
                    googleMap.addMarker(new MarkerOptions().position(petrol).title("Abundance Spares").snippet("Opposite Mwala Auto Spares +254711368518").icon(BitmapDescriptorFactory.fromResource(R.drawable.gasb)));
                    googleMap.addMarker(new MarkerOptions().position(now).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current)));

                    //  Toast.makeText(getContext(), "now"+now, Toast.LENGTH_SHORT).show();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(now, DEFAULT_ZOOM));


                } else {
                    // googleMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("Marker jos").snippet("i will add stations"));


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                }

                //googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                // CameraPosition cameraPosition=new CameraPosition.Builder().target(locations).zoom(15).build();
                // googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvTime = (TextView) findViewById(R.id.tvTime);
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapView.getMapAsync(this);
            // mapView = (MapView) View.findViewById(R.id.map);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setTrafficEnabled(true);
        float x = 9;
        mMap.addMarker(new MarkerOptions().position(new LatLng(-1.2921, 36.8219)));


    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        //  Toast.makeText(getContext(), "this are"+location, Toast.LENGTH_SHORT).show();
        Log.d("newlocx2", "" + location);
        mMap.clear();
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lats", currentLatitude + "");
        editor.putString("longs", currentLongitude + "");
        editor.commit();
        // Toast.makeText(getContext(), "latslots"+currentLatitude+"\n"+currentLongitude, Toast.LENGTH_SHORT).show();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Your current location").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current)));

      /*  Circle circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(200)
                .fillColor(0x5500ff00)
                .strokeWidth(3).strokeColor(Color.BLACK));
        circle.setCenter(latLng);*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mLastKnownLocation = location;
        // mMap.addCircle(circle)

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
       /* MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Your Locaion Check nearby stations");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    /*
     * Google Play services can resolve some errors it detects.
     * If the error has a resolution, try sending an Intent to
     * start a Google Play services activity that can resolve
     * error.
     */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
        /*
         * If no resolution is available, display a dialog to the
         * user with the error.
         */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(true);
    }

    public void locationChecker(final GoogleApiClient mGoogleApiClient, final Activity activity) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        //  Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        //  handleNewLocation(location);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    activity, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    private void getDeviceLocation()
    {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
          /*  LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
            // Toast.makeText(this, "trying to get current location", Toast.LENGTH_SHORT).show();
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
            mMap.setMaxZoomPreference(15);
            mMap.addMarker(new MarkerOptions().title("you are here"));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        // A step later in the tutorial adds the code to get the device location.
    }

}
           /* tvLatitude = (TextView) findViewById(R.id.tvLatitude);
            tvLongitude = (TextView) findViewById(R.id.tvLongitude);
            tvTime = (TextView) findViewById(R.id.tvTime);

            locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
            isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            /*permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionsToRequest = findUnAskedPermissions(permissions);

            if (!isGPS && !isNetwork) {
                Log.d(TAG, "Connection off");
                showSettingsAlert();
                getLastLocation();
            } else {
                Log.d(TAG, "Connection on");
                // check permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsToRequest.size() > 0) {
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                                ALL_PERMISSIONS_RESULT);
                        Log.d(TAG, "Permission requests");
                        canGetLocation = false;
                    }
                }

                // get location
                getLocation();
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "onLocationChanged");
            updateUI(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {
            getLocation();
        }

        @Override
        public void onProviderDisabled(String s) {
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        }

        private void getLocation() {
            try {
                if (canGetLocation) {
                    Log.d(TAG, "Can get location");
                    if (isGPS) {
                        // from GPS
                        Log.d(TAG, "GPS on");
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);

                        if (locationManager != null) {
                            loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (loc != null)
                                updateUI(loc);
                        }
                    } else if (isNetwork) {
                        // from Network Provider
                        Log.d(TAG, "NETWORK_PROVIDER on");
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                        if (locationManager != null) {
                            loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (loc != null)
                                updateUI(loc);
                        }
                    } else {
                        loc.setLatitude(0);
                        loc.setLongitude(0);
                        updateUI(loc);
                    }
                } else {
                    Log.d(TAG, "Can't get location");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        private void getLastLocation() {
            try {
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, false);
                Location location = locationManager.getLastKnownLocation(provider);
                Log.d(TAG, provider);
                Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
            ArrayList result = new ArrayList();

            for (String perm : wanted) {
                if (!hasPermission(perm)) {
                    result.add(perm);
                }
            }

            return result;
        }

        private boolean hasPermission(String permission) {
            if (canAskPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
                }
            }
            return true;
        }

        private boolean canAskPermission() {
            return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
        }

        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            switch (requestCode) {
                case ALL_PERMISSIONS_RESULT:
                    Log.d(TAG, "onRequestPermissionsResult");
                    for (String perms : permissionsToRequest) {
                        if (!hasPermission(perms)) {
                            permissionsRejected.add(perms);
                        }
                    }

                    if (permissionsRejected.size() > 0) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                                showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(permissionsRejected.toArray(
                                                            new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    } else {
                        Log.d(TAG, "No rejected permissions.");
                        canGetLocation = true;
                        getLocation();
                    }
                    break;
            }
        }

        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("GPS is not Enabled!");
            alertDialog.setMessage("Do you want to turn on GPS?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }

        private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
            new AlertDialog.Builder(Getlocation.this)
                    .setMessage(message)
                    .setPositiveButton("OK", okListener)
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        }

        private void updateUI(Location loc) {
            Log.d(TAG, "updateUI");
            tvLatitude.setText(Double.toString(loc.getLatitude()));
            tvLongitude.setText(Double.toString(loc.getLongitude()));
            tvTime.setText(DateFormat.getTimeInstance().format(loc.getTime()));
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            if (locationManager != null) {
                locationManager.removeUpdates(this);
            }
        }
}*/
