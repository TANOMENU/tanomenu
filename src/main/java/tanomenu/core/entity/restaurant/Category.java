package tanomenu.core.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Category {
    bakehouse("Padarias e Bolarias"),
    coffeeAndTea("Cafés e Chá"),
    BarsAndPubs("Bares e Pubs"),
    iceCreamShop("Sorveterias"),
    FastFood("Fast Food"),
    Steakhouse("Churrascarias"),
    VeganAndVegetarians("Veganos e Vegetarianos"),
    Pizzeria("Pizzaria"),
    Other("Outros");

    private final String value;
}
