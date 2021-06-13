package tanomenu.web.controllers.restaurant;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.storage.StorageService;
import tanomenu.web.dto.RestaurantRegisterDto;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    private final StorageService storageService;

    private final ModelMapper modelMapper;

    public RestaurantController(RestaurantRepository restaurantRepository, StorageService storageService, ModelMapper modelMapper) {
        this.restaurantRepository = restaurantRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{uuid}")
    public String show(@PathVariable String uuid, Model model) {
        Optional<Restaurant> restaurant;
        try {
            restaurant = restaurantRepository.find(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            restaurant = Optional.empty();
        }

        return restaurant.map(r -> {
            model.addAttribute("restaurant", r);

            if(r.getMenu() != null) {
                model.addAttribute("gallery", r.getMenu().values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(p -> p.getImage() != null)
                        .collect(Collectors.toList())
                );
            }
            return "restaurant/menu";
        }).orElse("redirect:/");
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("restaurantRegisterDto", new RestaurantRegisterDto());
        return "restaurant/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RestaurantRegisterDto restaurantRegisterDto, BindingResult bindingResult, @AuthenticationPrincipal AuthUserDetails userDetails) {
        restaurantRepository.findByCnpj(restaurantRegisterDto.getCnpj())
                .ifPresent(u -> bindingResult.rejectValue("cnpj", "already.exists", "CNPJ já cadastrado"));

        if (bindingResult.hasErrors())
            return "restaurant/register";

        var restaurant = modelMapper.map(restaurantRegisterDto, Restaurant.class);
        restaurant.setUserUuid(userDetails.getUUID());
        var image = storageService.save(restaurantRegisterDto.getImage());
        restaurant.setImage(image);
        restaurant = restaurantRepository.save(restaurant);

        return "redirect:/restaurant/profile/" + restaurant.getUuid();
    }
}
