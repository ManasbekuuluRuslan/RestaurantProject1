package peaksoft.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
public class ChequeRequest {
    private double priceAverage;
    public ChequeRequest(double priceAverage ) {
        this.priceAverage = priceAverage;
    }
}
