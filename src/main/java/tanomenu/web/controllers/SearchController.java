package tanomenu.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tanomenu.core.repository.RestaurantRepository;

import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
public class SearchController {

    private final RestaurantRepository restaurantRepository;

    public SearchController (RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String q, Model model){
        model.addAttribute("restaurants", restaurantRepository.findByName(q));
        return "search";
    }
}
