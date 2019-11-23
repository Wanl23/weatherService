package app.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@PropertySource(value={"classpath:app.properties"})
@Service
public class APIXUWeatherService implements WeatherForecast {

    @Value("${apixu_key}")
    private String apixuKey;
    private String url = "https://api.apixu.com/v1/current.json";

    @Override
    public Map<String, String> getForecast(String city) throws IOException, JSONException {
        String urlParameters = "?key=" + "e367dddb707143d6a11182604190207" + "&q=" + city;
        URL urlObj = new URL( url + urlParameters);
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
        JSONObject current = json.getJSONObject("current");
        forecast.put("t", current.getString("temp_c"));
        forecast.put("p", current.getString("pressure_mb"));
        forecast.put("h", current.getString("humidity"));
        return forecast;
    }
}
