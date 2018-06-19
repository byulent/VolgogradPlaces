package ru.byulent.volgogradplaces.loaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import ru.byulent.volgogradplaces.entities.LocalDB;
import ru.byulent.volgogradplaces.entities.Photo;
import ru.byulent.volgogradplaces.util.Common;

public class GalleryLoader extends AsyncTask<Void, Void, ArrayList<Photo>> {
    private final Listener mListener;

    public GalleryLoader(Listener listener) {
        mListener = listener;
    }

    public interface Listener{
        void onImageLoaded(ArrayList<Photo> bitmaps);
        void onError();
    }

    @Override
    protected ArrayList<Photo> doInBackground(Void... voids) {
        ArrayList<Photo> images = new ArrayList<>();
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
            FindIterable<Document> cursor = photos.find().sort(Sorts.ascending("_id"));
            for (Document aCursor : cursor) {

                images.add(new Photo(aCursor));
            }
            //Toast.makeText(context, "Succesfully connected to MongoDB", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("mongodb", e.getMessage());
        }
        return images;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        mListener.onImageLoaded(photos);
    }
}
