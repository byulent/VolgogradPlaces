package ru.byulent.volgogradplaces.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import ru.byulent.volgogradplaces.GalleryActivity;
import ru.byulent.volgogradplaces.R;
import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.loaders.GalleryLoader;


public class GalleryAdapter extends BaseAdapter implements GalleryLoader.Listener {

//    private int[] images = {R.drawable.one, R.drawable.three, R.drawable.two, R.drawable.four};
    private ArrayList<Photo> images;
    private Context context;
//    private final GalleryLoader.Listener listener = this;

    public GalleryAdapter(Context context){
        this.context = context;
        GalleryLoader loader = new GalleryLoader(this);
        loader.execute();
        setImages(new ArrayList<Photo>());
//        try {
//            images = loader.execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getCount() {
        return getImages().size();
//        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return getImages().get(position);
//        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        float dp = context.getResources().getDisplayMetrics().density;
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(getImages().get(position).getPhoto());
//        imageView.setImageResource(images[position]);
        imageView.setBackgroundResource(R.drawable.image_border);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, (int)(110*dp)));
        //imageView.setAdjustViewBounds(true);
        return imageView;
    }

    @Override
    public void onImageLoaded(ArrayList<Photo> bitmaps) {
        setImages(bitmaps);
        GalleryActivity activity = (GalleryActivity) context;
        activity.showGallery();
    }

    @Override
    public void onError() {

    }

    public ArrayList<Photo> getImages() {
        return images;
    }

    public void setImages(ArrayList<Photo> images) {
        this.images = images;
    }
}
