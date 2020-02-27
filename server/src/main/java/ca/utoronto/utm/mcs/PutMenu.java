package ca.utoronto.utm.mcs;
import ca.utoronto.utm.mcs.Utility.TextSplitter;
import org.json.*;

public class PutMenu {
	private static MongoDBConnector connecter = new MongoDBConnector();
	private static String res[] = { "Tim_Holtons", "Shawama_The_Meeting_Place", "Rotisserie_The_Food_Court",
			"Pizza_Pizza", "Harvey's" };
	private static String menu_link = "python menu scrapper/Menu_By_Hand.txt";
	private static TextSplitter Spliter;

	public static void main(String args[]) throws JSONException {

		//---------------------------------------------------------------------------------
		// Put part


		// MongoClient db =  connecter.getMongoDBConnection();

    	// MongoDatabase dbdata = db.getDatabase("UTMFoodTracker");

    	// MongoCollection collection = dbdata.getCollection("Menus");

		// for (String i : res) {

		// 	String first = i;
		// 	Spliter = new TextSplitter(first, menu_link);
		// 	JSONObject response = Spliter.Search_Restaurant();
		// 	Document doc = Document.parse( response.toString() );

		// 	 collection.insertOne(doc);
		// 	//  System.out.println(doc);

		//---------------------------------------------------------------------------------
		//Get part


		// MongoClient db =  connecter.getMongoDBConnection();

    	// MongoDatabase dbdata = db.getDatabase("UTMFoodTracker");

		// MongoCollection collection = dbdata.getCollection("Menus");

		// Bson filter = Filters.eq("_id", new ObjectId("5e56c3b33006e4042690dcb7"));

		// FindIterable<Document> findIt = collection.find(filter);

		// MongoCursor<Document> mongoCursor = findIt.iterator();

		// if (mongoCursor.hasNext()){
		// 	System.out.println(mongoCursor.next().toJson().toString());
		// }

		//---------------------------------------------------------------------------------

	

		}
    
} 
