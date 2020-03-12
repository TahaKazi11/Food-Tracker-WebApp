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

public class PutUser implements HttpHandler{
    private MongoClient mongoClient;
    public PutUser(MongoClient client) {
        this.mongoClient = client;
    }


    public void handle(HttpExchange r) throws IOException{
        try {
            if (r.getRequestMethod().equals("PUT")) {
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
    
            String body = Utils.convert(r.getRequestBody());
            JSONObject deserialized = new JSONObject(body);
    
            String result = "";
            try{
                if (deserialized.has("name")&&deserialized.has("email")&&deserialized.has("password")&&deserialized.has("birth")&&deserialized.has("phone")&&deserialized.has("gender")&&deserialized.has("private")){
                        //the user information json format need to be determined
                        Document userInfo = Document.parse( body );
                        String encrString = Utils.passEncrypt(userInfo.get("password").toString());
                        userInfo.put("password", encrString);
                        userInfo.put("budget", "0");
                        collection.insertOne(userInfo);
                        ObjectId id = (ObjectId)userInfo.get( "_id" );
                        System.out.println(id);
                        r.sendResponseHeaders(200, id.toString().getBytes().length);
                        OutputStream os = r.getResponseBody();
                        os.write(id.toString().getBytes());
                        os.close();
                        }

                else{
                    System.out.println("b");
                    r.sendResponseHeaders(400, -1);
                    return;
                }
            }catch (Exception e){
                r.sendResponseHeaders(500, -1);
                return;
                }
            }

        

    }