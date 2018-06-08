package ru.byulent.volgogradplaces.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.byulent.volgogradplaces.entities.LocalDB;
import ru.byulent.volgogradplaces.util.Common;

public class GalleryLoader extends AsyncTask<Void, Void, List<Bitmap>> {
    private final Listener mListener;

    public GalleryLoader(Listener listener) {
        mListener = listener;
    }

    public interface Listener{
        void onImageLoaded(List<Bitmap> bitmaps);
        void onError();
    }

    @Override
    protected List<Bitmap> doInBackground(Void... voids) {
        List<Bitmap> images = new ArrayList<>();
        try {
//            MongoClientURI uri = new MongoClientURI("mongodb://byulent:mobila@ds231549.mlab.com:31549/vlgplace");
//            MongoClient mongoClient = new MongoClient(uri);
//            List<String> databases = mongoClient.getDatabaseNames();
//            for (String db : databases) {5
//                Log.d("db", db);
//            }
//            MongoDatabase db = mongoClient.getDatabase(uri.getDatabase());
            MongoDatabase db = LocalDB.getInstance().getDb();
            MongoCollection<Document> photos = db.getCollection("photos");
            FindIterable<Document> cursor = photos.find();
            for (Document aCursor : cursor) {
                String folder = "http://" + Common.HOST_NAME + aCursor.get("filePhoto", String.class).substring(1);
                Log.d("obj", folder);
                InputStream stream = new URL(folder).openStream();
                images.add(BitmapFactory.decodeStream(stream));
            }
            //Toast.makeText(context, "Succesfully connected to MongoDB", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("mongodb", e.getMessage());
        }
        return images;
    }

    @Override
    protected void onPostExecute(List<Bitmap> bitmaps) {
        mListener.onImageLoaded(bitmaps);
    }
}
