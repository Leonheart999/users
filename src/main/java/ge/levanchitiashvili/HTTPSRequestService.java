package ge.levanchitiashvili;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HTTPSRequestService {
    private static final String token = "lk00JseRb2srooBsklve*w))";
    private static final String baseUserUrl="https://api.stackexchange.com/2.3/users";

    public static List<User> getFilteredUsers(){

        Map<String, Object> params = new HashMap<>();
        params.put("site", "stackoverflow");
        params.put("page", 1);
        params.put("pagesize", 100);
        params.put("order", "desc");
        params.put("sort", "reputation");
        params.put("filter", "!6WPIommSM*6B3");
        params.put("key", "sWttOb9L6NNBedLUNI*yvw((");
        params.put("access_token",token);
        List<JSONObject> jsonObjects = getJsoObjectListFromUrl(baseUserUrl, params);
        return jsonObjects.stream().filter(user -> user.has("location")
                &&
                user.getString("location") != null
                &&
                !user.getString("location").isEmpty()
                &&
                (user.getString("location").contains("Moldova")
                        ||
                        user.getString("location").contains("Romania"))
                &&
                user.getInt("reputation") >= 223
                &&
                user.getInt("answer_count") > 0

        ).map(user -> {
            User model = new User();
            model.setUserId(user.getLong("user_id"));
            model.setLocation(user.getString("location"));
            model.setUsername(user.getString("display_name"));
            model.setProfileLink(user.getString("link"));
            model.setAvatar(user.getString("profile_image"));
            model.setAnswerCount(user.getInt("answer_count"));
            model.setQuestionCount(user.getInt("question_count"));
            return model;
        }).peek(user -> user.setTags(getUserTags(user.getUserId()))).filter(user -> user.getTags().contains("JAVA")
                || user.getTags().contains(".NET")
                || user.getTags().contains("DOCKER")
                || user.getTags().contains("C#")).toList();
    }


    public static List<String> getUserTags(Long userId){
        String url = baseUserUrl+"/"+userId+"/tags";
        Map<String, Object> params = new HashMap<>();
        params.put("site", "stackoverflow");
        params.put("page", 1);
        params.put("pagesize", 100);
        params.put("order", "desc");
        params.put("sort", "popular");
        params.put("key", "sWttOb9L6NNBedLUNI*yvw((");
        params.put("access_token",token);
        List<JSONObject> jsonObjects =getJsoObjectListFromUrl(url,params);
        return jsonObjects.stream().map(tag->tag.getString("name").toUpperCase()).toList();
    }

    public static List<JSONObject> getJsoObjectListFromUrl(String url, Map<String, Object> params) {
        try {
            URL finlUrl = buildUriWithParams(url, params);
            HttpURLConnection con = (HttpURLConnection) finlUrl.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 ");
//            con.setRequestProperty("Authorization", "Basic " + "lIxT6X6lZt0cjw7LWkUy6g");
            con.connect();
            int responseCode = con.getResponseCode();
            String message = con.getResponseMessage();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                JSONObject json;
                boolean hasNext;
                if ("gzip".equals(con.getContentEncoding())) {
                    try (InputStreamReader inputStreamReader = new InputStreamReader(new GZIPInputStream(inputStream));
                         BufferedReader in = new BufferedReader(inputStreamReader)) {
                        json = new JSONObject(processResponse(in));
                    }
                } else {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
                        processResponse(in);
                        json = new JSONObject(processResponse(in));
                    }
                }
                JSONArray jsonArray = json.getJSONArray("items");
                List<JSONObject> jsonObjects = new ArrayList<>();
                hasNext = json.getBoolean("has_more");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObjects.add(jsonArray.getJSONObject(i));
                }
                if (hasNext) {
                    params.put("page", (Integer) params.get("page") + 1);
                    jsonObjects.addAll(getJsoObjectListFromUrl(url, params));
                }
                return jsonObjects;
            } else {
                System.out.println("GET request did not work."+ message);
//                throw new RuntimeException("GET request did not work.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static URL buildUriWithParams(String baseURL, Map<String, Object> queryParams) throws MalformedURLException {
        StringBuilder finalUrl = new StringBuilder(baseURL + "?");

        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
                finalUrl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return new URL(finalUrl.toString());
    }

    public static String processResponse(BufferedReader in) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }

}
