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
    public ArrayList<Long> flaggedDuration;
    public ArrayList<Double> flaggedDistance;
    public ArrayList<Double> longs;
    public ArrayList<Double>lats;
    long epoch;


    @Override
    protected Void doInBackground(String... params) {
        String dataa = params[0];
        locationlist =new ArrayList<>();
        flaggedDuration = new ArrayList<>();
        flaggedDistance = new ArrayList<>();
        longs = new ArrayList<>();
        lats = new ArrayList<>();
        epoch = ((System.currentTimeMillis())/1000);


        try{
            JSONObject allData = new JSONObject(dataa);
            JSONArray routes = allData.getJSONArray("routes");
            JSONObject routes1 = routes.getJSONObject(0);
            JSONArray legs = routes1.getJSONArray("legs");

            long start = 0;
            double startdistance= 0;
            int flag = 4200;

            for(int i = 0;i<=(legs.length()-1);i++) {
                JSONObject legs1 = legs.getJSONObject(i);
                JSONArray steps = legs1.getJSONArray("steps");


                for (int j = 0; j < steps.length(); j++) {
                    JSONObject location = steps.getJSONObject(j);
                    JSONObject manuever = location.getJSONObject("maneuver");
                    JSONArray location1 = manuever.getJSONArray("location");

                    start = (long) ((start + location.getDouble("duration")));
                    startdistance = startdistance + location.getDouble("distance");
                    System.out.println(start + "starts");


                    if (start > flag) {

                        //flaggedduration added


                        flaggedDuration.add(start + epoch);
                        flaggedDistance.add(startdistance / 1000);

                        System.out.print(start + epoch + "start+epoch");


                        // location added to feature list to be used for pointers
                        // can use raw lang lat look for other methods to put pointers
                        locationlist.add(Feature.fromGeometry(
                                Point.fromLngLat(location1.getDouble(0)
                                        , location1.getDouble(1))));
                        //raw lang lat, can use 2d array list
                        longs.add(location1.getDouble(0));
                        lats.add(location1.getDouble(1));

                        if (start >= 1.7 * flag) {
                            flag = (int) start;
                            System.out.println("flag is now equal to start");
                        } else {
                            flag = flag + 4200;
                        }
                    }


                }

            }

            JSONObject legs1 = legs.getJSONObject(legs.length()-1);
            System.out.print(legs.length()+"leg length");
            JSONArray steps = legs1.getJSONArray("steps");
            JSONObject location = steps.getJSONObject(steps.length()-1);
            System.out.println(steps.length()+"step length");
            JSONObject manuever = location.getJSONObject("maneuver");
            JSONArray location1 = manuever.getJSONArray("location");

            start = (long) ((start + location.getDouble("duration")));
            startdistance = startdistance + location.getDouble("distance");
            longs.add(location1.getDouble(0));
            lats.add(location1.getDouble(1));
            flaggedDuration.add(start + epoch);

            flaggedDistance.add(startdistance / 1000);






        }
        catch(JSONException e){
            e.printStackTrace();


        }

        for(int i = 0 ; i<locationlist.size();i++){

            propertyadder(locationlist,i);

        }
        return null;
    }

    public void propertyadder(List<Feature> asd , int i ){

        asd.get(i).addStringProperty("name","WeatherObject"+i);



    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();



    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        System.out.println("jsfetcher ran");

    }
}
