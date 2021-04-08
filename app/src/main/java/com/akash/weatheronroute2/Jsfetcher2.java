package com.akash.weatheronroute2;

import android.os.AsyncTask;

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







public class Jsfetcher2 extends AsyncTask<String,Void,Void> {

    String point;
    String datas ="";
    double lat;
    double longi;



    public Jsfetcher2(String point){

        this.point = point;





    }




        @Override
    protected Void doInBackground(String... strings) {
        try {

            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" +
                    point + "&appid=df7988b5831e56f5c8d05c9b2fc04df3");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while (line != null) {
                line = bufferedReader.readLine();
                datas= datas+line;
            }

            JSONObject allData = new JSONObject(this.datas);
            final JSONObject latlong = allData.getJSONObject("coord");
            final double longi = latlong.getDouble("lon");
            final double lati = latlong.getDouble("lat");
            System.out.println(lati+"   here");
            System.out.println(longi);

            this.longi= longi+0.0000001;
            this.lat= lati+0.0000001;


            System.out.println(this.lat+"   here");
            System.out.println(this.longi);















        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}

