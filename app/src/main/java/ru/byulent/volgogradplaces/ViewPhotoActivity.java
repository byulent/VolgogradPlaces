package ru.byulent.volgogradplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.loaders.PhotoLoader;

public class ViewPhotoActivity extends AppCompatActivity {
    private Photo photo;
    private ImageView ivPhoto;
    private TextView tvKeywords;
    private TextView tvSightPage;
    private TextView tvDescription;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);

        ivPhoto = findViewById(R.id.imageView2);
        toolbar = findViewById(R.id.toolbar5);
        tvKeywords = findViewById(R.id.textView14);
        tvSightPage = findViewById(R.id.textView16);
        tvDescription = findViewById(R.id.textView12);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String id = getIntent().getStringExtra("photo_id");
        PhotoLoader loader = new PhotoLoader(this);
        loader.execute(id);
//        photo = getIntent().getParcelableExtra("photo");
//        ivPhoto.setImageBitmap(photo.getPhoto());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
        ivPhoto.setImageBitmap(photo.getPhoto());
        toolbar.setTitle(photo.getTitle());
        tvKeywords.setText(TextUtils.join(", ", photo.getKeywords()));
        tvDescription.setText(photo.getDescription());
    }
}
