package ru.byulent.volgogradplaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import ru.byulent.volgogradplaces.entities.LocalDB;
import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.entities.User;
import ru.byulent.volgogradplaces.loaders.GalleryLoader;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GalleryLoader.Listener {

    private static final String MAP_VIEW_BUNDLE_KEY = "hueta";
    private MapView mapView;
    private GoogleMap map;
    private ArrayList<Photo> photos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {
            case R.id.menu_gallery:
                intent = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_auth:
                if (!User.isAuthorized()) {
                    intent = new Intent(MainActivity.this, AuthActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        GalleryLoader loader = new GalleryLoader(this);
        loader.execute();
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        LatLng vlg = new LatLng(48.69917,44.47333);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(vlg, 10));
        if (photos != null) {
            for (Photo photo : photos) {
                LatLng latLng = new LatLng(photo.getLatitude(), photo.getLongitude());
                map.addMarker(new MarkerOptions().position(latLng).title(photo.getTitle()).icon(fromPhoto(photo.getPhoto())));
            }
        }
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onImageLoaded(ArrayList<Photo> bitmaps) {
        photos = bitmaps;
        mapView.getMapAsync(this);
    }

    @Override
    public void onError() {

    }

    private BitmapDescriptor fromPhoto(Bitmap photo){
        View view = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.map_marker, null);
        ImageView imageView = view.findViewById(R.id.imageView3);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), photo);
        roundedBitmapDrawable.setCircular(true);
        imageView.setImageDrawable(roundedBitmapDrawable);
        DisplayMetrics d = getResources().getDisplayMetrics();
        view.measure(d.widthPixels, d.heightPixels);
        view.layout(0, 0, d.widthPixels, d.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
