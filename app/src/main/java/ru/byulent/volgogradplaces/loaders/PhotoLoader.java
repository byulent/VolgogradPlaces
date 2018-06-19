package ru.byulent.volgogradplaces.loaders;

import android.content.Context;
import android.os.AsyncTask;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;

import ru.byulent.volgogradplaces.ViewPhotoActivity;
import ru.byulent.volgogradplaces.entities.LocalDB;
import ru.byulent.volgogradplaces.entities.Photo;

import static com.mongodb.client.model.Filters.eq;

public class PhotoLoader extends AsyncTask <String, Void, Photo> {
    private Context context;

    public PhotoLoader(Context context){
        this.context = context;
    }

    @Override
    protected Photo doInBackground(String... strings) {
        String id = strings[0];
        MongoDatabase db = LocalDB.getInstance().getDb();
        MongoCollection<Document> photos = db.getCollection("photos");
        Document document = photos.find(eq("_id", new ObjectId(id))).first();
        return new Photo(document);
    }

    @Override
    protected void onPostExecute(Photo photo) {
        ViewPhotoActivity activity = (ViewPhotoActivity) context;
        activity.setPhoto(photo);
    }
}
