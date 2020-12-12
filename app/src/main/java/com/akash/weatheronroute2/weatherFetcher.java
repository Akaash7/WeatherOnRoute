package com.akash.weatheronroute2;

import android.os.AsyncTask;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class weatherFetcher extends AsyncTask<ArrayList<Double>,ArrayList<Double>, Void> {
    ArrayList<WeatherObject> weatherData = new ArrayList<>() ;
    public double tempss;
    public int humid;
    public double wind;
    public int pressure;
    public double uvrays;
    public int condiid;

    String datas ="";


    @Override
    protected Void doInBackground(ArrayList<Double>... params) {
        ArrayList<Double> longi  = params[0];
        ArrayList<Double> lati = params[1];
        ArrayList<Double> durations = params[2];

        for(int i = 0;i<longi.size()-1;i++){
            try{
                double latti = lati.get(i);
                double longg = longi.get(i);
                URL url = new URL("https://api.openweathermap.org/data/2.5/onecall?" +
                        "lat=" + latti + "&lon=" + longg +
                        "&%20exclude={part}&appid=df7988b5831e56f5c8d05c9b2fc04df3");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";



                while (line != null) {
                    line = bufferedReader.readLine();
                    datas= datas+line;
                }

                JSONObject allData = new JSONObject(datas);
                JSONArray weather = allData.getJSONArray("hourly");
                for(int j = 0;j<weather.length();j++){
                    JSONObject hourlyobjects = weather.getJSONObject(j);
                    double LongSeconds = hourlyobjects.getDouble("dt");
                    String LatLongtime = durations.get(i).toString();
                    double   LongStringconverted = Double.parseDouble(LatLongtime);
                    if((LongStringconverted-LongSeconds)<0){
                        double tempKelvin = hourlyobjects.getDouble("temp");
                        JSONArray weatherid = hourlyobjects.getJSONArray("weather");
                        JSONObject weatherCondition = weatherid.getJSONObject(0);
                        int conditionId = weatherCondition.getInt("id");
                        weatherData.add(new WeatherObject(tempKelvin,conditionId,latti,longg));
                        System.out.println("Worked fine"+latti+longg+tempKelvin);





                    }





                }








               /* final double tempKelvin2 = hourlyobjects.getDouble("temp");
                final JSONArray weatherinside = hourlyobjects.getJSONArray("weather");
                final JSONObject weatherobject = weatherinside.getJSONObject(0);
                final String conditionName = weatherobject.getString("description");
                final JSONObject current = allData.getJSONObject("current");



                final int conditionId = weatherobject.getInt("id");






                tempss =tempKelvin;
                condiid = conditionId;
                final int humidity = current.getInt("Humidity");
                humid = humidity;
                final double windspeed = current.getDouble("wind_speed");
                wind = windspeed;
                final int pressu = current.getInt("pressure");
                pressure = pressu;
                final double UV = current.getDouble("uvi");
                uvrays = UV;
                */


            }
            catch (MalformedURLException e ){
                e.printStackTrace();
            }
            catch (IOException e ){
                e.printStackTrace();
            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }


        return null;


    }


}
