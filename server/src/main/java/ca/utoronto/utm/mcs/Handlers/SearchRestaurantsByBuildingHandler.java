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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class SearchRestaurantsByBuildingHandler implements HttpHandler {

    private MongoClient mongoClient;

    public SearchRestaurantsByBuildingHandler(MongoClient client) {
        this.mongoClient = client;

    }

    public void handle(HttpExchange httpExchange) {
        try {
            if (httpExchange.getRequestMethod().equals("GET")) {
                handleGet(httpExchange);
            }
            else{
                Utils.writeResponse(httpExchange, "", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGet(HttpExchange httpExchange) throws IOException, JSONException {

        /* String body = Utils.convert(httpExchange.getRequestBody());
        Document deserialized = new Document();
        deserialized = deserialized.parse(body);
        String buildingName = deserialized.get("building").toString(); */
        Map<String, String> queryParams = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        String buildingName = queryParams.get("name");

        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("Restaurants");
        BasicDBObject query = new BasicDBObject();
        query.put("Building", buildingName);
        FindIterable<Document> iterable = collection.find(query);
        JSONArray jsonArray = new JSONArray();
        if(iterable.first() != null) {
            for (Document doc: iterable){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", doc.get("Restaurant").toString());
                jsonObject.put("hours", doc.get("Hours of Operation").toString());
                jsonObject.put("location", doc.get("Building").toString());
                jsonObject.put("image", "https://media.discordapp.net/attachments/666763770327990345/679081001019768849/Final_Logo.png?width=571&height=571");
                jsonArray.put(jsonObject);
            }
            Utils.writeResponse(httpExchange, jsonArray.toString(), 200);
        } else {
            Utils.writeResponse(httpExchange, "", 404);
        }
    }
}
