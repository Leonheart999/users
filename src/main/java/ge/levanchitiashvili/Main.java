package ge.levanchitiashvili;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://stackoverflow.com/oauth/lIxT6X6lZt0cjw7LWkUy6g");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            con.connect();
            int responseCode = con.getResponseCode();
            String message = con.getResponseMessage();
            int i = 7;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = "https://api.stackexchange.com/2.3/users";
        Map<String, Object> params = new HashMap<>();
        params.put("site", "stackoverflow");
        params.put("page", 1);
        params.put("pagesize", 100);
        params.put("order", "asc");
        params.put("sort", "reputation");
        params.put("filter", "default");
        params.put("key", "U4DMV*8nvpm3EOpvf69Rxw((");
        List<JSONObject> jsonObjects = HTTPSRequestService.getJsoObjectListFromUrl(url, params);
        jsonObjects = jsonObjects.stream().filter(user -> user.has("location")
                &&
                user.getString("location") != null
                &&
                !user.getString("location").isEmpty()
                &&

                (user.getString("location").contains("Moldova")
                        ||
                        user.getString("location").contains("Romania")
                &&
                user.getInt("reputation")>=223

                )
        ).toList();
        System.out.println(jsonObjects);
    }


}