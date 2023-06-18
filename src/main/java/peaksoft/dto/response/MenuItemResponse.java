package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class MenuItemResponse {
    private Long id;
    private String name;
    private String image;
    private double price;
    private String description;
    private boolean isVegetarian;

    public MenuItemResponse(Long id, String name, String image,
                            double price, String description,
                            boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
