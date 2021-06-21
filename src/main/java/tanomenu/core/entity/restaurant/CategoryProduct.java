package tanomenu.core.entity.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryProduct {
    coffe("Café"),
    pizza("Pizza"),
    candy("Doces"),
    japonese("Japonês"),
    lunch("Lanche"),
    savory("Salgados"),
    Other("Outros");

    private final String value;
}
