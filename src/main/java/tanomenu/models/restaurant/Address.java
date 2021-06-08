package tanomenu.models.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
