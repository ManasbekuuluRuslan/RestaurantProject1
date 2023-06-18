package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "cheques")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(generator = "cheque_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cheque_gen",sequenceName = "cheque_seq", allocationSize = 1)
    private Long id;
    private double priceAverage;
    private ZonedDateTime createdAt;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private User user;
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private List<MenuItem> menuItemList;

    public Cheque(Long id, double priceAverage, ZonedDateTime createdAt) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }
}
