package ca.utoronto.utm.mcs.Utility;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static void writeResponse(HttpExchange httpExchange, String response, int code) throws IOException
    {
        httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");

        httpExchange.sendResponseHeaders(code, response.getBytes().length);

        OutputStream output = httpExchange.getResponseBody();
        output.write(response.getBytes());
        output.flush();
        httpExchange.close();
    }

    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static String convert(InputStream inputStream) throws IOException {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

}
