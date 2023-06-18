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
public class PaginationSubRes {
    private List<SubCategoryResponse> subCategoryResponseList;
    private int pageSize;
    private int currentPage;

    public PaginationSubRes(List<SubCategoryResponse> subCategoryResponseList, int pageSize, int currentPage) {
        this.subCategoryResponseList = subCategoryResponseList;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
