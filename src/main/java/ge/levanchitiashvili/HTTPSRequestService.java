package ge.levanchitiashvili;


import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.UriBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class HTTPSRequestService {

    public static List<JSONObject> getJsoObjectListFromUrl(String url, Map<String, Object> params) {
        try {
            URI uri = buildUriWithParams(url, params);
            HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            con.setRequestProperty("Authorization", "Basic " + "lIxT6X6lZt0cjw7LWkUy6g");
            con.connect();

            int responseCode = con.getResponseCode();
            String message=con.getResponseMessage();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                JSONObject json;
                boolean hasNext = false;
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
                    params.put("page",(Integer)params.get("page")+1);
                     jsonObjects.addAll(getJsoObjectListFromUrl(url,params));
                }
                return jsonObjects;
            } else {
                System.out.println("GET request did not work.");
//                throw new RuntimeException("GET request did not work.");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static URI buildUriWithParams(String baseURL, Map<String, Object> queryParams) {
        UriBuilder uriBuilder = UriBuilder.fromUri(baseURL);

        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
            uriBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        return uriBuilder.build();
    }

    private static String processResponse(BufferedReader in) throws IOException {
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        return response.toString();
    }
}
