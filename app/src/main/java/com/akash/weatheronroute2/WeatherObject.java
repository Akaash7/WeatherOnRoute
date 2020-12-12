package com.akash.weatheronroute2;

public class WeatherObject {
    public double temprature;
    public int weatherCode;
    public double lat;
    public double longi;

    public WeatherObject(double Temp, int Weathercode,double Lat, double Longi){
        temprature = Temp;
        weatherCode = Weathercode;
        lat = Lat;
        longi = Longi;


    }









    public double GetTemprature(){

        return temprature;


    }

    public int GetWeathercode(){

        return weatherCode;
    }



}
