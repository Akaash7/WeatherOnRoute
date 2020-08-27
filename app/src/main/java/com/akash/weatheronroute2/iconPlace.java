package com.akash.weatheronroute2;


import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class iconPlace {



}
/** List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
 symbolLayerIconFeatureList.add(Feature.fromGeometry(
 Point.fromLngLat(-57.225365, -33.213144)));
 symbolLayerIconFeatureList.add(Feature.fromGeometry(
 Point.fromLngLat(-54.14164, -33.981818)));
 symbolLayerIconFeatureList.add(Feature.fromGeometry(
 Point.fromLngLat(-56.990533, -30.583266)));

 mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
 .withSource(new GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
 .withImage(ICON_ID, BitmapFactory.decodeResource(
 MainActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))
 .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
 .withProperties(
 iconImage(ICON_ID),
 iconAllowOverlap(true),
 iconIgnorePlacement(true)
 )
 ), new Style.OnStyleLoaded() {
@Override
public void onStyleLoaded(@NonNull Style style) {

enableLocation(style);




}
});
 */


