package ca.utoronto.utm.mcs;
import ca.utoronto.utm.mcs.Utility.Utils;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

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

public class GetProfile implements HttpHandler{
    private MongoClient mongoClient;
    public GetProfile(MongoClient client) {
        this.mongoClient = client;
    }

    public void handle(HttpExchange r) throws IOException
    {
        try {
            if (r.getRequestMethod().equals("GET")) {
                this.handleGet(r);
            }
            else {
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

        Map<String, String> queryParams = Utils.queryToMap(r.getRequestURI().getQuery());

        String result = "";
        Document preresult = new Document();
        try{
                if (!queryParams.get("_id").isEmpty()){
                    String value = queryParams.get("_id");
                    FindIterable<Document> findIterable = collection.find(Filters.eq("_id", new ObjectId(value)));
                    MongoCursor<Document> dbCursor = findIterable.iterator();
                    if(dbCursor.hasNext()){
                        preresult = dbCursor.next();
                        preresult.remove("_id");
                        preresult.remove("password");
                        preresult.put("_id", value);
                        result = preresult.toJson();
                    }else{
                        Utils.writeResponse(r, "", 404);
                        return;
                    }
                    Utils.writeResponse(r, result, 200);
                } else{
                    Utils.writeResponse(r, "", 400);
                    return;
                }
        }catch (Exception e){
            Utils.writeResponse(r, "", 500);
            return;
            }
        }

    }