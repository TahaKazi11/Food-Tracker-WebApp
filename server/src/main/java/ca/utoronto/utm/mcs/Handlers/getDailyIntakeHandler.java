package ca.utoronto.utm.mcs.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import ca.utoronto.utm.mcs.MongoDBConnector;
import ca.utoronto.utm.mcs.Utility.TextSplitter;
import ca.utoronto.utm.mcs.Utility.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;
import org.json.*;
import com.mongodb.MongoClient;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.types.ObjectId;

import com.mongodb.client.model.Filters;

public class getDailyIntakeHandler implements HttpHandler{

    private MongoClient mongoClient;

    public getDailyIntakeHandler(MongoClient client) {
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
        String id = queryParams.get("id");

        MongoDatabase database = this.mongoClient.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = database.getCollection("UserHistory");

        BasicDBObject query = new BasicDBObject();
        query.put("userid", id);
        FindIterable<Document> iterable = collection.find(query);
        Document databaseDoc = iterable.first();
        if(databaseDoc == null) {
            Utils.writeResponse(httpExchange, "", 404);
            return;
        }
        JSONObject output = new JSONObject(JSON.serialize(databaseDoc));
        Utils.writeResponse(httpExchange, output.get("dailyIntake").toString() , 200);





    }
}
