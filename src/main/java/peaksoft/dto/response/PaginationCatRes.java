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
public class PaginationCatRes {
    private List<CategoryResponse>categoryResponses;
    private int pageSize;
    private int currentPage;

    public PaginationCatRes(List<CategoryResponse> categoryResponses, int pageSize, int currentPage) {
        this.categoryResponses = categoryResponses;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
