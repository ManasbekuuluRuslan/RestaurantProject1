package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.entity.MenuItem;

import java.util.List;

@Builder
public record ChequeResponse(Long id,
                             String waiterFullName,
                             List<MenuItem> menuItems,
                             double priceAverage,
                             double service,
                             double grandTotal) {
    public ChequeResponse {
    }

    public ChequeResponse(Long id, double priceAverage) {
        this(id, null, null, priceAverage, 0, 0);
    }
}
