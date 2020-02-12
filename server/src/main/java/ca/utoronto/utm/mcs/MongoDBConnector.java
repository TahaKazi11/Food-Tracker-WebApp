package ca.utoronto.utm.mcs;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBConnector {

    public MongoClient getMongoDBConnection() {

        MongoClientURI uri = new MongoClientURI("mongodb://north_building:utmfoodtracker@utmfood-shard-00-00-cyzxf.azure.mongodb.net:27017,utmfood-shard-00-01-cyzxf.azure.mongodb.net:27017,utmfood-shard-00-02-cyzxf.azure.mongodb.net:27017/test?ssl=true&replicaSet=UTMFood-shard-0&authSource=admin&retryWrites=true&w=majority&maxIdleTimeMS=5000");
        MongoClient mongoClient = new MongoClient(uri);
        return mongoClient;

    }
}
