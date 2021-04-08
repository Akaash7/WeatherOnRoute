package com.akash.weatheronroute2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.os.Bundle;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mapbox.geojson.GeoJson;
import com.mapbox.geojson.Geometry;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;


import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;

import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.layers.Property.ICON_ANCHOR_BOTTOM;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.location.OnLocationClickListener;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Feature;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.security.cert.PolicyNode;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;


import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.match;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, PermissionsListener, Serializable {


    private static final String CALLOUT_LAYER_ID = "CALLOUT_LAYER_ID";
    private static final String GEOJSON_SOURCE_ID = "GEOJSON_SOURCE_ID";
    private static final String MY_LIST = "MY_LIST";


    private MapView mapView;
    private MapboxMap mapboxMap;
    private SymbolManager symbolManager;
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String rain_icon_id = "rain-icon-id";
    private static final String thunder_icon_id = "thunder-icon-id";
    private static final String sprinkle_icon_id = "sprinkle-icon-id";
    private static final String snow_icon_id = "snow-icon-id";
    private static final String smoke_icon_id = "smoke-icon-id";
    private static final String dayhaze_icon_id = "dayhaze-icon-id";
    private static final String dust_icon_id = "dust-icon-id";
    private static final String fog_icon_id = "fog-icon-id";
    private static final String cloudgust_icon_id = "cloudgust-icon-id";
    private static final String tornado_icon_id = "tornado-icon-id";
    private static final String daysunny_icon_id = "daysunny-icon-id";
    private static final String cloudy_icon_id = "cloudy-icon-id";
    private static final String destination_icon_id = "destination-icon-id";


    private static final String ICON_PROPERTY = "ICON_PROPERTY";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String ROUTE_LAYER_ID = "route-layer-id";
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ICON_LAYER_ID = "icon-layer-id";
    private static final String ICON_SOURCE_ID = "icon-source-id";
    private static final String RED_PIN_ICON_ID = "red-pin-icon-id";

    String Jsonraw = "";

    private LocationEngine locationEngine;

    ArrayList<WeatherObject> weatherData;
    weatherFetcher weatherFetchers;


    private Point destination;
    Point originpoint ;
    Point Destination;
    Point wayPoint =Point.fromLngLat(76.768066,30.741482);
    Point destinationTextviw;
    ArrayList<Point> OWD = new ArrayList<>();

    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private PermissionsManager permissionsManager;
    private LocationComponent locationComponent;
    Context context = this;
    Activity activity = this;

    ArrayList<GeoJsonSource> Gjson = new ArrayList<>();

    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    Button click ;
    Button moredetails;
    private MainActivityLocationCallback callback = new MainActivityLocationCallback(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        getSupportActionBar().hide();

        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));

        setContentView(R.layout.activity_main);


        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocation(style);
                addDestinationIconSymbolLayer(style);
                /*addWeatherSignsfog(style);
                addWeatherSigncloudy(style);
                addWeatherSigndayhaze(style);
                addWeatherSigndust(style);
                addWeatherSignrain(style);
                addWeatherSignsnow(style);
                addWeatherSignsprinkle(style);
                addWeatherSignthunder(style);
                addWeatherSigncloudgust(style);
                addWeatherSignstornado(style);
                addWeatherSigndaysunny(style);
                addWeatherSignsmoke(style);
                setUpInfoWindowLayer(style);

                addWeatherSigncloudy801(style);
                addWeatherSigncloudy802(style);

                addWeatherSignRainynight(style);
                addWeatherSignclearmoon(style);
                addWeatherSigncloudynight(style);*/

                onGoclick();



                mapboxMap.addOnMapClickListener(MainActivity.this);





            }
        });



    }
    @SuppressWarnings("MissingPermission")
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());

        if(OWD.size()<3){
            OWD.add(Point.fromLngLat(point.getLongitude(), point.getLatitude()));
            GeoJsonSource source = mapboxMap.getStyle().getSourceAs("destination-source-id");
            if (source != null) {
                source.setGeoJson(Feature.fromGeometry(destinationPoint));
            }



        }



        this.Destination = destinationPoint;

        return true;
    }

    public void onGoclick(){
        Button button = (Button) findViewById(R.id.getroute);
        final weatherloadingdialog loadingdialog = new weatherloadingdialog(MainActivity.this);
        final Handler handler = new Handler();
       // AddMarkerstoview();

        if(OWD.size()==2){

            OWD.add(OWD.get(OWD.size()-1));


        }
        else if(OWD.size()==3){
            OWD.clear();
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText starttext = (EditText) findViewById(R.id.start);
                EditText desttext = (EditText) findViewById(R.id.destination);
                String start = starttext.getText().toString();
                String dest = desttext.getText().toString();



                if(!(start.isEmpty())&&!(dest.isEmpty())){
                    //starting loading with 7seconds
                    loadingdialog.startloading();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            loadingdialog.dismissloading();
                        }
                    },7000);

                    //Geocoding
                    Jsfetcher2 startO = new Jsfetcher2(start);
                    Jsfetcher2 destO = new Jsfetcher2(dest);
                    try{
                        startO.execute().get();
                        destO.execute().get();
                    }
                    catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    catch (ExecutionException e){
                        e.printStackTrace();
                    }

                    Point startP = Point.fromLngLat(startO.longi,startO.lat);
                    Point destP = Point.fromLngLat(destO.longi,destO.lat);
                    Point DummydestP = Point.fromLngLat(destO.longi,destO.lat);
                    destinationTextviw = destP;
                    getRoute(startP,destP,DummydestP);
                    starttext.getText().clear();
                    desttext.getText().clear();


                }
                else if(OWD.size()>1) {

                    getRoute(OWD.get(0),OWD.get(1),OWD.get(2));

                }





            }
        });

        //loadingdialog.dismissloading();
    }



    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_panel, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("destination-icon-id", mBitmap);

        // loadedMapStyle.addImage("destination-icon-id",
        //BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }
    private void addWeatherSignrain(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_002_rain, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("rain-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("rain-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("rain-symbol-layer-id", "rain-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("rain-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignthunder(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_005_thunderstorm, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("thunder-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("thunder-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("thunder-symbol-layer-id", "thunder-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("thunder-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignsprinkle(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_060_rain, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("sprinkle-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("sprinkle-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("sprinkle-symbol-layer-id", "sprinkle-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("sprinkle-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignsnow(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_033_snow, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("snow-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("snow-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("snow-symbol-layer-id", "snow-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("snow-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignsmoke(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_035_cyclone, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("smoke-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("smoke-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("smoke-symbol-layer-id", "smoke-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("smoke-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigndayhaze(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_044_dawn, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("dayhaze-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("dayhaze-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("dayhaze-symbol-layer-id", "dayhaze-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("dayhaze-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigndust(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_035_cyclone, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("dust-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("dust-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("dust-symbol-layer-id", "dust-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("dust-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignsfog(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_027_nimbostratus, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("fog-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("fog-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("fog-symbol-layer-id", "fog-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("fog-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigncloudgust(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_035_cyclone, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("cloudgust-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("cloudgust-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("cloudgust-symbol-layer-id", "cloudgust-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("cloudgust-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignstornado(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_036_tornado, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("tornado-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("tornado-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("tornado-symbol-layer-id", "tornado-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("tornado-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigndaysunny(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_sun_1212, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("daysunny-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("daysunny-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("daysunny-symbol-layer-id", "daysunny-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("daysunny-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigncloudy(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_014_cloud, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("cloudy-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("cloudy-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("cloudy-symbol-layer-id", "cloudy-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("cloudy-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigncloudy801(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_011_cloud, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("cloudy801-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("cloudy801-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("cloudy801-symbol-layer-id", "cloudy801-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("cloudy801-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigncloudy802(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_013_cloudy, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("cloudy802-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("cloudy802-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("cloudy802-symbol-layer-id", "cloudy802-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("cloudy802-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignRainynight(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_011_night_rain, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("rainynight-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("rainynight-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("rainynight-symbol-layer-id", "rainynight-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("rainynight-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSignclearmoon(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_034_moon, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("clearmoon-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("clearmoon-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("clearmoon-symbol-layer-id", "clearmoon-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("clearmoon-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }
    private void addWeatherSigncloudynight(@NonNull Style loadedMapStyle) {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_009_cloud, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);

        loadedMapStyle.addImage("cloudynight-icon-id", mBitmap);

        GeoJsonSource geoJsonSource = new GeoJsonSource("cloudynight-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("cloudynight-symbol-layer-id", "cloudynight-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("cloudynight-icon-id"),

                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);

    }


    private void getRoute(Point origin, Point destination,Point Waypoint) {
        NavigationRoute.builder(this)
                .accessToken(getString(R.string.mapbox_access_token))
                .origin(origin)
                .addWaypoint(Waypoint)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        Jsonraw = response.body().toJson();








                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);

                        //get weather button
                        final weatherloadingdialog loadingdialog = new weatherloadingdialog(MainActivity.this);
                        final Handler handler =  new Handler();
                        click = (Button) findViewById(R.id.Getlatlang);
                        click.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadingdialog.startloading();
                                JSFetcher jsFetcher = new JSFetcher();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        loadingdialog.dismissloading();
                                    }
                                },10000);


                                try{
                                    jsFetcher.execute(Jsonraw).get();
                                    weatherFetcher weatherFetcherss = new weatherFetcher(activity,jsFetcher.flaggedDuration);
                                    weatherFetcherss.execute(jsFetcher.longs,jsFetcher.lats,jsFetcher.flaggedDistance).get();
                                    weatherFetchers = weatherFetcherss;
                                    weatherData = weatherFetchers.weatherData;
                                }
                                catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                catch (ExecutionException e){
                                    e.printStackTrace();
                                }
                                AddMarkerstoview();

                                GeoJsonSource rain = mapboxMap.getStyle().getSourceAs("rain-source-id");

                                if (rain != null&&weatherFetchers.ICrain.size()>0) {

                                    rain.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICrain));
                                }
                                GeoJsonSource thunder = mapboxMap.getStyle().getSourceAs("thunder-source-id");
                                if (thunder != null&&weatherFetchers.ICrain.size()>0) {

                                    thunder.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICthunder));
                                }
                                GeoJsonSource sprinkle = mapboxMap.getStyle().getSourceAs("sprinkle-source-id");
                                if (sprinkle != null&&weatherFetchers.ICsprinkle.size()>0) {

                                    sprinkle.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICsprinkle));
                                }
                                GeoJsonSource snow = mapboxMap.getStyle().getSourceAs("snow-source-id");
                                if (snow != null&&weatherFetchers.ICsnow.size()>0) {

                                    snow.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICsnow));
                                }
                                GeoJsonSource smoke = mapboxMap.getStyle().getSourceAs("smoke-source-id");
                                if (smoke != null&&weatherFetchers.ICrain.size()>0) {

                                    smoke.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICsmoke));
                                }
                                GeoJsonSource dayhaze = mapboxMap.getStyle().getSourceAs("dayhaze-source-id");
                                if (dayhaze != null&&weatherFetchers.ICdayhaze.size()>0) {

                                    dayhaze.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICdayhaze));
                                }
                                GeoJsonSource dust = mapboxMap.getStyle().getSourceAs("dust-source-id");
                                if (dust != null&&weatherFetchers.ICdust.size()>0) {

                                    dust.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICdust));
                                }
                                GeoJsonSource fog = mapboxMap.getStyle().getSourceAs("fog-source-id");
                                if (fog != null&&weatherFetchers.ICfog.size()>0) {

                                    fog.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICfog));
                                }
                                GeoJsonSource cloudgusts = mapboxMap.getStyle().getSourceAs("cloudgusts-source-id");
                                if (cloudgusts != null&&weatherFetchers.ICclougusts.size()>0) {

                                    cloudgusts.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICclougusts));
                                }
                                GeoJsonSource tornado = mapboxMap.getStyle().getSourceAs("tornado-source-id");
                                if (tornado != null&&weatherFetchers.ICtornado.size()>0) {

                                    tornado.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICtornado));
                                }
                                GeoJsonSource daysunny = mapboxMap.getStyle().getSourceAs("daysunny-source-id");
                                if (daysunny != null&&weatherFetchers.ICdaysunny.size()>0) {

                                    daysunny.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICdaysunny));
                                }


                                GeoJsonSource cloudy = mapboxMap.getStyle().getSourceAs("cloudy-source-id");
                                if (cloudy != null&&weatherFetchers.ICcloudy.size()>0) {

                                    cloudy.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICcloudy));


                                }
                                GeoJsonSource cloudy801 = mapboxMap.getStyle().getSourceAs("cloudy801-source-id");
                                if (cloudy801 != null&&weatherFetchers.ICcloudy801.size()>0) {

                                    cloudy801.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICcloudy801));


                                }
                                GeoJsonSource cloudy802 = mapboxMap.getStyle().getSourceAs("cloudy802-source-id");
                                if (cloudy802 != null&&weatherFetchers.ICcloudy802.size()>0) {

                                    cloudy802.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICcloudy802));


                                }

                                GeoJsonSource rainynight = mapboxMap.getStyle().getSourceAs("rainynight-source-id");
                                if (rainynight != null&&weatherFetchers.ICrainynight.size()>0) {

                                    rainynight.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICrainynight));


                                }
                                GeoJsonSource clearmoon = mapboxMap.getStyle().getSourceAs("clearmoon-source-id");
                                if (clearmoon != null&&weatherFetchers.ICclearmoon.size()>0) {

                                    clearmoon.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICclearmoon));


                                }
                                GeoJsonSource cloudynight = mapboxMap.getStyle().getSourceAs("cloudynight-source-id");
                                if (cloudynight != null&&weatherFetchers.ICcloudynight.size()>0) {

                                    cloudynight.setGeoJson(FeatureCollection.fromFeatures(weatherFetchers.ICcloudynight));


                                }


                               /* int halfve = jsFetcher.locationlist.size()/2;
                                double longii = jsFetcher.longs.get(halfve);
                                double lattii = jsFetcher.lats.get(halfve);

                                CameraPosition position = new CameraPosition.Builder()
                                        .target(new LatLng(lattii, longii)) // Sets the new camera position
                                        .zoom(6) // Sets the zoom
                                        .bearing(180) // Rotate the camera
                                        .tilt(30) // Set the camera tilt
                                        .build(); // Creates a CameraPosition from the builder

                                mapboxMap.animateCamera(CameraUpdateFactory
                                        .newCameraPosition(position), 3000);
                                */


                                moredetails = (Button) findViewById(R.id.MoreDetails);
                                moredetails.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openWdetails();
                                       /* mapboxMap.getStyle(new Style.OnStyleLoaded() {
                                            @Override
                                            public void onStyleLoaded(@NonNull Style style) {
                                                style.removeLayer("rainynight-symbol-layer-id");
                                                style.removeSource("rainynight-source-id");

                                            }
                                        });*/
                                    }
                                });








                            }
                        });
                    }

                    private void openWdetails() {
                        Intent intent = new Intent(MainActivity.this , Wdetails.class);
                        intent.putExtra("MY_LIST",weatherData);

                        startActivity(intent);
                        DeleteMarkers();
                    }


                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });


    }
    public void DeleteMarkers(){

        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.removeLayer("rain-symbol-layer-id");
                style.removeLayer("thunder-symbol-layer-id");
                style.removeLayer("sprinkle-symbol-layer-id");
                style.removeLayer("snow-symbol-layer-id");
                style.removeLayer("smoke-symbol-layer-id");
                style.removeLayer("dayhaze-symbol-layer-id");
                style.removeLayer("dust-symbol-layer-id");
                style.removeLayer("fog-symbol-layer-id");
                style.removeLayer("cloudgust-symbol-layer-id");
                style.removeLayer("tornado-symbol-layer-id");
                style.removeLayer("daysunny-symbol-layer-id");
                style.removeLayer("cloudy-symbol-layer-id");
                style.removeLayer("cloudy801-symbol-layer-id");
                style.removeLayer("cloudy802-symbol-layer-id");
                style.removeLayer("rainynight-symbol-layer-id");
                style.removeLayer("clearmoon-symbol-layer-id");
                style.removeLayer("cloudynight-symbol-layer-id");
                style.removeLayer("rainynight-symbol-layer-id");


                style.removeSource("rain-source-id");
                style.removeSource("thunder-source-id");
                style.removeSource("sprinkle-source-id");
                style.removeSource("snow-source-id");
                style.removeSource("smoke-source-id");
                style.removeSource("dayhaze-source-id");
                style.removeSource("dust-source-id");
                style.removeSource("fog-source-id");
                style.removeSource("tornado-source-id");
                style.removeSource("daysunny-source-id");
                style.removeSource("cloudy-source-id");
                style.removeSource("cloudy801-source-id");
                style.removeSource("cloudy802-source-id");
                style.removeSource("rainynight-source-id");
                style.removeSource("clearmoon-source-id");
                style.removeSource("rainynight-source-id");
                style.removeSource("cloudynight-source-id");
                style.removeSource("rainynight-source-id");
                style.removeSource("cloudgust-source-id");




            }
        });



    }

    public void AddMarkerstoview(){
        mapboxMap.getStyle(new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                addWeatherSignsfog(style);
                addWeatherSigncloudy(style);
                addWeatherSigndayhaze(style);
                addWeatherSigndust(style);
                addWeatherSignrain(style);
                addWeatherSignsnow(style);
                addWeatherSignsprinkle(style);
                addWeatherSignthunder(style);
                addWeatherSigncloudgust(style);
                addWeatherSignstornado(style);
                addWeatherSigndaysunny(style);
                addWeatherSignsmoke(style);
                addWeatherSigncloudy801(style);
                addWeatherSigncloudy802(style);

                addWeatherSignRainynight(style);
                addWeatherSignclearmoon(style);
                addWeatherSigncloudynight(style);


            }
        });



    }




    @SuppressWarnings("MissingPermission")
    private void enableLocation(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();
            locationComponent.activateLocationComponent(locationComponentActivationOptions);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
            initLocationEngine();


        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }

    }

    @SuppressLint("MissingPermission")

    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();
        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);



    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocation(mapboxMap.getStyle());

            }
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }




    private static class MainActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {


        private final WeakReference<MainActivity> activityWeakReference;

        MainActivityLocationCallback(MainActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }


        @Override
        public void onSuccess(LocationEngineResult result) {
            MainActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }
                activity.originpoint = Point.fromLngLat(result.getLastLocation().getLongitude(),
                        result.getLastLocation().getLatitude());

// Create a Toast which displays the new location's coordinates
                Toast.makeText(activity, String.format(activity.getString(R.string.new_location),
                        String.valueOf(result.getLastLocation().getLatitude()), String.valueOf(result.getLastLocation().getLongitude())),
                        Toast.LENGTH_LONG).show();


// Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            MainActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }



    /*

    public void setImageGenResults(final HashMap<String, Bitmap> imageMap) {
        if (mapboxMap != null) {
            mapboxMap.getStyle().addImages(imageMap);

        }
    }


    public Context getContext() {
        return context;
    }

    */

}




