package ca.utoronto.utm.mcs.Handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.stream.Collectors;

import ca.utoronto.utm.mcs.MongoDBConnector;
import ca.utoronto.utm.mcs.Utility.TextSplitter;
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

public class GetMenuHandler implements HttpHandler {

    private static MongoDBConnector connecter = new MongoDBConnector();
    private String menu_link = "python menu scrapper/Menu_By_Hand.txt";
    private TextSplitter Spliter;

    public GetMenuHandler() {
    }
   
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
    
    
    public static String convert(InputStream inputStream) throws IOException {
    	 
        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream))) {
            return buffer.lines().collect(Collectors.joining(System.lineSeparator()));
	}}


    public void handleGet(HttpExchange r) throws IOException, JSONException{
        /* TODO: Implement this.
           Hint: This is very very similar to the get just make sure to save
                 your result in memory instead of returning a value.*/

        JSONObject response = new JSONObject();
        Boolean firstsetted = false;
        String first = "";
        try{
        String body = convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        if (deserialized.has("Name")){
            first = deserialized.getString("Name");
            firstsetted = true;}
        if (!firstsetted){
            r.sendResponseHeaders(400,-1);
            return;
        }
        }catch (Exception e){
            r.sendResponseHeaders(400,-1);
            e.printStackTrace();
        }


        MongoClient db =  connecter.getMongoDBConnection();

    	MongoDatabase dbdata = db.getDatabase("UTMFoodTracker");

		MongoCollection collection = dbdata.getCollection("Menus");

		Bson filter = Filters.eq("Name", new ObjectId(first));

		FindIterable<Document> findIt = collection.find(filter);

		MongoCursor<Document> mongoCursor = findIt.iterator();

		if (mongoCursor.hasNext()){
			response = new JSONObject(mongoCursor.next().toJson().toString());
        }
        else{
            r.sendResponseHeaders(500,-1);
            return;
        }
        // Spliter = new  TextSplitter(first,menu_link);
        // response = Spliter.Search_Restaurant();
        if (response == null) {
        	r.sendResponseHeaders(400,-1);
        }
        else {
            r.sendResponseHeaders(200,response.toString().getBytes().length);   
        }
        OutputStream os = r.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
      
    }
}

