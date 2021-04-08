package com.akash.weatheronroute2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.BubbleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class weatherFetcher extends AsyncTask<ArrayList<Double>,ArrayList<Double>, Void> {
    ArrayList<WeatherObject> weatherData = new ArrayList<>();
    ArrayList<Long> durations = new ArrayList<>();



    HashMap<String, Bitmap> imagesMap = new HashMap<>();


    private static final String IDS = "SOURCE_ID";




    ArrayList<Feature> ICthunder = new ArrayList<>();
    ArrayList<Feature> ICsprinkle = new ArrayList<>();
    ArrayList<Feature> ICrain = new ArrayList<>();
    ArrayList<Feature> ICsnow = new ArrayList<>();
    ArrayList<Feature> ICsmoke = new ArrayList<>();
    ArrayList<Feature> ICdayhaze = new ArrayList<>();
    ArrayList<Feature> ICdust = new ArrayList<>();
    ArrayList<Feature> ICfog = new ArrayList<>();
    ArrayList<Feature> ICclougusts = new ArrayList<>();
    ArrayList<Feature> ICtornado = new ArrayList<>();
    ArrayList<Feature> ICdaysunny = new ArrayList<>();
    ArrayList<Feature> ICcloudy = new ArrayList<>();
    ArrayList<Feature> ICcloudy801 = new ArrayList<>();
    ArrayList<Feature> ICcloudy802 = new ArrayList<>();

    ArrayList<Feature> ICrainynight = new ArrayList<>();
    ArrayList<Feature> ICclearmoon = new ArrayList<>();
    ArrayList<Feature> ICcloudynight = new ArrayList<>();



    public double tempss;
    public int humid;
    public double wind;
    public int pressure;
    public double uvrays;
    public int condiid;

    String datas = "";

    Activity activity_a;


    public weatherFetcher(Activity _activity,ArrayList<Long> durationsL){
        this.activity_a = _activity;
        this.durations = durationsL;
        System.out.println(durationsL + "aray ");



    }


    @Override
    protected Void doInBackground(ArrayList<Double>... params) {
        ArrayList<Double> longi = params[0];
        ArrayList<Double> lati = params[1];
        ArrayList<Double> distances = params[2];

        for (int i = 0; i < longi.size() - 1; i++) {
            try {
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
                    datas = datas + line;
                }

                JSONObject allData = new JSONObject(datas);
                JSONArray weather = allData.getJSONArray("hourly");
                JSONObject sunrisesunset = allData.getJSONObject("current");



                long sunrise =(long) sunrisesunset.getDouble("sunrise");
                long sunset = (long) sunrisesunset.getDouble("sunset");
                System.out.println(sunrise+"Isun"+sunset);
                int sunriseHH = fromUnixTimestamp(sunrise);
                System.out.println(sunriseHH+"RISE");
                int sunsetHH = fromUnixTimestamp(sunset);
                System.out.println(sunsetHH+"SET");


                double ddouble = distances.get(i);
                int   iint = (int) ddouble;
                String distance =iint+"";




                double tempKelvin = -700.0;
                int conditionId = 781;
                long LatLongtimeList;
                String timeString="";
                String nameme="";
                String ConditionString ="";




                for (int j = 0; j < weather.length(); j++) {
                    JSONObject hourlyobjects = weather.getJSONObject(j);
                    long LongSeconds = (long)hourlyobjects.getDouble("dt");
                    LatLongtimeList = durations.get(i);
                    //double LongStringconverted = Double.parseDouble(LatLongtime);

                    System.out.println(LatLongtimeList+ "LatLongtime  "+i);


                    //Time String
                    timeString = fromUnixTimestampString(LatLongtimeList);
                    int currentHH = fromUnixTimestamp(LatLongtimeList);

                    //Distance String
                    Double distanceintdouble = distances.get(i);

                    System.out.println(distanceintdouble+"   distances");




                    if ((LatLongtimeList-LongSeconds) < 0) {
                        tempKelvin = (int)(hourlyobjects.getDouble("temp"))-273;

                        JSONArray weatherid = hourlyobjects.getJSONArray("weather");
                        JSONObject weatherCondition = weatherid.getJSONObject(0);
                        conditionId = weatherCondition.getInt("id");

                        nameme = "WeatherObject"+i;



                        System.out.println("CurrentHH"+currentHH+"sunrise"+sunriseHH+"sunset"+sunsetHH);




                        if(currentHH<=sunriseHH&&currentHH>=sunsetHH){
                            ConditionString = conditionId+"0";
                            System.out.println("day tan");
                            ArrayfillerDay(conditionId, latti, longg,i,nameme);

                        }
                        else{
                            ConditionString = conditionId+"1";
                            System.out.println("night tan");

                            ArrayfillerNight(conditionId, latti, longg,i,nameme);

                        }






                        System.out.println("Worked fine" + latti + longg + "kelvin "+(tempKelvin-273.15) +"  "+conditionId);
                        break;

                    }


                }
                weatherData.add(new WeatherObject(nameme,tempKelvin, conditionId, latti, longg,timeString,distance, ConditionString,getCitynamefromLatLang(latti,longg)));








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


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        for (int i = 0; i < weatherData.size(); i++) {
            System.out.println(weatherData.get(i).GetWeathercode() + "" +
                    weatherData.get(i).GetTemprature() + "      " + i);


        }



        return null;


    }




  /*  public void TempTimgen(){


        for(int i = 0 ;i<weatherData.size();i++){

            LayoutInflater myLayoutinflator = activity_a.getLayoutInflater();

            BubbleLayout bubbleLayout = (BubbleLayout)
                    myLayoutinflator.inflate(R.layout.bubble_layout, null);


            TextView text = bubbleLayout.findViewById(R.id.info_window_title);
            text.setText(String.valueOf(weatherData.get(i).temprature+"C"));

            TextView text2 = bubbleLayout.findViewById(R.id.info_window_description);
            //text2.setText(fromUnixTimestamp(weatherData.get(i).time));

            int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            bubbleLayout.measure(measureSpec, measureSpec);

            float measuredWidth = bubbleLayout.getMeasuredWidth();

            bubbleLayout.setArrowPosition(measuredWidth / 2 - 5);

            Bitmap bitmap = generate(bubbleLayout);
            imagesMap.put("WeatherObject"+i, bitmap);










        }




    }
*/
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
            System.out.println(imagesMap.size());



    }


   /* static Bitmap generate(@NonNull View view) {
        int measureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(measureSpec, measureSpec);

        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();

        view.layout(0, 0, measuredWidth, measuredHeight);
        Bitmap bitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        bitmap.eraseColor(Color.TRANSPARENT);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    */

    public static int fromUnixTimestamp(double itemDouble) {

        long itemLong = (long) itemDouble*1000;
        Date itemDate = new Date(itemLong);


        String itemDateStr = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss")
                .format(itemDate)
                .substring(11,13);
        int finall = Integer.parseInt(itemDateStr);

        System.out.println(itemDateStr+"here time");



        return finall;
    }
    public static String fromUnixTimestampString(double itemDouble) {

        long itemLong = (long) itemDouble*1000;
        Date itemDate = new Date(itemLong);


        String itemDateStr = new SimpleDateFormat("dd/MM/yyyy hh:mm aa")
                .format(itemDate)
                .substring(11);

        System.out.println(itemDateStr+"here time");






        return itemDateStr;
    }





    public void ArrayfillerDay(int conditionid, double latitude, double longitude,int k,String namee) {

        switch (conditionid) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                ICthunder.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
               //ICthunder.get(k).addStringProperty("name",namee);
                break;
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                ICsprinkle.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICsprinkle.get(k).addStringProperty("name",namee);
                break;
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
            case 701:
                ICrain.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICrain.get(k).addStringProperty("name",namee);

                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                ICsnow.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICsnow.get(k).addStringProperty("name",namee);

                break;
            case 711:
                ICsmoke.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICsmoke.get(k).addStringProperty("name",namee);

                break;
            case 721:
                ICdayhaze.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdayhaze.get(k).addStringProperty("name",namee);

                break;
            case 731:
            case 761:
            case 762:
                ICdust.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdust.get(k).addStringProperty("name",namee);

                break;
            case 741:
                ICfog.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICfog.get(k).addStringProperty("name",namee);

                break;
            case 771:
                ICclougusts.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICclougusts.get(k).addStringProperty("name",namee);

                break;
            case 800:
                ICdaysunny.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdaysunny.get(k).addStringProperty("name",namee);

                break;
            case 781:
                ICtornado.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICtornado.get(k).addStringProperty("name",namee);
                break;


            case 801:
                ICcloudy801.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;
            case 802:
                ICcloudy802.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;

            case 803:
            case 804:
                ICcloudy.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;


            default:
                System.out.println("ok");
        }


    }



















    private void ArrayfillerNight(int conditionid, double latitude, double longitude,int k,String namee) {


        switch (conditionid) {
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
            case 701:
                ICrainynight.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICrain.get(k).addStringProperty("name",namee);

                break;
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 613:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                ICsnow.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICsnow.get(k).addStringProperty("name",namee);

                break;
            case 711:
                ICsmoke.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICsmoke.get(k).addStringProperty("name",namee);

                break;
            case 721:
                ICdayhaze.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdayhaze.get(k).addStringProperty("name",namee);

                break;
            case 731:
            case 761:
            case 762:
                ICdust.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdust.get(k).addStringProperty("name",namee);

                break;
            case 741:
                ICfog.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICfog.get(k).addStringProperty("name",namee);

                break;
            case 771:
                ICclougusts.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICclougusts.get(k).addStringProperty("name",namee);

                break;

            case 781:
                ICtornado.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICtornado.get(k).addStringProperty("name",namee);

                break;

            case 800:
                ICclearmoon.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                //ICdaysunny.get(k).addStringProperty("name",namee);

                break;
            case 801:
            case 802:
            case 803:
            case 804:
                ICcloudynight.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));
                break;


            default:
                System.out.println("bjgya");
        }
    }

    public String getCitynamefromLatLang(double latti,double longg){
        String allfData = "";
        String finalCityname ="";
        try{
            URL url = new URL("https://api.openweathermap.org/data/2.5/find?lat="
                    + latti + "&lon=" + longg +
                    "&cnt=1&appid=df7988b5831e56f5c8d05c9b2fc04df3");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";


            while (line != null) {
                line = bufferedReader.readLine();
                allfData = allfData + line;
            }

            JSONObject allData = new JSONObject(allfData);
            JSONArray list = allData.getJSONArray("list");
            JSONObject listobject = list.getJSONObject(0);
            String CityName = listobject.getString("name");
            finalCityname = CityName;


        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }





        return finalCityname;
    }
}




