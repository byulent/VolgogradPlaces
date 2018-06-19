package ru.byulent.volgogradplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.byulent.volgogradplaces.adapters.GalleryAdapter;
import ru.byulent.volgogradplaces.comparators.DistanceComparator;
import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.entities.User;

public class GalleryActivity extends AppCompatActivity {

    GridView gallery;
    ProgressBar progressBar;
    GalleryAdapter adapter;
    LocationManager locationManager;
    private ArrayList<Photo> original;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        Location location = getCurrentLocation();
        double latitude = 0, longitude = 0;
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        Intent intent;
        switch (id) {
            case R.id.menu_near:
                ArrayList<Photo> photosNear = new ArrayList<>(original);
                Collections.sort(photosNear, new DistanceComparator(latitude, longitude));
                redrawGallery(photosNear);
                return true;
            case R.id.menu_far:
                ArrayList<Photo> photosFar = new ArrayList<>(original);
                Collections.sort(photosFar, new DistanceComparator(latitude, longitude));
                Collections.reverse(photosFar);
                redrawGallery(photosFar);
                return true;
            case R.id.menu_added_earlier:
                redrawGallery(original);
                return true;
            case R.id.menu_added_later:
                ArrayList<Photo> photosLater = new ArrayList<>(original);
                Collections.reverse(photosLater);
                redrawGallery(photosLater);
                return true;
            case R.id.menu_auth:
                intent = new Intent(GalleryActivity.this, AuthActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_map:
                intent = new Intent(GalleryActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        gallery = findViewById(R.id.gallery);
        progressBar = findViewById(R.id.progressBar);
        if (savedInstanceState != null) {
            adapter = new GalleryAdapter(this, false);
            ArrayList<Photo> items = savedInstanceState.getParcelableArrayList("photos");
            adapter.setImages(items);
            showGallery();
        } else adapter = new GalleryAdapter(this, true);
        gallery.setAdapter(adapter);
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("gallery", "onItemClick");
                Photo photo = (Photo) parent.getItemAtPosition(position);
                Intent intent = new Intent(GalleryActivity.this, ViewPhotoActivity.class);
                intent.putExtra("photo_id", photo.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("photos", adapter.getImages());
        super.onSaveInstanceState(outState);
    }

    public void showGallery() {
//        gallery.setAdapter(new GalleryAdapter(this));
        progressBar.setVisibility(View.GONE);
        gallery.setVisibility(View.VISIBLE);
    }

    private void redrawGallery(ArrayList<Photo> photos) {
        adapter.setImages(photos);
        gallery.setAdapter(adapter);
    }

    private Location getCurrentLocation() {
        Location location = null;
        List<String> matchingProviders = locationManager.getAllProviders();
        for (String provider : matchingProviders) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                break;
            }
        }
        return location;
    }

    public void setOriginal(ArrayList<Photo> original) {
        this.original = original;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        checkAuth(menu);
        return true;
    }

    private void checkAuth(Menu menu){
        MenuItem menuAuth = menu.findItem(R.id.menu_auth);
        MenuItem menuAdd = menu.findItem(R.id.menu_add);
        menuAuth.setVisible(!User.isAuthorized());
        menuAdd.setVisible(User.isAuthorized());
    }
}
