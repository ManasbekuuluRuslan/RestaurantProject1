package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_gen",sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH},mappedBy = "category")
    private List<SubCategory> subCategoryList;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
