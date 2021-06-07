package tanomenu.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tanomenu.repository.UserRepository;

import java.util.Map;

@Controller
@RequestMapping("/Cardapio")
public class CardapioController {

    @GetMapping
    public String cardapio() {
        return "cardapio";
    }

}
