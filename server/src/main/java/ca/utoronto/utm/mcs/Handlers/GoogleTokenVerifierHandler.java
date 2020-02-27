package ca.utoronto.utm.mcs.Handlers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.mongodb.MongoClient;
import ca.utoronto.utm.mcs.Utility.Utils;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import java.util.Collections;


public class GoogleTokenVerifierHandler implements HttpHandler {

    MongoClient connection;

    public GoogleTokenVerifierHandler(MongoClient connection){
        this.connection = connection;
    }
    JacksonFactory jacksonFactory = new JacksonFactory();
    private static String CLIENT_ID = "377423863574-557hvra2pn0t186ms49rcd6p423d68nh.apps.googleusercontent.com";
    private static final NetHttpTransport transport = new NetHttpTransport();

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
            // Specify the CLIENT_ID of the app that accesses the backend:
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();


    public void handle(HttpExchange r){
       try{
        if (r.getRequestMethod().equals("POST")){
            handlePost(r); }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Receive idTokenString by HTTPS POST
    public void handlePost(HttpExchange r){
        try {
            String idTokenString = Utils.convert(r.getRequestBody());
            googleIdTokenVerifier(idTokenString);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void googleIdTokenVerifier(String idTokenString) {
        try {
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                //User identifier
                String userId = payload.getSubject();

                // Get profile information from payload
                String email = payload.getEmail();
                if (authorize(connection, userId, email)) {
                    System.out.println("Authorized");
                }

            } else {
                System.out.println("Invalid ID token.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public boolean authorize(MongoClient connection, String userID, String email){

        MongoDatabase DB = connection.getDatabase("UTMFoodTracker");
        MongoCollection<Document> collection = DB.getCollection("Users");

        FindIterable<Document> users = collection.find();

        for (Document user : users){
            String authorizedName = user.getString("username");
            String authorizedEmail = user.getString("email");
            if (email.equalsIgnoreCase(authorizedEmail) && userID.equalsIgnoreCase(authorizedName)){
                return true;
            }
        }
        return false;
    }

}
