package ru.byulent.volgogradplaces.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import ru.byulent.volgogradplaces.entities.LocalDB;

import static com.mongodb.client.model.Filters.eq;

public class AuthTask extends AsyncTask <String, Void, Boolean> {

    private AuthListener authListener;

    public interface AuthListener {
        void onAuth(Boolean success);
        void onPreAuth();
    }
    
    public AuthTask (AuthListener listener) {
        authListener = listener;
    }
    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String pass = strings[1];
        try {
            MongoDatabase db = LocalDB.getInstance().getDb();
            MongoCollection<Document> users = db.getCollection("users");
            Document document = users.find(eq("local.email", email)).first();
            Document local = document.get("local", Document.class);
            String hash = local.getString("password");
            return BCrypt.checkpw(pass, hash);
        } catch (Exception e) {
            Log.e("mongodb", e.getMessage());
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

    }

    @Override
    protected void onPreExecute() {
        authListener.onPreAuth();
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        authListener.onAuth(aBoolean);
    }
}
