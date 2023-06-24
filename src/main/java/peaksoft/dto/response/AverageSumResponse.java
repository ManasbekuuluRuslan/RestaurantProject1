package peaksoft.dto.response;

import lombok.Builder;

@Builder
public record AverageSumResponse(int sum) {
    public AverageSumResponse {
    }
}