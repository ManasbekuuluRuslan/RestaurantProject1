package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "menuItems")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(generator = "menu_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "menu_gen",sequenceName = "menu_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private double price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Restaurant restaurant;
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private List<Cheque> chequeList;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private SubCategory subcategory;
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "menuItem")
    private StopList stopList;

    public MenuItem(Long id, String name, String image, double price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
