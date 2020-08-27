package com.akash.weatheronroute2;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.MainThread;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSFetcher extends AsyncTask<String,Void,Void> {

    public List<Feature> locationlist;
    @Override
    protected Void doInBackground(String... params) {
        String dataa = params[0];


        try{
            JSONObject allData = new JSONObject(dataa);
            JSONArray routes = allData.getJSONArray("routes");
            JSONObject routes1 = routes.getJSONObject(0);
            JSONArray legs = routes1.getJSONArray("legs");
            JSONObject legs1 = legs.getJSONObject(0);
            JSONArray steps = legs1.getJSONArray("steps");
             locationlist =new ArrayList<>();
            for(int j =0 ; j<steps.length();j++){
                JSONObject location = steps.getJSONObject(j);
                JSONObject manuever = location.getJSONObject("maneuver");
                JSONArray location1 = manuever.getJSONArray("location");
                double one = (location1.getDouble(0));
                double two = location1.getDouble(1);
                locationlist.add(Feature.fromGeometry(
                       Point.fromLngLat(one, two)));

            }

        }
        catch(JSONException e){
            e.printStackTrace();


        }

        return null;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();



    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);




    }
}
