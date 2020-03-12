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
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.BsonArray;

public class Login implements HttpHandler{
    private MongoClient mongoClient;
    public Login(MongoClient client) {
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

        String body = Utils.convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        String result = "";
        Document preresult = new Document();
        try{
                if (deserialized.has("email")&&deserialized.has("password")){
                    String email = deserialized.get("email").toString();
                    String pass = Utils.passEncrypt(deserialized.get("password").toString());
                    FindIterable<Document> findIterable = collection.find(and(eq("email", email), eq("password", pass)));
                    MongoCursor<Document> dbCursor = findIterable.iterator();
                    if(dbCursor.hasNext()){
                        preresult = dbCursor.next();
                        preresult.remove("password");
                        result = preresult.toJson().toString();
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