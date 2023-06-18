package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.entity.MenuItem;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class StopListResponse {
    private Long id;
    private String reason;
    private ZonedDateTime date;
    public StopListResponse(Long id, String reason, ZonedDateTime date) {
        this.id = id;
        this.reason = reason;
        this.date = date;
    }
}
