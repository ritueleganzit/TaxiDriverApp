package com.eleganzit.taxidriverapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eleganzit.taxidriverapp.animations.MapAnimator;
import com.eleganzit.taxidriverapp.utils.DirectionsJSONParser;
import com.eleganzit.taxidriverapp.utils.FloatingViewService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TripLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    MapView mapView;
    Button cnfarrived;
    LinearLayout driverprofile,tripdetails;
    List<LatLng> polyLineList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_location);
        mapView = (MapView) findViewById(R.id.map2);
        mapView.getMapAsync(this);
        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(TripLocationActivity.this);
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        TripLocationActivity.this, R.raw.silvermode));

        if (!success) {
            Log.e("MainAct", "Style parsing failed.");
        }
        Log.e("ddddddd", "Style parsing failed.");

        this.googleMap=googleMap;
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                drawRoute(googleMap,23.0133, 72.5308,23.0235, 72.5290);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
    }
    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(TripLocationActivity.this, FloatingViewService.class));
                //
                // finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void drawRoute(GoogleMap googleMap,double from_lat, double from_lng, double to_lat, double to_lng) {


        LatLng origin = new LatLng(from_lat, from_lng);
        LatLng destination = new LatLng(to_lat, to_lng);
        ArrayList<Marker> mMarkerArray = new ArrayList<Marker>();
        MarkerOptions options = new MarkerOptions();
        // Setting the position of the marker
        options.position(origin);

        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.pickuppin);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap firstMarker = Bitmap.createScaledBitmap(b, 70   , 70, false);

        Marker amarker1=    googleMap.addMarker(options.icon(BitmapDescriptorFactory.fromBitmap(firstMarker)));
        MarkerOptions options2 = new MarkerOptions();
        BitmapDrawable bitmapdraw2=(BitmapDrawable)getResources().getDrawable(R.drawable.droppin);
        Bitmap b2=bitmapdraw2.getBitmap();
        Bitmap firstMarker2 = Bitmap.createScaledBitmap(b2, 70   , 70, false);

        // Setting the position of the marker
        options2.position(destination);
        Marker amarker2=    googleMap.addMarker(options2.icon(BitmapDescriptorFactory.fromBitmap(firstMarker2)));

        mMarkerArray.add(amarker1);
        mMarkerArray.add(amarker2);
        Log.d("fgfg","fxgdfgdf");

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkerArray) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 80; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cu);
        googleMap.animateCamera(cu);
        String url = getDirectionsUrl(origin, destination);
        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }
    private class DownloadTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String,String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String,String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String,String>>> result) {
            ArrayList points  = new ArrayList();
            PolylineOptions lineOptions = new PolylineOptions();

            //Log.d("mylatlon",""+result);
            MarkerOptions markerOptions = new MarkerOptions();
            if (result!=null)
                for (int i = 0; i < result.size(); i++) {



                    List<HashMap<String,String>> path = result.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String,String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));

                        //  Log.d("mylatlon",""+lat);
                        LatLng position = new LatLng(lat, lng);
                        Log.d("mylatlon",""+position.latitude);
                        polyLineList.add(position);

                        points.add(position);
                    }

                    lineOptions.addAll(points);
                    lineOptions.width(10);
                    lineOptions.color(Color.parseColor("#4885ed"));
                    lineOptions.geodesic(true);

                }
            Log.d("polyLineList",""+polyLineList.size());
            MapAnimator.getInstance().animateRoute(googleMap, polyLineList);

// Drawing polyline in the Google Map for the i-th route
            // googleMap.addPolyline(lineOptions);
            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });*/
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+"&key=AIzaSyAlfxVsErR9EwJwhLaEmEJnm2kZMhMrovU";

        Log.d("theurl",url);
        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("Exceptiondata", data);

            JSONObject jsonObject=new JSONObject(""+data);
            JSONArray jsonArray=jsonObject.getJSONArray("routes");

            for (int i=0;i<jsonArray.length();i++)
            {

                JSONObject childObject=jsonArray.getJSONObject(i);

                JSONArray jsonArray1=childObject.getJSONArray("legs");
                for (int j=0;j<jsonArray1.length();j++)
                {
                    JSONObject jsonObject1=jsonArray1.getJSONObject(i);
                    Log.d("Exceptiondataaa",jsonObject1.getJSONObject("distance").getString("text"));

                    //   durationtxt=jsonObject1.getJSONObject("duration").getString("text");


                }

            }


           /* runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    duration.setText(""+durationtxt+", away");
                 //   CaptureMapScreen();
                }
            });*/

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
    
}
