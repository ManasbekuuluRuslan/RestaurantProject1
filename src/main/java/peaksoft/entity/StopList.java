package peaksoft.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
@Entity
@Table(name = "stopList")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopList {
    @Id
    @GeneratedValue(generator = "stop_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "stop_gen",sequenceName = "stop_seq", allocationSize = 1)
    private Long id;
    private String reason;
    private ZonedDateTime date;
    @OneToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.DETACH})
    private MenuItem menuItem;

    public StopList(Long id, String reason, ZonedDateTime date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
