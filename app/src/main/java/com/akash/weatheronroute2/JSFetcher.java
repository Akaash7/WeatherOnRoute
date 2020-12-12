package com.akash.weatheronroute2;

import android.os.AsyncTask;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JSFetcher extends AsyncTask<String,Void,Void> {

    public List<Feature> locationlist;
    public ArrayList<Double> flaggedDuration;
    public List<Integer> flaggedDurationint;
    public ArrayList<Double> longs;
    public ArrayList<Double>lats;
    double epoch;


    @Override
    protected Void doInBackground(String... params) {
        String dataa = params[0];
        locationlist =new ArrayList<>();
        flaggedDuration = new ArrayList<>();
       //flaggedDurationint = new ArrayList<>();

        longs = new ArrayList<>();
        lats = new ArrayList<>();
        epoch = (System.currentTimeMillis())/1000;


        try{
            JSONObject allData = new JSONObject(dataa);
            JSONArray routes = allData.getJSONArray("routes");
            JSONObject routes1 = routes.getJSONObject(0);
            JSONArray legs = routes1.getJSONArray("legs");
            JSONObject legs1 = legs.getJSONObject(0);
            JSONArray steps = legs1.getJSONArray("steps");
            double start = 0;
            int flag = 4000;



            for(int j =0 ; j<steps.length();j++){
                JSONObject location = steps.getJSONObject(j);
                JSONObject manuever = location.getJSONObject("maneuver");
                JSONArray location1 = manuever.getJSONArray("location");

                start = start + location.getDouble("duration");


                if(start>=flag){

                    //flaggedduration added
                    flaggedDuration.add(start+epoch);
                    System.out.println(start+epoch);
                    //flaggedDurationint.add(j);


                    // location added to feature list to be used for pointers
                    // can use raw lang lat look for other methods to put pointers
                    locationlist.add(Feature.fromGeometry(
                            Point.fromLngLat(location1.getDouble(0)
                                    , location1.getDouble(1))));
                    //raw lang lat, can use 2d array list
                    longs.add(location1.getDouble(0));
                    lats.add(location1.getDouble(1));

                    if(start>=1.7*flag) {
                        flag =(int)start;
                        System.out.println("flag is now equal to start");
                    }

                    else {
                        flag = flag +4000;
                    }
                }




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
