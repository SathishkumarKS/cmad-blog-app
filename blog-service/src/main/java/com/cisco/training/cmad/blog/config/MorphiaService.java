package com.cisco.training.cmad.blog.config;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by satkuppu on 4/23/16.
 */
public class MorphiaService {

    private Morphia morphia;
    private Datastore datastore;

    public MorphiaService(){

        // we us MongoClient to connect the local host (127.0.0.1)
        // (assuming this is where your mongodb instance is running)
        // on port 27017 (the default port)
        MongoClient mongoClient = new MongoClient("127.0.0.1:27017");

        //create a new morphia instance
        this.morphia = new Morphia();
        String databaseName = "blogs";
        this.datastore = morphia.createDatastore(mongoClient, databaseName);
        this.datastore.ensureIndexes();
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public void setMorphia(Morphia morphia) {
        this.morphia = morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

}
