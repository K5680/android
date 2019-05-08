package fi.jamk.mapsapp;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    static List<infoToStore> info = new ArrayList<>();  // sijainnit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FetchDataTask task = new FetchDataTask();
        task.execute("http://student.labranet.jamk.fi/~K5680/ototjkl.json");

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    private class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {

            try {
                JSONArray otot = json.getJSONArray("otot");

                for (int i = 0; i < otot.length(); i++) {
                    JSONObject hs = otot.getJSONObject(i);

                    Float lat =  Float.parseFloat(hs.getString("LAT")); // latitude
                    Float lon = Float.parseFloat(hs.getString("LON"));  // longitude
                    String nimi = hs.getString("AHJOKEISARI");          // = nimi
                    String osoite = hs.getString("AHJOKATU 18");        // = osoite

                    // liitetään sijainnit info-listaan
                    info.add(new infoToStore(lat, lon, nimi, osoite));
                }
            } catch (JSONException e) {
                Log.e("JSON", "Error getting data.");
            }
        }
    }

        @Override
        public void onMapReady(GoogleMap googleMap) {
            // store map object to member variable
            GoogleMap mMap = googleMap;
            // set map type
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // add one marker
            final LatLng ICT = new LatLng(62.2416223, 25.7597309);
            // create one marker
            final Marker ict = mMap.addMarker(new MarkerOptions()
                    .position(ICT)
                    .title("JAMK/ICT"));
                    ict.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.jamklogo));

            // käy läpi koko lista (0 - info:n koko)
            for (int i = 0; i < info.size(); i++) {

                // etäisyys
           //     float[] results = new float[1];
           //     Location.distanceBetween(ICT.latitude, ICT.longitude, info.get(i).getLat(), info.get(i).getLng(), results);

                // tee markerit
                     final Marker ict2 = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(info.get(i).getLat(), info.get(i).getLng()))
                            .title(""+info.get(i).getNimi())
                            .snippet(""+info.get(i).getOsoite()));

                LatLng OTTO = new LatLng(info.get(i).getLat(), info.get(i).getLng());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(OTTO, 10));    // siirrellään joka pisteeseen luupissa, ei ehkä fiksua

                Log.i("osoitteet ", "info: " + info.get(i).getOsoite());

                // marker listener
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(final Marker marker) {
                            // laske etäisyys jamkilta ottopisteelle
                            float[] results = new float[1];
                            Location.distanceBetween(ICT.latitude, ICT.longitude, marker.getPosition().latitude, marker.getPosition().longitude, results);

                            // toustaa etäisyys jamkista
                            Toast.makeText(getApplicationContext(), "" + marker.getSnippet() + "\n  " + results[0]/1000 + " km", Toast.LENGTH_LONG).show();
                            return true;
                    }
                });
            }
        }
}
