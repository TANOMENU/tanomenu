package tanomenu.models.restaurant;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Address implements Serializable {

    @NotBlank
    private String area;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String city;

    @NotBlank
    private State state;

}
