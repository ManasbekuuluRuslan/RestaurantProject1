package peaksoft.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MenuItemRequest {
    private String name;
    private String image;
    private double price;
    private String description;
    private boolean isVegetarian;

    public MenuItemRequest(String name, String image, double price, String description, boolean isVegetarian) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
