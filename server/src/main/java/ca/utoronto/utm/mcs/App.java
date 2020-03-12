package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

import ca.utoronto.utm.mcs.Handlers.GetMenuHandler;
import ca.utoronto.utm.mcs.Handlers.GetRestaurantsHandler;
import ca.utoronto.utm.mcs.Handlers.GetRestaurantHandler;
import ca.utoronto.utm.mcs.Handlers.SetBudgetHandler;
import ca.utoronto.utm.mcs.Handlers.SubtractFromBudgetHandler;
import ca.utoronto.utm.mcs.Handlers.SearchRestaurantsByBuildingHandler;
import com.mongodb.*;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.*;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        MongoDBConnector connector = new MongoDBConnector();
        MongoClient connection = connector.getMongoDBConnection();
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.createContext("/getRestaurants", new GetRestaurantsHandler(connection)); //Should be depenedecy injected
        server.createContext("/full-info/by-restaurant-id", new GetRestaurantHandler(connection)); //Should be depenedecy injected
        server.createContext("/api/getProfile", new GetProfile(connection));
        server.createContext("/api/addUser", new PutUser(connection));
        server.createContext("/api/login", new Login(connection));
        server.createContext("/menu/by-restaurant", new GetMenuHandler(connection));
        server.createContext("/setBudget", new SetBudgetHandler(connection));
        server.createContext("/subtractFromBudget", new SubtractFromBudgetHandler(connection));
        server.createContext("/findRestaurantsByBuilding", new SearchRestaurantsByBuildingHandler(connection));
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);

        //RetrieveHoursOfOperation result = new RetrieveHoursOfOperation(connection, building);

    }
}
