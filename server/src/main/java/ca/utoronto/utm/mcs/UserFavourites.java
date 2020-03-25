package ca.utoronto.utm.mcs;
import ca.utoronto.utm.mcs.Utility.Utils;
import java.util.Iterator;
import java.util.Map;
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
                System.out.println("a");
                this.handleGet(r);
            }
            else if (r.getRequestMethod().equals("PUT")) {
                System.out.println("b");
                this.handlePut(r);
            }
            else {
                System.out.println("c");
                Utils.writeResponse(r, "", 400);
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

        // String body = Utils.convert(r.getRequestBody());
        // JSONObject deserialized = new JSONObject(body);
        Map<String, String> queryParams = Utils.queryToMap(r.getRequestURI().getQuery());

        JSONObject result = new JSONObject();
        Document preresult = new Document();
        Object favlist = new Object();
        try{
            if (!queryParams.get("_id").isEmpty()){
                String value = queryParams.get("_id");
                FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                MongoCursor<Document> dbCursor = findIterable.iterator();
                if(dbCursor.hasNext()){
                    preresult = dbCursor.next();
                    favlist = preresult.get("fav");
                    result.put("fav", favlist);
                }else{
                    Utils.writeResponse(r, "", 404);
                    return;
                }
                Utils.writeResponse(r, result.toString(), 200);
                }
            else{
                Utils.writeResponse(r, "", 400);
                return;
            }
        }catch (Exception e){
            Utils.writeResponse(r, "", 500);
            return;
            }
        }
        public void handlePut(HttpExchange r) throws IOException, JSONException {
            String DatabaseName = "UTMFoodTracker"; //to be filled
            String collectionName = "Users"; // to be filled
    
            
            MongoDatabase dbdata = this.mongoClient.getDatabase(DatabaseName); 
            MongoCollection collection = dbdata.getCollection(collectionName);
    
            Map<String, String> queryParams = Utils.queryToMap(r.getRequestURI().getQuery());

            JSONObject result = new JSONObject();
            Document preresult = new Document();
            Object favlist = new Object();
            try{
                if (!queryParams.get("_id").isEmpty()){
                    String value = queryParams.get("_id");
                    FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                    MongoCursor<Document> dbCursor = findIterable.iterator();
                    if(dbCursor.hasNext()){
                        preresult = dbCursor.next();
                        favlist = preresult.get("fav");
                        result.put("fav", favlist);
                                    BasicDBObject updatedDocument = new BasicDBObject();
            updatedDocument.append("$set", new BasicDBObject().append("budget", budget));
            collection.findOneAndUpdate(iterable.first(), updatedDocument);
                    }else{
                        Utils.writeResponse(r, "", 404);
                        return;
                    }
                    Utils.writeResponse(r, result.toString(), 200);
                    }
                else{
                    Utils.writeResponse(r, "", 400);
                    return;
                }
            }catch (Exception e){
                Utils.writeResponse(r, "", 500);
                return;
                }
            
        }

    }