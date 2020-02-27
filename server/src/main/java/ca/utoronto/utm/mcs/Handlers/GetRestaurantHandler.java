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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class GetRestaurantHandler implements HttpHandler {
    private MongoClient mongoClient;

    public GetRestaurantHandler(MongoClient client) {
        this.mongoClient = client;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        try {
            if (httpExchange.getRequestMethod().equals("GET")) {
                this.handleGet(httpExchange);
            }
            else {
                Utils.writeResponse(httpExchange, "", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleGet(HttpExchange httpExchange) throws JSONException, IOException{
        Map<String, String> queryParams = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        String restrauntName = queryParams.get("name");
        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("Restaurants");
        BasicDBObject query = new BasicDBObject();
        query.put("Restaurant", restrauntName);
        FindIterable<Document> iterable = collection.find(query);
        for(Document doc : iterable) {
            String location = doc.get("Building").toString();
            String hours = doc.get("Hours of Operation").toString();
            Utils.writeResponse(httpExchange, getFinalJSON(restrauntName, hours, location).toString(), 200);
            return;
        }

    }

    private JSONObject getFinalJSON(String name, String location, String hours) throws JSONException
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("menu", new ArrayList()); // TODO actual menu at some point
        jsonObject.put("location", location);
        jsonObject.put("hours", hours);
        jsonObject.put("image", "https://media.discordapp.net/attachments/666763770327990345/679081001019768849/Final_Logo.png?width=571&height=571"); // TODO fix
        return jsonObject;
    }

}
