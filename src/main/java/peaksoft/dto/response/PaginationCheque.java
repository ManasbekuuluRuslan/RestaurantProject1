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
public class PaginationCheque {
    private List<ChequeResponse> chequeResponseList;
    private int pageSize;
    private int currentPage;

    public PaginationCheque(List<ChequeResponse> chequeResponseList, int pageSize, int currentPage) {
        this.chequeResponseList = chequeResponseList;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }
}
