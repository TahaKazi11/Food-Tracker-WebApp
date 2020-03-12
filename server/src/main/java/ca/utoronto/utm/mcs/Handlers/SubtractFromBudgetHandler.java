package ca.utoronto.utm.mcs.Handlers;
import ca.utoronto.utm.mcs.Utility.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.bson.Document;
import java.io.IOException;
import java.util.Map;
import org.json.*;

public class SubtractFromBudgetHandler implements HttpHandler {

    private MongoClient mongoClient;

    public SubtractFromBudgetHandler(MongoClient client) {
        this.mongoClient = client;

    }

    public void handle(HttpExchange r) {
        try {
            System.out.println(r.getRequestMethod() + "\n");

            if (r.getRequestMethod().equals("POST")) {
                handlePut(r);
            } else {
                System.out.println("some errors occur\n");
                Utils.writeResponse(r, "", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //front end will give me a user id and a number which is how much they purchased and I will just subtract their
    //budget..Need to think about when budget would reset to original amount..I will send back an alert if their budget goes down
    private void handlePut(HttpExchange httpExchange) throws JSONException, IOException {

        Map<String, String> queryParams = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        /*Document deserialized = new Document();
        deserialized = deserialized.parse(body);*/
        String id = queryParams.get("_id");
        String amount= queryParams.get("amount"); //this is the amount I will receive
        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", new ObjectId(id));
        FindIterable<Document> iterable = collection.find(query);
        if(iterable.first() != null) {
            Float budget = Float.parseFloat(iterable.first().get("budget").toString());
            Float deduction = Float.parseFloat(amount);
            Float finalNumber = budget - deduction;
            BasicDBObject updatedDocument = new BasicDBObject();
            updatedDocument.append("$set", new BasicDBObject().append("budget", finalNumber));
            collection.findOneAndUpdate(iterable.first(), updatedDocument);
            if (finalNumber < 0){
                Utils.writeResponse(httpExchange, getFinalJSON(true).toString(), 200);
            } else {
                Utils.writeResponse(httpExchange, getFinalJSON(false).toString(), 200);
            }
        } else {
            Utils.writeResponse(httpExchange, "", 404);
        }    
    }

    private JSONObject getFinalJSON(Boolean bool) throws JSONException //exceeded will be true when user exceeds their budget
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("exceeded", bool.toString());
        return jsonObject;
    }
}
