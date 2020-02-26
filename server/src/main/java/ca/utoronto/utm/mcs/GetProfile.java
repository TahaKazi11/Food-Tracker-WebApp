package ca.utoronto.utm.mcs;

import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.BsonArray;

public class GetProfile{

    public void HandleGet(HttpExchange r) throws IOException, JSONException {

        MongoClientURI uri = new MongoClientURI("mongodb://north_building:utmfoodtracker@utmfood-shard-00-00-cyzxf.azure.mongodb.net:27017,utmfood-shard-00-01-cyzxf.azure.mongodb.net:27017,utmfood-shard-00-02-cyzxf.azure.mongodb.net:27017/test?ssl=true&replicaSet=UTMFood-shard-0&authSource=admin&retryWrites=true&w=majority&maxIdleTimeMS=5000");
        MongoClient mongoClient = new MongoClient(uri);

        String DatabaseName = ""; //to be filled
        String collectionName = ""; // to be filled

        
        MongoDatabase dbdata = mongoClient.getDatabase(DatabaseName); 
        MongoCollection collection = dbdata.getCollection(collectionName);

        String body = Utils.convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        String result = "";
        try{
            if (r.getRequestBody().equals("GET")){
                if (deserialized.has("_id")){
                    String value = deserialized.get("_id").toString();
                    FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                    MongoCursor<Document> dbCursor = findIterable.iterator();
                    if(dbCursor.hasNext()){
                        result = dbCursor.next().toJson().toString();
                    }else{
                        r.sendResponseHeaders(404, -1);
                        return;
                    }
                    r.sendResponseHeaders(200, result.getBytes().length);
                    OutputStream os = r.getResponseBody();
                    os.write(result.getBytes());
                    os.close();
                    }
                }
            else{
                r.sendResponseHeaders(400, -1);
                return;
            }
        }catch (Exception e){
            r.sendResponseHeaders(500, -1);
            return;
            }
        }

    }