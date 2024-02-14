package ge.levanchitiashvili;


public class Main {
    public static void main(String[] args) {
//        try {
//            String accessTokenUrl = "https://stackoverflow.com/oauth/access_token";
//            String clientId = "28269";
//            String clientSecret = "cBvc75pOW4mgbCF7w1Q7sQ((";
//            String redirectUri = "https://stackexchange.com/oauth/login_success";
//            String authorizationCode = "n8iEEZYmbYmkWV5If*GEow))";
//            Map<String, Object> access = new HashMap<>();
//            access.put("client_id", clientId);
//            access.put("client_secret",clientSecret);
//            access.put("redirect_uri",redirectUri);
//            access.put("code",authorizationCode);
//
//
//            URL url = HTTPSRequestService.buildUriWithParams(accessTokenUrl,access);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Content-Length", "365");
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
//            connection.setDoOutput(true);
//            connection.connect();
//
//            int responseCode = connection.getResponseCode();
//            String message= connection.getResponseMessage();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                InputStream inputStream = connection.getInputStream();
//                JSONObject json;
//                boolean hasNext = false;
//                if ("gzip".equals(connection.getContentEncoding())) {
//                    try (InputStreamReader inputStreamReader = new InputStreamReader(new GZIPInputStream(inputStream));
//                         BufferedReader in = new BufferedReader(inputStreamReader)) {
//                        json = new JSONObject(HTTPSRequestService.processResponse(in));
//                    }
//                } else {
//                    try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
//                        HTTPSRequestService.processResponse(in);
//                        json = new JSONObject(HTTPSRequestService.processResponse(in));
//                    }
//                }
//                token=json.getString("access_token");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        HTTPSRequestService.getFilteredUsers().forEach(user -> System.out.println(user.toString()));

    }


}