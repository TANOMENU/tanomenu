package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;
import tanomenu.models.Restaurant;
import tanomenu.repository.RestaurantRepository;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }



    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("restaurant", new Restaurant());
        return "restaurant/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Restaurant restaurant, BindingResult bindingResult, @AuthenticationPrincipal AuthUserDetails userDetails) {
        restaurantRepository.findByCnpj(restaurant.getCnpj())
                .ifPresent(u -> bindingResult.rejectValue("cnpj",
                        "already.exists", "CNPJ já cadastrado"));

        if (bindingResult.hasErrors())
            return "/restaurant/register";

        restaurant.setUserUuid(userDetails.getUUID());
        restaurant = restaurantRepository.save(restaurant);

        return "redirect:/restaurant/profile/" + restaurant.getUuid();
    }
}
