package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(generator = "sub_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sub_gen",sequenceName = "sub_seq", allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private Category category;
    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "subcategory")
    private List<MenuItem> menuItemList;

    public SubCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
