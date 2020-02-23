import org.json.*;


public class test_only_main { 
	private static String menu_link = "python menu scrapper/Menu_By_Hand.txt";
	private static Txt_information_spliter Spliter;
    public static void main(String args[]) 
    { 
        String first = "Harvey's";
		Spliter = new  Txt_information_spliter(first ,menu_link);
        JSONObject response = Spliter.Search_Restaurant();
    	System.out.println(response);
    } 
} 