package peaksoft.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.entity.MenuItem;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
public class StopListRequest {
    private String reason;
    private ZonedDateTime date;
    public StopListRequest(String reason, ZonedDateTime date) {
        this.reason = reason;
        this.date = date;

    }
}
