package tanomenu.web.controllers.restaurant;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tanomenu.config.AuthUserDetails;
import tanomenu.core.entity.Restaurant;
import tanomenu.core.entity.restaurant.Product;
import tanomenu.core.repository.ProductRepository;
import tanomenu.core.repository.RestaurantRepository;
import tanomenu.core.storage.StorageService;
import tanomenu.web.dto.ProductDto;
import tanomenu.web.dto.RestaurantRegisterDto;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("restaurant")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    private final StorageService storageService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public RestaurantController(RestaurantRepository restaurantRepository, StorageService storageService, ModelMapper modelMapper, ProductRepository productRepository) {
        this.restaurantRepository = restaurantRepository;
        this.storageService = storageService;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @GetMapping("/{uuid}")
    public String show(@PathVariable String uuid, Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {
        Optional<Restaurant> restaurant;
        try {
            restaurant = restaurantRepository.find(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            restaurant = Optional.empty();
        }

        var products2 = productRepository.findByRestaurant(UUID.fromString(uuid));

        for (Product product: products2) {
            System.out.println(product.getUuid());
        }

        return restaurant.map(r -> {
            model.addAttribute("restaurant", r);

            var products = productRepository.findByRestaurant(r.getUuid());
            var menu = products.stream()
                    .collect(Collectors.groupingBy(Product::getCategory));
            var productsWithImage = products.stream()
                    .filter(p -> !Objects.isNull(p.getImage()))
                    .collect(Collectors.toList());

            model.addAttribute("menu", menu);
            model.addAttribute("gallery", productsWithImage);

            if(userDetails.getUUID().equals(r.getUserUuid())) {
                model.addAttribute("validate", true);
            } else {
                model.addAttribute("validate", false);
            }
            return "restaurant/menu";
        }).orElse("redirect:/");
    }

    @GetMapping("/{uuid}/register")
    public String register(@PathVariable String uuid, Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {

        var restaurant = restaurantRepository.find(UUID.fromString(uuid));

        return restaurant.map(r -> {
            if(r.getUserUuid().equals(userDetails.getUUID())) {
                model.addAttribute("restaurant", r);
                model.addAttribute("productDto", new ProductDto());
                model.addAttribute("url", "/restaurant/" + r.getUuid() + "/register");
                return "restaurant/product/register";
            }
            return "redirect:/";
        }).orElse("redirect:/");
    }

    @PostMapping("/{uuid}/register")
    public String register(@PathVariable String uuid, @AuthenticationPrincipal AuthUserDetails userDetails, @ModelAttribute ProductDto productDto) {
        var restaurant = restaurantRepository.find(UUID.fromString(uuid));

        return restaurant.map(r -> {
            if(r.getUserUuid().equals(userDetails.getUUID())) {
                var image = storageService.save(productDto.getImage());
                var product = modelMapper.map(productDto, Product.class);
                product.setImage(image);
                product.setRestaurantUuid(r.getUuid());
                productRepository.save(product);
                return "redirect:/restaurant/" + uuid;
            }
            return "redirect:/";
        }).orElse("redirect:/");
    }

    @GetMapping("/{uuid}/edit/{productUuid}")
    public String edit(@PathVariable String uuid, @PathVariable String productUuid, Model model, @AuthenticationPrincipal AuthUserDetails userDetails) {

        var restaurant = restaurantRepository.find(UUID.fromString(uuid));

        return restaurant.map(r -> {
            if(r.getUserUuid().equals(userDetails.getUUID())) {
                var product = productRepository.find(UUID.fromString(productUuid));
                var productDto = modelMapper.map(product.get(), ProductDto.class);

                model.addAttribute("productDto", productDto);
                model.addAttribute("restaurant", r);
                model.addAttribute("url", "/restaurant/" + r.getUuid() + "/edit/" + productDto.getUuid());
                model.addAttribute("image", product.get().getImage());
                return "restaurant/product/register";
            }
            return "redirect:/";
        }).orElse("redirect:/");
    }

    @PostMapping("/{uuid}/edit/{productUuid}")
    public String edit(@PathVariable String uuid, @PathVariable String productUuid, Model model, @AuthenticationPrincipal AuthUserDetails userDetails, @ModelAttribute ProductDto productDto) {
        var restaurant = restaurantRepository.find(UUID.fromString(uuid));

        return restaurant.map(r -> {
            if(r.getUserUuid().equals(userDetails.getUUID())) {
                var product = productRepository.find(productDto.getUuid()).get();
                modelMapper.map(productDto, product);

                if (!productDto.getImage().isEmpty()) {
                    var image = (product.getImage() != null)
                            ? storageService.update(product.getImage(), productDto.getImage())
                            : storageService.save(productDto.getImage());
                    product.setImage(image);
                }

                productRepository.update(product.getUuid(), product);
                return "redirect:/restaurant/" + uuid;
            }
            return "redirect:/";
        }).orElse("redirect:/");
    }

    @PostMapping("/{uuid/register")
    public String register(@PathVariable String uuid, Model Model, @ModelAttribute Product product) {
        return "redirect:/";
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
        var restaurantUuid = restaurantRepository.save(restaurant);
        return "redirect:/restaurant/" + restaurantUuid;
    }
}
