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
public class OpenWeatherMapWeatherService implements WeatherForecast {

    @Value("${openweathermap_key}")
    public String openweathermap_key;

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
        Map<String, String> forecast = new HashMap<>();
        String urlParameters = "q=" + city + "&appid=" + openweathermap_key + "&units=metric";
        URL urlObj = new URL("https://api.openweathermap.org/data/2.5/weather" + "?" + urlParameters);
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
        JSONObject json = new JSONObject(response);
        JSONObject main = json.getJSONObject("main");
        forecast.put("t", main.getString("temp"));
        forecast.put("p", main.getString("pressure"));
        forecast.put("h", main.getString("humidity"));
        return forecast;
    }
}
