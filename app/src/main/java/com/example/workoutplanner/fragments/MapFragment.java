package com.example.workoutplanner.fragments;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.example.workoutplanner.MainActivity;
import com.example.workoutplanner.R;
import com.example.workoutplanner.data.viewModel.PlanViewModel;
import com.example.workoutplanner.databinding.MapFragmentBinding;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;


public class MapFragment extends Fragment {
    private PlanViewModel model;
    private MapFragmentBinding addBinding;
    private MapboxMap map;

    final Point point = Point.fromLngLat(145.045837, -37.876823 );
    public MapFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        addBinding = MapFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(13.0)
                .center(point)
                .build();
        addBinding.mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        addBinding.mapView.getMapboxMap().setCamera(cameraPosition);

        /*
        Reference:https://github.com/mapbox/mapbox-maps-android/issues/916
         */

        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations(addBinding.mapView);
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI,addBinding.mapView);
        Drawable drawable = AppCompatResources.getDrawable(getContext(), R.drawable.red_marker);
        Bitmap myMark = ((BitmapDrawable) drawable).getBitmap();
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(com.mapbox.geojson.Point.fromLngLat(145.045837, -37.876823))
                .withIconImage(myMark);
        pointAnnotationManager.create(pointAnnotationOptions);

        return view;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addBinding = null;
    }
}