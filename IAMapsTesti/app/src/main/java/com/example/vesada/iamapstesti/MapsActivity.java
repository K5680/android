package com.example.vesada.iamapstesti;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.indooratlas.android.sdk.IALocation;
import com.indooratlas.android.sdk.IALocationListener;
import com.indooratlas.android.sdk.IALocationManager;
import com.indooratlas.android.sdk.IALocationRequest;
import com.indooratlas.android.sdk.IARegion;
//import com.indooratlas.android.sdk.examples.ListExamplesActivity;
//import com.indooratlas.android.sdk.examples.R;
//import com.indooratlas.android.sdk.examples.SdkExample;
//import com.indooratlas.android.sdk.examples.utils.ExampleUtils;



public class MapsActivity extends FragmentActivity implements IALocationListener, OnMapReadyCallback  {

    private GoogleMap mMap;
    private Marker mMarker;
    private IALocationManager mIALocationManager;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mIALocationManager = IALocationManager.create(this);

        // onko Google Play -palvelut käytössä
        Toast.makeText(getApplicationContext(), "Google Play services:  "+mMap, Toast.LENGTH_SHORT).show();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.kartta);
        mapFragment.getMapAsync(this);


        // optional setup of floor plan id
        // if setLocation is not called, then location manager tries to find  location automatically
        final String floorPlanId = getString(R.string.indooratlas_floor_plan_id);
        if (!TextUtils.isEmpty(floorPlanId)) {
            final IALocation FLOOR_PLAN_ID = IALocation.from(IARegion.floorPlan(floorPlanId));
            mIALocationManager.setLocation(FLOOR_PLAN_ID);

            Toast.makeText(getApplicationContext(), "floorPlan:  "+floorPlanId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap mapFragment) {
        mMap = mapFragment;

        // Add a marker in Sydney and move the camera
        LatLng jamk = new LatLng(62.2414034, 25.7592567);
        mMap.addMarker(new MarkerOptions().position(jamk).title("Dynamo"));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(jamk, 14);
        mMap.animateCamera(cameraUpdate);

        Toast.makeText(getApplicationContext(), "zoomaus...  "+jamk, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIALocationManager.destroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMap == null) {

             // Obtain the SupportMapFragment and get notified when the map is ready to be used.
             // mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.kartta);
             // mapFragment.getMapAsync(this);

              mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.kartta)).getMap();
        }

        mIALocationManager.requestLocationUpdates(IALocationRequest.create(), this);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

               // ExampleUtils.shareText(MapsActivity.this, mIALocationManager.getExtraInfo().traceId,
               //         "traceId");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIALocationManager != null) {
            mIALocationManager.removeLocationUpdates(this);
        }

    }

    /**
     * Callback for receiving locations.
     * This is where location updates can be handled by moving markers or the camera.
     */
    public void onLocationChanged(IALocation location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (mMarker == null) {
            if (mMap != null) {
                mMarker = mMap.addMarker(new MarkerOptions().position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(200.0f)));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));
            }
        } else {
            mMarker.setPosition(latLng);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // N/A
    }
}



/* vanha

package com.example.vesada.iamapstesti;

        import android.support.v4.app.FragmentActivity;
        import android.os.Bundle;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.SupportMapFragment;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
*/