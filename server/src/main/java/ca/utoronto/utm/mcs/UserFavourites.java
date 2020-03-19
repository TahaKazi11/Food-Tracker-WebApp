package ca.utoronto.utm.mcs;
import ca.utoronto.utm.mcs.Utility.Utils;
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

public class UserFavourites implements HttpHandler{
    private MongoClient mongoClient;
    public UserFavourites(MongoClient client) {
        this.mongoClient = client;
    }

    public void handle(HttpExchange r) throws IOException
    {
        try {
            if (r.getRequestMethod().equals("GET")) {
                this.handleGet(r);
            }
            if (r.getRequestMethod().equals("PUT")) {
                this.handlePut(r);
            }
            else {
                r.sendResponseHeaders(400, -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange r) throws IOException, JSONException {
        String DatabaseName = "UTMFoodTracker"; //to be filled
        String collectionName = "Users"; // to be filled

        
        MongoDatabase dbdata = this.mongoClient.getDatabase(DatabaseName); 
        MongoCollection collection = dbdata.getCollection(collectionName);

        String body = Utils.convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        String result = "";
        Document preresult = new Document();
        Object favlist = new Object();
        try{
                if (deserialized.has("_id")){
                    String value = deserialized.get("_id").toString();
                    FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                    MongoCursor<Document> dbCursor = findIterable.iterator();
                    if(dbCursor.hasNext()){
                        preresult = dbCursor.next();
                        favlist = preresult.get("fav");
                        result = favlist.toString();
                    }else{
                        r.sendResponseHeaders(404, -1);
                        return;
                    }
                    r.sendResponseHeaders(200, result.getBytes().length);
                    OutputStream os = r.getResponseBody();
                    os.write(result.getBytes());
                    os.close();
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
        public void handlePut(HttpExchange r) throws IOException, JSONException {
            String DatabaseName = "UTMFoodTracker"; //to be filled
            String collectionName = "Users"; // to be filled
    
            
            MongoDatabase dbdata = this.mongoClient.getDatabase(DatabaseName); 
            MongoCollection collection = dbdata.getCollection(collectionName);
    
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);
    
            String result = "";
            Document preresult = new Document();
            Object favlist = new Object();
            try{
                    if (deserialized.has("_id")){
                        String value = deserialized.get("_id").toString();
                        FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                        MongoCursor<Document> dbCursor = findIterable.iterator();
                        if(dbCursor.hasNext()){
                            // todo
                            preresult = dbCursor.next();
                            favlist = preresult.get("fav");
                            result = favlist.toString();
                        }else{
                            r.sendResponseHeaders(404, -1);
                            return;
                        }
                        r.sendResponseHeaders(200, result.getBytes().length);
                        OutputStream os = r.getResponseBody();
                        os.write(result.getBytes());
                        os.close();
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