package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class PaginationStopList {
    private List<StopListResponse> stopListResponses;
    private int pageSize;
    private int currentPage;

    public PaginationStopList(List<StopListResponse> stopListResponses, int pageSize, int currentPage) {
        this.stopListResponses = stopListResponses;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
