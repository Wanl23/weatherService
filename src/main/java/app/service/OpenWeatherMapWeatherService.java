package app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@PropertySource(value={"classpath:app.properties"})
@Service
public class OpenWeatherMapWeatherService implements WeatherForecast {

    @Value("${openweathermap_key}")
    private static String openweathermap_key;
    private static String url = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    public Map<String, String> getForecast(String city) throws IOException, JSONException {
        String urlParameters = "?q=" + city + "&appid=" + "5db4458c1ed074f12534841cc90cfbeb" + "&units=metric";
        URL urlObj = new URL(url + urlParameters);
        URLConnection connection = urlObj.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.connect();

        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        String response = sb.toString();
        Map<String, String> forecast = new HashMap<>();
        JSONObject json = new JSONObject(response);
        JSONObject main = json.getJSONObject("main");
        forecast.put("t", main.getString("temp"));
        forecast.put("p", main.getString("pressure"));
        forecast.put("h", main.getString("humidity"));
        return forecast;
    }

    public static void main(String[] args) throws IOException, JSONException {
        String urlParameters = "?q=" + "Moscow" + "&appid=" + "5db4458c1ed074f12534841cc90cfbeb" + "&units=metric";
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = url + urlParameters;
        ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JSONObject json = new JSONObject(root.toString());
        JSONObject main = json.getJSONObject("main");
        Map<String, String> forecast = new HashMap<>();
        forecast.put("t", main.getString("temp"));
        forecast.put("p", main.getString("pressure"));
        forecast.put("h", main.getString("humidity"));
        System.out.println(forecast.get("t"));
        System.out.println(forecast.get("p"));
        System.out.println(forecast.get("h"));
        JsonNode name = root.path("name");
        System.out.println(response.getStatusCode());
    }
}
