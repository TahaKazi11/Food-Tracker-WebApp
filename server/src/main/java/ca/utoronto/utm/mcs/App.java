package ca.utoronto.utm.mcs;

import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class App 
{
    public static void main(String[] args)
    {
        //Establishing a connection to a Mlabs cluster where so far it stores the restaurants and their hours of operations
        String building = "Davis";
        MongoDBConnector connector = new MongoDBConnector();
        MongoClient connection = connector.getMongoDBConnection();
        RetrieveHoursOfOperation result = new RetrieveHoursOfOperation(connection, building);

        server.createContext("/api/getProfile", new GetProfile());
        server.createContext("/api/getProfile", new PutUser());
    }
}
