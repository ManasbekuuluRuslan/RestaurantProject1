package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate createdAt;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE})
    private User user;
    @ManyToMany(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE},mappedBy = "chequeList" )
    private List<MenuItem> menuItemList;

    public Cheque(Long id, double priceAverage, LocalDate createdAt) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
    }
}
