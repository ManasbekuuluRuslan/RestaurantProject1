package peaksoft.dto.response;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PaginationResponse {
    private List<RestaurantResponse> responseList;
    private int pageSize;
    private int currentPage;

    public PaginationResponse(List<RestaurantResponse> responseList, int pageSize, int currentPage) {
        this.responseList = responseList;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}

