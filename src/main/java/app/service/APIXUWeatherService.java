package app.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(APIXUWeatherService.class);

    @Value("${apixu_key}")
    public String apixuKey;

    @Override
    public Map<String, String> getForecast(String city) {
        Map<String, String> forecast = new HashMap<>();
        try {
            forecast = sendAndGet(city);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecast;
    }

    private Map<String, String> sendAndGet(String city) throws IOException, JSONException {
        String urlParameters = "key=" + apixuKey + "&q=" + city;
        URL urlObj = new URL(" https://api.apixu.com/v1/current.json" + "?" + urlParameters);
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
