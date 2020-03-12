package ca.utoronto.utm.mcs.Handlers;
import ca.utoronto.utm.mcs.Utility.Utils;
import com.mongodb.MongoClient;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import java.util.Map;
import java.io.IOException;

public class SetBudgetHandler implements HttpHandler{

    //This will be used when user wants to adjust their budget and will simply give us a number..in the request I want
    //user id and the number

    private MongoClient mongoClient;

    public SetBudgetHandler(MongoClient client) {
        this.mongoClient = client;

    }

    public void handle(HttpExchange httpExchange) {
        try {
            if (httpExchange.getRequestMethod().equals("PUT")) {
                handlePut(httpExchange);
            }
            else{ r.sendResponseHeaders(400,-1);}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handlePost(HttpExchange httpExchange) throws IOException{

        Map<String, String> queryParams = Utils.queryToMap(httpExchange.getRequestURI().getQuery());
        String id = queryParams.get("_id");
        String budget = queryParams.get("budget");
        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        query.put("_id", id);
        FindIterable<Document> iterable = collection.find(query);
        if(iterable.first() != null) {
            BasicDBObject updatedDocument = new BasicDBObject();
            updatedDocument.append("$set", new BasicDBObject().append("budget", budget));
            collection.findOneAndUpdate(iterable.first(), updatedDocument);
            Utils.writeResponse(httpExchange, "", 200);
        } else {
            Utils.writeResponse(httpExchange, "", 404);
        }
    }

}
