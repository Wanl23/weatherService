package app;

import app.service.Router;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private Router router;

    @GetMapping()
    public String mainPage(@RequestParam(name="name", required=false) String name, Model model) {
        model.addAttribute("name", name);
        return "forecast";
    }

    @PostMapping("/get_forecast")
    public String getWeather(@RequestParam(name = "city_name") String city,
                             @RequestParam(name = "service_name") String service,
                             Model model) {
        System.out.println(router.getForecast(city, service));
        return "forecast";
    }
}
