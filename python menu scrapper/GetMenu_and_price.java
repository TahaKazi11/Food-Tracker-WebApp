
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.util.stream.Collectors;

import org.json.*;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class GetMenu_and_price implements HttpHandler {

	private String menu_link = "python menu scrapper/Menu_By_Hand.txt";
	private Txt_information_spliter Spliter;
    public GetMenu_and_price() {
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

        if (deserialized.has("restaurant")){
            first = deserialized.getString("restaurant");
            firstsetted = true;}
        if (!firstsetted){
            r.sendResponseHeaders(400,-1);
            return;
        }
        }catch (Exception e){
            r.sendResponseHeaders(400,-1);
            e.printStackTrace();
        }
        Spliter = new  Txt_information_spliter(first,menu_link);
        response = Spliter.Search_Restaurant();
        OutputStream os = r.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
      
    }
}

