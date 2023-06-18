package peaksoft.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.entity.MenuItem;
import peaksoft.enums.Role;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ChequeRequest {
    private double priceAverage;
    private Role role;
    private List<MenuItem> menuItems;

    public ChequeRequest(double priceAverage, Role role, List<MenuItem> menuItems) {
        this.priceAverage = priceAverage;
        this.role = role;
        this.menuItems = menuItems;
    }
}
