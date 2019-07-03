package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class ServiceRouter {

    @Autowired
    private APIXUWeatherService apixuWeatherService;
    @Autowired
    private OpenWeatherMapWeatherService openWeatherMapWeatherService;

    public Map<String, String> getForecast(String city, String service) {
        Map<String, String> response = new HashMap<>();

        if (city.isEmpty() || service == null) {
            response.put("t", city.isEmpty()? (service != null ? "Enter city name" : "Enter city name and choose service") : "Choose service");
            return response;
        }

        WeatherForecast weatherForecast = null;
        if (service.equals("apixu")) {
            weatherForecast = apixuWeatherService;
        }
        else if (service.equals("openweathermap")) {
            weatherForecast = openWeatherMapWeatherService;
        }
        response = weatherForecast.getForecast(city);
        if (response.get("t") == null) {
            response.put("t", "Enter correct city name or this city is unknown for service");
        }
        else {
            response.put("city", "Weather in " + city);
            response = getPrettyValues(response);
        }
        return response;
    }

    private Map<String, String> getPrettyValues(Map<String, String> forecast) {
        forecast.put("t", "Temperature: " + forecast.get("t") + " *c");
        forecast.put("h", "Humidity: " + forecast.get("h") + " %");
        forecast.put("p", "Pressure: " + forecast.get("p") + " mb");
        return forecast;
    }
}
