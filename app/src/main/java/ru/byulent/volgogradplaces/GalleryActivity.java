package ru.byulent.volgogradplaces;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.mongodb.MongoClientURI;

import ru.byulent.volgogradplaces.adapters.GalleryAdapter;
import ru.byulent.volgogradplaces.loaders.GalleryLoader;

public class GalleryActivity extends AppCompatActivity {

    GridView gallery;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        gallery = findViewById(R.id.gallery);
        gallery.setAdapter(new GalleryAdapter(this));
        progressBar = findViewById(R.id.progressBar);
    }

    public void showGallery() {
        progressBar.setVisibility(View.GONE);
        gallery.setVisibility(View.VISIBLE);
    }
}
