package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Entity
@Table(name = "restaurants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    @Id
    @GeneratedValue(generator = "restaurant_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "restaurant_gen",sequenceName = "restaurant_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private String restType;
    private Integer numberOfEmployees;
    private Double service;
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "restaurant")
    private List<User> userList;
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "restaurant")
    private List<MenuItem>menuItemList;
    public Restaurant(Long id, String name, String location,
                      String restType, Integer numberOfEmployees, Double service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
