package ru.byulent.volgogradplaces;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.mongodb.MongoClientURI;

import java.util.ArrayList;

import ru.byulent.volgogradplaces.adapters.GalleryAdapter;
import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.loaders.GalleryLoader;

public class GalleryActivity extends AppCompatActivity {

    GridView gallery;
    ProgressBar progressBar;
    GalleryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        gallery = findViewById(R.id.gallery);
        progressBar = findViewById(R.id.progressBar);
        adapter = new GalleryAdapter(this);
        if (savedInstanceState != null) {
            ArrayList<Photo> items = savedInstanceState.getParcelableArrayList("photos");
            adapter.setImages(items);
            showGallery();
        }
        gallery.setAdapter(adapter);
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
}
