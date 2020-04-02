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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
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

public class PutUser implements HttpHandler{
    private MongoClient mongoClient;
    public PutUser(MongoClient client) {
        this.mongoClient = client;
    }


    public void handle(HttpExchange r) throws IOException{
        try {
            if (r.getRequestMethod().equals("POST")) {
                this.handlePut(r);
            }
            else {
                System.out.println("a");
                Utils.writeResponse(r, "", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        private void handlePut(HttpExchange r) throws JSONException, IOException{
    
            String DatabaseName = "UTMFoodTracker"; //to be filled
            String collectionName = "Users"; // to be filled
    
            
            MongoDatabase dbdata = this.mongoClient.getDatabase(DatabaseName); 
            MongoCollection collection = dbdata.getCollection(collectionName);

            Map<String, String> queryParams = Utils.queryToMap(r.getRequestURI().getQuery());
    
            JSONObject result = new JSONObject();
            try{
                if (!queryParams.get("username").isEmpty() && !queryParams.get("email").isEmpty()
                        && !queryParams.get("password").isEmpty() && !queryParams.get("birth").isEmpty()
                        && !queryParams.get("phone").isEmpty() && !queryParams.get("gender").isEmpty()) {
                    //the user information json format need to be determined
                    JSONObject requestBody = new JSONObject();
                    ArrayList<String> favlist = new ArrayList<>();
                    FindIterable<Document> iterable = collection.find(Filters.eq("email", queryParams.get("email")));
                    if(iterable.first() != null) {
                        Utils.writeResponse(r, "User Found", 400);
                        return;
                    }

                    requestBody.put("name", queryParams.get("username"));
                    requestBody.put("email", queryParams.get("email"));
                    requestBody.put("password", queryParams.get("password"));
                    requestBody.put("birth", queryParams.get("birth"));
                    requestBody.put("gender", queryParams.get("gender"));
                    requestBody.put("phone", queryParams.get("phone"));
                    Document userInfo = Document.parse(requestBody.toString());
                    String encrString = Utils.passEncrypt(userInfo.get("password").toString());
                    userInfo.put("password", encrString);
                    userInfo.put("budget", "0");
                    userInfo.put("private", "no");
                    userInfo.put("fav", favlist);
                    collection.insertOne(userInfo);
                    ObjectId id = (ObjectId)userInfo.get( "_id" );
                    result.put("_id", id.toString());
                    Utils.writeResponse(r, result.toString(), 200);
                } else{
                    Utils.writeResponse(r, "", 400);
                    return;
                }
            } catch (Exception e){
                Utils.writeResponse(r, "", 500);
                return;
                }
            }

        

    }