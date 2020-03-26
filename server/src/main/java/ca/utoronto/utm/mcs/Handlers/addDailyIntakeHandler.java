package ca.utoronto.utm.mcs.Handlers;

import ca.utoronto.utm.mcs.Utility.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class addDailyIntakeHandler implements HttpHandler {

    private MongoClient mongoClient;


    public addDailyIntakeHandler(MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public void handle(HttpExchange r) {
        try {
            if (r.getRequestMethod().equals("GET")) {
                handleGet(r);
            }
            else{ r.sendResponseHeaders(400,-1);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleGet(HttpExchange httpExchange) throws JSONException, IOException {
        Map<String, String> queryParams = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        String restrauntName = queryParams.get("restaurant");
        String tag = queryParams.get("tag");
        String foodName = queryParams.get("name");
        String id = queryParams.get("id");

        Document doc  = new Document();
        doc.put("restaurant", restrauntName);
        doc.put("tag", tag);
        doc.put("name", foodName);

        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("UserHistory");

        BasicDBObject query = new BasicDBObject();
        query.put("userid", id);
        FindIterable<Document> iterable = collection.find(query);
        Document databaseDoc = iterable.first();
        if(databaseDoc == null) { // Doc doesnt exist, create it
            Document newDoc  = new Document();
            List<Document> intakeList = new ArrayList<Document>();
            intakeList.add(doc);
            newDoc.put("userid", id);
            newDoc.put("dailyIntake", intakeList);
            collection.insertOne(newDoc);
            Utils.writeResponse(httpExchange, "success", 200);
            return;
        } else {
            List<Document> daily = (List<Document>) databaseDoc.get("dailyIntake");
            daily.add(doc);
            Document newDoc  = new Document();
            newDoc.put("userid", id);
            newDoc.put("dailyIntake", daily);
            collection.findOneAndReplace(query, newDoc);
            Utils.writeResponse(httpExchange, "success", 200);
            return;
        }


    }

}
