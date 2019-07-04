package app.service;

import org.json.JSONException;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Map;

@Component
public interface WeatherForecast {

    Map<String, String> getForecast(String city) throws IOException, JSONException;
}
