package ru.byulent.volgogradplaces.entities;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class LocalDB {
    private static final LocalDB ourInstance = new LocalDB();
    private MongoDatabase db;

    public static LocalDB getInstance() {
        return ourInstance;
    }

    private LocalDB() {
        MongoClientURI uri = new MongoClientURI("mongodb://byulent:mobila1@ds163667.mlab.com:63667/vlgplace1");
        MongoClient mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(uri.getDatabase());
    }

    public MongoDatabase getDb() {
        return db;
    }
}
