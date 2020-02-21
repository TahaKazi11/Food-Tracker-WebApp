
import java.io.IOException;
import java.io.OutputStream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import org.json.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class Txt_information_spliter {
    private String Restaurant;
    private String File;

    
    
    public Txt_information_spliter(String Restaurant_name,String File_name) {
   
    	this.Restaurant = Restaurant_name;
    	this.File = File_name;
    
    }
   
    public String Search_Restaurant() {
        String line;
        
        try {
			BufferedReader in = new BufferedReader(new FileReader(File));
			System.out.println("File Found");
			System.out.println("ame:"+Restaurant);
			while((line = in.readLine()) != null){
				System.out.println(line);
				if (line.contains("name:"+Restaurant)){
					System.out.println(line);
					line = in.readLine();
					System.out.println(line);
					line = in.readLine();
					System.out.println(line);
				}
//		        String[] var = line.split(":");
		    }
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return null;
    }
    public String get_menu() {
    	
		return null;
    	
    }
    
    public String get_price() {
    	return null;
    }
    
}
