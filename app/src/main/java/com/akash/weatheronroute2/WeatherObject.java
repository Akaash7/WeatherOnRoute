package com.akash.weatheronroute2;

import android.os.Parcel;
import android.os.Parcelable;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;

public class WeatherObject implements Parcelable {
    public String Cityname;
    public String temprature;
    public int weatherCode;
    public double lat;
    public double longi;
    public String name;
    public String time;
    public String distance;
    public String CoditionString;
    public int ConditionInt;
    public int ImageID;


    public WeatherObject(String name,double Temp, int Weathercode,double Lat, double Longi,String time,String distance,String ConditionString,String Cityname){
        temprature = Temp+"";
        weatherCode = Weathercode;
        lat = Lat;
        longi = Longi;
        this.name = name;
        this.time = time;
        this.distance = distance;
        this.CoditionString = ConditionString;
        ConditionInt = Integer.parseInt(this.CoditionString);
        this.ImageID = returnDrawable(ConditionInt);
        this.Cityname =Cityname;



    }


    protected WeatherObject(Parcel in) {
        Cityname = in.readString();
        temprature = in.readString();
        weatherCode = in.readInt();
        lat = in.readDouble();
        longi = in.readDouble();
        name = in.readString();
        time = in.readString();
        distance = in.readString();
        CoditionString = in.readString();
        ConditionInt = in.readInt();
        ImageID = in.readInt();
    }

    public static final Creator<WeatherObject> CREATOR = new Creator<WeatherObject>() {
        @Override
        public WeatherObject createFromParcel(Parcel in) {
            return new WeatherObject(in);
        }

        @Override
        public WeatherObject[] newArray(int size) {
            return new WeatherObject[size];
        }
    };

    public String GetTemprature(){

        return temprature;


    }

    public int GetWeathercode(){

        return weatherCode;
    }
    public String GetTime(){


        return this.time;
    }
    public String GetDistance(){


        return this.distance;
    }
    public String GetCityname(){


        return this.Cityname;
    }

    public int GetImageID(){

        return this.ImageID;

    }

    public int returnDrawable(int conditionInt){
        switch (conditionInt) {
            case 2000:
            case 2010:
            case 2020:
            case 2100:
            case 2110:
            case 2120:
            case 2210:
            case 2300:
            case 2310:
            case 2320:
                return R.drawable.ic_005_thunderstorm;
            case 2001:
            case 2011:
            case 2021:
            case 2101:
            case 2111:
            case 2121:
            case 2211:
            case 2301:
            case 2311:
            case 2321:
            case 3001:
            case 3011:
            case 3021:
            case 3101:
            case 3111:
            case 3121:
            case 3131:
            case 3141:
            case 3211:
            case 5001:
            case 5011:
            case 5021:
            case 5031:
            case 5041:
            case 5111:
            case 5201:
            case 5211:
            case 5221:
            case 5311:
            case 7011:
                return R.drawable.ic_011_night_rain;
            case 3000:
            case 3010:
            case 3020:
            case 3100:
            case 3110:
            case 3120:
            case 3130:
            case 3140:
            case 3210:
                return R.drawable.ic_060_rain;
            case 5000:
            case 5010:
            case 5020:
            case 5030:
            case 5040:
            case 5110:
            case 5200:
            case 5210:
            case 5220:
            case 5310:
            case 7010:
                return R.drawable.ic_002_rain;
            case 6000:
            case 6010:
            case 6020:
            case 6110:
            case 6120:
            case 6130:
            case 6150:
            case 6160:
            case 6200:
            case 6210:
            case 6220:
            case 6001:
            case 6011:
            case 6021:
            case 6111:
            case 6121:
            case 6131:
            case 6151:
            case 6161:
            case 6201:
            case 6211:
            case 6221:
                return R.drawable.ic_033_snow;
            case 7110:
            case 7111:
                return R.drawable.ic_035_cyclone;

            case 7210:
            case 7211:
                return R.drawable.ic_044_dawn;
            case 7310:
            case 7610:
            case 7620:
            case 7311:
            case 7611:
            case 7621:
                return R.drawable.ic_035_cyclone;

            case 7410:
            case 7411:

                return R.drawable.ic_027_nimbostratus;

            case 7710:
            case 7711:

                return R.drawable.ic_035_cyclone;

            case 8000:
               return R.drawable.ic_sun_1212;
            case 8001:
                return R.drawable.ic_034_moon;
            case 7810:
            case 7811:
                return R.drawable.ic_036_tornado;

            case 8010:
               return R.drawable.ic_011_cloud;
            case 8020:
                return R.drawable.ic_013_cloudy;

            case 8030:
            case 8040:
                return R.drawable.ic_014_cloud;
            case 8011:
            case 8021:
            case 8031:
            case 8041:
                return R.drawable.ic_009_cloud;


            default:
                return R.drawable.ic_035_cyclone;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Cityname);
        dest.writeString(temprature);
        dest.writeInt(weatherCode);
        dest.writeDouble(lat);
        dest.writeDouble(longi);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(distance);
        dest.writeString(CoditionString);
        dest.writeInt(ConditionInt);
        dest.writeInt(ImageID);
    }
}
