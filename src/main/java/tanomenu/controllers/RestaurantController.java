package tanomenu.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;
import tanomenu.models.Restaurant;
import tanomenu.models.restaurant.Product;
import tanomenu.models.restaurant.Address;
import tanomenu.models.restaurant.State;
import tanomenu.repository.RestaurantRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
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
            model.addAttribute("gallery", r.getMenu().values()
                    .stream()
                    .flatMap(Collection::stream)
                    .filter(p -> p.getImage() != null)
                    .collect(Collectors.toList())
            );
            return "restaurant/menu";
        }).orElse("redirect:/");
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

    @GetMapping("/populate")
    public String populate(@AuthenticationPrincipal AuthUserDetails userDetails) {
        var petiscos = List.of(
                Product.builder()
                        .name("Filé de mandioca")
                        .price(BigDecimal.valueOf(25.30))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .image("/upload/camarao.jpeg")
                        .build(),
                Product.builder()
                        .name("Batata Frita")
                        .price(BigDecimal.valueOf(19.99))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .image("/upload/pizza.jpeg")
                        .build(),
                Product.builder()
                        .name("Bolinho de Arroz")
                        .price(BigDecimal.valueOf(10.89))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .image("/upload/almodenga.jpeg")
                        .build()
        );

        var bebidas = List.of(
                Product.builder()
                        .name("Suco de Laranja")
                        .price(BigDecimal.valueOf(5.99))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .build(),
                Product.builder()
                        .name("Suco de abacaxi")
                        .price(BigDecimal.valueOf(6.99))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .build(),
                Product.builder()
                        .name("Guaraná Antártica")
                        .price(BigDecimal.valueOf(9.45))
                        .description("Descrição dos ingredientes, preparos, receita, etc")
                        .build()
        );

        var restaurant = restaurantRepository.save(Restaurant.builder()
                .userUuid(userDetails.getUUID())
                .companyName("Rede Toca do Urso LTDA")
                .tradeName("Restaurante Toca do Urso")
                .cnpj("90.979.500/0001-00")
                .phone("+551231567001")
                .address(Address.builder()
                    .area("Rua dos bobos, 0")
                    .neighborhood("Vizinhança desconhecida")
                    .postalCode("12620-000")
                    .city("Piquete")
                    .state(State.SAO_PAULO)
                    .build())
                .menu(Map.of(
                        "petiscos", petiscos,
                        "bebidas", bebidas
                ))
                .build());

        return "redirect:/";
    }
}
