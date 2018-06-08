package ru.byulent.volgogradplaces.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import ru.byulent.volgogradplaces.entities.LocalDB;

public class RegTask extends AsyncTask <String, Void, Boolean> {

    public RegTask(){

    }
    @Override
    protected Boolean doInBackground(String... strings) {
        String email = strings[0];
        String pass = strings[1];
        String name = strings[2];
        String bdate = strings[3];
        String city = strings[4];
        String sex = strings[5];
        try {
            MongoDatabase db = LocalDB.getInstance().getDb();
            MongoCollection<Document> users = db.getCollection("users");
            Document local = new Document("email", email)
                    .append("password", BCrypt.hashpw(pass, BCrypt.gensalt(8)));
            if (!name.isEmpty()) local.append("name", name);
            if (!bdate.isEmpty()) local.append("dateOfBirth", bdate);
            if (!city.isEmpty()) local.append("city", city);
            if (!sex.isEmpty()) local.append("sex", sex);
            Document user = new Document("local", local);
            users.insertOne(user);
            return true;
        } catch (Exception e) {
            Log.e("mongodb", e.getMessage());
        }
        return false;
    }
}
