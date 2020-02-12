package ca.utoronto.utm.mcs;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

public class RetrieveHoursOfOperation {

    public RetrieveHoursOfOperation(MongoClient connection, String buildingName){
        String location = "%s_Restaurants";
        location = String.format(location, buildingName);
        MongoDatabase database = connection.getDatabase("Locations");
        MongoCollection<Document> collection = database.getCollection(location);

        Document myDoc = collection.find().first(); //This is just returning the first document for illustrative purposes
        System.out.printf("%s",myDoc.toJson());
    }
}
